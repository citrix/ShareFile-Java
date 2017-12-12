package com.citrix.sharefile.api.https.upload;

import com.citrix.sharefile.api.SFApiClient;
import com.citrix.sharefile.api.SFConnectionManager;
import com.citrix.sharefile.api.constants.SFKeywords;
import com.citrix.sharefile.api.exceptions.SFCanceledException;
import com.citrix.sharefile.api.exceptions.SFNotAuthorizedException;
import com.citrix.sharefile.api.exceptions.SFSDKException;
import com.citrix.sharefile.api.exceptions.SFServerException;
import com.citrix.sharefile.api.https.SFCookieManager;
import com.citrix.sharefile.api.https.SFHttpsCaller;
import com.citrix.sharefile.api.log.Logger;
import com.citrix.sharefile.api.models.SFUploadSpecification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HttpsURLConnection;

import static com.citrix.sharefile.api.https.upload.SFUploadRunnable.md5ToString;
import static com.citrix.sharefile.api.https.upload.UploadHelper.closeStream;

/**
 * Created by sai on 2/13/17.
 */

public class MultiThreadedUploadManager {

    private static final String TAG = MultiThreadedUploadManager.class.getSimpleName();
    
    private final String mUsername;
    private final String mPassword;
    private final SFCookieManager mCookieManager;

    private final SFUploadSpecification mSfUploadSpecification;

    private final long mResumeFromByteIndex;

    private final InputStream mFileInputStream;

    private final SFUploadRunnable.IUploadProgress mProgressListener;

    private CountDownLatch mCountDownLatch;

    private final SFApiClient mSFSfApiClient;

    private final AtomicBoolean isLast = new AtomicBoolean(false);

    private static final int chunkSize = 1024 * 1024;

    private final long mFileSize;

    private final AtomicBoolean mCancelRequested;

    private final AtomicInteger threadCount = new AtomicInteger(0);

    private final AtomicLongArray mProgressArray;

    private final AtomicLongArray mResumeArray;

    private long mChunkIndex = 0;

    private final ExceptionHandler mExceptionHandler;

    private final String mLocalFilePath;

    private final long maxChunkIndex;

    private final Lock threadLock;

    public MultiThreadedUploadManager(String username, String password, SFCookieManager sfCookieManager,
                                      SFUploadSpecification sfUploadSpecification, long resumeFromByteIndex, InputStream inputStream,
                                      SFUploadRunnable.IUploadProgress progress, int numThreads, SFApiClient sfApiClient, long fileSize, AtomicBoolean cancelRequested,
                                      String localFilePath) {
        mUsername = username;
        mPassword = password;
        mCookieManager = sfCookieManager;

        mSfUploadSpecification = sfUploadSpecification;
        mResumeFromByteIndex = resumeFromByteIndex;
        mFileInputStream = inputStream;
        mProgressListener = progress;
        mSFSfApiClient = sfApiClient;
        mFileSize = fileSize;
        mCancelRequested = cancelRequested;
        mLocalFilePath = localFilePath;

        mCountDownLatch = new CountDownLatch(numThreads);
        mProgressArray = new AtomicLongArray(numThreads);
        mResumeArray =  new AtomicLongArray(numThreads);

        // Initializing the progress and resume arrays
        for(int i = 0;i < numThreads;i++) {
            mProgressArray.set(i, 0);
            mResumeArray.set(i, 0);
        }

        maxChunkIndex = (fileSize - mResumeFromByteIndex) / chunkSize;

        mExceptionHandler = new ExceptionHandler();
        mExceptionHandler.setException(null);
        threadLock = new ReentrantLock();
    }

    private void finalizeUpload() throws Exception {
        HttpsURLConnection conn;
        conn = (HttpsURLConnection) SFConnectionManager.openConnection(new URL(mSfUploadSpecification.getFinishUri().toString()));

        SFHttpsCaller.addAuthenticationHeader(conn, mSFSfApiClient.getOAuthToken(), mUsername, mPassword, mCookieManager);
        conn.setUseCaches(false);
        conn.setRequestProperty(SFKeywords.CONTENT_TYPE, SFKeywords.APPLICATION_OCTET_STREAM);

        SFHttpsCaller.setPostMethod(conn);
        SFConnectionManager.connect(conn);

        int httpErrorCode = SFHttpsCaller.safeGetResponseCode(conn);

        String responseString;
        switch(httpErrorCode) {
            case HttpsURLConnection.HTTP_OK:
                responseString = SFHttpsCaller.readResponse(conn);
                parseAndCompleteUpload(responseString);
                break;

            case HttpsURLConnection.HTTP_UNAUTHORIZED:
                throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED);
                //break;

            default:
                responseString = SFHttpsCaller.readErrorResponse(conn);
                final SFServerException sfServerException = new SFServerException(httpErrorCode, responseString);
                Logger.e(TAG, "Finish Call  Err Response: " + responseString, sfServerException);
                throw sfServerException;
        }

    }

    private void parseAndCompleteUpload(String responseString) {
        if(responseString != null && !responseString.equalsIgnoreCase("")) {

            Logger.d(TAG,"Server Response on upload complete: " + responseString);

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(responseString);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd").create();

            FinishUpload finishUpload = gson.fromJson(jsonElement, FinishUpload.class);

            if (finishUpload != null) {

                if(finishUpload.error){
                    mProgressListener.onError(new SFSDKException(finishUpload.errorMessage),mFileSize);
                    return;
                }

                FinishUpload.UploadValue uploadValue = finishUpload.getValueList().get(0);
                mProgressListener.onComplete(uploadValue.getSize(), uploadValue.getItemId());
                return;
            }
        }
        mProgressListener.onComplete((mChunkIndex - 1) * chunkSize, "");
    }

    private long getAndIncrementChunkIndex() {
        threadLock.lock();
        long returnChunk = mChunkIndex;
        mChunkIndex += 1;
        if(mChunkIndex > maxChunkIndex) isLast.set(true);

        threadLock.unlock();
        return returnChunk;
    }

    private void workerThreadsDone() throws Exception{
        finalizeUpload();
    }


    public void execute() throws Exception {
        Logger.d(TAG, "MultiThreaded Upload starting...");
        for (int i = 0; i < mCountDownLatch.getCount(); i++) {
            Thread workerThread = new Thread(new WorkerRunnable(mCountDownLatch, mExceptionHandler));
            workerThread.start();
        }

        mCountDownLatch.await();

        if (mExceptionHandler.getException() != null) {
            Logger.e(TAG, "Error Uploading file  :" + mExceptionHandler.getException().getLocalizedMessage(), mExceptionHandler.getException());
            closeStream(mFileInputStream);
            throw mExceptionHandler.getException();
        }

        workerThreadsDone();
        UploadHelper.closeStream(mFileInputStream);
    }



    private class ExceptionHandler {
        private Exception exception;

        public synchronized Exception getException() {
            return exception;
        }

        public synchronized void setException(Exception exception) {
            if (this.exception == null) {
                this.exception = exception;
            }
        }
    }

    private class WorkerRunnable implements Runnable{
        final String md5 = "MD5";

        private final CountDownLatch mCountDownLatch;

        private final int threadNumber;

        private final ExceptionHandler mExceptionHandler;

        public WorkerRunnable(CountDownLatch countDownLatch, ExceptionHandler exceptionHandler) {
            threadNumber = threadCount.incrementAndGet();
            this.mCountDownLatch = countDownLatch;
            this.mExceptionHandler = exceptionHandler;
        }

        @Override
        public void run()  {
            Logger.d(TAG, "Thread " + threadNumber + " starting upload...");
            RandomAccessFile file  = null;
            try {
                file = new RandomAccessFile(mLocalFilePath, "r");

                while (!isLast.get()) {
                    if(mExceptionHandler.getException() != null) {
                        mCountDownLatch.countDown();
                        return;
                    }

                    long index = getAndIncrementChunkIndex();

                    int chunkLength;

                    final MessageDigest md = MessageDigest.getInstance(md5);
                    byte[] fileChunk = new byte[chunkSize];

                    chunkLength = readFromFile(file,fileChunk, index);

                    if (chunkLength < 0) {
                        isLast.set(true);
                        mCountDownLatch.countDown();
                        return;
                    }

                    long byteOffset = (mResumeFromByteIndex) + (index * chunkSize);
                    uploadChunk(fileChunk, chunkLength, md, index, byteOffset);

                    abortIfCancelledRequested();
                }
                mCountDownLatch.countDown();
            }
            catch(Exception e) {
                Logger.e(TAG,e);
                mExceptionHandler.setException(e);
                mCountDownLatch.countDown();
            }
            finally {
                if(file!=null){
                    try {
                        file.close();
                    }
                    catch (Exception e){
                        Logger.e(TAG,e);
                    }
                }
            }
        }

        private int readFromFile(RandomAccessFile file, byte[] fileChunk, long index) throws IOException{
            final long readByteOffset = mResumeFromByteIndex + (index * chunkSize);

            file.seek(readByteOffset);

            return file.read(fileChunk);
        }

        private void uploadChunk(byte[] fileChunk, int chunkLength, MessageDigest md, long index,long byteOffset) throws Exception {
            long bytesUploaded = 0;
            HttpsURLConnection conn = null;
            String responseString;
            int httpErrorCode;
            OutputStream poster = null;

            try {
                md.update(fileChunk, 0, chunkLength);
                
                String append = UploadHelper.getAppendParams(md5ToString(md), index, byteOffset, mFileSize);

                final String finalURL = mSfUploadSpecification.getChunkUri() + append;

                conn = UploadHelper.getChunkUploadConnection(finalURL, mSFSfApiClient, mUsername, mPassword, mCookieManager, chunkLength);
                SFConnectionManager.connect(conn);

                //small buffer between the chunk and the stream so we can interrupt and kill task quickly
                final byte[] buffer = new byte[1024];
                final ByteArrayInputStream in = new ByteArrayInputStream(fileChunk,0,chunkLength);
                int currentBytesRead;
                poster = new DataOutputStream(conn.getOutputStream());

                while((currentBytesRead = in.read(buffer,0,1024)) >0)
                {
                    poster.write(buffer,0,currentBytesRead);
                    bytesUploaded+=(long)currentBytesRead;
                    poster.flush();

                    abortIfCancelledRequested();
                }

                httpErrorCode = SFHttpsCaller.safeGetResponseCode(conn);

                SFHttpsCaller.getAndStoreCookies(conn, new URL(finalURL),mCookieManager);

                switch(httpErrorCode )
                {
                    case HttpsURLConnection.HTTP_OK:
                        updateProgress(index, bytesUploaded, threadNumber - 1);
                        break;

                    case HttpsURLConnection.HTTP_UNAUTHORIZED:
                        throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED);
                        //break;

                    default:
                        responseString = SFHttpsCaller.readErrorResponse(conn);
                        final SFServerException sfServerException = new SFServerException(httpErrorCode, responseString);
                        Logger.e(TAG, "Upload Err Response: " + responseString, sfServerException);
                        throw sfServerException;
                        //break
                }

            }
            finally
            {
                if(poster != null) {
                    poster.close();
                }
                SFHttpsCaller.disconnect(conn);
            }
        }

        private void updateProgress(long chunkIndex, long uploadedBytes, int threadNumber) {
            if(mProgressListener == null) {
                return;
            }

            try {
                mResumeArray.set(threadNumber, chunkIndex);
                mProgressArray.set(threadNumber, mProgressArray.get(threadNumber) + uploadedBytes);

                long totalBytes = mResumeFromByteIndex;
                for (int i = 0; i < mProgressArray.length(); i++) {
                    totalBytes += mProgressArray.get(i);
                }

                long minChunkIndex = getMinChunkIndex(mResumeArray);

                if (totalBytes % (64 * 1024) == 0) {
                    mProgressListener.bytesTransferred(totalBytes, minChunkIndex, mResumeFromByteIndex);
                }
            }
            catch (Exception e) {
                Logger.e(TAG, "Exception updating progress", e);
            }
        }

        private long getMinChunkIndex(AtomicLongArray mResumeArray) {
            long min = Long.MAX_VALUE;

            for(int i =0; i< mResumeArray.length(); i++) {
                final long chunkIndex = mResumeArray.get(i);
                if(chunkIndex < min) {
                    min = chunkIndex;
                }
            }

            return min;
        }

        private void abortIfCancelledRequested() throws SFCanceledException
        {
            if ( mCancelRequested.get() )
            {
                throw new SFCanceledException("Upload Cancelled");
            }
        }
    }
}

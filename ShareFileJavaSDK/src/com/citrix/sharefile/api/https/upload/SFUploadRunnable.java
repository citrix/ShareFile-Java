package com.citrix.sharefile.api.https.upload;

import com.citrix.sharefile.api.SFApiClient;
import com.citrix.sharefile.api.SFConnectionManager;
import com.citrix.sharefile.api.SFSDKDefaultAccessScope;
import com.citrix.sharefile.api.constants.SFKeywords;
import com.citrix.sharefile.api.constants.SFSdkGlobals;
import com.citrix.sharefile.api.enumerations.SFSafeEnum;
import com.citrix.sharefile.api.exceptions.SFCanceledException;
import com.citrix.sharefile.api.exceptions.SFInvalidStateException;
import com.citrix.sharefile.api.exceptions.SFNotAuthorizedException;
import com.citrix.sharefile.api.exceptions.SFOAuthTokenRenewException;
import com.citrix.sharefile.api.exceptions.SFOtherException;
import com.citrix.sharefile.api.exceptions.SFResetUploadException;
import com.citrix.sharefile.api.exceptions.SFSDKException;
import com.citrix.sharefile.api.exceptions.SFServerException;
import com.citrix.sharefile.api.gson.SFGsonHelper;
import com.citrix.sharefile.api.https.SFCookieManager;
import com.citrix.sharefile.api.https.SFHttpsCaller;
import com.citrix.sharefile.api.https.TransferRunnable;
import com.citrix.sharefile.api.interfaces.ISFQuery;
import com.citrix.sharefile.api.log.Logger;
import com.citrix.sharefile.api.models.SFUploadMethod;
import com.citrix.sharefile.api.models.SFUploadRequestParams;
import com.citrix.sharefile.api.models.SFUploadSpecification;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import static com.citrix.sharefile.api.https.upload.UploadHelper.closeStream;

/**
 *
 *  {
 "Method": "Streamed",
 "ChunkUri": "https://storage-ec2-34.sharefile.com/upload-streaming-2.aspx?batchid=723ffb79-d720-4de8-b95d-087be1e6462a&overwrite=true&uploadid=rsu-9c48c04a5fb84f9d8fc93875231b01ca&raw=1&encparams=phT2SuROQY1XrVtmjxwO2yRkHK2uBy1BtAjWhNYAu-lvRQr3eTm-Xh6zYbz3Lc9FfRoDvzBCdI7SyPDVrxL0bR9-cb7IsTFbIcnPRRJ8xLn0nJ0yxIeKXV-m-gb0HWzHGdHOnKTBEEgwD969n-8rbXu6jvkZ3UhEwY6I79GfdTogsf1vi7lFrfPrTe8TV5isPOPSpSINN3qxBI_qWJoy8D_AUnK009vuFHJP4t9yzPi6rDqmfU-7ZhmhncG4rsBfTdEWL3wQiOdU5kNnFl5WkSzjO8dg1vmjSidVSreUN_YJeQw95i8w5XaVA7BGuWDDNXjE8rV53cyQ9p2blo6Xovj4fxQi6wSf0J6CqLKZnFgbHTd7IVvexhdXhTMYAWce01eMBr7iWD9Kd2J3O_nLX3oEWKMDgqCg6SCSd8baO9_EBiQ8QnDwfh8tWeOQq60_gXeX-wBn_TAPNUPTmV1RyD5nt9sn5Q77Rd6YtiOiSjNI98z2mCTSiLp3TYMm4gB38CawObllVnJuWIERHKGbyEwJhE5PNxdMQC4PeM4$",
 "IsResume": false,
 "ResumeIndex": 0,
 "ResumeOffset": 0,
 "ResumeFileHash": "",
 "odata.metadata": "https://nilesh.sf-api.com/sf/v3/$metadata#UploadSpecification/ShareFile.Api.Models.UploadSpecification@Element"
 }
 */
public class SFUploadRunnable extends TransferRunnable
{
    private static final String TAG = "SFUploadRunnable";

    //Server does not localize this error.
    private static final String INVALID_UPLOAD_ID = "Unrecognized Upload ID";

    private final long mResumeFromByteIndex;
    private final long mTotalBytes;
    private final InputStream mFileInputStream;
    private final String mDestinationFileName;

    private final String mDetails;

    //	private final String mParentId;
    private final String mV3Url;
    private final boolean mOverwrite;

    private SFUploadSpecification mUploadSpecification;
    private SFChunkUploadResponse mChunkUploadResponse=null;

    private String localFilePath;

    private static final long sOneMB = 1024 * 1024;

    private boolean mHasMultiThreadedCapability;

    public interface IUploadProgress extends IProgress {
        void bytesTransferred(long byteCount, long chunkIndex, long previousUploadedByteOffset);
    }

    public void setUploadSpec(String previousUploadSpec)
    {
        Gson gson = new Gson();
        mUploadSpecification = gson.fromJson(previousUploadSpec,SFUploadSpecification.class);
    }

    public String getUploadSpec() throws SFNotAuthorizedException, SFOAuthTokenRenewException, SFOtherException, SFInvalidStateException, SFServerException
    {
        if(mUploadSpecification == null)
        {
            mUploadSpecification = getSpecification();
        }

        Gson gson = new Gson();
        return gson.toJson(mUploadSpecification,SFUploadSpecification.class);
    }

    public SFUploadRunnable(
            String v3Url, boolean overwrite,
            long resumeFromByteIndex, long tolalBytes, String destinationName,
            InputStream inputStream, SFApiClient client, IUploadProgress progressListener,
            SFCookieManager cookieManager, String connUserName, String connPassword, String details) {
        super(client,progressListener,cookieManager,connUserName,connPassword);
        mResumeFromByteIndex = resumeFromByteIndex;
        mDestinationFileName = destinationName;
        mTotalBytes = tolalBytes;
        mFileInputStream = inputStream;
        mDetails = details;

        // mParentId = parentId;
        mV3Url = v3Url;
        mOverwrite = overwrite;
    }

    public SFUploadRunnable(
            String v3Url, boolean overwrite,
            long resumeFromByteIndex, long tolalBytes, String destinationName,
            InputStream inputStream, SFApiClient client, IUploadProgress progressListener,
            SFCookieManager cookieManager, String connUserName, String connPassword, String details,
            String localFilePath, boolean hasMultiThreadedCapability) {
        this(v3Url, overwrite, resumeFromByteIndex, tolalBytes, destinationName, inputStream, client, progressListener, cookieManager, connUserName, connPassword, details);
        this.localFilePath = localFilePath;
        mHasMultiThreadedCapability = hasMultiThreadedCapability;
    }

    private void abortIfCancelledRequested() throws SFCanceledException
    {
        if ( cancelRequested.get() )
        {
            throw new SFCanceledException("Upload Cancelled");
        }
    }

    private boolean shouldUseThreadedUpload() {
        if(!mHasMultiThreadedCapability) {
            return false;
        }

        if(mTotalBytes / sOneMB <= 8) {
            return false;
        }
        return true;

    }

    private int getNumberOfThreads() {
        if(mUploadSpecification != null && mUploadSpecification.getMaxNumberOfThreads() != null && mUploadSpecification.getMaxNumberOfThreads() > 0) {
            return mUploadSpecification.getMaxNumberOfThreads();
        }
        return 1;
    }

    protected void runInThisThread() throws SFSDKException
    {
        try
        {
            if(mUploadSpecification == null)
            {
                mUploadSpecification = getSpecification();// get spec
            }

            abortIfCancelledRequested();

			if(shouldUseThreadedUpload()) {
				MultiThreadedUploadManager manager = new MultiThreadedUploadManager(mUsername, mPassword, mCookieManager, mUploadSpecification,
						mResumeFromByteIndex, mFileInputStream, (IUploadProgress) mProgressListener, getNumberOfThreads(), mApiClient, mTotalBytes, cancelRequested, localFilePath);
				manager.execute();
			}
			else {
				upload();
			}

            abortIfCancelledRequested();
        }
        catch (SFSDKException e)
        {
            Logger.e(TAG, e);
            throw e;
        }
        catch(Exception e)
        {
            Logger.e(TAG, e);
            SFOtherException other = new SFOtherException(e);
            throw other;
        }
    }

    private SFUploadSpecification getSpecification() throws SFInvalidStateException,
            SFServerException,
            SFNotAuthorizedException,
            SFOAuthTokenRenewException,
            SFOtherException{
        if(shouldUseThreadedUpload()) {
            return getThreadedSpecification();
        }
        return getStandardSpecification();
    }

    private SFUploadSpecification getStandardSpecification() throws SFInvalidStateException,
            SFServerException,
            SFNotAuthorizedException,
            SFOAuthTokenRenewException,
            SFOtherException
    {
        try
        {
            Date now = new Date();
            ISFQuery<SFUploadSpecification> uploadQuery = mApiClient.items().upload(new URI(mV3Url)
                    ,new SFSafeEnum<SFUploadMethod>(SFUploadMethod.Streamed),
                    true,
                    mDestinationFileName,
                    mTotalBytes,
                    "",
                    false,
                    true,
                    false,
                    false,
                    "SFJavaSDK",
                    mOverwrite,
                    mDestinationFileName,
                    mDetails,
                    false,
                    "",
                    "",
                    1,
                    "json",
                    false, now,now);

            uploadQuery.setCredentials(mUsername,mPassword);

            return mApiClient.executeQuery(uploadQuery);
        }
        catch (URISyntaxException e)
        {
            Logger.e(TAG, e);
        }

        return null;
    }

    private SFUploadSpecification getThreadedSpecification() throws SFInvalidStateException,
            SFServerException,
            SFNotAuthorizedException,
            SFOAuthTokenRenewException,
            SFOtherException {
        try {
            ISFQuery<SFUploadSpecification> uploadQuery = mApiClient.items().upload(new URI(mV3Url),
                    new SFSafeEnum<>(SFUploadMethod.Threaded),
                    true,
                    mDestinationFileName,
                    mTotalBytes,
                    "",
                    false,
                    false,
                    false,
                    false,
                    "SFJavaSDK",
                    true);

            uploadQuery.setCredentials(mUsername, mPassword);

            return mApiClient.executeQuery(uploadQuery);
        }
        catch (URISyntaxException e) {
            Logger.e(TAG, e);
        }
        return null;
    }

    private SFUploadRequestParams buildUploadRequestParams(String fileName,
                                                           String details, long fileSize)
    {
        Date now = new Date();

        SFUploadRequestParams uploadRequestParams = new SFUploadRequestParams();
        uploadRequestParams.setFileName(fileName);
        uploadRequestParams.setClientCreatedDate(now);
        uploadRequestParams.setClientModifiedDate(now);
        uploadRequestParams.setDetails(details);
        uploadRequestParams.setFileSize(fileSize);
        uploadRequestParams.setMethod(new SFSafeEnum<SFUploadMethod>(SFUploadMethod.Streamed));
        uploadRequestParams.setOverwrite(true);
        uploadRequestParams.setThreadCount(1);
        uploadRequestParams.setTitle(fileName);
        uploadRequestParams.setTool("SFV3JAVASDK");
        uploadRequestParams.setRaw(true);

        return uploadRequestParams;
    }

    private void seekInputStream()
    {
        try
        {
            if(mResumeFromByteIndex > 0)
            {
                Logger.d(TAG,"ResumeSupp:Resuming Upload from byte: " + mResumeFromByteIndex);
                mFileInputStream.skip(mResumeFromByteIndex);
            }
            else {
                Logger.d(TAG,"ResumeSupp:Brand new upload");
            }
        }
        catch(Exception e)
        {
            Logger.d(TAG, "Seek exception" , e);
        }
    }



    public static String md5ToString(MessageDigest md)
    {
        StringBuilder hash = new StringBuilder();
        byte digest[] = md.digest();
        String hex;
        for(byte part : digest) {
            hex = Integer.toHexString(0xff & part);
            if(hex.length() == 1)
                hash.append("0");
            hash.append(hex);
        }
        return hash.toString();
    }

    /**
     * Chunk upload response can be of json type sample:
     *
     * //Sample error message {"error":true,"errorMessage":"Thread was being aborted.","errorCode":420}
     *
     */
    public static class SFChunkUploadResponse
    {
        public boolean mWasError;
        public int mErrorCode;
        public String mErrorMessage;
        public int mBytesTransferredInChunk;
        public String mItemId;

        @SFSDKDefaultAccessScope SFChunkUploadResponse(String jsonString, boolean isLastChunk)
        {
            try
            {
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(jsonString);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                mWasError =  SFGsonHelper.getBoolean(jsonObject, "error", false);

                if(mWasError)
                {
                    mErrorMessage = SFGsonHelper.getString(jsonObject, "errorMessage", "");
                    mErrorCode = SFGsonHelper.getInt(jsonObject, "errorCode", 0);
                    Logger.d(TAG, "Parsed Chunk response: " + mErrorMessage);
                }
                // Parse for item id only in the last chunk
                else if (isLastChunk)
                {
                    String value = SFGsonHelper.getString(jsonObject, "value", "");
                    Logger.d(TAG, "Parsed Chunk response: value = " + value);
                    if (value!=null) {
                        JsonArray valueArray = jsonObject.getAsJsonArray("value");
                        if (valueArray != null && valueArray.size() >= 1) {
                            JsonObject firstValue = (JsonObject) valueArray.get(0);
                            mItemId = SFGsonHelper.getString(firstValue, "id", null);
                            Logger.d(TAG, "Parsed Chunk response : item id = " + mItemId);
                        }
                    }
                }

            }
            catch (Exception e)
            {
                Logger.e(TAG,new Exception(jsonString));
                mWasError = true;
                mErrorMessage = "exception parsing upload response";
                mErrorCode = SFSdkGlobals.INTERNAL_HTTP_ERROR;
            }
        }

    }

    /**
     *   This tries to upload a chunk. Returns a detialed object with the httpErrorCode and the ChunkResponse from the server.
     *   ChunkResonse will never be null. In case of http errors or exceptions we fill the chunk response with https err response string.
     */
    private long uploadChunk(byte[] fileChunk,int chunkLength,long index,boolean isLast, MessageDigest md, long previousChunkTotal) throws SFSDKException
    {
        long bytesUploaded = 0;
        HttpsURLConnection conn = null;
        String responseString = null;
        int httpErrorCode;

        try
        {
            //md5 hash buffer
            md.update(fileChunk, 0, chunkLength);

            //you need the RAW param or you'll have to do HTTP multi-part post...
            String append = UploadHelper.getAppendParams(mDestinationFileName, mDetails, mTotalBytes,isLast?1:0, isLast, md5ToString(md),index, previousChunkTotal);
            final String finalURL = mUploadSpecification.getChunkUri() + append;

            conn = UploadHelper.getChunkUploadConnection(finalURL, mApiClient, mUsername, mPassword, mCookieManager, chunkLength);
            SFConnectionManager.connect(conn);

            //small buffer between the chunk and the stream so we can interrupt and kill task quickly
            final byte[] buffer = new byte[1024];
            final ByteArrayInputStream in = new ByteArrayInputStream(fileChunk,0,chunkLength);
            int currentBytesRead;
            OutputStream poster = new DataOutputStream(conn.getOutputStream());

            while((currentBytesRead = in.read(buffer,0,1024)) >0)
            {
                poster.write(buffer,0,currentBytesRead);
                bytesUploaded+=(long)currentBytesRead;
                poster.flush();//needs to be here

                // only send notifications every 64kb
                if ( bytesUploaded % (64*1024) == 0 ) updateProgress(bytesUploaded+previousChunkTotal);

                abortIfCancelledRequested();
            }

            poster.close();

            httpErrorCode = SFHttpsCaller.safeGetResponseCode(conn);

            SFHttpsCaller.getAndStoreCookies(conn, new URL(finalURL),mCookieManager);

            switch(httpErrorCode )
            {
                case HttpsURLConnection.HTTP_OK:
                    responseString = SFHttpsCaller.readResponse(conn);
                    Logger.d(TAG, "Upload Response: " + responseString);

                    mChunkUploadResponse = new SFChunkUploadResponse(responseString, isLast);
                    if(!mChunkUploadResponse.mWasError)
                    {
                        mChunkUploadResponse.mBytesTransferredInChunk = (int) bytesUploaded;
                        mTotalBytesTransferredForThisFile +=bytesUploaded;
                        mItemId = mChunkUploadResponse.mItemId;
                        return bytesUploaded;
                    }
                    else
                    {
                        throw new SFServerException(httpErrorCode,mChunkUploadResponse.mErrorMessage);
                    }
                    //break;

                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED);
                    //break;

                default:
                    responseString = SFHttpsCaller.readErrorResponse(conn);
                    Logger.d(TAG, "Upload Err Response: " + responseString);
                    throw new SFServerException(httpErrorCode,responseString);
                    //break
            }
        }
        catch (SFSDKException e)
        {
            throw e;
        }
        catch(Exception ex)
        {
            Logger.e(TAG,ex);
            SFOtherException other = new SFOtherException(ex);
            throw other;
        }
        finally
        {
            SFHttpsCaller.disconnect(conn);
        }
    }

    private void upload() throws SFSDKException
    {
        long startTime = System.currentTimeMillis();
        int chunkSize = 1024*1024;
        long previousChunkTotalBytes = mResumeFromByteIndex;

        try
        {
            Logger.d(TAG, "POST " + mUploadSpecification.getChunkUri());
            boolean isZeroBytesFile = (mFileInputStream.available() == 0);
            boolean isLast = false;
            seekInputStream();
            int chunkLength;
            final MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] fileChunk = new byte[chunkSize];
            boolean done = false;
            long index = previousChunkTotalBytes/chunkSize;
            while(!done)
            {
                if(isZeroBytesFile)
                {
                    chunkLength = 0;
                    isLast = true;
                    //Don't break here so that we can get a chance to finalize the upload.
                }
                else {
                    chunkLength = mFileInputStream.read(fileChunk, 0, fileChunk.length);
                    if (chunkLength < 0) {
                        Logger.d(TAG, "Chunk < 0: " + chunkLength);
                        done = true;
                        break;
                    }

                    isLast = (mFileInputStream.available() == 0);
                }

                if(isLast)
                {
                    Logger.d(TAG,"isLast = true");
                    done = true;
                }

                previousChunkTotalBytes += uploadChunk(fileChunk,chunkLength, index++,isLast,md,previousChunkTotalBytes);

                abortIfCancelledRequested();

                Thread.yield();
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            Logger.d(TAG, "Streamed Upload Time to upload file " + elapsedTime);
            if(mProgressListener!=null)
            {
                mProgressListener.onComplete(mTotalBytesTransferredForThisFile, mItemId);
            }
        }
        catch (SFServerException e)
        {
            if(e.getLocalizedMessage().contains(INVALID_UPLOAD_ID))
            {
                e = new SFResetUploadException(e);
            }

            throw e;
        }
        catch (SFSDKException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            SFOtherException other = new SFOtherException(ex);
            throw other;
        }
        finally
        {
            closeStream(mFileInputStream);
        }
    }

    private void updateProgress(long uploadedBytes)
    {
        if(mProgressListener == null) {
            return;
        }

        try
        {
            Logger.d(TAG,"ResumeSupp: Bytes Uploaded = " + uploadedBytes);
            mProgressListener.bytesTransferred(uploadedBytes);
        }
        catch(Exception e)
        {
            Logger.d(TAG, "exception update progress", e);
        }
    }
}
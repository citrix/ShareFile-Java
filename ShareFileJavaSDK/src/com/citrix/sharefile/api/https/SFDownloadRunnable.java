package com.citrix.sharefile.api.https;

import com.citrix.sharefile.api.SFApiClient;
import com.citrix.sharefile.api.SFConnectionManager;
import com.citrix.sharefile.api.SFV3ErrorParser;
import com.citrix.sharefile.api.constants.SFKeywords;
import com.citrix.sharefile.api.constants.SFSdkGlobals;
import com.citrix.sharefile.api.enumerations.SFHttpMethod;
import com.citrix.sharefile.api.exceptions.SFCanceledException;
import com.citrix.sharefile.api.exceptions.SFNotAuthorizedException;
import com.citrix.sharefile.api.exceptions.SFOtherException;
import com.citrix.sharefile.api.exceptions.SFSDKException;
import com.citrix.sharefile.api.exceptions.SFServerException;
import com.citrix.sharefile.api.log.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class SFDownloadRunnable extends TransferRunnable {
	private static final String TAG = SFKeywords.TAG + "-FileDownloadRunnable";
	
	private String mUrl;
	private final long mResumeFromByteIndex;
	private final OutputStream mOutputStream;

	// current transfer
	private int httpErrorCode =  SFSdkGlobals.INTERNAL_HTTP_ERROR;
	private String responseString = null;
	private long bytesRead = 0;

	public SFDownloadRunnable(String url,
									 int resumeFromByteIndex, 
									 OutputStream outpuStream, 
									 SFApiClient client,
									 IProgress progressListener,SFCookieManager cookieManager,String connUserName,String connPassword) 
	{
        super(client,progressListener,cookieManager,connUserName,connPassword);
		mUrl = url;
		mResumeFromByteIndex = resumeFromByteIndex;
		mOutputStream = outpuStream;
	}

	/**
	 * execute download in this thread
	 * @return
	 */
    @Override
	protected void runInThisThread() throws SFSDKException
    {
		try
        {
			download();
		}
        catch (SFSDKException e)
        {
            throw e;
        }
        catch(Exception e)
        {
		    throw new SFOtherException(e);
		}
	}

	private void download() throws SFSDKException
    {
		bytesRead = mResumeFromByteIndex;
		
		URLConnection connection = null;
		InputStream fis = null;
		
		try
		{										
			Logger.d(TAG, "GET " + mUrl);
			
			URL url = new URL(mUrl);
			connection = SFConnectionManager.openConnection(url);
			SFHttpsCaller.setMethod(connection, SFHttpMethod.GET.toString(),null);
			SFHttpsCaller.setAcceptLanguage(connection);
			SFHttpsCaller.addAuthenticationHeader(connection,mApiClient.getOAuthToken(),mUsername,mPassword,mCookieManager);
			
			if(mResumeFromByteIndex!=0)
			{
				connection.setRequestProperty(SFKeywords.Range, "bytes="+mResumeFromByteIndex+"-");
			}
																				
			SFConnectionManager.connect(connection);
			
			httpErrorCode = SFHttpsCaller.safeGetResponseCode(connection);			
						
			SFHttpsCaller.getAndStoreCookies(connection, url,mCookieManager);
		    
			switch (httpErrorCode)
			{
                case HttpsURLConnection.HTTP_OK:
                    fis = SFConnectionManager.getInputStream(connection);

                    byte[] buffer = new byte[1024 * 1024];

                    int length;

                    while ((length = fis.read(buffer)) > 0)
                    {
                        if ( isCanceled() )
                        {
                            throw new SFCanceledException("Download Cancelled");
                            //break;
                        }

                        mOutputStream.write(buffer, 0, length);
                        bytesRead+= length;
                        updateProgress(bytesRead);
                        mTotalBytesTransferredForThisFile += length;
                    }

                    if(mProgressListener!=null)
                    {
                        mProgressListener.onComplete(mTotalBytesTransferredForThisFile, null);
                    }
                break;

                case HttpsURLConnection.HTTP_UNAUTHORIZED:
					throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED);
                //break;

                default:
					responseString = SFHttpsCaller.readErrorResponse(connection);
					SFV3ErrorParser sfV3error = new SFV3ErrorParser(httpErrorCode, responseString, null);
                    Logger.d(TAG,"Error " + responseString);
                    throw new SFServerException(httpErrorCode, sfV3error.errorDisplayString());
                //break;
			}
		}
        catch (IOException ex)
        {
            throw new SFOtherException(ex);
        }
        finally
        {
			closeStream(fis);
			closeStream(mOutputStream);
			SFHttpsCaller.disconnect(connection);
		}
	}
	
	private void closeStream(Closeable fis)
	{
		if(fis!=null)
		{
			try 
			{
				fis.close();
			}
			catch (IOException e) 
			{				
				Logger.e(TAG,e);
			}
		}
	}

    private void updateProgress(long downloadedBytes)
	{
		if(mProgressListener == null) {
			return;
		}
		
		try {
			mProgressListener.bytesTransferred(downloadedBytes);
			
		} catch(Exception e) {
			Logger.d(TAG, "exception in updateProgress" , e);
		}		
	}

	public String getUrl() {
		return mUrl;
	}
	
	public boolean isCanceled() {
		return cancelRequested.get();
	}
}
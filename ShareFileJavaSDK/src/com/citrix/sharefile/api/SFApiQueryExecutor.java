package com.citrix.sharefile.api;

import com.citrix.sharefile.api.authentication.SFOAuth2Token;
import com.citrix.sharefile.api.authentication.SFOAuthTokenRenewer;
import com.citrix.sharefile.api.constants.SFFolderID;
import com.citrix.sharefile.api.constants.SFKeywords;
import com.citrix.sharefile.api.enumerations.SFHttpMethod;
import com.citrix.sharefile.api.exceptions.SFConnectionException;
import com.citrix.sharefile.api.exceptions.SFFormsAuthenticationCookies;
import com.citrix.sharefile.api.exceptions.SFInvalidStateException;
import com.citrix.sharefile.api.exceptions.SFNotAuthorizedException;
import com.citrix.sharefile.api.exceptions.SFNotFoundException;
import com.citrix.sharefile.api.exceptions.SFOAuthTokenRenewException;
import com.citrix.sharefile.api.exceptions.SFOtherException;
import com.citrix.sharefile.api.exceptions.SFServerException;
import com.citrix.sharefile.api.https.SFCookieManager;
import com.citrix.sharefile.api.https.SFHttpsCaller;
import com.citrix.sharefile.api.interfaces.ISFApiExecuteQuery;
import com.citrix.sharefile.api.interfaces.ISFApiResultCallback;
import com.citrix.sharefile.api.interfaces.ISFQuery;
import com.citrix.sharefile.api.interfaces.ISFReAuthHandler;
import com.citrix.sharefile.api.log.Logger;
import com.citrix.sharefile.api.models.SFFolder;
import com.citrix.sharefile.api.models.SFODataFeed;
import com.citrix.sharefile.api.models.SFRedirection;
import com.citrix.sharefile.api.models.SFSymbolicLink;
import com.citrix.sharefile.api.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.net.ssl.HttpsURLConnection;

/**
 *  This class provides the bare-minimum functions to make the V3 API server calls and read + parse their responses.
 *  <br>These calls are blocking calls so that the application can use its own thread management.
 *  
 *  <br><br>The calls to be made are in this sequence:<br>
 *  
 *  <b>
 *  <br>executeBlockingQuery();
 *  <br>callresponseListeners();
 *  </b>
 *  
 *  <br><br>Typical usage in Android AsyncTask would be :<br>
 *  
 *  <br>doInBackgrond()
 *  <br>{
 *  <br>	executeBlockingQuery();
 *  <br>}
 *  <br>
 *  <br>onPostExecute()
 *  <br>{
 *  <br>	callresponseListeners();
 *  <br>}
 *  
 */
@SFSDKDefaultAccessScope 
class SFApiQueryExecutor<T> implements ISFApiExecuteQuery
{
	private static final String TAG = SFKeywords.TAG + "-SFApiThread";
    private static final String EMPTY_JSON = "{}";
			
	private final ISFQuery<T> mQuery; 
	private final ISFApiResultCallback<T> mResponseListener;
	private final SFCookieManager mCookieManager;
	private final SFConfiguration mAppSpecificConfig;
	private final SFOAuthTokenRenewer mAccessTokenRenewer;
	private final ISFReAuthHandler mReauthHandler;
	private final SFApiClient mSFApiClient;
    private final SFReAuthContext<T> mReAuthContext;

	public SFApiQueryExecutor(SFApiClient apiClient, ISFQuery<T> query, ISFApiResultCallback<T> responseListener, SFCookieManager cookieManager, SFConfiguration config, SFOAuthTokenRenewer tokenRenewer, ISFReAuthHandler reauthHandler) throws SFInvalidStateException
	{			
		mSFApiClient = apiClient;				
		mQuery = query;
		mResponseListener = responseListener;
		mCookieManager = cookieManager;
		mAppSpecificConfig = config;
		mAccessTokenRenewer = tokenRenewer;
		mReauthHandler = reauthHandler;

        mReAuthContext = new SFReAuthContext<>(query,responseListener,reauthHandler,apiClient);
	}
		
	private void handleHttPost(URLConnection conn) throws IOException
	{
        //Don't do post for GET methods
        if(SFHttpMethod.GET.toString().equalsIgnoreCase(mQuery.getHttpMethod()))
        {
            return;
        }

        //Don't do post for empty DELETE methods
        //Certain Androids are giving errors on POST body.
        //https://jira.ops.expertcity.com/browse/SFAND-2873
        if(SFHttpMethod.DELETE.toString().equalsIgnoreCase(mQuery.getHttpMethod()) && Utils.isEmpty(mQuery.getBody()))
        {
             return;
        }

        String body = mQuery.getBody();

        // On Desktop systems CONTENT_LENGTH is not set by default.
        // Also setting zero content length and not sending anything causes server errors
        // So set an empty JSON.
        //https://community.sharefilesupport.com/citrixsharefile/topics/-content-length
        // NOTE: by this point we have already set the HTTP method on the connection to POST
        //       and all the other verbs: DELETE, PATCH etc are passed as an HTTP_METHOD_OVERRIDE
        //       so this should work on all systems.
        if(Utils.isEmpty(body))
        {
            body = EMPTY_JSON;
        }

        conn.setRequestProperty(SFKeywords.CONTENT_LENGTH, ""+body.getBytes().length);
        conn.setRequestProperty(SFKeywords.CONTENT_TYPE, SFKeywords.APPLICATION_JSON);
        Logger.d(TAG, "Posting body = " + body);
        SFHttpsCaller.postBody(conn, body);
	}			

    private boolean shouldGetInputStream()
    {
        return (mQuery instanceof SFQueryStream);

    }

    private boolean closeTheConnection = true;
    private InputStream getInputStream(URLConnection connection, int httpErrorCode) throws IOException
    {
        // normally, 3xx is redirect
        if (httpErrorCode != HttpsURLConnection.HTTP_OK)
        {
            if (httpErrorCode        == HttpsURLConnection.HTTP_MOVED_TEMP
                    || httpErrorCode == HttpsURLConnection.HTTP_MOVED_PERM
                    || httpErrorCode == HttpsURLConnection.HTTP_SEE_OTHER)
            {
                String newUrl = connection.getHeaderField("Location");

                if(Utils.isEmpty(newUrl))
                {
                    newUrl = connection.getHeaderField("location");
                }

                Logger.d(TAG, "Redirect to: "+ newUrl);

                connection = SFConnectionManager.openConnection(new URL(newUrl));

                SFHttpsCaller.addAuthenticationHeader(connection,
                        mSFApiClient.getOAuthToken(),
                        mQuery.getUserName(),
                        mQuery.getPassword(),
                        mCookieManager);

                SFConnectionManager.connect(connection);

                return SFConnectionManager.getInputStream(connection);
            }
        }

        closeTheConnection = false;
        return SFConnectionManager.getInputStream(connection);
    }

    private void reauthenticate() throws SFNotAuthorizedException, SFOAuthTokenRenewException
    {
        if (mQuery.canReNewTokenInternally())
        {
            if(mAccessTokenRenewer == null)
            {
                throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED, mReAuthContext);
            }
            renewToken();
            logMultipleTokenRenewals();
        }
        else
        {
            if(mReauthHandler != null)
            {
                SFCredential creds = mReauthHandler.getCredentials(mQuery.getLink().toString(),mSFApiClient);
                if(creds!=null && creds.getUserName()!=null && creds.getPassword()!=null)
                {
                    mQuery.setCredentials(creds.getUserName(),creds.getPassword());
                }
            }
            throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED, mReAuthContext);
        }
    }

    /**
        This call has to be synchronized to protect from the OAuthToken renewal problems otherwise
        it nmay happen that two parellel threads invoke this function, receive 401 for ShareFile
        and one of them renews the OAuthToken leaving the other one with a stale copy.
     */
	@Override
	public T executeBlockingQuery() throws SFServerException,
            SFInvalidStateException, SFOAuthTokenRenewException, SFNotAuthorizedException, SFOtherException
    {

        mSFApiClient.validateClientState();

        int httpErrorCode;
        String responseString;
        URLConnection connection = null;

        // Do query under read lock
        mSFApiClient.readWriteLock.readLock().lock();
        String urlstr;
        URL url;
        try {
            String server = mSFApiClient.getOAuthToken().getApiServer();
            urlstr = mQuery.buildQueryUrlString(server);

            setCurrentUri(urlstr);

            url = new URL(urlstr);
            connection = SFConnectionManager.openConnection(url);
            SFHttpsCaller.setMethod(connection, mQuery.getHttpMethod(), mQuery.getBody());
            mAppSpecificConfig.setAddtionalHeaders(connection);

            SFHttpsCaller.addAuthenticationHeader(connection, mSFApiClient.getOAuthToken(),
                    mQuery.getUserName(), mQuery.getPassword(), mCookieManager);

            handleHttPost(connection);

            Logger.d(TAG, mQuery.getHttpMethod() + " " + urlstr);

            SFConnectionManager.connect(connection);

            httpErrorCode = SFHttpsCaller.safeGetResponseCode(connection);

            SFHttpsCaller.getAndStoreCookies(connection, url, mCookieManager);

            if(shouldGetInputStream())
            {
                return (T)getInputStream(connection,httpErrorCode);
            }
        }
        catch (Throwable ex)
        {
            Logger.e(TAG,ex);
            throw new SFOtherException(ex);
        } finally {
            mSFApiClient.readWriteLock.readLock().unlock();
        }

        try {
            switch (httpErrorCode)
            {
                case HttpsURLConnection.HTTP_OK:
                {
                    responseString = SFHttpsCaller.readResponse(connection);
                    mQuery.setStringResponse(responseString);
                    Logger.v(TAG, responseString);

                    T ret = callSuccessResponseParser(responseString);
                    callSaveCredentialsCallback(ret);
                    if(!SFCapabilityService.getInternal().providerCapabilitiesLoaded(urlstr)) {
                        SFCapabilityService.getInternal().getCapabilities(urlstr,mSFApiClient);
                    }
                    return ret;
                }

                case HttpsURLConnection.HTTP_NO_CONTENT:
                {
                    return null;
                }

                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                {
                    Logger.d(TAG, "RESPONSE = AUTH ERROR");

                    callWipeCredentialsCallback();

                    SFFormsAuthenticationCookies formsAuthResponseCookies = SFHttpsCaller.getFormsAuthResponseCookies(url, connection, mCookieManager);
                    if(formsAuthResponseCookies != null) {
                        throw new SFNotAuthorizedException(SFKeywords.UN_AUTHORIZED, formsAuthResponseCookies, mReAuthContext);
                    }
                    // Writelock to prevent use of token while it's being updated, note state before
                    String tokenBefore = mSFApiClient.getOAuthToken().getAccessToken();
                    mSFApiClient.readWriteLock.writeLock().lock();
                    if (! tokenBefore.equals(mSFApiClient.getOAuthToken().getAccessToken())) {
                        // If another thread already refreshed while this thread was waiting
                        return executeBlockingQuery();
                    }
                    try {
                        // Attempt to reauthenticate, if no exception, retry query
                        reauthenticate();
                    } finally {
                        mSFApiClient.readWriteLock.writeLock().unlock();
                    }
                    return executeBlockingQuery();
                }

                /*
                   The ShareFile server doesn't treat 404 correctly. Eg: Try creating a
                   duplicate folder on ShareFile. The resulting http code
                   is ShareFile(404) vs Connectors(409).
                   So attempt to parse the server response String for accurate error message.
                */
                case HttpsURLConnection.HTTP_NOT_FOUND:
                {
                    responseString = SFHttpsCaller.readErrorResponse(connection);
                    Logger.v(TAG, responseString);
                    SFV3ErrorParser sfV3error = new SFV3ErrorParser(httpErrorCode, responseString, null);
                    throw new SFNotFoundException(sfV3error.errorDisplayString());
                }

                default:
                {
                    if(retryDeleteWithPostOverride(httpErrorCode))
                    {
                        mQuery.setBody(EMPTY_JSON);
                        //This wont cause infinite recursion since retryDeleteWithPostOverride returns false for empty String ""
                        //and for the retry we have set the body to and empty json "{}".
                        return executeBlockingQuery();
                    }

                    responseString = SFHttpsCaller.readErrorResponse(connection);
                    Logger.v(TAG, responseString);
                    SFV3ErrorParser sfV3error = new SFV3ErrorParser(httpErrorCode, responseString, null);
                    throw new SFServerException(httpErrorCode,sfV3error.errorDisplayString());
                }
            }
        }
        catch (ConnectException | UnknownHostException ex)
        {
            Logger.e(TAG,ex);
            throw new SFConnectionException(ex);
        }
        catch (SFServerException| SFInvalidStateException |
                SFOAuthTokenRenewException | SFNotAuthorizedException e)
        {
            Logger.e(TAG,e);
            throw e;
        }
        catch (Throwable ex)
        {
            Logger.e(TAG,ex);
            throw new SFOtherException(ex);
        }
        finally
        {
            if(closeTheConnection) {
                SFHttpsCaller.disconnect(connection);
            }
        }
    }


	//On certain connectors , the DELETE verb is not accepted by the server.
    //We need to pass the DELETE as POST override for them.
	private boolean retryDeleteWithPostOverride(int httpErrorCode)
    {
        //Retry with POST only for DELETE methods if we receive BadMethod from server.
        if(httpErrorCode != HttpsURLConnection.HTTP_BAD_METHOD || !SFHttpMethod.DELETE.toString().equalsIgnoreCase(mQuery.getHttpMethod()))
        {
            return false;
        }

        //For non-empty Bodies we anyway use POST.
        if(!Utils.isEmpty(mQuery.getBody()))
        {
            return false;
        }

        //Apply only to non-SF providers. ShareFile server always supports DELETE.
        if(SFProvider.PROVIDER_TYPE_SF.equalsIgnoreCase(SFProvider.getProviderType(mQuery.getLink())))
        {
            return false;
        }

        Logger.d(TAG, "DELETE method not accepted. Use POST override.");

        return true;
    }

    private void callSaveCredentialsCallback(T sfobject)
    {
        if(mReauthHandler == null || sfobject==null)
        {
            return;
        }

        //the auth was success. if the query had credentials, callback the caller to store those creds
        if(!Utils.isEmpty(mQuery.getPassword()))
        {
            try
            {
                mReauthHandler.storeCredentials(new SFCredential(mQuery.getUserName(),
                        mQuery.getPassword()),mQuery.getLink().toString(),mSFApiClient);
            }
            catch (Exception e)
            {
                Logger.e(TAG, "This can be dangerous if the caller cant store the credentials he might get prompted when cookies expire",e);
            }
        }
    }

    private void callWipeCredentialsCallback()
    {
        if(mReauthHandler == null)
        {
            return;
        }

        //the auth was failure. if the query had credentials, callback the caller to wipe those creds.
        if(!Utils.isEmpty(mQuery.getPassword()))
        {
            try
            {
                Logger.d(TAG, "The stored credentials don't work anymore! Wipe them!");
                mReauthHandler.wipeCredentials(mQuery.getLink().toString(),mSFApiClient);
                mQuery.setCredentials(null,null);
            }
            catch (Exception e)
            {
                Logger.e(TAG, "This can be dangerous if the caller cant store the credentials he might get prompted when cookies expire",e);
            }
        }
    }

	private void renewToken() throws SFOAuthTokenRenewException
	{
        Logger.d(TAG, "!!!Trying to renew token");

		if(mAccessTokenRenewer==null)
		{
            Logger.d(TAG, "!!!no token renewer");
			throw new SFOAuthTokenRenewException("No token Re-newer");
		}

        try
        {
            SFOAuth2Token newToken = mAccessTokenRenewer.getNewAccessToken();
            mSFApiClient.storeNewToken(mSFApiClient, newToken);//this might seem redundant but we don't want to create a separate interface
        }
        catch (SFOAuthTokenRenewException e)
        {
            mSFApiClient.tokenRenewFailed(mSFApiClient,e);
            throw e;
        }
	}

    //https://crashlytics.com/citrix2/android/apps/com.citrix.sharefile.mobile.tablet/issues/5486913f65f8dfea154945c8/sessions/54834f7502e400013d029118062ebeab
    private boolean alreadyRenewedToken = false;
    private void logMultipleTokenRenewals() throws SFNotAuthorizedException {
          if(!alreadyRenewedToken)
          {
              alreadyRenewedToken = true;
              return;
          }

        //Token already renewed once before in this query. dump logs
        Logger.e(TAG, "!!Multiple token renewals in same query. Might lead to stack overflow " +
                "\n mCurrentUri =  " + mCurrentUri
                + "\nmLink = " + mQuery.getLink());

        throw new SFNotAuthorizedException("Account not authorized");

    }

	private T executeQueryOnRedirectedObject(SFRedirection redirection) throws
            SFInvalidStateException, SFServerException,
            SFOAuthTokenRenewException, SFOtherException,
            SFNotAuthorizedException
    {
        try
        {
            URI redirectLink = redirection.getUri();
            Logger.d(TAG,"REDIRECT TO: " + redirectLink);
            mQuery.setLinkAndAppendPreviousParameters(redirectLink);
            if(mQuery.getBody() == null && redirection.getBody() != null) {
                JsonParser parser = new JsonParser();
                JsonObject bodyJson = (JsonObject)parser.parse(redirection.getBody());
                mQuery.setBody(bodyJson);
            }
            return executeBlockingQuery();
        }
        catch (NullPointerException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object or Uri is null");
        }
        catch (URISyntaxException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object syntax error");
        }
        catch (UnsupportedEncodingException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object unsupported encoding");
        }
	}

    private URI mCurrentUri = null;

    /**
        Use this to keep track of the current Uri on which the executor made an https call
        that way we can avoid making extra redirection calls.
     */
    private void setCurrentUri(String str)
    {
        try
        {
            mCurrentUri = new URI(str);
            //Want to control max number of redirects here?
        }
        catch(Exception e)
        {
            Logger.e(TAG,e);
        }
    }

    private boolean isNewRedirectionUri(SFRedirection redirection)
    {
        if(redirection == null)
        {
            return false;
        }

        if(mCurrentUri ==null || redirection.getUri()==null)
        {
            return false;
        }

        try
        {
            String currentHost = mCurrentUri.getHost();
            String currentPath = mCurrentUri.getPath();

            String targetHost = redirection.getUri().getHost();
            String targetPath = redirection.getUri().getPath();

            if (currentHost.equalsIgnoreCase(targetHost) && currentPath.equalsIgnoreCase(targetPath))
            {
                Logger.v(TAG, "Don't Redirect. Already fetched response from link " + redirection.getUri());
                return false;
            }
        }
        catch (Exception e)
        {
            Logger.e(TAG, "ZK folder might not show up correctly.",e);
            return false;
        }

        return true;
    }
	
	private SFRedirection getRedirectionObject(T object)
	{
        if((object == null) )
        {
            return null;
        }

		if(!mQuery.reDirectionAllowed())
		{
			return null;
		}

		if(object instanceof SFFolder)
		{
			SFFolder folder = (SFFolder) object;
			
			Boolean hasRemoteChildren = folder.getHasRemoteChildren();
			
			if(hasRemoteChildren!=null && hasRemoteChildren &&
                    isNewRedirectionUri(folder.getRedirection()))
			{					
				return folder.getRedirection();
			}
		}
		else if(object instanceof SFRedirection)
		{
			return (SFRedirection)object;
		}		
		
		return null;
	}


    /**
	 *  If an error happens during parsing the success response, 
	 *  we return the exception description + the original server response in V3Error Object
	 * @throws URISyntaxException 
	 * @throws com.citrix.sharefile.api.exceptions.SFServerException
	 * @throws UnsupportedEncodingException 
	 */
	protected T callSuccessResponseParser(String responseString) throws SFServerException,
            SFInvalidStateException, SFOAuthTokenRenewException, SFNotAuthorizedException, SFOtherException
    {
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement =jsonParser.parse(responseString);
		T sfobject = (T)mSFApiClient.customParse(jsonElement,null);

        SFRedirection redirection = getRedirectionObject(sfobject);

        if(redirection != null)
        {
            return executeQueryOnRedirectedObject(redirection);
        }

        URI link = getShareConnectRedirectLink(sfobject);

        if(link != null)
        {
            return executeQueryForShareConnectRead(link);
        }

        return sfobject;
	}

    private URI getFirstSymlinkChildURI(ArrayList feed)
    {
        if( feed!=null && feed.size()>0 && feed.get(0) instanceof SFSymbolicLink)
        {
            return ((SFSymbolicLink) feed.get(0)).geturl();
        }

        return null;
    }

    private static final String SHARE_CONNECT_ITEMS_QUERY_PART = "/Items("+SFFolderID.CONNECTOR_SHARE_CONNECT+")";


    private URI getShareConnectRedirectLink(T sfobject) throws SFOtherException
    {
        if(!SFHttpMethod.GET.toString().equalsIgnoreCase(mQuery.getHttpMethod()) || mQuery.getLink() == null)
        {
            return null;
        }

        String path = mQuery.getLink().getPath();
        if(path==null || !path.contains(SHARE_CONNECT_ITEMS_QUERY_PART))
        {
            return null;
        }


        if(sfobject instanceof SFFolder)
        {
            SFFolder folder = (SFFolder) sfobject;

            if (SFFolderID.CONNECTOR_SHARE_CONNECT.equalsIgnoreCase(folder.getId()))
            {
                return getFirstSymlinkChildURI(folder.getChildren());
            }

            return null;
        }

        if(sfobject instanceof SFODataFeed)
        {
            URI uri = getFirstSymlinkChildURI(((SFODataFeed) sfobject).getFeed());
            if(uri !=null)
            {
                //For feed type reads the url needs to have /Children in the path.
                path = uri.getPath();
                String uriStr = uri.toString();
                uriStr = uriStr.replace(path,path+"/Children");
                try
                {
                    return new URI(uriStr);
                }
                catch (URISyntaxException e)
                {
                    throw new SFOtherException("Invalid URI");
                }
            }
        }

        return null;
    }

    private T executeQueryForShareConnectRead(URI shareConnectLink) throws
            SFInvalidStateException, SFServerException,
            SFOAuthTokenRenewException, SFOtherException,
            SFNotAuthorizedException
    {
        try
        {
            Logger.d(TAG,"ShareConnect read from: " + shareConnectLink);
            mQuery.setLinkAndAppendPreviousParameters(shareConnectLink);
            return executeBlockingQuery();
        }
        catch (NullPointerException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object or Uri is null");
        }
        catch (URISyntaxException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object syntax error");
        }
        catch (UnsupportedEncodingException e)
        {
            Logger.e(TAG,e);
            throw new SFOtherException("Server Bug: Redirection object unsupported encoding");
        }
    }

    protected ISFApiResultCallback<T> getResponseListener()
	{
		return mResponseListener;
	}			
}
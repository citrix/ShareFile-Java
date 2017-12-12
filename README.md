# ShareFile Client SDK Documentation #

Before continuing please familiarize yourself with the API and it's methodology
at https://api.sharefile.com/rest


License
----
All code is licensed under the [MIT
License](https://github.com/citrix/ShareFile-PowerShell/blob/master/ShareFileSnapIn/LICENSE.txt).

## Definitions ##

* `applicationControlPlane` - Describes the domain that the ShareFile account is available on.  
For example: `sharefile.com`, `securevdr.com`, `sharefile.eu`, etc.
* `authorizationUrl` - The initial url that should be visited to being web authentication.
* `client_id` - The identifier that is uniquely identifies an OAuth client consumer.
* `client_secret` - This is a shared secret that is required to exchange an `OAuthAuthorizationCode` for an `OAuthToken`.
* `completionUri` - Alias for `redirectUri`.  Used primarily in `OAuth2AuthenticationHelper`.
* `OAuthAuthorizationCode` - One-time use code that is returned as part of an oauth `code` grant request.  
We provide a class with the specific properties for this type of response.
* `OAuthToken` - Used to authenticate with ShareFile, specifically using AccessToken - however, this is taken care of for you by the SDK.
* `redirectUri` - Resource that can be used to track when authentication is complete.  Generally, this resource is controlled by the OAuth client consumer.
* `state` - Token created by the OAuth consumer to associate an authorization request
with an authorization response.

## Building the SDK ##
The SDK is a pure Java code and you can build it using the Eclipse IDE (Kepler or higher) or using the Android Studio IDE. Simply point your IDE's import functionality to the SDK folder and it should be able to import the projects correctly. Make sure you have Java-7 JDK atleast.  You could either generate `.jar` files from the SDK and use them in your application projects or directly include the SDK Module in your Android application Project or Eclipse Workspace of the application.

Alternatively you can use the SDK without building the SDK code, if you are using Gradle or Maven, you can directly add the dependency as follows:
(Always check for the latest version [here on mavenCentral](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22sharefile-api%22)   since we keep posting enhancements and bug fixes. Alternatively use 3.+ as the version code in dependencies.)

* Gradle Dependency:

		dependencies {	
		    compile 'com.citrix:sharefile-api:3.1.0'
		}
	
	Gradle on Android might need the addional :

		android {
	    
			//...
		    packagingOptions {
		        exclude 'META-INF/DEPENDENCIES'
		        exclude 'META-INF/LICENSE'
		        exclude 'META-INF/NOTICE'
		    }
			//...
		}


* Maven Dependence:

		    <dependency>
	    		<groupId>com.citrix</groupId>
	    		<artifactId>sharefile-api</artifactId>
	    		<version>3.1.0</version>
    		</dependency>
    	 


The project uses Java-7 source compatibility. So in case you see errors like :

* Error:(87, 46) java: diamond operator is not supported in -source 1.6
  (use -source 7 or higher to enable diamond operator)
* Error:(281, 37) java: multi-catch statement is not supported in -source 1.6
  (use -source 7 or higher to enable multi-catch statement)

To fix these set the follow settings depend on your IDE:

* Android Studio:  `File->Project Structure->Project language Level  to 7.0`

* Eclipse : `File->Properties->JavaCompiler->Compiler Compliance level to 1.7`

## Proguard Settings ##

If you are using pro-guard, make sure to set the following in the configuration file.
    
    	-keepattributes Signature
    	-keepattributes *Annotation*
    	-keep class com.citrix.sharefile.api.** { *; }
    

## Initialise the SDK ##
		
 *	Initializing the SDK can be done as follows:
 
		SFSdk.init("[client_id]","[client_secret]","[redirect_url]");

		
	Optionally implement the ILog interface to read the 
	logs generated when the SDK functions execute
	 	 	
		SFSdk.setLogger(new ILog(){...}); 


## Authentication ##

Authentication with ShareFile v3 API makes use of [OAuth 2.0 protocol](http://api.sharefile.com/rest/oauth2.aspx).
Some helper methods and classes are provided to make authentication easier for consumers.

* **Web Authentication** [To be Done]       

* **Password Authentication**: Requires the consumer perform ShareFile account discovery,
which is not currently documented.  In order to complete this authentication
the consumer will must know `username`, `password`, `subdomain`, and `applicationControlPlane`.  In the sample below,
these are assumed to have been obtained already.

        ISFOAuthService oAuthService = new SFOAuthService();

		SFOAuth2Token authToken = oAuthService.authenticate (subdomain, apiControlPlane, username, password);

	
	Or Asynchronously. Note: all the functions exposed by the SDK which make network
	calls have a synchronous as well as Async versions to simplify using the SDK on
	systems like Android where network calls need to executed asynchronously on a 
	non-UI thread.
 

		oAuthService.authenticateAsync(subdomain, apiControlPlane, username, password, oAuthTokenCallback);



* **SAML Authentication**:  This authentication support assumes you have a mechanism
for obtaining a SAML assertion, `samlAssertion` from the user's IdP.

        ISFOAuthService oAuthService = new SFOAuthService();

		SFOAuth2Token authToken = oAuthService.authenticate (subdomain, apiControlPlane, samlAssertion);

	Or Asynchronously 

		oAuthService.authenticateAsync(subdomain, apiControlPlane, samlAssertion, oAuthTokenCallback);


* **Refreshing an OAuthToken**:  Any `OAuthToken` that is obtained using a `code`
grant type can be refreshed.  This allows a consumer to silently reauthenticate
with the ShareFile API without needing to prompt the user.  This is useful if
you plan on caching the `OAuthToken`.  The sample below assumes you have already
pulled an instance of `OAuthToken` as `cachedOAuthToken` from some local cache.

        ISFOAuthService oAuthService = new SFOAuthService();

  		SFOAuth2Token authToken = oAuthService.refreshOAuthToken(oldAuthToken);//equivalent Async function available



## ShareFile Basics ##

Once authenticated, getting information from ShareFile is pretty easy.  
Below are some samples on what you can do, it assumes there is an instance of
ShareFileClient - `sfClient` available.  As mentioned previously:  all the functions exposed by the SDK which make network calls have a synchronous(blocking) as well as Async versions to simplify using the SDK on systems like Android where network calls need to executed asynchronously on a non-UI thread.

The general pattern of sdk calls you can make is of the following type:

* Blocking calls

		try
		{
			SFObject object = apiClient.<some function>.execute();
		}
		catch(SFSDKException exception)
		{
			//handle errors			
		}

* Async(Non-blocking) calls:

	The Async versions of the functions take a callback function of the following type: `ISFApiResultCallback<T>` and return the appropriate results or `SFSDKException`. 

		ISFApiResultCallback<T> callback = new ISFApiResultCallback<T>()
    	{
			@Override
         	public void onSuccess(T returnValue)
			{
				//handle success
			}

        	@Override
         	public void onError(SFSDKException exception, ISFQuery<T> originalQuery)
			{
				//handle failure
			} 
     	}



### Obtain an instance of the SFApiClient ###

   Everything related to the ShareFile API can be accessed by using an instance of the `SFApiClient`

	ISFApiClient apiClient = new SFApiClient(oAuthToken);

### Start a Session ###

      SFSession session = apiClient.sessions().login().execute(); //or executeAsync(callback)

### End session ###

      apiClient.sessions().delete().execute();  // or executeAsync(callback)

### Get the current user ###

A User in ShareFile derives from the `SFPrincipal` object. For most consumers you
will be interested in `SFUser` and `SFAccountUser`. The `SFAccountUser` type designates
the user to be an Employee and will have some additional properties available.  

      SFUser user = apiClient.users().get().execute();  //or executeAsync(callback)

### Get the default folder for a User ###

This call will return the default folder for the currently authenticated `SFUser`.

      SFFolder folder = apiClient.items().get().execute(); //or executeAsync(callback)

### Get the contents of a folder ###

      SFODataFeed<SFItem> folderContents = apiClient.items().getChildren(parentURI).execute();  
	  //or executeAsync(callback)
  
      ArrayList<SFItem> children = folderContents.getFeed();


### Create a Folder ###

      //Assuming you have got access to the (SFFolder) parentFolder object using one 
	  //of the above methods of folder/children enumeration	
           
      SFFolder newFolder = new SFFolder();
      newFolder.setName("new folder1");
            
      apiClient.items().createFolder(parentFolder.geturl(),newFolder).execute();   //or executeAsync(callback) 



### Search ###

        SFSearchResults searchResult = apiClient.items().search("query").execute();
        
		ArrayList<SFSearchResult> result = searchResult.getResults();


To browse search results (currently, there is no `Uri` returned that points to the `Item`):

      	SFSearchResult item = result.get(index);
        URI uri = apiClient.getDefaultUrl(item.getId());
        SFItem actualItem = apiClient.items().get(uri).execute();


### Access Aliased Folders ###
There are some folders within ShareFile that are not easily discovered, however
the SDK can help you find them.  These aliases are exposed on the `SFFolderID`.

      URI itemUri = apiClient.items().getDefaultUrl(SFFolderID.TOP);
	  
	  SFFolder folder = apiClient.items.get(itemUri).execute();  //or executeAsync(callback)


## Upload/Download ##

### Download ###

      SFFile fileToDownload;//Assuming you have obtained a valid SFFile object from the folder enumeration
            
      OutputStream outputStream = new FileOutputStream("system specific file path");
            
      TransferRunnable.IProgress progressListener = new TransferRunnable.IProgress() 
      {
      	@Override
        public void bytesTransferred(long bytesTransferred) {}

        @Override
        public void onError(SFSDKException e, long bytesTransferred) {}

        @Override
        public void onComplete(long bytesTransferred) {}
      };
            
      SFDownloadRunnable downloader  = apiClient.getDownloader(fileToDownload,outputStream, progressListener);
            
      downloader.start(); // this is async by default


### Upload ###

		TransferRunnable.IProgress progressListener = new TransferRunnable.IProgress() 
		{
			@Override
			public void bytesTransferred(long bytesTrasnfered) {}
			
			@Override
			public void onError(SFSDKException e, long bytesTrasnfered) {}
			
			@Override
			public void onComplete(long bytesTrasnfered) {}
		};

	    FileInputStream inputStream = new FileInputStream("system specific file path");
	
	    SFUploadRequestParams requestParams = new SFUploadRequestParams();
	    requestParams.setFileName("destinaltionFileName");
	    requestParams.setDetails("details");
	    requestParams.setFileSize((long) inputStream.available());
	    requestParams.seturl(parentUrl);
	
	
	    SFUploadRunnable uploader = apiClient.getUploader(requestParams,inputStream,progressListener);
	
	    uploader.start(); // this is async by default



## Accessing a Share ##

Assuming you have the url that points to the Share API resource (ex. `https://subdomain.sharefile.com/sf/v3/Shares(s0123456789)`), you can easily access the `Items` shared.  Depending on the share you may be required to already be authenticated.

	URI uri = new URI("https://subdomain.sharefile.com/sf/v3/Shares(s0123456789)");
    
	SFShare share = apiClient.shares().get(uri).execute();
    
	SFODataFeed<SFItem> items = apiClient.shares().getItems(uri).execute();
    
	ArrayList<SFItem> shareItems = items.getFeed();


Items associated with a `Share` cannot be downloaded as you normally might, instead you need to use the `Shares` API to download.

	// assuming you already have shareItems as noted before
	InputStream inputStream = apiClient.shares().downloadWithAlias(uri,share.getAliasID(),shareItems.get(0).getId()).execute();


## Leveraging oData ##

ShareFile supports the oData protocol which provides standard ways of handling
common tasks such as:

  * Select specific properties
  * Expand Navigation properties such as `Folder.Children`
  * Perform paging operations

### Select ###

The following `Query` will only select the Name property.  If you execute this,
all other properties will be their default values.  This is convenient for
reducing payloads on the wire.

      SFFolder folder = (SFFolder) apiClient.items().get().select("Name").execute();

### Expand ###

The following `Query` will expand `Children`.  Since we know we are querying for
a `Folder` we can ask ShareFile to go ahead and return the list of Children.  This
helps reduce the number of round trips required.  Note `Chlidren` is presented
as a `ArrayList<SFItem>` instead of an `SFODataFeed<SFItem>`.
	
	SFFolder folder = (SFFolder) apiClient.items().get().expand("Children").execute();
	
	ArrayList<SFItems> children = folder.getChildren();


### Top/Skip ###

When working with `ODataFeed` responses, you can limit the size of the response
by using `Top` and `Skip`.  The following `Query` will return up to 10 Children
and skip the first 10.

      SFODataFeed<SFItem> children = apiClient.items().getChildren(parenturl).top(10).skip(10).execute();

To support paging `ODataFeed` will also return a nextLink which will compute the
Top and Skip values for you.


## Android Specific ##

The ShareFile SDK is a pure Java SDK and can be directly used with Android apps. To make life simpler for the Android developers, the SDK Allows you to extend the SDK to suit some of the Android specific constructs as follows:

### Getting Logs ###

You can use the Android Log.*() mechanism so you can get the logs from execution of the SDK functions by implementing the ILog interface:
	
		public class SFLogger implements ILog
		{
		    @Override
		    public int v(String tag, String msg)
		    {
		        return Log.v("SF_"+tag, msg);
		    }
		
		    @Override
		    public int v(String tag, String msg, Throwable tr) {
		        return Log.v("SF_"+tag, msg);
		    }
		
		    //…. Lots more overrides
		
		    @Override
		    public int e(String tag, String msg, Throwable tr) {
		        return Log.v("SF_"+tag, msg,tr);
		    }
		}
	
	
		SFSdk.setLogger(new SFLogger());


### AsyncTasks ###

The SDK Async functions rely on the Java Threads by default. You can use the Android AsyncTask instead by registering an AsyncTaskFactory with the SDK and creating your own AsyncTasks follows. This is a onetime thing which you can do in the Application().onCreate() of your Android app.

	private static ISFAsyncTaskFactory asyncTaskFactory = new ISFAsyncTaskFactory()
	{
        @Override
        protected ISFAsyncTask createNewTask()
        {
            return new SampleAsyncTask();
        }
	};

	SFSdk.setAsyncTaskFactory(asyncTaskFactory);


Following is a sample Asynctask. Note the use of the `IAsyncHelper` in the task.


	public class SampleAsyncTask extends AsyncTask implements ISFAsyncTask
	{
	    ISFAsyncHelper asyncHelper;
	
	    @Override
	    protected Object doInBackground(Object[] objects)
	    {
	        asyncHelper.execute();
	        return null;
	    }
	
	    @Override
	    protected void onPostExecute(Object o)
	    {
	        super.onPostExecute(o);
	        asyncHelper.onPostExecute();
	    }
	
	    @Override
	    public void start(ISFAsyncHelper asyncHelper)
	    {
	        this.asyncHelper = asyncHelper;
	        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	    }
	}

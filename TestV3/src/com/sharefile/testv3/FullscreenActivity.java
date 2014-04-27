package com.sharefile.testv3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONObject;

import com.sharefile.api.SFApiClient;
import com.sharefile.api.SFApiQuery;
import com.sharefile.api.V3Error;
import com.sharefile.api.utils.SFLog;
import com.sharefile.api.authentication.SFOAuth2Token;
import com.sharefile.api.constants.SFKeywords;
import com.sharefile.api.entities.SFAccessControlsEntity;
import com.sharefile.api.entities.SFAccountsEntity;
import com.sharefile.api.entities.SFCapabilitiesEntity;
import com.sharefile.api.entities.SFFavoriteFoldersEntity;
import com.sharefile.api.entities.SFSharesEntity;
import com.sharefile.api.entities.SFZonesEntity;
import com.sharefile.api.exceptions.SFInvalidStateException;
import com.sharefile.api.exceptions.SFJsonException;
import com.sharefile.api.interfaces.SFApiResponseListener;
import com.sharefile.api.interfaces.SFAuthTokenChangeListener;
import com.sharefile.api.models.SFAccessControl;
import com.sharefile.api.models.SFAccount;
import com.sharefile.api.models.SFCapability;
import com.sharefile.api.models.SFFavoriteFolder;
import com.sharefile.api.models.SFODataFeed;
import com.sharefile.api.models.SFShare;
import com.sharefile.api.models.SFZone;
import com.sharefile.mobile.shared.dataobjects.v3.SFOAuthAccessToken;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements SFAuthTokenChangeListener
{
	public static SFOAuth2Token mOAuthToken = null;
	public static final String WEB_LOGIN_CLIENT_ID_SHAREFILE = "qhRBpcI7yj931hV2wzGlmsi6b";
	public static final String WEB_LOGIN_CLIENT_SECRET_SHAREFILE = "Nu8JDCC9EK598e4PmA2NBbF09oYBS8";	 	 
	public static SFApiClient mSFApiClient;
	public static SFOAuthAccessToken gToken = null;
	private final SFAuthTokenChangeListener mTokenChangeListener;
	
	public FullscreenActivity()
	{
		mTokenChangeListener = this;
	}
	
	public void showToast(final String msg)
	{
		runOnUiThread(new Runnable() 
		{			
			@Override
			public void run() 
			{				
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();;
			}
		});
	}
	

	private String readFolder()
	{
		try 
		{
	        Resources res = getResources();
	        InputStream in_s = res.openRawResource(R.raw.v3folder);

	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        return new String(b);
	    } 
		catch (Exception e) 
	    {	        
	    }
		
		return null;
	}
	
	
	private ArrayList<Button> mTestButtons = new ArrayList<Button>();
	
	
	private Button getNewButton(int id, OnClickListener clickListener,String text)
	{
		final Button uiButton = new Button(this);				
		uiButton.setOnClickListener(clickListener);
		final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(0, 0, 0, 15);
		
		uiButton.setLayoutParams(layoutParams);
		uiButton.setPadding(0,18,0,18);
		uiButton.setText(text);
		uiButton.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
		uiButton.setId(id);
		uiButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bkg));
		uiButton.setEnabled(false);		
				
		return uiButton;
	}
	
	public static class APITest
	{
		public int mButtonId = 0;
		public OnClickListener mOnClickListener = null;
		public String mText;		
		
		public APITest(int id,OnClickListener clickLickClickListener, String text)
		{
			mButtonId = id;
			mOnClickListener = clickLickClickListener;
			mText = text ;					
		}
	}
	
	private static final int BASE_BUTTON_ID = R.id.buttonInitSession + 1;
	
	private OnClickListener getItems = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent(getApplicationContext(), FoldersActivity.class);
	    	startActivity(intent);
		}
	};
	
	
	private void saveToken(final SFOAuth2Token newAccessToken)
	{
		runOnUiThread(new Runnable() 
		{			
			@Override
			public void run() 
			{				
				try 
				{
					PersistantToken.saveToken(getApplicationContext(), newAccessToken);
				} 
				catch (SFJsonException | IOException e) 
				{							
					e.printStackTrace();
				}
			}
		});				
	}
	
	private OnClickListener getAccessControl = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFAccessControl> query = SFAccessControlsEntity.get(mSFApiClient.getSession().getPrincipal().getId(),"top");
								
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFAccessControl>() 
				{														

					@Override
					public void sfApiSuccess(SFAccessControl object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");						
					}

					@Override
					public void sfApiError(V3Error v3error,SFApiQuery<SFAccessControl> asApiqueri) 
					{
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");						
					}
				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	private OnClickListener getCapabilities = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFODataFeed<SFCapability>> query = SFCapabilitiesEntity.get();
			
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFODataFeed<SFCapability>>() 
				{														

					@Override
					public void sfApiSuccess(SFODataFeed<SFCapability> object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");
					}

					@Override
					public void sfApiError(V3Error v3error, SFApiQuery<SFODataFeed<SFCapability>> asApiqueri) 
					{						
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");
					}
				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	private OnClickListener getShares = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFODataFeed<SFShare>> query = SFSharesEntity.get();
			
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFODataFeed<SFShare>>() 
				{														

					@Override
					public void sfApiSuccess(SFODataFeed<SFShare> object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");
					}

					@Override
					public void sfApiError(V3Error v3error, SFApiQuery<SFODataFeed<SFShare>> asApiqueri) 
					{						
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");
					}

				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	
	private OnClickListener getFavorites = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFODataFeed<SFFavoriteFolder>> query = SFFavoriteFoldersEntity.getByUser(mSFApiClient.getSession().getPrincipal().getId());
			
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFODataFeed<SFFavoriteFolder>>() 
				{														

					@Override
					public void sfApiSuccess(SFODataFeed<SFFavoriteFolder> object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");
					}

					@Override
					public void sfApiError(V3Error v3error, SFApiQuery<SFODataFeed<SFFavoriteFolder>> asApiqueri) 
					{						
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");
					}
				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	private OnClickListener getAccount = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFAccount> query = SFAccountsEntity.get();
			
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFAccount>() 
				{														

					@Override
					public void sfApiSuccess(SFAccount object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");
					}

					@Override
					public void sfApiError(V3Error v3error, SFApiQuery<SFAccount> asApiqueri) 
					{						
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");
					}
				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	private OnClickListener getZones = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			SFApiQuery<SFODataFeed<SFZone>> query = SFZonesEntity.get();
			
			try 
			{
				mSFApiClient.executeQuery(query, new SFApiResponseListener<SFODataFeed<SFZone>>() 
				{														

					@Override
					public void sfApiSuccess(SFODataFeed<SFZone> object) 
					{
						SFLog.d2("SFSDK","getItem success: ");
						showToast("success");
					}

					@Override
					public void sfApiError(V3Error v3error, SFApiQuery<SFODataFeed<SFZone>> asApiqueri) 
					{						
						SFLog.d2("SFSDK","get Item failed: ");
						showToast("Failed");
					}
				},null);
			} 
			catch (SFInvalidStateException e) 
			{							
				e.printStackTrace();
				showToast("Exception "+ e.getLocalizedMessage());							
			}
		}
	};
	
	private OnClickListener getDevices = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{		
		}
	};
	
	private OnClickListener deleteSavedToken = new OnClickListener() 
	{		
		@Override
		public void onClick(View v) 
		{
			PersistantToken.deleteToken(getApplicationContext());
			finish();
		}
	};
	
	private APITest[] mApiTests = 
	{
			new APITest(BASE_BUTTON_ID,getItems,"Get Items"),
			new APITest(BASE_BUTTON_ID+1,getAccessControl,"Get Access Control"),
			new APITest(BASE_BUTTON_ID+2,getCapabilities,"Get Capabilities"),
			new APITest(BASE_BUTTON_ID+3,getShares,"Get Shares"),
			new APITest(BASE_BUTTON_ID+4,getFavorites,"Get Favorite Folders"),
			new APITest(BASE_BUTTON_ID+5,getAccount,"Get Account"),
			new APITest(BASE_BUTTON_ID+5,getZones,"Get Zones"),
			new APITest(BASE_BUTTON_ID+6,getDevices,"Get Devices"),
			new APITest(BASE_BUTTON_ID+7,deleteSavedToken,"Delete Saved Token"),
	};
								
	private void addTestButtonsToLayout(LinearLayout layout)
	{
		for(APITest test:mApiTests)
		{
			Button b = getNewButton(test.mButtonId, test.mOnClickListener, test.mText);
			mTestButtons.add(b);
			layout.addView(b);			
			
			if(test.mOnClickListener == deleteSavedToken)
			{
				b.setEnabled(true);
			}
		}
	}
	
	private void changeTestButtons(final boolean enable)
	{
		runOnUiThread(new Runnable() 
		{			
			@Override
			public void run() 
			{				
				for(Button b:mTestButtons)
				{						
					b.setEnabled(enable);
					b.invalidate();
				}
			}
		});
				
	}
	
	private void copyToken()
	{
		if(gToken!=null)
		{
			try
			{
				JSONObject jsonObject = new JSONObject();
										
				jsonObject.put(SFKeywords.ACCESS_TOKEN, gToken.access_token);
				jsonObject.put(SFKeywords.REFRESH_TOKEN,gToken.refresh_token);
				jsonObject.put(SFKeywords.TOKEN_TYPE,gToken.token_type);
				jsonObject.put(SFKeywords.APP_CP,gToken.appcp);
				jsonObject.put(SFKeywords.API_CP,gToken.apicp);
				jsonObject.put(SFKeywords.SUBDOMAIN,gToken.subdomain);
				jsonObject.put(SFKeywords.EXPIRES_IN,gToken.expires_in);
				
				
				mOAuthToken = new SFOAuth2Token(jsonObject.toString());
			}
			catch(Exception e)
			{
				showToast("Copy toke problem");
			}
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_fullscreen);
		
		copyToken();
		
		Button buttonInitSession = (Button)findViewById(R.id.buttonInitSession);
		
		buttonInitSession.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{												
				 showToast("Stating auth");	
				 
				 SFLog.d2("SFSDK","staring auth task");
				 
				 //SFTask task = new SFTask();			 			 
				 //SFAsyncTask.execute(task, new Object(){});
				 try 
				 {
					mSFApiClient = new SFApiClient(mOAuthToken,"defaultuser",WEB_LOGIN_CLIENT_ID_SHAREFILE,WEB_LOGIN_CLIENT_SECRET_SHAREFILE,mTokenChangeListener);
					changeTestButtons(true);
				 } 
				 catch (SFInvalidStateException e) 
				 {					
					e.printStackTrace();
				 }
			}
		});
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutTestButtons);
		
		addTestButtonsToLayout(layout);
				
		
	}

	@Override
	public void sfApiStoreNewToken(SFOAuth2Token newAccessToken) 
	{		
		saveToken(newAccessToken);
	}	
}

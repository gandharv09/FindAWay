package iiitd.ngo.findaway;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreen extends Activity {

	Context context;
    static boolean value = false;
    String  accountName;
    public static final String PREFS_NAME = "MyPrefsFile";
	private static final int PICK_ACCOUNT_REQUEST = 0;
	 SharedPreferences settings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();

    	//Set "hasLoggedIn" to true
    	//editor.putBoolean("hasLoggedIn", false);

    	// Commit the edits!
    	editor.commit();
       
     //   mLogo = (ImageView)findViewById(R.id.logo);

         value=false;
            context = this;
           
           
                	
    	//SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
    	//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
    	//boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

    	if(settings.contains("hasLoggedIn"))
    	{
    		
    	    //Go directly to main activity
    	loginButton.setVisibility(View.INVISIBLE);
    	 new Handler().postDelayed(new Runnable() {
             public void run() {
    	Intent j = new Intent();
        j.setClass(context, MainActivity.class);
        startActivity(j);
        finish();
             }
         }, 2500);
    	}else{
    		
    		loginButton.setVisibility(View.VISIBLE);
    		
    	}
    }
          
    public void login(View v) {
    	/*
    	* If account name already stored in shared preferences, then use that.
    	* Else show account picker.
    	*/
    	if(isOnline(this)){
    	showGoogleAccountPicker();}
    	else{
    		Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT);
    	}
    	}  
    public static boolean isOnline(Context c) {
		
	      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
	      return ( netInfo != null && netInfo.isConnected() );
	}
            
    private void showGoogleAccountPicker() {
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null,
                        new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, true,
                        null, null, null, null);
        startActivityForResult(googlePicker, PICK_ACCOUNT_REQUEST);

}

/*
 * takes selected account data, stores email id in shared preferences and
 * calls method for taking token
 */
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
                accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                SharedPreferences.Editor editor = settings.edit();
                Log.d("accountname",accountName);
                editor.putString("accountName", accountName);
                editor.putString("hasLoggedIn", "LoggedIn");
               
                Thread thread = new Thread()
        		{
        			@Override
        			public void run()
        			{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://findaway.in/card/login.php");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("email", accountName));
				httppost.setHeader("X-FINDAWAY-KEY", "35f1c80573140143e1d338aef233e1f2");
				try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                HttpResponse resp;
				try {
					resp = httpclient.execute(httppost);
					HttpEntity responseEntity = resp.getEntity();
			        String s = EntityUtils.toString(responseEntity);
			        Log.d("List response", s);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        			}


        		};
        		thread.start();
        		
              
                editor.commit();
                Intent j = new Intent();
                j.setClass(context, MainActivity.class);
                startActivity(j);
                finish();
        }
}
    
}

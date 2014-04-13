package iiitd.ngo.findaway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Description extends FragmentActivity {
	
	  private static LatLng mylocation = new LatLng(0.00,0.00); 
	  private GoogleMap map;
	  String latlong;
	  String name;
	  ArrayList<String> Name=new ArrayList<String>();
	  ArrayList<String> LL= new ArrayList<String>();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_description);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
 		actionBar.setLogo(R.drawable.bar_logo);
 		actionBar.setDisplayShowTitleEnabled(false);
		TextView text = (TextView)findViewById(R.id.Description);
		TextView  add= (TextView)findViewById(R.id.Address);
		TextView  ph= (TextView)findViewById(R.id.Number);
		TextView  val= (TextView)findViewById(R.id.Date);
		TextView  zom= (TextView)findViewById(R.id.Link);
		TextView dis = (TextView)findViewById(R.id.dis_cp);
		TextView tnc = (TextView)findViewById(R.id.Shart);
		ImageView logo = (ImageView)findViewById(R.id.Logo);
		Bundle extras = getIntent().getExtras();
		
		Name = extras.getStringArrayList("Listname");	
		LL = extras.getStringArrayList("Listcord");
		//String latlong = getIntent().getExtras().getString("latlong");
		
		if(isOnline(this)==true)
		{
		
		
		//Log.d("name",Name.get(position).getName()+"null");
		String link = extras.getString("Logo1");
		new DownloadImageTask(logo).execute(link);
	    name = extras.getString("Name");
		String address = extras.getString("Address");
		latlong = extras.getString("latlong");
		String di = extras.getString("Discount");
		String cp = extras.getString("Coupon");
		String term = extras.getString("Term");
		if(term.equals("0"))
		{
			term = "";
		}
		String net = "<p>Only one Find A Way Card is valid per table.</p>" +
					   "<p>Cannot be combined with any other offer or promotion.</p>"+
                       "<p>Cannot be availed on festive occastions and specific holidays.</p>"+
                       "<p>Please contact the restaurant manager for a complete list of terms and conditions.</p><p>"+term+"</p>";
		
		if(di!=null || cp!=null)
		{
			String concat = "<p>"+di+".</p>"+"<p><b>Use Coupon Code : "+cp+"</b></p>";
			dis.setText(Html.fromHtml(concat));
		}
		String phone = extras.getString("Phone");
		String[] s = phone.split(",");
		String zomato = extras.getString("Zomato");
		String str = zomato.replace("\"","");
		String validity = extras.getString("Validity");
		tnc.setText(Html.fromHtml(net));
		text.setText(name);
		add.setText(address);
		ph.setText(s[0]);
		zom.setClickable(true);
		zom.setMovementMethod(LinkMovementMethod.getInstance());
		String tex = "<a href='"+str+"'> @Link </a>";
		zom.setText(Html.fromHtml(tex));
		
		val.setText(validity);
		
		}
		if(isOnline(this)==false){
		    name = extras.getString("Name");
			String address = extras.getString("Address");
			String phone = extras.getString("Phone");
			String[] s = phone.split(",");
			String zomato = extras.getString("Zomato");
			String str = zomato.replace("\"","");
			String validity = extras.getString("Validity");
			latlong = extras.getString("latlong");
			String di = extras.getString("Discount");
			String cp = extras.getString("Coupon");

			if(di!=null || cp!=null)
			{
				String concat = "<p>"+di+".</p>"+"<p><b>Use Coupon Code : "+cp+"</b></p>";
				dis.setText(Html.fromHtml(concat));
			}
			String term = extras.getString("Term");
			if(term.equals("0"))
			{
				term = "";
			}
			String net = "<p>Only one Find A Way Card is valid per table.</p>" +
						   "<p>Cannot be combined with any other offer or promotion.</p>"+
                           "<p>Cannot be availed on festive occastions and specific holidays.</p>"+
                           "<p>Please contact the restaurant manager for a complete list of terms and conditions.</p><p>"+term+"</p>";
			tnc.setText(Html.fromHtml(net));
			text.setText(name);
			add.setText(address);
			ph.setText(s[0]);
			zom.setClickable(true);
			zom.setMovementMethod(LinkMovementMethod.getInstance());
			String tex = "<a href='"+str+"'> @Link </a>";
			zom.setText(Html.fromHtml(tex));
			
			val.setText(validity);
			
			//Log.d("offline","image");
			//Log.d("offline",extras.getByteArray("Logo2").toString());
			//byte[] outImage=(byte[]) extras.getByteArray("Logo2");
			//if(outImage!=null)
			//{
			//ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
			//Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			//logo.setImageBitmap(theImage);}else{Log.d("Error","image null");}

			//Read more: http://www.androidhub4you.com/2012/09/hello-friends-today-i-am-going-to-share.html#ixzz2o9fjlik2
		}
	 
		//ImageView mapbutton = (ImageView)findViewById(R.id.mapbutton);
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.mainLayout);
		rl.setOnClickListener(new OnClickListener() {
			 
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startactivity();
				
	            
			}
 
		});
		
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case android.R.id.home:
             finish();
            case R.id.feedback:
                // search action
            	Feedback();
                return true;
            default: 
             return super.onOptionsItemSelected(item);      
            }
    }
	public void Feedback(){
    	FeedbackSettings feedbackSettings = new FeedbackSettings();

        
        //SUBMIT-CANCEL BUTTONS

        feedbackSettings.setCancelButtonText("Cancel");

        feedbackSettings.setSendButtonText("Send");


        //DIALOG TEXT

        feedbackSettings.setText("Hey, would you like to give us some feedback so that we can improve your experience?");

        feedbackSettings.setYourComments("Type feedback here...");

        feedbackSettings.setTitle("Feedback");


        //TOAST MESSAGE

        feedbackSettings.setToast("Thank you so much!");


        //RADIO BUTTONS

        feedbackSettings.setRadioButtons(true); // Disables radio buttons

        feedbackSettings.setIdeaLabel("Feedback on Restaurants");

        feedbackSettings.setBugLabel("Feedback on Find A Way Card");

        feedbackSettings.setQuestionLabel("App Bug Report");

        feedbackSettings.setQuestionLabel("Any other Suggestion");

        //RADIO BUTTONS ORIENTATION AND GRAVITY
        
        
        feedbackSettings.setOrientation(LinearLayout.VERTICAL);

        
        feedbackSettings.setGravity(Gravity.LEFT);

        

        //SET DIALOG MODAL

        feedbackSettings.setModal(true);


        //TITLE FOR DEVELOPER REPLIES

        feedbackSettings.setReplyTitle("Message from the Developer");


        FeedbackDialog feedBack = new FeedbackDialog(this, "AF-D013F6EAA6B4-76", feedbackSettings);

        
        feedBack.show();
    }
	public void startactivity()
	{
		Intent j = new Intent(this, Map.class);
		//j.setClass(this, Map.class);
        Bundle item = new Bundle();
        
        item.putString("name", name);
        item.putString("latlng", latlong);
        item.putStringArrayList("Listname", (ArrayList<String>) Name);
        item.putStringArrayList("Listcord", (ArrayList<String>) LL);
        
        j.putExtras(item);
        startActivity(j);
	}
	public static boolean isOnline(Context c) {
	      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
	      return ( netInfo != null && netInfo.isConnected() );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		return true;
	}

}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;

	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	         // Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	  }
	}

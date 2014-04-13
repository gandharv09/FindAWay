package iiitd.ngo.findaway;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.widget.SearchView;

import org.apache.http.util.ByteArrayBuffer;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;
import com.viewpagerindicator.TitlePageIndicator;


import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    //private static ListView itcItems;
    //private static ListAdapter adapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static SearchView searchView;
    static List<Data> result2;
    private static GoogleMap map;
    static MenuItem searchItem;
    static List<Data> result1;
    
    static SearchView.OnQueryTextListener queryTextListener;
    private static int flag=0; 
    private static ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//final ActionBar actionBar = getActionBar();
    	
    	//actionBar.setBackgroundDrawable( new ColorDrawable(0x00f47629));
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
     // Set up the action bar.
     		final ActionBar actionBar = getActionBar();
     		actionBar.setLogo(R.drawable.bar_logo);
     		actionBar.setDisplayShowTitleEnabled(false);
     		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
     		actionBar.setStackedBackgroundDrawable( new ColorDrawable(0x00f47629));
     		// Create the adapter that will return a fragment for each of the three
     		// primary sections of the app.
     		mSectionsPagerAdapter = new SectionsPagerAdapter(
     				getSupportFragmentManager());
     		
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
      
        //TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
		//titleIndicator.setViewPager(mViewPager)
		 
		 mViewPager
			.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);
				}
			});

	// For each of the sections in the app, add a tab to the action bar.
	for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
		// Create a tab with text corresponding to the page title defined by
		// the adapter. Also specify this Activity object, which implements
		// the TabListener interface, as the callback (listener) for when
		// this tab is selected.
		actionBar.addTab(actionBar.newTab()
				.setText(mSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
	}
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		return true;
	}
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
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
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
        	switch (position) {

        	case 0:

        	fragment = new DummySectionFragment();

        	//args.putString(CourseInfoFragment.CourseInfo, CourseInfo);

        	break;

        	case 1:

        	fragment = new FeedbackFragment();

        	//args.putString(DeadlinesFragment.deadlines, DeadlineInfo);

        	break;

        	case 2:

        	fragment = new AboutusFragment();

        	//args.putString(DummySectionFragment.data, ResourceInfo);

        	break;
        }
			return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
            case 0:

        	return "Restaurants";

        	case 1:

        	return "Nearby";

        	case 2:

        	return "About Us";

        	}

        	return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    
    public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		ListView itcItems;
		DataBaseHandler db;
		//private SearchView mSearchView;
	    private EditText mStatusView;
		
		public DummySectionFragment() {
		}
		
		/*public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			 //MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.option_menu, menu); 
		    
	        //getMenuInflater().inflate(R.menu.action, menu);   
			searchItem = menu.findItem(R.id.grid_default_search);
			SearchManager searchManager = (SearchManager) getActivity()
		            .getSystemService(Context.SEARCH_SERVICE);

		        searchView = (SearchView) searchItem.getActionView();
		        searchView.setSearchableInfo(searchManager
		                .getSearchableInfo(getActivity().getComponentName()));
		        searchView.setIconified(true);
		        searchView.setSubmitButtonEnabled(true);  
	
	        

	    }*/
		       
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			
			View rootView = inflater.inflate(R.layout.common_listview,
					container, false);
			setHasOptionsMenu(true);


            ActionBar actionbar = getActivity().getActionBar();
            getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            actionbar.show();
			 //getActivity().requestWindowFeature(Window.FEATURE_ACTION_BAR);
			mStatusView = (EditText) rootView.findViewById(R.id.status_text);
			itcItems = (ListView) rootView.findViewById(R.id.streamList);
			
			
			
			/*MyAsyncTask task = new MyAsyncTask(getActivity());
			task.execute("http://findaway.in/card/restlist.xml");
			
			
			*
			*/
			db = new DataBaseHandler(getActivity());
			
			if(isOnline(getActivity()))
				
			{ 
				flag=1;
				db.delete();
				try{
				getDataInAsyncTask();
				
				}catch(Exception e){
					Toast.makeText(getActivity(), "Slow or No Internet Connection",
	                        Toast.LENGTH_LONG).show();
				}//getImage(db);
				
				//db.close();
				
			}
			
			else
            {
				flag=0;
				Toast.makeText(getActivity(), "No internet Connection",
                        Toast.LENGTH_LONG).show();
				
		        try {
		            db.openDataBase();
		        } catch (SQLException sqle) {
		            throw sqle;
		        }
		        
		        getDataInDataBase(db);
				
            }
			
			
			/*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

				    @Override
				    public boolean onQueryTextSubmit(String query) {
				        // collapse the view ?
				        adapter.getFilter().filter(query);
				        return false;
				    }

				    @Override
				    public boolean onQueryTextChange(String newText) {
				        // search goes here !!
				        // listAdapter.getFilter().filter(query);
				    	 adapter.getFilter().filter(newText);
				        return false;
				    }

				});

	       
			
			*/
			mStatusView.addTextChangedListener(new TextWatcher() {
	             
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	            	adapter.getFilter().filter(cs);
	            }
	             
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	                 
	            }
	             
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                // TODO Auto-generated method stub                         
	            }

				
	        });
			
			
			itcItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		        @SuppressWarnings("null")
				@Override
		    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		        // do things with the clicked item
		        String f="1";
		        Intent intent = new Intent(view.getContext(), Description.class);
	            Bundle item = new Bundle();
	            ArrayList<String> Name = new ArrayList<String>();
	            ArrayList<String> LL=new ArrayList<String>();
	            if(isOnline(getActivity()))
	            {
	            f="1";
	            for(int i=0;i<result1.size();i++)
				{
					Log.d("adding..","name:"+result1.get(i).getName());
	            	Name.add(result1.get(i).getName());
	            	Log.d("adding..","name:"+result1.get(i).getLatLong());
					LL.add(result1.get(i).getLatLong());
				
				}
	            item.putString("Logo1", adapter.getItem(position).getImage());
	            //String describe = result1.get(position).getName()+"\n Address: "+result1.get(position).getAddress()+"\n Phone: "
	            //+result1.get(position).getPhone()+"Zomato Link : " + result1.get(position).getZomato();
	            //item.putString("Description",describe);
	            //item.putString("latlong",result1.get(position).getLatLong());
	            //intent.putExtra("Logo2", "");
	            item.putString("latlong",adapter.getItem(position).getLatLong());
	            item.putString("Name", adapter.getItem(position).getName());
	            item.putString("Address",adapter.getItem(position).getAddress());
	            item.putString("Phone",adapter.getItem(position).getPhone());
	            item.putString("Zomato",adapter.getItem(position).getZomato());
	            item.putString("Coupon", adapter.getItem(position).getCoupon());
	            item.putString("Discount",adapter.getItem(position).getDiscount());
	            item.putString("Validity",adapter.getItem(position).getValidity());
	            item.putString("Term", adapter.getItem(position).getTerm());
	            item.putStringArrayList("Listname", (ArrayList<String>) Name);
	            item.putStringArrayList("Listcord", (ArrayList<String>) LL);
	            
	            
	            }
	            
	            else{
	            f="0";
	            for(Data d: result2 )
				{
					Name.add(d.getName());
					LL.add(d.getLatLong());
				}
	            item.putString("latlong",adapter.getItem(position).getLatLong());
	            item.putString("Name", adapter.getItem(position).getName());
	            item.putString("Address",adapter.getItem(position).getAddress());
	            item.putString("Phone",adapter.getItem(position).getPhone());
	            item.putString("Zomato",adapter.getItem(position).getZomato());
	            item.putString("Coupon", adapter.getItem(position).getCoupon());
	            item.putString("Discount",adapter.getItem(position).getDiscount());
	            item.putString("Validity",adapter.getItem(position).getValidity());
	            item.putString("Term", adapter.getItem(position).getTerm());
	            item.putStringArrayList("Listname", (ArrayList<String>) Name);
	            item.putStringArrayList("Listcord", (ArrayList<String>) LL);
	           }
	            //String f = Integer.toString(flag);
	            //item.putString("flag",f);
	            intent.putExtras(item);
	            view.getContext().startActivity(intent);
		    }
		    });
			return rootView;
		}

		/*@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			
			
			MyAsyncTask task = new MyAsyncTask(getActivity());
			task.execute("http://findaway.in/card/restlist.xml");
			super.onActivityCreated(savedInstanceState);
		}
		*/
		void getDataInAsyncTask(){
			/*final ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);

            pd.setTitle("Loading Resturant List...");

            pd.setCancelable(false);

            pd.setIndeterminate(true);

            pd.setMessage("Please Wait");
				*/AsyncTask<Void, Void, List<Data>> task = new AsyncTask<Void, Void, List<Data>>() {


                  /*  @Override
                    protected void onPreExecute() {

                            pd.show();

                    }*/
				protected List<Data> doInBackground(Void... urls) {

					// Debug the task thread name
					Log.d("ITCRssReader", "inside");

					try {
						// Create RSS reader
						
						RssReader rssReader = new RssReader("http://findaway.in/card/restaurants/feed.xml");
						Log.d("ITCRssReader", "inside1");
						// Parse RSS, get items
						// Log.d("ITCRssReader", rssReader.getItems().get(3).getName());
						return rssReader.getItems();
						
					} catch (Exception e) {
						Toast.makeText(getActivity(), "Slow or No Internet Connection",
		                        Toast.LENGTH_LONG).show();
						  // Intent intent = new Intent(Intent.ACTION_MAIN);
					       //intent.addCategory(Intent.CATEGORY_HOME);
					       //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					       //startActivity(intent);
					}

					return null;
				}

				protected void onPostExecute(List<Data> result) {
					//pd.dismiss();
					Log.d("RESULT", result.get(3).getName());// <-chal raha hai yaha tk
					result1 = new ArrayList<Data>(result);
					// Get a ListView from main view
					/*ListView itcItems = (ListView) mContext.findViewById(R.id.streamList);*/
					 adapter = new ListAdapter(getActivity(),
							R.layout.fragment_main_dummy, result);
					// Create a list adapter
					itcItems.setAdapter(adapter);
					
					getImage(db);
					
				}

				
			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		
		public static boolean isOnline(Context c) {
			
		      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
		      return ( netInfo != null && netInfo.isConnected() );
		}
		
		void getDataInDataBase(final DataBaseHandler db){
			/*final ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
			pd.setTitle("Loading Resturant List...");

            pd.setCancelable(false);

            pd.setIndeterminate(true);

            pd.setMessage("Please Wait");*/
			AsyncTask<Void, Void, List<Data>> task = new AsyncTask<Void, Void, List<Data>>() {

				 /*@Override
                 protected void onPreExecute() {

                         pd.show();

                 }*/
				protected List<Data> doInBackground(Void... urls) {
					

					// Debug the task thread name
					Log.d("ITCRssReader", "inside");

					try {
						// Create RSS reader
						//RssReader rssReader = new RssReader("http://findaway.in/card2/restlist.xml");
						//Log.d("ITCRssReader", "inside1");
						// Parse RSS, get items
						// Log.d("ITCRssReader", rssReader.getItems().get(3).getName());
						return db.getAllData();

					} catch (Exception e) {
						Toast.makeText(getActivity(), "No Database found",
		                        Toast.LENGTH_LONG).show();
						  // Intent intent = new Intent(Intent.ACTION_MAIN);
					      // intent.addCategory(Intent.CATEGORY_HOME);
					      // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					      // startActivity(intent);
					}

					return null;
				}

				protected void onPostExecute(List<Data> result) {
					
					//pd.dismiss();
					result2 = new ArrayList<Data>(result);
					Log.d("RESULTdb", result2.get(0).getTerm());// <-chal raha hai yaha tk
					
					// Get a ListView from main view
					/*ListView itcItems = (ListView) mContext.findViewById(R.id.streamList);*/
					 adapter = new ListAdapter(getActivity(),
							R.layout.fragment_main_dummy, result);
					// Create a list adapter
					itcItems.setAdapter(adapter);
					
					
				}
			
		};
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
		void getImage(final DataBaseHandler db){
			AsyncTask<Void, Void, DataBaseHandler> task = new AsyncTask<Void, Void, DataBaseHandler>() {
				

			protected DataBaseHandler doInBackground(Void... urls) {

				// Debug the task thread name
				Log.d("ITCRssReader", "inside");

				try {
					// Create RSS reader
					for(Data d:result1){
						Data newdata = new Data();
						newdata.setIndex(d.getIndex());
						newdata.setName(d.getName());
						newdata.setLocation(d.getLocation());
						newdata.setDiscount(d.getDiscount());
						newdata.setDescription(d.getDescription());
						newdata.setValidity(d.getValidity());
						newdata.setAddress(d.getAddress());
						newdata.setPhone(d.getPhone());
						newdata.setCoupon(d.getCoupon());
						newdata.setTerm(d.getTerm());
						Log.d("term",d.getTerm());
						newdata.setLatLong((String)d.getLatLong());
						newdata.setZomato(d.getZomato());
						//byte[] bimage = getLogoImage(d.getImage());
						//newdata.setBimage(bimage);
						
						db.addData(newdata);
						Log.d("Inserting","Inserting.."+newdata.getName());
					}
				} catch (Exception e) {
					Log.d("ITCRssReader", "error");
				}
				return null;

				
			}

			private byte[] getLogoImage(String url) {
				// TODO Auto-generated method stub
				byte[] photo=null;
				try {
				    URL imageUrl = new URL(url);
				    URLConnection ucon = imageUrl.openConnection();
				    //System.out.println("11111");
				    InputStream is = ucon.getInputStream();
				    //System.out.println("12121");

				    BufferedInputStream bis = new BufferedInputStream(is);
				    //System.out.println("22222");

				    ByteArrayBuffer baf = new ByteArrayBuffer(128);
				    int current = 0;
				    //System.out.println("23333");

				    while ((current = bis.read()) != -1) {
				        baf.append((byte) current);

				    }
				    photo = baf.toByteArray();
				    //Log.d("image",baf.toString());
				    //System.out.println("photo length" + photo);
				    //return photo;
				    
				} catch (Exception e) {
				    Log.d("ImageManager", "Error: " + e.toString());
				}
				//return photo;
				return photo;
				
			}
			
		};
		task.execute();
	}

		
	}

	
    public static class FeedbackFragment extends Fragment implements
    OnInfoWindowClickListener,
    OnMarkerClickListener, 
    GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener,
    LocationListener {
	GoogleMap map;
	LatLng latlng;
	private LocationRequest lr;
	private LocationClient lc;
	MapFragment mapFragment;
	private static View view;
	ArrayList<Data> MapList = new ArrayList<Data>();

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		//ListView itcItems;
		
		public FeedbackFragment() {
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			 if (view != null) {
	                ViewGroup parent = (ViewGroup) view.getParent();
	                if (parent != null)
	                    parent.removeView(view);
	            }
			//View rootView = inflater.inflate(R.layout.fragment_feedback,
					//container, false);
			try {
                view = inflater.inflate(R.layout.fragment_feedback,
    					container, false);

                mapFragment = ((MapFragment) this.getActivity()
                        .getFragmentManager().findFragmentById(R.id.mapfragment));
                //iv = (ImageView) view.findViewById(R.id.iv);

                map = mapFragment.getMap();
               // map.getUiSettings().setAllGesturesEnabled(false);
                //map.getUiSettings().setMyLocationButtonEnabled(false);
                final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );

               if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }

                map.setMyLocationEnabled(true);
                
				Location my = map.getMyLocation();
				LatLng latlong = new LatLng(my.getLatitude(),my.getLongitude());
				Marker m =map.addMarker(new MarkerOptions().position(latlong).title(
		                    "my Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		            		m.showInfoWindow();
		            		
		            //map.addMarker(m);
		            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 13));
                //map.getUiSettings().setZoomControlsEnabled(false);

                MapsInitializer.initialize(this.getActivity());
            } catch (GooglePlayServicesNotAvailableException e) {
                Toast.makeText(getActivity(), "Google Play Services missing !",
                        Toast.LENGTH_LONG).show();
            } catch (InflateException e) {
                Toast.makeText(getActivity(), "Problems inflating the view !",
                        Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), "Google Play Services missing !",
                        Toast.LENGTH_LONG).show();
            }
			if(isOnline(getActivity()))
			{
			try{
			getDataInAsyncTask();
			
			}catch(Exception e){
				Toast.makeText(getActivity(), "Slow or No Internet Connection",
                        Toast.LENGTH_LONG).show();
			}
			}
			//Log.d("done","async");
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
						    
				public void onInfoWindowClick(Marker marker){
                    // if marker source is clicked
                	
					LatLng lat = marker.getPosition();
					//Log.d("latlong",String.format("%.6f",(lat.latitude))+","+String.format( "%.6f",(lat.longitude)));
					String cordinates = (String.format("%.6f",(lat.latitude))+","+String.format( "%.6f",(lat.longitude)));
					for(Data dd:MapList)
                	{
                		if(dd.getLatLong().equals(cordinates))
                		{
                			Intent intent = new Intent(view.getContext(), Description.class);
            	            Bundle item = new Bundle();
            	            ArrayList<String> Name = new ArrayList<String>();
            	            ArrayList<String> LL=new ArrayList<String>();
            	            if(isOnline(getActivity()))
            	            {
            	           // f="1";
            	            for(int i=0;i<MapList.size();i++)
            				{
            					//Log.d("adding..","name:"+MapList.get(i).getName());
            	            	Name.add(result1.get(i).getName());
            	            	//Log.d("adding..","name:"+MapList.get(i).getLatLong());
            					LL.add(result1.get(i).getLatLong());
            				
            				}
            	            item.putString("Logo1", dd.getImage());
            	            //String describe = result1.get(position).getName()+"\n Address: "+result1.get(position).getAddress()+"\n Phone: "
            	            //+result1.get(position).getPhone()+"Zomato Link : " + result1.get(position).getZomato();
            	            //item.putString("Description",describe);
            	            //item.putString("latlong",result1.get(position).getLatLong());
            	            //intent.putExtra("Logo2", "");
            	            item.putString("latlong",dd.getLatLong());
            	            item.putString("Name", dd.getName());
            	            item.putString("Address",dd.getAddress());
            	            item.putString("Phone",dd.getPhone());
            	            item.putString("Zomato",dd.getZomato());
            	            item.putString("Coupon",dd.getCoupon());
            	            item.putString("Discount",dd.getDiscount());
            	            item.putString("Term", dd.getTerm());
            	            item.putString("Validity",dd.getValidity());
            	            item.putStringArrayList("Listname", (ArrayList<String>) Name);
            	            item.putStringArrayList("Listcord", (ArrayList<String>) LL);
            	            
            	            
            	            }
            	            
            	            else{
            	            
            	            for(Data d: MapList )
            				{
            					Name.add(d.getName());
            					LL.add(d.getLatLong());
            				}
            	            item.putString("latlong",dd.getLatLong());
            	            item.putString("Name", dd.getName());
            	            item.putString("Address",dd.getAddress());
            	            item.putString("Phone",dd.getPhone());
            	            item.putString("Zomato",dd.getZomato());
            	            item.putString("Coupon", dd.getCoupon());
            	            item.putString("Discount",dd.getDiscount());
            	            item.putString("Term", dd.getTerm());
            	            
            	            item.putString("Validity",dd.getValidity());
            	            item.putStringArrayList("Listname", (ArrayList<String>) Name);
            	            item.putStringArrayList("Listcord", (ArrayList<String>) LL);
            	           }
            	            //String f = Integer.toString(flag);
            	            //item.putString("flag",f);
            	            intent.putExtras(item);
            	            view.getContext().startActivity(intent);
                		}
                	}
                 // true;        
                }

            });  
            return view;
        
			/*  
	        final EditText editTextSubject=(EditText)rootView.findViewById(R.id.editText1);  
	        final EditText editTextMessage=(EditText)rootView.findViewById(R.id.editText2);  
	          
	        Button send=(Button)rootView.findViewById(R.id.button1);  
	          
	        send.setOnClickListener(new OnClickListener(){  
	  
	            @Override  
	            public void onClick(View arg0) {  
	                 String to="findawayteam@gmail.com";  
	                 String subject=editTextSubject.getText().toString();  
	                 String message=editTextMessage.getText().toString();  
	                   
	                  if(subject!=null && message!=null)
	                  {
	                 Intent email = new Intent(Intent.ACTION_SEND);  
	                  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});  
	                  email.putExtra(Intent.EXTRA_SUBJECT, subject);  
	                  email.putExtra(Intent.EXTRA_TEXT, message);  
	       
	                  //need this to prompts email client only  
	                  email.setType("message/rfc822");  
	       
	                  startActivity(Intent.createChooser(email, "Choose an Email client.."));  
	                  }else{Toast.makeText(getActivity(), "Please fill all the fields first",
	                          Toast.LENGTH_SHORT).show();}
	            }  
	              
	        });*/
			
			//map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapfragment))
		           //.getMap();
			
		        //...

			
			//return rootView;
		}
		 @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            lr = LocationRequest.create();
	           // lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	            lc = new LocationClient(this.getActivity().getApplicationContext(),
	                    this, this);
	            lc.connect();
	        }
		 private void buildAlertMessageNoGps() {
			    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
			    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
			           .setCancelable(false)
			           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
			                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			               }
			           })
			           .setNegativeButton("No", new DialogInterface.OnClickListener() {
			               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
			                    dialog.cancel();
			               }
			           });
			    final AlertDialog alert = builder.create();
			    alert.show();
			}
		void getDataInAsyncTask(){
			final ProgressDialog pd = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);

            pd.setTitle("Loading ...");

           pd.setCancelable(false);

            pd.setIndeterminate(true);

            pd.setMessage("Please wait while we load nearby restaurants");
				AsyncTask<Void, Void, List<Data>> task = new AsyncTask<Void, Void, List<Data>>() {

					
                    @Override
                    protected void onPreExecute() {

                        pd.show();
                            
                    }
				protected List<Data> doInBackground(Void... urls) {

					// Debug the task thread name
					Log.d("ITCRssReader", "inside");

					try {
						// Create RSS reader
						RssReader rssReader = new RssReader("http://findaway.in/card/restaurants/feed.xml");
						Log.d("ITCRssReader", "inside1");
						// Parse RSS, get items
						// Log.d("ITCRssReader", rssReader.getItems().get(3).getName());
						return rssReader.getItems();

					} catch (Exception e) {
						Toast.makeText(getActivity(), "Slow or No Internet Connection",
		                        Toast.LENGTH_LONG).show();
						   Intent intent = new Intent(Intent.ACTION_MAIN);
					       intent.addCategory(Intent.CATEGORY_HOME);
					       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					       startActivity(intent);
					}

					return null;
				}

				protected void onPostExecute(List<Data> result) {
					
					
					Log.d("Map",result.size()+"");// <-chal raha hai yaha tk
					MapList = new ArrayList<Data>(result);
					
					// Get a ListView from main view
					
					flood();					
					pd.dismiss();
						
				}

				
			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		public void flood(){
			int i=0;
			try{
				
			for(Data d: MapList)
			{
				if(d.getLatLong().contains(","))
				{
					String loc = d.getLatLong();
					String s[] = loc.split(",");
					Log.d("map latlong",i+" "+d.getLatLong());
					LatLng mylocation=new LatLng(Double.parseDouble(s[0]),Double.parseDouble(s[1]));
			//Log.d("Location","Double.parseDouble(s[0]),Double.parseDouble(s[1])");
			
					map.addMarker(new MarkerOptions().position(mylocation)
			        .title(MapList.get(i).getName()));
					//m.showInfoWindow();
			}
				i++;
			}
			}catch(Exception e){
				Log.d("exception","Arrayindex caught");
			}

		}

		@Override
        public void onLocationChanged(Location l2) {
			
			 LatLng myLaLn = new LatLng(l2.getLatitude(),
		                l2.getLongitude());
			 Marker m = map.addMarker(new MarkerOptions().position(myLaLn).title(
                    "My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            		m.showInfoWindow();
            		map.setMyLocationEnabled(true);
            //map.addMarker(m);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLaLn, 13));
            Log.d("inside","location");

		}

		public static boolean isOnline(Context c) {
			
		      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
		      return ( netInfo != null && netInfo.isConnected() );
		}
        @Override
        public void onConnectionFailed(ConnectionResult arg0) {

        }

        @Override
        public void onConnected(Bundle connectionHint) {
            lc.requestLocationUpdates(lr, this);

        }

        @Override
        public void onDisconnected() {

        }


		@Override
		public boolean onMarkerClick(Marker arg0) {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public void onInfoWindowClick(Marker arg0) {
			// TODO Auto-generated method stub
			
		}


		
		
    }
    public static class AboutusFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		//ListView itcItems;
		
		public AboutusFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_aboutus,
					container, false);
			TextView mytextview = (TextView)rootView.findViewById(R.id.AboutUs);
			String url = "https://www.findaway.in/card";
			String link = "<a href='"+url+"'>www.findaway.in/card</a>";
			String sourceString = "<p><b>You can discover restaurants where the Find A Way card works using this app.</b></p>" +
					"<p>The Find A Way card is an amazing discount card that keeps money IN your wallet and a child OUT of the streets.</p>"+
					"All proceeds from the card go to our partner NGO <b>Child and Women Care Society </b>to sponsor the education of underprivileged children."+
					"<p>To know more about card and how it works, visit "+link+"</p>Find A Way is a recognised non-profit organisation." +
					"To find more about us, visit us on web."; 
			mytextview.setText(Html.fromHtml(sourceString));
			ImageView img = (ImageView)rootView.findViewById(R.id.web);
			img.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v){
			        Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse("http://www.findaway.in/card"));
			        startActivity(intent);
			    }
			});
			ImageView img1 = (ImageView)rootView.findViewById(R.id.fb);
			img1.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v){
			        Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse("https://www.facebook.com/find.a.way"));
			        startActivity(intent);
			    }
			});
			ImageView img2 = (ImageView)rootView.findViewById(R.id.twitter);
			img2.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v){
			        Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse("https://twitter.com/findawayteam"));
			        startActivity(intent);
			    }
			});
			return rootView;
		}
    }
	
}
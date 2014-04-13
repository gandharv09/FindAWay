package iiitd.ngo.findaway;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class Map extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		
		Bundle extras = getIntent().getExtras();
		String current = extras.getString("latlng");
		String name = extras.getString("name");
		ArrayList<String> Name = new ArrayList<String>();
		Name = extras.getStringArrayList("Listname");
		ArrayList<String> LL = new ArrayList<String>(); 
		LL=extras.getStringArrayList("Listcord");
		try {
        // Loading map
        initilizeMap(current,Name,LL,name);

    } catch (Exception e) {
        e.printStackTrace();
    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		return true;
	}
	public void initilizeMap(String p,ArrayList<String> name,ArrayList<String> cord,String n)
	{
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		int i=0;
		for(i=0 ; i<name.size();i++)
		{
		String loc = cord.get(i);
		String s[] = loc.split(",");
		LatLng mylocation=new LatLng(Double.parseDouble(s[0]),Double.parseDouble(s[1]));
		Log.d("Location","Double.parseDouble(s[0]),Double.parseDouble(s[1])");
		
		map.addMarker(new MarkerOptions().position(mylocation)
		        .title(name.get(i)));
		}
		String current = p;
		String s[] = current.split(",");
		LatLng outlet=new LatLng(Double.parseDouble(s[0]),Double.parseDouble(s[1]));
		Marker m = map.addMarker(new MarkerOptions().position(outlet).title(n).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		m.showInfoWindow();
		map.setMyLocationEnabled(true);
		Log.d("showing","outlet");
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(outlet, 13));
	
	}
}

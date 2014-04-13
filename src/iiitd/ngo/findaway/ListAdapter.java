package iiitd.ngo.findaway;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;



public class ListAdapter extends ArrayAdapter<Data> implements Filterable{

  // List context
   Context context;
   // List values
   List<Data> foodList;
   //LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
   
   private ArrayList<Data> datavalues;
   private DataFilter filter;
   Typeface lig;
   Typeface reg;
   
  public ListAdapter(Context context, int resource , List<Data> result) {
      super(context, resource, result);
      //Log.d("RESULT2", result.get(3).getName());
      this.context = context;

      this.foodList = result;
      
	//if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
    	//	    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
    	  //		sort();
    		//    }
      /*lig = Typeface.createFromAsset(context.getAssets(),
              "Raleway-Light.otf");
      reg = Typeface.createFromAsset(context.getAssets(),
              "Raleway-Regular.otf");*/
      Log.d("RESULT2", foodList.get(3).getName());//<- ye bhe chal raha hai
  }

  public void sort()
  {
	  List<Data> SortList;
	  
	  
  }
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  //View rowView=convertView;
	 // ImageHolder holder = null;
      View rowView = convertView;
    
	  
	  //Log.d("list",obj.getName()); //<-not running
	  ImageHolder holder = null;
	  
	  if(rowView==null)
	  {
		  LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  rowView = inflater.inflate(R.layout.fragment_main_dummy,parent,false);
		  holder =  new ImageHolder();
		  holder.name = (TextView)rowView.findViewById(R.id.name);
		  
		  holder.discount = (TextView)rowView.findViewById(R.id.discount);
		  
		  holder.location= (TextView)rowView.findViewById(R.id.Type);
		  //holder.Logo=(ImageView)rowView.findViewById(R.id.Main_logo);
		 
	      
	     //holder.name.setTypeface(reg);
	      //holder.discount.setTypeface(lig);
	      //holder.location.setTypeface(lig);
		  rowView.setTag(holder);}
	  else{holder = (ImageHolder)rowView.getTag();}
	  
	  Data obj = foodList.get(position);
	  
	  holder.name.setText(obj.getName());
	  holder.discount.setText(obj.getDiscount());
	  holder.location.setText(obj.getLocation());
	  //String link = obj.getImage();
	  //new DownloadImageTask(holder.Logo).execute(link);
	  //holder.Logo.setImageBitmap(bm);
	  return rowView;

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

  @Override
  public int getCount()
  {
     return foodList.size();
  }
  
  public Data getItem (int pos){
	     return foodList.get(pos);
	}
  static class ImageHolder
  {
     
      TextView name;
      TextView location;
      TextView discount;
      ImageView Logo;
  }

  public Filter getFilter() {

      if (filter == null )
            filter  = new DataFilter();
      return filter ;
   }

     private class DataFilter extends Filter {

      @SuppressWarnings("unchecked")
	@Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
          // TODO Auto-generated method stub

          
    	  if (results.count == 0)
    	        notifyDataSetInvalidated();
    	    else {
    	        foodList = (List<Data>) results.values;
    	        notifyDataSetChanged();
    	    }
    	 }

      @Override
    
      protected FilterResults performFiltering(CharSequence constraint) {

          FilterResults r = new FilterResults();
          
          if(datavalues==null){
        	  datavalues = new ArrayList<Data>(foodList);
          }
          
          if (constraint == null || constraint.length() == 0) {

        	  synchronized(this){
                  r.values = datavalues;
                  r.count = datavalues.size();}
              }

          else {

             
              ArrayList<Data> list = new ArrayList<Data>();
              String prefix = constraint.toString().toLowerCase();

              for (Data d : datavalues) { 

                  if (d.getName().toLowerCase().contains(prefix) || d.getLocation().toLowerCase().contains(prefix)) {
                      list.add(d);
                  }

              }

              r.values = list;
              r.count = list.size();

          }
          
          return r;
          
      }
}
}
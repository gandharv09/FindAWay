package iiitd.ngo.findaway;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper{

	private static final int DB_VERSION = 1;
	private static final String DB_NAME="data.db";
	private static final String TABLE_DB = "eatries";
	private static String DB_PATH = "/data/data/iiitd.ngo.findaway/databases/";
	private SQLiteDatabase myDataBase; 

    private final Context mycontext;
	//eatries table ^ column names
	
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_DISCOUNT = "discount";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_VALIDITY = "validity";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_LATLONG = "latlong";
	private static final String KEY_ZOMATO = "zomato";
	private static final String KEY_COUPON = "coupon";
	private static final String KEY_TERM= "term";
	private static final String KEY_IMAGE = "image";
	
	
	public DataBaseHandler(Context context)
	{
		super(context,DB_NAME,null,DB_VERSION);
		this.mycontext=context;
	}
	
	


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = "CREATE TABLE "+TABLE_DB + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
				+ " TEXT," + KEY_LOCATION + " TEXT," + KEY_DISCOUNT
				+ " TEXT," + KEY_DESCRIPTION + " TEXT,"+ KEY_VALIDITY + " TEXT," 
				+ KEY_ADDRESS + " TEXT," + KEY_PHONE + " TEXT," 
				+ KEY_LATLONG + " TEXT,"+ KEY_ZOMATO + " TEXT,"  + KEY_COUPON+" TEXT,"
				+ KEY_TERM + " TEXT,"+ KEY_IMAGE + " BLOB" + ")";
		db.execSQL(CREATE_TABLE);
	}

	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }
	}
	private void copyDataBase() throws IOException{

	    //Open your local db as the input stream
		InputStream myInput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
	}
	
	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

	    boolean checkdb = false;
        try{
            String myPath = mycontext.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator + DB_NAME;
            File dbfile = new File(myPath);                
            checkdb = dbfile.exists();
        }
        catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }

        return checkdb;
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS"+ TABLE_DB);
		onCreate(db);
	}
	 public void readDataBase() throws SQLException{
		 
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		 
		}
	 public void openDataBase() throws SQLException{
		 
			//Open the database
		 String myPath = DB_PATH + DB_NAME;
	        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

			}
	 public synchronized void close() {

         if(myDataBase != null)
             myDataBase.close();

         super.close();

 }
	 public void delete()
		{
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_DB, null, null);
			db.close();
		}
	 
	public void addData(Data d)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME,d.getName());
		values.put(KEY_LOCATION,d.getLocation());
		values.put(KEY_DISCOUNT,d.getDiscount());
		values.put(KEY_DESCRIPTION,d.getDescription());
		values.put(KEY_VALIDITY,d.getValidity());
		values.put(KEY_ADDRESS,d.getAddress());
		values.put(KEY_PHONE,d.getPhone());
		values.put(KEY_LATLONG,d.getLatLong());
		values.put(KEY_ZOMATO,d.getZomato());
		values.put(KEY_COUPON, d.getCoupon());
		values.put(KEY_TERM,d.getTerm());
		values.put(KEY_IMAGE,d.getBimage());
		//Log.d("dbhandle",d.ge)
		db.insert(TABLE_DB, null, values);
		db.close();
	}
	
	Data getData(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DB, new String[] {KEY_ID, KEY_NAME,KEY_LOCATION,KEY_DISCOUNT,KEY_DESCRIPTION,KEY_VALIDITY,KEY_ADDRESS,
				KEY_PHONE,KEY_LATLONG,KEY_ZOMATO,KEY_COUPON,KEY_TERM,KEY_IMAGE},
		KEY_ID + "=?", new String[] {String.valueOf(id)},null,null,null,null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
	Data d = new Data(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getBlob(12));
	return d;
	}

	public List<Data> getAllData(){
		List<Data> foody = new ArrayList<Data>();
		String query = "SELECT * FROM eatries ORDER BY id";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do{
				Data d = new Data();
				d.setIndex(cursor.getString(0));
				d.setName(cursor.getString(1));
				d.setLocation(cursor.getString(2));
				d.setDiscount(cursor.getString(3));
				d.setDescription(cursor.getString(4));
				d.setValidity(cursor.getString(5));
				d.setAddress(cursor.getString(6));
				d.setPhone(cursor.getString(7));
				d.setLatLong(cursor.getString(8));
				d.setZomato(cursor.getString(9));
				d.setCoupon(cursor.getString(10));
				d.setTerm(cursor.getString(11));
				d.setBimage(cursor.getBlob(12));
				foody.add(d);
			  }while(cursor.moveToNext());
		}
		db.close();
		return foody;
	}

}

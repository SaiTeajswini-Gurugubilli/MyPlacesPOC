package com.example.mobility.myplacespoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;


public class DataBaseHandler extends SQLiteOpenHelper {
    public static final int version = 1;
    public static final String name = "Place";

    public static final String table_name = "place_Details";
    public static final String place_id = "id";
    public static final String place_name = "name";
    public static final String place_address = "address";
    public static final String place_phone = "phone";
    public static final String place_rating = "rating";
    public static final String place_Uri = "uri";
    public static  final String place_lat = "latitude";
    public static  final String place_lng= "longitude";
    public static final String place_fav = "favourite";

Context mcon;
    public DataBaseHandler(Context context) {
        super(context, name, null, version);
        this.mcon = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/*
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "(" + place_id + " TEXT PRIMARY KEY," + place_name + " TEXT," + place_phone + " TEXT," + place_rating + " TEXT," + place_Uri + " TEXT,"+place_address+" TEXT,"+place_lat+" TEXT,"+place_lng+" TEXT,"+place_fav+" INTEGER)");
*/
        db.execSQL("CREATE TABLE IF NOT EXISTS place_Details(id TEXT PRIMARY KEY,name TEXT,phone TEXT,rating TEXT,uri TEXT,address TEXT,latitude TEXT,longitude TEXT,favourite INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }

    /*public void addPlace(String mId, String mName, String mPhone, String mRating, String mWebsite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(place_id, mId);
        values.put(place_name, mName);
        values.put(place_phone, mPhone);
        values.put(place_rating, mRating);
        values.put(place_Uri, mWebsite);
        db.insert(table_name, null, values);

        db.close();
    }*/

    public ArrayList<NearByPlaces> retiveAll() {
        ArrayList<NearByPlaces> list = new ArrayList<>();
        String selectQuery = "select * from " + table_name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                NearByPlaces place = new NearByPlaces();
                place.setPlaceId(cursor.getString(0));
                place.setPlaceName(cursor.getString(1));
                place.setPhoneNumber(cursor.getString(2));
                place.setRating(Float.parseFloat(cursor.getString(3)));
                place.setWebsiteUri(Uri.parse(cursor.getString(4)));
                place.setAddress(cursor.getString(5));
                place.setLatitude(Double.parseDouble(cursor.getString(6)));
                place.setLongitude(Double.parseDouble(cursor.getString(7)));
                list.add(place);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("DROP TABLE IF EXISTS "+table_name);
    }

    public void addPlace(NearByPlaces nearByPlaces) {
       /* String id = nearByPlaces.getPlaceId();
        String name = nearByPlaces.getPlaceName();
        String phone = nearByPlaces.getPhoneNumber();
        String rating = String.valueOf(nearByPlaces.getRating());
        String uri = String.valueOf(nearByPlaces.getWebsiteUri());
        String address = nearByPlaces.getAddress();
        String lat = String.valueOf(nearByPlaces.getLatitude());
        String lng = String.valueOf(nearByPlaces.getLongitude());
        int fav = 0;
*/
      /*  String query = "INSERT INTO place_Details VALUES('"+nearByPlaces.getPlaceId()+"', '"+nearByPlaces.getPlaceName()+"', '"+nearByPlaces.getPhoneNumber()+"', '"+nearByPlaces.getRating()+"', '"+String.valueOf(nearByPlaces.getWebsiteUri())+"', '"+nearByPlaces.getAddress()+"', '"+String.valueOf(nearByPlaces.getLatitude())+"', '"+ String.valueOf(nearByPlaces.getLongitude())+"', "+0+");";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);*/
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",nearByPlaces.getPlaceId());
        values.put("name",nearByPlaces.getPlaceName());
        values.put("phone",nearByPlaces.getPhoneNumber());
        values.put("rating",String.valueOf(nearByPlaces.getRating()));
        values.put("uri", String.valueOf(nearByPlaces.getWebsiteUri()));
        values.put("address",nearByPlaces.getAddress());
        values.put("latitude",String.valueOf(nearByPlaces.getLatitude()));
        values.put("longitude",String.valueOf(nearByPlaces.getLongitude()));
        values.put("favourite",0);

        db.insert("place_Details", null, values);
        db.close();
    }

    public void makeFav(String mId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("favourite", 1);
        int rowsUpdates = db.update("place_Details",values,"id = ?",new String[]{String.valueOf(mId)});
        if(rowsUpdates ==1){
            Toast.makeText(mcon, "rows updated successfully", Toast.LENGTH_SHORT).show();
        }
/*mNumberOfRowsUpdated = mWritableDB.update(WORD_LIST_TABLE,
values, // new values to insert
KEY_ID + " = ?",
new String[]{String.valueOf(id)});
        db.execSQL("UPDATE place_Details SET favourite=1 WHERE id="+mId);*/
    }

    public ArrayList<NearByPlaces> retriveFav() {
        ArrayList<NearByPlaces> list = new ArrayList<>();
        String selectQuery = "select * from place_Details WHERE favourite = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                NearByPlaces place = new NearByPlaces();
                place.setPlaceId(cursor.getString(0));
                place.setPlaceName(cursor.getString(1));
                place.setPhoneNumber(cursor.getString(2));
                place.setRating(Float.parseFloat(cursor.getString(3)));
                place.setWebsiteUri(Uri.parse(cursor.getString(4)));
                place.setAddress(cursor.getString(5));
                place.setLatitude(Double.parseDouble(cursor.getString(6)));
                place.setLongitude(Double.parseDouble(cursor.getString(7)));
                list.add(place);
            } while (cursor.moveToNext());
        }
        return list;
    }
}


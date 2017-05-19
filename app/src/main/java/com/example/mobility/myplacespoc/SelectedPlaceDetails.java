package com.example.mobility.myplacespoc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectedPlaceDetails extends AppCompatActivity implements View.OnClickListener {

    String websiteuri,mId,mRating,mName,mAddress,mPhone;

    TextView mCall, mFavourite, mWebsite;
    private String PhotoRefURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    String photoreference = "&photoreference=";
    private String ImageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=";
    String KeyString = "&key=";
    private String Key = "&key=AIzaSyCOuAzVZAptyyX1SfzdNwXSORhrl7Eg8rw";
    private String TAG = SelectedPlaceDetails.class.getSimpleName();
    ArrayList<Photos> photosList;
    ArrayList<Images> imagesArrayList;

    private ImageAdapter adapter;
    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    ArrayList<Photos> photosArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_place_details);

        mCall = (TextView) findViewById(R.id.call);
        mFavourite = (TextView) findViewById(R.id.make_fav);
        mWebsite = (TextView) findViewById(R.id.website);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setPageMargin((int) getResources().getDimension(R.dimen.page_margin));
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);

        mCall.setOnClickListener(this);
        mFavourite.setOnClickListener(this);
        mWebsite.setOnClickListener(this);

        mId = getIntent().getStringExtra("id");
        mRating = getIntent().getStringExtra("rating");
        mName = getIntent().getStringExtra("name");
        String[] result = getIntent().getStringExtra("address").split(",");
        StringBuilder stBuilder  = new StringBuilder();
        for( int i =0; i<result.length;i++){
            stBuilder.append(result[i]);
            stBuilder.append(",");
            stBuilder.append("\n");
        }
        mAddress = stBuilder.toString();
        mPhone = getIntent().getStringExtra("phone");
        websiteuri = getIntent().getStringExtra("websiteUri");


        TextView nametxt = (TextView) findViewById(R.id.name);
        TextView addresstxt = (TextView) findViewById(R.id.address);
        TextView phonetxt = (TextView) findViewById(R.id.phonenumber);
        nametxt.setText(mName);
        addresstxt.setText(mAddress);
        phonetxt.setText(mPhone);

        photosList = new ArrayList<>();
        imagesArrayList = new ArrayList<>();
        getDetails();


    }

    private void getDetails() {
        new GetDetails().execute();
    }


    private class GetDetails extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler handle = new HttpHandler();

            String jsonStr = handle.serviceCall(PhotoRefURL+mId+Key);
            Log.e(TAG,"Response From Url :"+jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonobject = new JSONObject(jsonStr);
                    JSONObject result = jsonobject.getJSONObject("result");
                    JSONArray p1 = result.getJSONArray("photos");
                    int temp = p1.length();
                    String reference[] = new String[temp];
                    int width[] = new int[temp];
                    int height[] = new int[temp];

                    Log.d(TAG,"JSON ARRAY SIZE :: "+temp);
                    for(int i = 0; i< p1.length();i++){

                        JSONObject photo = p1.getJSONObject(i);

                        reference[i] = photo.getString("photo_reference");

                        width[i] = photo.getInt("width");
                        height[i] = photo.getInt("height");
                        Log.e(TAG,reference[i]);
                        Photos photo_Object = new Photos();
                        photo_Object.setHeight(height[i]);
                        photo_Object.setWidth(width[i]);
                        photo_Object.setReference(reference[i]);
                        photosList.add(photo_Object);

                    }

                    getImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Log.e(TAG,"Couldn't get Json from server......");

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);


           /* Intent intent = new Intent(MainActivity.this,ImagesActivity.class);
            intent.putExtra("data",photosList);
            startActivity(intent);*/

        }
    }

    private void getImages() {
        new GetImage().execute();
    }

    private class GetImage extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler handler = new HttpHandler();
            Log.d(TAG,"PHOTO REF ARRAY SIZE :: "+ photosList.size());
            for (int i = 0; i< photosList.size();i++) {

                Photos currentPhoto = photosList.get(i);
                Log.d("IMAGE_REF::::",currentPhoto.getReference());
                Bitmap jsonStr =   handler.imageServiceCall(ImageURL+currentPhoto.getWidth()+photoreference+currentPhoto.getReference()+Key);

                Images img = new Images();
                img.setImage(jsonStr);
                imagesArrayList.add(img);
                Log.d("IMAGE_ARRAY_SIZE::::", String.valueOf(imagesArrayList.size()));
            }

            Log.d(TAG,"SUCESSFULLY EXECUTED");
           // setAdapter(imagesArrayList);
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            /*recyclerview.setAdapter(adapter);
            Log.d(TAG,"SUCESSFULLY EXECUTED");*/
            adapter = new ImageAdapter(getBaseContext(), imagesArrayList,getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            circlePageIndicator.setFillColor(R.color.colorPrimary);
            circlePageIndicator.setViewPager(viewPager,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:

                Toast.makeText(this, "Make a Call", Toast.LENGTH_SHORT).show();

                break;
            case R.id.make_fav:
                //function
                //mFavourite.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_star_black,0,0);
                          addToFav(mId);
                break;
            case R.id.website:

                Log.e("WebsiteUri",websiteuri);

                if(websiteuri != null  && websiteuri.length()>6){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteuri));
                    startActivity(intent);
                }else{
                    Toast.makeText(this, getString(R.string.website_null_msg), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void deletefromFav(String mId) {
        DataBaseHandler db= new DataBaseHandler(SelectedPlaceDetails.this);
        db.deleteFav(mId);
    }

    private void addToFav(String mId) {
        DataBaseHandler db= new DataBaseHandler(SelectedPlaceDetails.this);
        db.makeFav(mId);
    }





    private void makeCall() throws SecurityException {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(mPhone));
        startActivity(callIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length >0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    makeCall();
                }else {
                    Log.e("Permissions","permission Denied");
                }
        }
    }
}

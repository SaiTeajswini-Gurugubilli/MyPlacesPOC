package com.example.mobility.myplacespoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RetrivingImageReference extends AppCompatActivity {

    String websiteuri,mId,mRating,mName,mAddress,mPhone;


    private String PhotoRefURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    String photoreference = "&photoreference=";
    private String ImageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=";
    String KeyString = "&key=";
    private String Key = "&key=AIzaSyCOuAzVZAptyyX1SfzdNwXSORhrl7Eg8rw";
    private String TAG = SelectedPlaceDetails.class.getSimpleName();
    ArrayList<Photos> photosList;
    ArrayList<Images> imagesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retriving_image_reference);
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
        photosList = new ArrayList<>();
    }
}

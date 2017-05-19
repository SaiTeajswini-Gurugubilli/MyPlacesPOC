package com.example.mobility.myplacespoc;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mobility on 07/04/17.
 */

public class PlacesDetails implements Serializable {
    int permission_request_code = 100;


    Context mContext;
    Activity mActivity;
  private  GoogleApiClient mGoogleApiClient,mApiclient;
    public PlacesDetails(Activity activity, Context context) {
        this.mContext = context;
        this.mActivity = activity;

    }


    ArrayList<NearByPlaces> mNearByPlaces = new ArrayList<>();

   public ArrayList<NearByPlaces> nearPlace() {

       ArrayList<NearByPlaces> list = null;
        /*if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},permission_request_code);

        }else {*/
           list =  callPlaceDetectionApi();
               return list;
    }

    private ArrayList<NearByPlaces> callPlaceDetectionApi() throws SecurityException{

        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);

       result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {

                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    Log.i("TAG", String.format("Place '%s' with" + "likelihood : '%g' latitude: '%g' and logitude : '%g' and id '%s'", placeLikelihood.getPlace().getName(), placeLikelihood.getLikelihood(), placeLikelihood.getPlace().getLatLng().latitude, placeLikelihood.getPlace().getLatLng().longitude, placeLikelihood.getPlace().getId()));
                    String name = (String) placeLikelihood.getPlace().getName();
                    String number = (String) placeLikelihood.getPlace().getPhoneNumber();
                    float rating = placeLikelihood.getPlace().getRating();
                    double latitude = placeLikelihood.getPlace().getLatLng().latitude;
                    double longitude = placeLikelihood.getPlace().getLatLng().longitude;
                    String address = (String) placeLikelihood.getPlace().getAddress();
                    String id = placeLikelihood.getPlace().getId();
                    Uri website = placeLikelihood.getPlace().getWebsiteUri();

                    NearByPlaces nearByPlaces = new NearByPlaces();
                    nearByPlaces.setAddress(address);
                    nearByPlaces.setPlaceId(id);
                    nearByPlaces.setLongitude(longitude);
                    nearByPlaces.setLatitude(latitude);
                    nearByPlaces.setPhoneNumber(id);
                    nearByPlaces.setPlaceName(name);
                    nearByPlaces.setRating(rating);
                    nearByPlaces.setWebsiteUri(website);


                    mNearByPlaces.add(nearByPlaces);
                    Log.e("SAISIZE1",String.valueOf(mNearByPlaces.size()));

                }
                placeLikelihoods.release();
            }
        });

        Log.e("SAISIZE2",String.valueOf(mNearByPlaces.size()));
            return mNearByPlaces;
    }
}


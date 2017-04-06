package com.example.mobility.myplacespoc;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {


    int permission_request_code= 100;
    private GoogleApiClient mGoogleApiClient;
    ArrayList<NearByPlaces> mNearByPlaces;
    private RecyclerView recyclerview;
    Button currentplace,navigatemap;
    GoogleMap mMap;
    HashMap<String,String> googlePlace;
    public MainActivityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);;

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();
        recyclerview = (RecyclerView)view.findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
        mNearByPlaces = new ArrayList<>();
        currentplace = (Button)view.findViewById(R.id.current_place);
        navigatemap = (Button)view.findViewById(R.id.navigate_map);
        currentplace.setOnClickListener(this);
        navigatemap.setOnClickListener(this);
        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    public void currentPlace(View view) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},permission_request_code);

        }else {
            callPlaceDetectionApi();
        }
    }

    private void callPlaceDetectionApi() throws SecurityException{
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            //Place places=null;
            DataBaseHandler db= new DataBaseHandler(getContext());
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                for(PlaceLikelihood placeLikelihood :placeLikelihoods){
                    Log.i("TAG",String.format("Place '%s' with"+"likelihood : '%g' latitude: '%g' and logitude : '%g' and id '%s'",placeLikelihood.getPlace().getName(),placeLikelihood.getLikelihood(),placeLikelihood.getPlace().getLatLng().latitude,placeLikelihood.getPlace().getLatLng().longitude,placeLikelihood.getPlace().getId()));
                    String name = (String)placeLikelihood.getPlace().getName();
                    String number = (String)placeLikelihood.getPlace().getPhoneNumber();
                    float rating = placeLikelihood.getPlace().getRating();
                    double latitude = placeLikelihood.getPlace().getLatLng().latitude;
                    double longitude = placeLikelihood.getPlace().getLatLng().longitude;
                    String address = (String) placeLikelihood.getPlace().getAddress();
                    String id = placeLikelihood.getPlace().getId();
                    Uri website = placeLikelihood.getPlace().getWebsiteUri();
                    /*String placeName, String phoneNumber, float rating, double latitude, double longitude, String address, String placeId, Uri websiteUri*/
                    /*db.dropTable();*/
                    db.addPlace(new NearByPlaces(id,name,number,rating,website,address,latitude,longitude));
                    mNearByPlaces.add(new NearByPlaces(id,name,number,rating,website,address,latitude,longitude));
                    // mNearByPlaces.add(new NearByPlaces((String)placeLikelihood.getPlace().getName(),(String)placeLikelihood.getPlace().getPhoneNumber(),placeLikelihood.getPlace().getRating(),placeLikelihood.getPlace().getLatLng().latitude,placeLikelihood.getPlace().getLatLng().longitude,(String)placeLikelihood.getPlace().getAddress(),placeLikelihood.getPlace().getId(),placeLikelihood.getPlace().getWebsiteUri()));

                }
                RecyclerView.Adapter adapter = new DataAdapter(getActivity().getBaseContext(),mNearByPlaces);
                recyclerview.setAdapter(adapter);
                placeLikelihoods.release();
            }
        });
    }

    public void navigateMap(View view) {
        NearByPlaces place = new NearByPlaces();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        MapFragment mapfragment = new MapFragment();

      /*  Bundle bundle = new Bundle();
       bundle.putSerializable("data",mNearByPlaces);
       mapfragment.setArguments(bundle);*/
        fragmentTransaction.replace(R.id.activitydetails,mapfragment);
        fragmentTransaction.addToBackStack("one");
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.current_place:
                currentPlace(v);
                break;
            case R.id.navigate_map:
                navigateMap(v);
                break;
            default:
                break;
        }

    }
}

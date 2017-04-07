package com.example.mobility.myplacespoc;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    int permission_request_code= 100;

    ArrayList<NearByPlaces> mNearByPlaces;
    //private RecyclerView recyclerview;
    Button currentplace,navigatemap;
  //  GoogleMap mMap;
   // HashMap<String,String> googlePlace;
    public MainActivityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        currentplace = (Button)view.findViewById(R.id.current_place);
        navigatemap = (Button)view.findViewById(R.id.navigate_map);
        currentplace.setOnClickListener(this);
        navigatemap.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();
        mNearByPlaces = new ArrayList<>();

       /* TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.near_palces));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.view_on_map));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);

        final PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount(),mNearByPlaces);
*/
      /*  mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();*/
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext()).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();

              /*  viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.arrayList(mNearByPlaces);

                viewPager.setCurrentItem(tab.getPosition());
                *//*switch (tab.getPosition()){
                    case 0:
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,new NearByPlacesFragment());
                        transaction.commit();
                        break;
                    case 1:
                         transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container,new MapFragment());
                        transaction.commit();
                        break;

                }*//*
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        return view;

    }
     /*recyclerview = (RecyclerView)view.findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
       recyclerview.setLayoutManager(layoutManager);
        mNearByPlaces = new ArrayList<>();
        currentplace = (Button)view.findViewById(R.id.current_place);
        navigatemap = (Button)view.findViewById(R.id.navigate_map);
        currentplace.setOnClickListener(this);
        navigatemap.setOnClickListener(this);
        return view;

    }*/
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
   public void currentPlace(View v) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},permission_request_code);

        }else {
            callPlaceDetectionApi();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            NearByPlacesFragment nearByPlacesFragment = new NearByPlacesFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",mNearByPlaces);
            nearByPlacesFragment.setArguments(bundle);
            transaction.replace(R.id.lin2,nearByPlacesFragment);
            transaction.commit();
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
                   // String placeName, String phoneNumber, float rating, double latitude, double longitude, String address, String placeId, Uri websiteUri
//                    db.dropTable();
                    NearByPlaces nearByPlaces = new NearByPlaces();
                    nearByPlaces.setAddress(address);
                    nearByPlaces.setPlaceId(id);
                    nearByPlaces.setLongitude(longitude);
                    nearByPlaces.setLatitude(latitude);
                    nearByPlaces.setPhoneNumber(number);
                    nearByPlaces.setPlaceName(name);
                    nearByPlaces.setRating(rating);
                    nearByPlaces.setWebsiteUri(website);


                    mNearByPlaces.add(nearByPlaces);
                    // mNearByPlaces.add(new NearByPlaces((String)placeLikelihood.getPlace().getName(),(String)placeLikelihood.getPlace().getPhoneNumber(),placeLikelihood.getPlace().getRating(),placeLikelihood.getPlace().getLatLng().latitude,placeLikelihood.getPlace().getLatLng().longitude,(String)placeLikelihood.getPlace().getAddress(),placeLikelihood.getPlace().getId(),placeLikelihood.getPlace().getWebsiteUri()));

                }
               /* RecyclerView.Adapter adapter = new DataAdapter(getActivity().getBaseContext(),mNearByPlaces);
                recyclerview.setAdapter(adapter);*/
                placeLikelihoods.release();
            }
        });
    }

    public void navigateMap(View view) {
        NearByPlaces place = new NearByPlaces();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        MapFragment mapfragment = new MapFragment();

        Bundle bundle = new Bundle();
       bundle.putSerializable("data",mNearByPlaces);
       mapfragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.lin2,mapfragment);
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

package com.example.mobility.myplacespoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


private ArrayList<NearByPlaces> mNearByPlaces;
    private GoogleApiClient mGoogleApiClient;
    public MapFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        init(view);
        return view;
    }

    private void init(View view){

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();
       /* PlacesDetails placesDetails = new PlacesDetails(getActivity(),getContext());
        mNearByPlaces = placesDetails.nearPlace();*/
       mNearByPlaces = (ArrayList<NearByPlaces>)getArguments().getSerializable("data");

        Log.e("SIZE",String.valueOf(mNearByPlaces.size()));
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        for(int i=0;i<mNearByPlaces.size();i++) {
            NearByPlaces place = mNearByPlaces.get(i);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.getLatitude(), place.getLongitude()))
                    .title(place.getPlaceName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLatitude(), place.getLongitude()),15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),1000,null);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}

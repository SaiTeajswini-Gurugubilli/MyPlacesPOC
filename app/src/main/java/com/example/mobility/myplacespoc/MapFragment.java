package com.example.mobility.myplacespoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    SupportMapFragment supportMapFragment;
    ArrayList<NearByPlaces> mNearByPlaces = new ArrayList<>();

    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Toast.makeText(getContext(),String.valueOf(mNearByPlaces.size()), Toast.LENGTH_SHORT).show();
        Log.e("SIZE",String.valueOf(mNearByPlaces.size()));
        /*supportMapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);*/
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
       /* for(int i=0;i<mNearByPlaces.size();i++) {
            NearByPlaces place = mNearByPlaces.get(i);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())).title(place.getPlaceName()).icon(BitmapDescriptorFactory.defaultMarker(R.drawable.ic_place)));
        }*/
    }
}

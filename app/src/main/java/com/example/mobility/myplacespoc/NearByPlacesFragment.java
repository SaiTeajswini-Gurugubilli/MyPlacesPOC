package com.example.mobility.myplacespoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;



public class NearByPlacesFragment extends Fragment {

    private RecyclerView recyclerview;
    private ArrayList<NearByPlaces> mNearByPlaces;
     private GoogleApiClient mGoogleApiClient;
    public NearByPlacesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View  view =  inflater.inflate(R.layout.fragment_near_by_places, container, false);

        /*PlacesDetails placesDetails = new PlacesDetails(getActivity(),getContext());
        mNearByPlaces = placesDetails.nearPlace();*/
        try {
            mNearByPlaces = (ArrayList<NearByPlaces>) getArguments().getSerializable("data");
            Log.e("saiSize", String.valueOf(mNearByPlaces.size()));
            recyclerview = (RecyclerView) view.findViewById(R.id.rv);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerview.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new DataAdapter(getActivity().getBaseContext(), mNearByPlaces);
            recyclerview.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

}

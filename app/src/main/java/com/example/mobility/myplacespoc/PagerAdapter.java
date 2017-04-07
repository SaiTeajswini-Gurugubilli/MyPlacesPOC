package com.example.mobility.myplacespoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;
    private ArrayList<NearByPlaces> mNearByplacesArrayList;

    public PagerAdapter(FragmentManager fm, int numOfTabs, ArrayList<NearByPlaces> mNearByPlaces) {
        super(fm);
        this.mNearByplacesArrayList = mNearByPlaces;
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                NearByPlacesFragment nearByPlacesFragment = new NearByPlacesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",mNearByplacesArrayList);
                nearByPlacesFragment.setArguments(bundle);
                return nearByPlacesFragment;
            case 1:
                MapFragment mapFragment = new MapFragment();
                 bundle = new Bundle();
                bundle.putSerializable("data",mNearByplacesArrayList);
                mapFragment.setArguments(bundle);
                return mapFragment;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }



}

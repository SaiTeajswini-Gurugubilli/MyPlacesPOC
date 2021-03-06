package com.example.mobility.myplacespoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class RetriveAll extends Fragment {


    public RetriveAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrive_all, container, false);
        DataBaseHandler db = new DataBaseHandler(getContext());
        ArrayList<NearByPlaces> allplaces = db.retriveFav();

        final ListView ta = (ListView)view.findViewById(R.id.rows);
        ta.setAdapter(new DetailsTableAdapter(getContext(),allplaces));
        return view;
    }

}

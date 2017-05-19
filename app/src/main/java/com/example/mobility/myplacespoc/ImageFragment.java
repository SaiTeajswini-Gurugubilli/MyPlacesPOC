package com.example.mobility.myplacespoc;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;


public class ImageFragment extends Fragment {
      ImageView image;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.images_list, container, false);
        image = (ImageView)view.findViewById(R.id.image);
        image.setImageBitmap((Bitmap) getArguments().getParcelable("img"));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SelectedImage.class);
                intent.putExtra("selected_image", (Serializable) image);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public static Fragment newInsatnce(Images images) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("img",images.getImage());
        fragment.setArguments(bundle);
        return fragment;
    }
}

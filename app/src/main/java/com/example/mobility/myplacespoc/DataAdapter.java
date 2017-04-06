package com.example.mobility.myplacespoc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<NearByPlaces> nearByPlacesArrayList;
    private Context mContext;

   /* private Place mPlace;
    private GoogleApiClient mGoogleApiClient;*/

    public DataAdapter(Context context, ArrayList<NearByPlaces> nearByPlacesArrayList) {
        this.nearByPlacesArrayList = nearByPlacesArrayList;
        this.mContext = context;
        /*this.mPlace = places;
        this.mGoogleApiClient = mGoogleApiClient;*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nearby_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        NearByPlaces currentObject = nearByPlacesArrayList.get(position);
        if (currentObject.getRating() >= 0 && currentObject.getRating() <= 5) {
            //holder.placeRating.setNumStars((int) currentObject.getRating());
            holder.placeRating.setRating(currentObject.getRating());
        } else {
            //holder.placeRating.setNumStars(0);
            holder.placeRating.setRating(0);
        }
        holder.placeName.setText(currentObject.getPlaceName());
        holder.placeAddress.setText(currentObject.getAddress());
        holder.placePhonenumber.setText(currentObject.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return nearByPlacesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView placeName;
        TextView placeAddress;
        TextView placePhonenumber;
        RatingBar placeRating;
        //TextView latitude,longitude;
        ImageView imgbtn;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            placePhonenumber = (TextView) view.findViewById(R.id.place_phone);
            placeRating = (RatingBar) view.findViewById(R.id.rating);
            placeName = (TextView) view.findViewById(R.id.place_name);
            imgbtn = (ImageView) view.findViewById(R.id.imagebtn);
            placeAddress = (TextView) view.findViewById(R.id.place_address);
            imgbtn.setOnClickListener(this);
            // placeLikelihood = (TextView)view.findViewById(R.id.place_likelihood);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imagebtn) {
                NearByPlaces currentplace = nearByPlacesArrayList.get(getAdapterPosition());
                double lat, lng;

                lat = currentplace.getLatitude();
                lng = currentplace.getLongitude();
                String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?daddr=%f,%f", lat, lng);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, SelectedPlaceDetails.class);
                NearByPlaces currentplace = nearByPlacesArrayList.get(getAdapterPosition());
                String address = currentplace.getAddress();
                String phonenumber = currentplace.getPhoneNumber();
                //Bitmap image =currentplace.getPlaceImage();
                String name = currentplace.getPlaceName();
                String website = String.valueOf(currentplace.getWebsiteUri());
                float rating = currentplace.getRating();
                String placeid = currentplace.getPlaceId();




                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("image",image);
                intent.putExtra("name", name);
                intent.putExtra("phone", phonenumber);
                intent.putExtra("address",address);
                intent.putExtra("websiteUri",website);
                intent.putExtra("rating",String.valueOf(rating));
                intent.putExtra("id",placeid);

                mContext.startActivity(intent);

            }
        }
    }
}


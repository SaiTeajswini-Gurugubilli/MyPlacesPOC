package com.example.mobility.myplacespoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class DetailsTableAdapter extends BaseAdapter {
   private final ArrayList<NearByPlaces> nearByPlacesArrayList;
    private LayoutInflater layoutinflater;

    public DetailsTableAdapter(Context context, ArrayList<NearByPlaces> nearByPlacesArrayList) {
        this.nearByPlacesArrayList = nearByPlacesArrayList;
        layoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nearByPlacesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return nearByPlacesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
      if(convertView == null) {
          convertView = layoutinflater.inflate(R.layout.favourites_list, null);
          holder = new ViewHolder();
          holder.name = (TextView)convertView.findViewById(R.id.rName);
          holder.phone = (TextView)convertView.findViewById(R.id.rPhone);
          holder.rating = (TextView)convertView.findViewById(R.id.rRating);
          holder.uri = (TextView)convertView.findViewById(R.id.uri);
          convertView.setTag(holder);
      }else{
          holder = (ViewHolder) convertView.getTag();
      }
        holder.name.setText(nearByPlacesArrayList.get(position).getPlaceName());
        holder.phone.setText(nearByPlacesArrayList.get(position).getPhoneNumber());
        holder.rating.setText(String.valueOf(nearByPlacesArrayList.get(position).getRating()));
        holder.uri.setText(String.valueOf(nearByPlacesArrayList.get(position).getWebsiteUri()));
        return convertView;
    }
    static class ViewHolder{
        TextView name,phone,rating,uri;

    }
}

package com.example.mobility.myplacespoc;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


public class NearByPlaces implements Parcelable{
    private String placeName;
    private String phoneNumber;
    private float rating;
    private double latitude,longitude;
    private String address;
    private String placeId;
    private Uri websiteUri;


    protected NearByPlaces(Parcel in) {
        placeName = in.readString();
        phoneNumber = in.readString();
        rating = in.readFloat();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        placeId = in.readString();
        websiteUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<NearByPlaces> CREATOR = new Creator<NearByPlaces>() {
        @Override
        public NearByPlaces createFromParcel(Parcel in) {
            return new NearByPlaces(in);
        }

        @Override
        public NearByPlaces[] newArray(int size) {
            return new NearByPlaces[size];
        }
    };

    public void setAddress(String address) {
        this.address = address;
    }

   /* public Bitmap getPlacePhoto() {
        return placePhoto;
    }

    public void setPlacePhoto(Bitmap placePhoto) {
        this.placePhoto = placePhoto;
    }*/

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public NearByPlaces() {

    }

    public String getPlaceId() {
        return placeId;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String  rating) {
        this.phoneNumber = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public String getAddress() {
        return address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(phoneNumber);
        dest.writeFloat(rating);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(address);
        dest.writeString(placeId);
        dest.writeParcelable(websiteUri, flags);
    }
}

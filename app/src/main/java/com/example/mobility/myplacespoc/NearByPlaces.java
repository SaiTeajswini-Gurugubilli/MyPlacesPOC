package com.example.mobility.myplacespoc;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;


public class NearByPlaces implements Serializable{
    private String placeName;
    private String phoneNumber;
    private float rating;
    private double latitude,longitude;
    private String address;
    private String placeId;
    private Uri websiteUri;



    public void setAddress(String address) {
        this.address = address;
    }

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

    public NearByPlaces(String placeId,String placeName, String phoneNumber, float rating, Uri websiteUri, String address,double latitude, double longitude) {
        this.placeName = placeName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.placeId = placeId;
        this.websiteUri = websiteUri;
    }

    public NearByPlaces(String placeName, String phoneNumber, float rating, double latitude, double longitude, String address) {
        this.placeName = placeName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
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

    public NearByPlaces(String placeName, float likelihood, String phonenumber, float rating) {

        this.placeName = placeName;
        this.phoneNumber = phonenumber;
        this.rating = rating;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
/*
    public String getPhoneNumber() {
        return  phoneNumber;
    }

    public void setPhoneNumber(String image) {
        this. phoneNumber = image;
    }*/


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







}

package com.example.mobility.myplacespoc;

import java.io.Serializable;

/**
 * Created by Mobility on 10/04/17.
 */

public class Photos  {
    private  String reference;
    private int height;
    private int width;

    public  String getReference() {
        return reference;
    }

    public  void setReference(String reference) {
        this.reference = reference;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

package com.example.mobility.myplacespoc;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Mobility on 10/04/17.
 */

public class Images implements Serializable{

    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

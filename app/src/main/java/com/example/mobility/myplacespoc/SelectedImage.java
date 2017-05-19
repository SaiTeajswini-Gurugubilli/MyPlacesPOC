package com.example.mobility.myplacespoc;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SelectedImage extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);
        image = (ImageView)findViewById(R.id.selectedimg);
        Bitmap img = getIntent().getParcelableExtra("selected_image");
        image.setImageBitmap(img);
    }
}

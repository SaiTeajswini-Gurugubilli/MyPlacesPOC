package com.example.mobility.myplacespoc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedPlaceDetails extends AppCompatActivity implements View.OnClickListener {

    String websiteuri,mId,mRating,mName,mAddress,mPhone;

    TextView mCall, mFavourite, mWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_place_details);

        mCall = (TextView) findViewById(R.id.call);

        mFavourite = (TextView) findViewById(R.id.make_fav);
        mWebsite = (TextView) findViewById(R.id.website);
        mCall.setOnClickListener(this);
        mFavourite.setOnClickListener(this);
        mWebsite.setOnClickListener(this);

        mId = getIntent().getStringExtra("id");
        mRating = getIntent().getStringExtra("rating");
        mName = getIntent().getStringExtra("name");
        String[] result = getIntent().getStringExtra("address").split(",");
        StringBuilder stBuilder  = new StringBuilder();
        for( int i =0; i<result.length;i++){
            stBuilder.append(result[i]);
            stBuilder.append(",");
            stBuilder.append("\n");
        }
        mAddress = stBuilder.toString();
        mPhone = getIntent().getStringExtra("phone");
        websiteuri = getIntent().getStringExtra("websiteUri");
        TextView nametxt = (TextView) findViewById(R.id.name);
        TextView addresstxt = (TextView) findViewById(R.id.address);
        TextView phonetxt = (TextView) findViewById(R.id.phonenumber);
        nametxt.setText(mName);
        addresstxt.setText(mAddress);
        phonetxt.setText(mPhone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:

                Toast.makeText(this, "Make a Call", Toast.LENGTH_SHORT).show();

                break;
            case R.id.make_fav:
                //function
                //mFavourite.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_star_black,0,0);
                addToFav(mId);
                break;
            case R.id.website:

                Log.e("WebsiteUri",websiteuri);

                if(websiteuri != null  && websiteuri.length()>6){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteuri));
                    startActivity(intent);
                }else{
                    Toast.makeText(this, getString(R.string.website_null_msg), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void addToFav(String mId) {
        DataBaseHandler db= new DataBaseHandler(SelectedPlaceDetails.this);
        db.makeFav(mId);
    }





    private void makeCall() throws SecurityException {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(mPhone));
        startActivity(callIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length >0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    makeCall();
                }else {
                    Log.e("Permissions","permission Denied");
                }
        }
    }
}

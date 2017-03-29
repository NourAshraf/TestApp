package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

public class StoresDetails extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTextViewName;
    private TextView mTextViewDes;
    private TextView mTextViewPhone;
    private Button mButtonCall;
    private Button mButtonLocation;
    private Button mButtonAds;
    private String name;
   private String description;
    private String phone;
    private String longitude;
    private String latitude;
    private String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_details2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onGetIntentData();
        onVariable();



    }

    private void onVariable() {
        mImageView= (ImageView) findViewById(R.id.ivStoriesDetails);
        mTextViewName= (TextView) findViewById(R.id.tvStoresDetailsName);
        mTextViewDes= (TextView) findViewById(R.id.tvStoresDetailsDes);
        mTextViewPhone= (TextView) findViewById(R.id.tvStoresNumber);
        mButtonCall= (Button) findViewById(R.id.button);
        mButtonLocation= (Button) findViewById(R.id.bStoresDetailsLocation);
        mButtonAds= (Button) findViewById(R.id.bStoresDetailsAds);
        mButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StoresAds.class);
                intent.putExtra("Name",name);
                startActivity(intent);
            }
        });
        mTextViewName.setText(name);
        mTextViewPhone.setText(phone);
        description=description.replace("\t","");
        description=description.replace("\n","");
        description=description.replace("\r","");
        Log.i(MainApp.Tag,description);
        mTextViewDes.setText(description);
        // mButtonCall.setText(phone);
       // mButtonLocation.setText(latitude);
        //mButtonLocation.setText(longitude);
        Picasso.with(StoresDetails.this).load(MainApp.ImagesUrl+photo).fit().into(mImageView);
    }
    private void onGetIntentData() {
         name = getIntent().getExtras().getString("Name");
         description = getIntent().getExtras().getString("Description");
         phone = getIntent().getExtras().getString("Phone");
         longitude = getIntent().getExtras().getString("longitude");
         latitude = getIntent().getExtras().getString("latitude");
         photo = getIntent().getExtras().getString("Photo");

    }

}

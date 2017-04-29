package com.example.nour.makssab.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public class StoresDetails extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageView;
    static final Integer CALL = 0x2;
    private TextView mTextViewName;
    private TextView mTextViewDes;
    private Button mButtonLocation;
    private Button mButtonAds;
    private String name;
   private String description;
    private String phone;
    private String longitude;
    private String latitude;
    private String photo;
    private TextView mTextViewPhone;
    private ImageView mImageViewBack;
    private String mType;
    private String mId;
    private String mCity;
    private String mUserId;
    private String mUserName;
    private StoresDetails mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_details2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onGetIntentData();
        onVariable();
    }

    private void onVariable() {
        mContext=StoresDetails.this;
        mImageView= (ImageView) findViewById(R.id.ivStoriesDetails);
        mTextViewName= (TextView) findViewById(R.id.tvStoresDetailsName);
        mTextViewDes= (TextView) findViewById(R.id.tvStoresDetailsDes);
        mTextViewPhone= (TextView) findViewById(R.id.tvPhone);
        mTextViewPhone.setOnClickListener(this);
        mButtonLocation= (Button) findViewById(R.id.bStoresDetailsLocation);
        mButtonLocation.setOnClickListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBackButton);
        mImageViewBack.setOnClickListener(this);
        mButtonAds= (Button) findViewById(R.id.bStoresDetailsAds);
        mButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StoresAds.class);
                intent.putExtra("Name",name);
                intent.putExtra("Type",mType);
                intent.putExtra("Id",mId);
                intent.putExtra("City",mCity);
                intent.putExtra("UserName",mUserName);
                intent.putExtra("UserId",mUserId);
                startActivity(intent);
            }
        });
        mTextViewName.setText(name);
        description=description.replace("\t","");
        description=description.replace("\n","");
        description=description.replace("\r","");
        mTextViewDes.setText(description);
        mTextViewPhone.setText("رقم الجوال:"+phone);
        Picasso.with(StoresDetails.this).load(MainApp.ImagesUrl+photo).fit().into(mImageView);
    }
    private void onGetIntentData() {
         name = getIntent().getExtras().getString("Name");
         description = getIntent().getExtras().getString("Description");
         phone = getIntent().getExtras().getString("Phone");
         longitude = getIntent().getExtras().getString("longitude");
         latitude = getIntent().getExtras().getString("latitude");
         photo = getIntent().getExtras().getString("Photo");
         mType = getIntent().getExtras().getString("Type");
         mId = getIntent().getExtras().getString("Id");
         mCity = getIntent().getExtras().getString("City");
         mUserName = getIntent().getExtras().getString("UserName");
         mUserId = getIntent().getExtras().getString("UserId");
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.bStoresDetailsLocation:
             String mLocation = latitude + "," +longitude ;
             String geoUri = "http://maps.google.com/maps?q=loc:" + mLocation + " (" + name + ")";
             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
             startActivity(intent);
             break;
         case R.id.tvPhone:
             askForPermission(Manifest.permission.CALL_PHONE,CALL);
             break;
         case R.id.ivBack:
             finish();
             break;
     }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);
            }
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(MainApp.getAppContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Call
                case 2:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    if (ActivityCompat.checkSelfPermission(MainApp.getAppContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                    break;
            }
            Log.i(MainApp.Tag, "Permission granted");
        } else {
            Log.i(MainApp.Tag, "Permission denied");
        }
    }
}

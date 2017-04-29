package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

public class Profile extends AppCompatActivity {
    private  String filename2="mkssab";
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewPhone;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String token;
    private String username;
    private String email;
    private String phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onGetIntentData();
        onVariables();



    }

    private void onGetIntentData() {
        username = getIntent().getExtras().getString("username");
        email=getIntent().getExtras().getString("email");
        phone=getIntent().getExtras().getString("phone");
    }

    private void onVariables() {
        mContext=Profile.this;
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        mTextViewName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewEmail= (TextView) findViewById(R.id.tvProfileEmail);
        mTextViewPhone= (TextView) findViewById(R.id.tvProfilePhone);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mTextViewName.setText(username);
        mTextViewEmail.setText(email);
        mTextViewPhone.setText(phone);

    }



}

package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    private  String filename2="mkssab";
    private TextView mTextViewName;
    private TextView mTextViewAds;
    private TextView mTextViewMember;
    private TextView mTextViewTime;
    private TextView mTextViewLast;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String token;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onGetIntentData();
        onVariables();



    }

    private void onGetIntentData() {
        username = getIntent().getExtras().getString("username");
    }

    private void onVariables() {
        mContext=Profile.this;
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        mTextViewName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewAds= (TextView) findViewById(R.id.tvProfileads);
        mTextViewMember= (TextView) findViewById(R.id.tvProfileMemebr);
        mTextViewTime= (TextView) findViewById(R.id.tvTime);
        mTextViewLast= (TextView) findViewById(R.id.Last);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mTextViewName.setText(username);


    }



}

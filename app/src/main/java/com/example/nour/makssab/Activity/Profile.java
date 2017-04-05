package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Model.ProfileModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private TextView mTextViewName;
    private TextView mTextViewAds;
    private TextView mTextViewMember;
    private TextView mTextViewTime;
    private TextView mTextViewLast;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private ArrayList<ProfileModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // onProfileData();
        onVariables();


    }

    private void onVariables() {
        mContext=Profile.this;
        mTextViewName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewAds= (TextView) findViewById(R.id.tvProfileads);
        mTextViewMember= (TextView) findViewById(R.id.tvProfileMemebr);
        mTextViewTime= (TextView) findViewById(R.id.tvTime);
        mTextViewLast= (TextView) findViewById(R.id.Last);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();

    }
//    public void onProfile(){
//        String Url= MainApp.ProfileUrl+token;
//        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        mVolleySingletonRequestQueue.add(mStringRequestonProfile);
//
//    }


}

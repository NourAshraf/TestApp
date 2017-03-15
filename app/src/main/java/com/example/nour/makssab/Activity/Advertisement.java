package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.AdvAdapter;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Advertisement extends AppCompatActivity {

    private RecyclerView mRecyclerViewAdv;
    private ArrayList<AdvModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private AdvAdapter mAdvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();

    }

    private void onVariables() {
        mContext=Advertisement.this;
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvAdv);
        mRecyclerViewAdv.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<AdvModel>();
        mAdvAdapter=new AdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        onLoadAdv();
    }

    private void onLoadAdv() {
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, MainApp.AdvUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    Log.i(MainApp.Tag,mJsonArray.length()+"");
                    for (int i=0;i<mJsonArray.length();i++){
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        int id = mJsonObject.getInt("id");
                        int city_id = mJsonObject.getInt("city_id");
                        int category_id = mJsonObject.getInt("category_id");
                        int views = mJsonObject.getInt("views");
                        String title = mJsonObject.getString("title");
                        String description = mJsonObject.getString("description");
                        String phone = mJsonObject.getString("phone");
                        JSONObject city = mJsonObject.getJSONObject("city");
                        String city_name = city.getString("name");
                        AdvModel advModel = new AdvModel(id, city_id, views, category_id, title, description, phone, city_name);
                        models.add(advModel);
                    }
                    mAdvAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                onLoadAdv();
                Log.i(MainApp.Tag,"Worked Error");
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }

}

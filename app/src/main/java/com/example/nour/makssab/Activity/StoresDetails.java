package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.AdvAdapter;
import com.example.nour.makssab.Adapter.StoresDetailsAdapter;
import com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Model.StoresDetailsModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoresDetails extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerViewStoresDetails;
    private ArrayList<StoresDetailsModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private StoresDetailsAdapter mStoresDetailsAdapter;
    private String next_page_url;
    private ArrayList<String> ImagesModels;
    private ProgressBar mProgressBar;
    private boolean mDelete;
    private ImageView mImageViewBack;
    private TextView mTextViewNoInternet;
    private SwipeRefreshLayout mSwipeRefreshLayoutAdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();


    }

    private void onVariables() {
        mDelete=false;
        mContext=StoresDetails.this;
        mSwipeRefreshLayoutAdv = (SwipeRefreshLayout) findViewById(R.id.srlAdv);
        mSwipeRefreshLayoutAdv.setOnRefreshListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentHome=new Intent(mContext,Home.class);
                startActivity(mIntentHome);
                finish();
            }
        });
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_adv:

                        break;
                    case R.id.tab_main:
                        Intent mIntentHome=new Intent(mContext,Home.class);
                        startActivity(mIntentHome);
                        finish();
                        break;
                    case R.id.tab_notify:
                        Intent mIntentNotification=new Intent(mContext,Notifications.class);
                        startActivity(mIntentNotification);
                        finish();
                        break;
                    case R.id.tab_message:

                        break;
                }
            }
        });
        mTextViewNoInternet= (TextView) findViewById(R.id.tvNoInternet);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewStoresDetails= (RecyclerView) findViewById(R.id.rvStoreDetails);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewStoresDetails.setLayoutManager(manager);
        mRecyclerViewStoresDetails.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<StoresDetailsModel>();
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        ImagesModels=new ArrayList<String>();
        mStoresDetailsAdapter=new StoresDetailsAdapter(mContext,models);
        mRecyclerViewStoresDetails.setAdapter(mStoresDetailsAdapter);
        onLoadStoresDetails(MainApp.StoresDetailsUrl);
        mRecyclerViewStoresDetails.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)

            {
                mDelete=true;
                onLoadStoresDetails(next_page_url);
            }
        });
    }

    private void onLoadStoresDetails(String storesDetailsUrl) {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest  mStringRequestStoresDetails=new StringRequest(Request.Method.GET, storesDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mRecyclerViewStoresDetails.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTextViewNoInternet.setVisibility(View.GONE);
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    next_page_url=mJsonObject.getString("next_page_url");
                    JSONArray merchant = mJsonObject.getJSONArray("merchant");
                    for (int i=0;i<merchant.length();i++) {
                        if (mDelete) {
                            mDelete = false;
                            ImagesModels = new ArrayList<String>();
                        }
                        JSONObject jsonObject=merchant.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        JSONArray comments_count = jsonObject.getJSONArray("comments_count");
                        int CommentCount=comments_count.length();
                       String name=jsonObject.getString("name");
                        String category_id = jsonObject.getString("category_id");
                        String created_at = jsonObject.getString("created_at");
                        jsonObject.getString("id");
                        String city_id = jsonObject.getString("city_id");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String views = jsonObject.getString("views");
                        String phone = jsonObject.getString("phone");
                        JSONObject city = jsonObject.getJSONObject("city");
                        String City_Name = city.getString("name");
                        JSONArray images = jsonObject.getJSONArray("images");
                        for (int j=0;j<images.length();j++) {
                            JSONObject jsonObject2 = images.getJSONObject(j);
                            String photo = jsonObject2.getString("photo");
                            ImagesModels.add(photo);
                        }
                        JSONObject user = jsonObject.getJSONObject("user");
                        String UserId = user.getString("id");
                        String username = user.getString("username");
                        StoresDetailsModel storesDetailsModel=new StoresDetailsModel(id,UserId,name,description,phone,images,created_at,username,city_id,category_id,title,views,comments_count);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    @Override
    public void onRefresh() {

    }
}

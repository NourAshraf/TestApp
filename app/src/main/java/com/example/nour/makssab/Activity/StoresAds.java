package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.StoresAdvAdapter;
import com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class StoresAds extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerViewAdv;
    private ArrayList<AdvModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private StoresAdvAdapter mAdvAdapter;
    private String next_page_url;
    private ArrayList<String> ImagesModels;
    private ProgressBar mProgressBar;
    private boolean mDelete;
    private ImageView mImageViewBack;
    private TextView mTextViewNoInternet;
    private SwipeRefreshLayout mSwipeRefreshLayoutAdv;
    private String name;
    private TextView mTextViewName;
    private String mType;
    private String mId;
    private String mCity;
    private String mUserName;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onGetIntentData();
        onVariables();
    }

    private void onGetIntentData() {
        name = getIntent().getExtras().getString("Name");
        mType = getIntent().getExtras().getString("Type");
        mId = getIntent().getExtras().getString("Id");
        mCity = getIntent().getExtras().getString("City");
        mUserName = getIntent().getExtras().getString("UserName");
        mUserId = getIntent().getExtras().getString("UserId");
    }

    private void onVariables() {
        mDelete=false;
        mContext=StoresAds.this;
        mTextViewName= (TextView) findViewById(R.id.tvStoresAdsName);
        mTextViewName.setText(name);
        mSwipeRefreshLayoutAdv = (SwipeRefreshLayout) findViewById(R.id.srlAdv);
        mSwipeRefreshLayoutAdv.setOnRefreshListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextViewNoInternet= (TextView) findViewById(R.id.tvNoInternet);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvStoresAds);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewAdv.setLayoutManager(manager);
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<AdvModel>();
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        ImagesModels=new ArrayList<String>();
        mAdvAdapter=new StoresAdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        if (mType.equals("1")){
            onLoadAdv(MainApp.BuildingDetailsUrl+mId);
        }else if (mType.equals("2")){
            onLoadAdv(MainApp.StoresDetailsUrl+mId);
        }else if (mType.equals("3")){
            onLoadAdv(MainApp.ExhibitionDetailsUrl+mId);
        }

        mRecyclerViewAdv.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)

            {
                if (next_page_url.equals("null")){

                }else {
                    mDelete=true;
                    onLoadAdv(next_page_url);
                }

            }
        });
    }


    private void onLoadAdv(final String StoresDetailsUrl) {
        Log.i(MainApp.Tag,StoresDetailsUrl);
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, StoresDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewNoInternet.setVisibility(View.GONE);
                    JSONObject mJsonObject=new JSONObject(response);
                    JSONObject jsonObject=mJsonObject.getJSONObject("ads");
                    next_page_url = jsonObject.getString("next_page_url");
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        if (mDelete){
                            mDelete=false;
                            ImagesModels = new ArrayList<String>();
                        }
                        JSONObject jsonObject2=data.getJSONObject(i);
                        JSONArray comments_count = jsonObject2.getJSONArray("comments");
                        int CommentCount=comments_count.length();
                        String id = jsonObject2.getString("id");
                        String created_at = jsonObject2.getString("created_at");
                        jsonObject2.getString("id");
                        String city_id = jsonObject2.getString("city_id");
                        String title = jsonObject2.getString("title");
                        String description = jsonObject2.getString("description");
                        String views = jsonObject2.getString("views");
                        String category_id = jsonObject2.getString("category_id");
                        String phone = jsonObject2.getString("phone");
                        JSONArray images = jsonObject2.getJSONArray("images");
                        for (int j=0;j<images.length();j++) {
                            JSONObject jsonObject3 = images.getJSONObject(j);
                            String photo = jsonObject3.getString("photo");
                            ImagesModels.add(photo);
                        }
                        AdvModel advModel=new AdvModel(id,city_id,views,category_id,title,description,phone,mCity,mUserId,mUserName,ImagesModels,created_at,CommentCount);
                        models.add(advModel);
                        if (true){
                            mDelete=true;
                        }
                    }
                    mAdvAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadAdv(StoresDetailsUrl);
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }



    @Override
    public void onRefresh() {
        current_page=1;
        previousTotal=0;
        loading=true;
        mRecyclerViewAdv.setVisibility(View.INVISIBLE);
        if (models!=null){
            models.clear();
        }
        onLoadAdv(MainApp.StoresDetailsUrl);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
            mSwipeRefreshLayoutAdv.setRefreshing(false);
        }

    }
}

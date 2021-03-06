package com.ibtdi.team.mkssab.Activity;

import android.content.Context;
import android.content.Intent;
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
import com.ibtdi.team.mkssab.Adapter.AdvAdapter;
import com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener;
import com.ibtdi.team.mkssab.Decoration.VerticalSpaceItemDecoration;
import com.ibtdi.team.mkssab.MainApp.MainApp;
import com.ibtdi.team.mkssab.Model.AdvModel;
import com.ibtdi.team.mkssab.Network.VolleySingleton;
import com.ibtdi.team.mkssab.R;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.Build.ID;
import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class CategoryDetails extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerViewAdv;
    private ArrayList<AdvModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private AdvAdapter mAdvAdapter;
    private String next_page_url;
    private ArrayList<String> ImagesModels;
    private ProgressBar mProgressBar;
    private boolean mDelete;
    private ImageView mImageViewBack;
    private TextView mTextViewNoInternet;
    private SwipeRefreshLayout mSwipeRefreshLayoutAdv;
    private String mId;
    private String Url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mId = getIntent().getExtras().getString("ID", "");
        onVariables();
    }

    private void onVariables() {
        Url=MainApp.CategoryDetailsUrl+mId;
        mDelete=false;
        mContext=CategoryDetails.this;
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
        mTextViewNoInternet= (TextView) findViewById(R.id.tvNoInternet);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvAdv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewAdv.setLayoutManager(manager);
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<AdvModel>();
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        ImagesModels=new ArrayList<String>();
        mAdvAdapter=new AdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        onLoadAdv(Url);
        mRecyclerViewAdv.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)

            {
                if (next_page_url.equals("null")){

                }else {
                    mDelete = true;
                    onLoadAdv(next_page_url);
                }
            }
        });
    }


    private void onLoadAdv(final String Url) {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewNoInternet.setVisibility(View.GONE);
                    JSONObject mJsonObject=new JSONObject(response);
                    next_page_url = mJsonObject.getString("next_page_url");
                    Log.i(MainApp.Tag,next_page_url);
                    JSONArray data = mJsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        if (mDelete){
                            mDelete=false;
                            ImagesModels = new ArrayList<String>();
                        }
                        JSONObject jsonObject=data.getJSONObject(i);
                        JSONArray comments_count = jsonObject.getJSONArray("comments_count");
                        int CommentCount=comments_count.length();
                        String id = jsonObject.getString("id");
                        String created_at = jsonObject.getString("created_at");
                        jsonObject.getString("id");
                        String city_id = jsonObject.getString("city_id");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String views = jsonObject.getString("views");
                        String category_id = jsonObject.getString("category_id");
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
                        AdvModel advModel=new AdvModel(id,city_id,views,category_id,title,description,phone,City_Name,UserId,username,ImagesModels,created_at,CommentCount);
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
                onLoadAdv(ID);
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }

    @Override
    public void onBackPressed() {
        Intent mIntentHome=new Intent(mContext,Home.class);
        startActivity(mIntentHome);
        finish();
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
    onLoadAdv(Url);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
        mSwipeRefreshLayoutAdv.setRefreshing(false);
    }
}
}

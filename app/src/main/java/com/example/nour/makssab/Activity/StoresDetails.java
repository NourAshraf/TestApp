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
import android.view.Menu;
import android.view.MenuItem;
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

import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

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

    private void onLoadStoresDetails(final String storesDetailsUrl) {
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
                        jsonObject.getString("id");
                        String city_id = jsonObject.getString("city_id");
                        String id=jsonObject.getString("id");
                       String name=jsonObject.getString("name");
                        String category_id = jsonObject.getString("category_id");
                        String created_at = jsonObject.getString("created_at");
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
                        String userId = user.getString("id");
                        String username = user.getString("username");
                        StoresDetailsModel storesDetailsModel=new StoresDetailsModel(id,userId,name,ImagesModels,description,phone,created_at,username,city_id,City_Name,category_id,title,views);
                        models.add(storesDetailsModel);
                        if (true){
                            mDelete=true;
                        }
                    }
                    mStoresDetailsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadStoresDetails(storesDetailsUrl);

            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestStoresDetails);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Intent mIntentSearch=new Intent(getApplicationContext(),Search.class);
                startActivity(mIntentSearch);
                break;

            case R.id.action_Adv_Favorites:
                Intent mIntentAdvFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                startActivity(mIntentAdvFavorites);
                break;

            case R.id.action_New_Account:
                Intent mIntentNewAccount=new Intent(getApplicationContext(),NewAccount.class);
                startActivity(mIntentNewAccount);
                break;

            case R.id.action_Login:
                Intent mIntentLogin=new Intent(getApplicationContext(),Login.class);
                startActivity(mIntentLogin);
                break;
        }
        return true;
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
        mRecyclerViewStoresDetails.setVisibility(View.INVISIBLE);
        if (models!=null){
            models.clear();
        }
        onLoadStoresDetails(MainApp.StoresDetailsUrl);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
            mSwipeRefreshLayoutAdv.setRefreshing(false);
        }


    }
}

package com.ibtdi.team.mkssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
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
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.ibtdi.team.mkssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class MyAdvertisement extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private  String filename2="mkssab";
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
    private SharedPreferences mSharedPreferences;
    private String token;
    private String UserId;
    private TextView mTextViewNoFav;

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
        mDelete=false;
        mContext=MyAdvertisement.this;
        UserId = getIntent().getExtras().getString("UserId");
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        mTextViewNoFav= (TextView) findViewById(R.id.tvNoFav);
        mSwipeRefreshLayoutAdv = (SwipeRefreshLayout) findViewById(R.id.srlAdv);
        mSwipeRefreshLayoutAdv.setOnRefreshListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setVisibility(View.GONE);
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
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvAdv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewAdv.setLayoutManager(manager);
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<AdvModel>();
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        ImagesModels=new ArrayList<String>();
        mAdvAdapter=new AdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        onLoadAdv(MainApp.UserProfileUrl+UserId+"?token="+token);
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
        mTextViewNoFav.setVisibility(View.VISIBLE);
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mTextViewNoFav.setVisibility(View.VISIBLE);
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewNoInternet.setVisibility(View.GONE);
                    JSONArray mJsonArray = new JSONArray(response);
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject = jsonObject.getJSONObject("ads");
                    next_page_url = mJsonObject.getString("next_page_url");
                    JSONArray data = mJsonObject.getJSONArray("data");
                    for (int j = 0; j < data.length(); j++) {
                        mTextViewNoFav.setVisibility(View.GONE);
                        if (mDelete) {
                            mDelete = false;
                            ImagesModels = new ArrayList<String>();
                        }
                        JSONObject jsonObject1 = data.getJSONObject(j);
                        JSONArray comments_count = jsonObject1.getJSONArray("comments_count");
                        int CommentCount = comments_count.length();
                        String id = jsonObject1.getString("id");
                        String created_at = jsonObject1.getString("created_at");
                        jsonObject1.getString("id");
                        String city_id = jsonObject1.getString("city_id");
                        String title = jsonObject1.getString("title");
                        String description = jsonObject1.getString("description");
                        String views = jsonObject1.getString("views");
                        String category_id = jsonObject1.getString("category_id");
                        String phone = jsonObject1.getString("phone");
                        JSONObject city = jsonObject1.getJSONObject("city");
                        String City_Name = city.getString("name");
                        JSONArray images = jsonObject1.getJSONArray("images");
                        for (int k = 0; k < images.length(); k++) {
                            JSONObject jsonObject2 = images.getJSONObject(k);
                            String photo = jsonObject2.getString("photo");
                            ImagesModels.add(photo);
                        }
                        JSONObject user = jsonObject1.getJSONObject("user");
                        String UserId = user.getString("id");
                        String username = user.getString("username");
                        AdvModel advModel = new AdvModel(id, city_id, views, category_id, title, description, phone, City_Name, UserId, username, ImagesModels, created_at, CommentCount);
                        models.add(advModel);
                        if (true) {
                            mDelete = true;
                        }
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
                mTextViewNoFav.setVisibility(View.GONE);
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadAdv(Url);
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String phpsessid = response.headers.get("Authorization");
//                String[] split = phpsessid.split(" ");
//                Log.i(MainApp.Tag,split[1]);
//                mSharedPreferences.edit().putString("token",split[1]).commit();
//                token=split[1];
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
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
    onLoadAdv(MainApp.UserProfileUrl+UserId+"?token="+token);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
        mSwipeRefreshLayoutAdv.setRefreshing(false);
    }
}
}

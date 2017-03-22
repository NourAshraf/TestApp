package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.AdvAdapter;
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

public class Advertisement extends AppCompatActivity {

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
        mContext=Advertisement.this;
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
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
        onLoadAdv(MainApp.AdvUrl);
        mRecyclerViewAdv.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)

            {
                mDelete=true;
               onLoadAdv(next_page_url);
            }
        });
    }

    private void onLoadAdv(String Url) {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mProgressBar.setVisibility(View.GONE);
                    JSONObject mJsonObject=new JSONObject(response);
                    next_page_url = mJsonObject.getString("next_page_url");
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
                Log.i(MainApp.Tag,"Worked Error");
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
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


}

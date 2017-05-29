package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
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

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class MemberFavorites extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
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
    private TextView mTextViewUsername;
    private Button mButtonProfile;
    private String username;
    private String email;
    private String phone_main;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onGetIntentData();
        onVariables();
    }
    private void onGetIntentData() {
        username = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");
        phone = getIntent().getExtras().getString("phone");
    }

    private void onVariables() {
        mDelete=false;
        mContext=MemberFavorites.this;
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        mTextViewUsername= (TextView) findViewById(R.id.tvProfileName1);
        mTextViewUsername.setText(username);
        mButtonProfile= (Button) findViewById(R.id.bProfile);
        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext,Profile.class);
                mIntent.putExtra("username",username+"");
                mIntent.putExtra("email",email);
                mIntent.putExtra("phone",phone);
                startActivity(mIntent);
            }
        });
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
//        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                switch (tabId){
//                    case R.id.tab_adv:
//
//                        break;
//                    case R.id.tab_main:
//                        Intent mIntentHome=new Intent(mContext,Home.class);
//                        startActivity(mIntentHome);
//                        finish();
//                        break;
//                    case R.id.tab_notify:
//                        Intent mIntentNotification=new Intent(mContext,Notifications.class);
//                        startActivity(mIntentNotification);
//                        finish();
//                        break;
//                    case R.id.tab_message:
//
//                        break;
//                }
//            }
//        });
        final MultiStateToggleButton button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
        button.setSelected(false);
        button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                switch (position){
                    case 0:
                        Intent mIntent=new Intent(mContext,MyAdvertisement.class);
                        startActivity(mIntent);

                        break;
                    case 1:

                        break;
                    case 2:

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
        onLoadAdv(MainApp.ProfileUrl+token);
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
    public void onProfile(){
        String Url= MainApp.ProfileUrl+token;
        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    for (int i=0;i<mJsonArray.length();i++){
                        JSONObject jsonObject=mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject=jsonObject.getJSONObject("user");
                        String id=mJsonObject.getString("id");
                        username=mJsonObject.getString("username");
                        email=mJsonObject.getString("email");
                        String created_at=mJsonObject.getString("created_at");
                        String last_login=mJsonObject.getString("last_login");
                        phone=mJsonObject.getString("phone");

                        JSONObject mJsonObject1=jsonObject.getJSONObject("ads");
                        String next_page_url=mJsonObject1.getString("next_page_url");
                        JSONArray jsonArray=mJsonObject1.getJSONArray("data");
                        for (int j=0;j<jsonArray.length();j++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String id1=jsonObject1.getString("id");
                            String category_id=jsonObject1.getString("category_id");
                            String sub_category_id=jsonObject1.getString("sub_category_id");
                            String title=jsonObject1.getString("title");
                            String description=jsonObject1.getString("description");
                            String created_at1=jsonObject1.getString("created_at");

                        }
                        JSONObject mJsonObject2=jsonObject.getJSONObject("favoriteAds");
                        String next_page_url1=mJsonObject2.getString("next_page_url");
                        JSONArray jsonArray1=mJsonObject2.getJSONArray("data");
                        for (int j=0;j<jsonArray1.length();j++){
                            JSONObject jsonObject2=jsonArray1.getJSONObject(i);
                            String id2=jsonObject2.getString("id");
                            String category_id1=jsonObject2.getString("category_id");
                            String sub_category_id1=jsonObject2.getString("sub_category_id");
                            String title1=jsonObject2.getString("title");
                            String description1=jsonObject2.getString("description");
                            String created_at2=jsonObject2.getString("created_at");
                            String phone1=jsonObject2.getString("phone");

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onProfile();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                token=split[1];
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonProfile);
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
                    JSONArray mJsonArray = new JSONArray(response);
                    Log.i(MainApp.Tag,"fav");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject = jsonObject.getJSONObject("favoriteAds");
                        next_page_url = mJsonObject.getString("next_page_url");
                        JSONArray data = mJsonObject.getJSONArray("data");
                        for (int k = 0; k < data.length(); k++) {
                            Log.i(MainApp.Tag,k+"");
                            if (mDelete) {
                                mDelete = false;
                                ImagesModels = new ArrayList<String>();
                            }
                            JSONObject jsonObject1 = data.getJSONObject(k);
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
                            for (int j = 0; j < images.length(); j++) {
                                JSONObject jsonObject2 = images.getJSONObject(j);
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
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadAdv(Url);
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                Log.i(MainApp.Tag,split[1]);
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }
        };
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
    onLoadAdv(MainApp.ProfileUrl+token);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
        mSwipeRefreshLayoutAdv.setRefreshing(false);
    }
}
}

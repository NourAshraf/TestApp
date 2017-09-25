package com.example.nour.makssab.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.nour.makssab.Activity.Home.FollowesIds;
import static com.example.nour.makssab.Activity.Home.FollowesNames;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class MemberFavorites extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener,Animation.AnimationListener {
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
    private Button mButtonMyAdv;
    private Button mButtonMyFav;
    private Button mButtonMyMember;
    private boolean mLogin;
    private LinearLayout linearLayout;
    private String UserId;
    private TextView mTextViewNoFav;
    private String MyUserId;


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
        UserId = getIntent().getExtras().getString("UserId");
    }

    private void onVariables() {
        mDelete=false;
        mContext=MemberFavorites.this;
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        MyUserId = mSharedPreferences.getString("ID", "");
        mLogin = mSharedPreferences.getBoolean("Login",false);
        token = mSharedPreferences.getString("token","");
        mTextViewUsername= (TextView) findViewById(R.id.tvProfileName1);
        mTextViewNoFav= (TextView) findViewById(R.id.tvNoFav);
        mTextViewUsername.setText(username);
        mButtonMyFav= (Button) findViewById(R.id.bMyFav);
        mButtonMyAdv= (Button) findViewById(R.id.bMyAdv);
        mButtonMyMember= (Button) findViewById(R.id.bMyMember);
        mButtonMyAdv.setOnClickListener(this);
        mButtonMyFav.setOnClickListener(this);
        mButtonMyMember.setOnClickListener(this);
        mButtonMyFav.setBackgroundResource(R.drawable.button_bg_6);
        mButtonMyFav.setTextColor(getResources().getColor(android.R.color.background_light));
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
                finish();
            }
        });
        linearLayout=(LinearLayout)findViewById(R.id.linear);
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
//        mRecyclerViewAdv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                linearLayout.animate()
//                        .translationY(0)
//                        .alpha(0.0f)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                linearLayout.setVisibility(View.GONE);
//                            }
//                        });
//            }
//        });

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
        if (getIntent().hasExtra("Member")){
            mButtonProfile.setText("بيانات العضو");
            LinearLayout mLinearLayout= (LinearLayout) findViewById(R.id.llOption);
            mLinearLayout.setVisibility(View.GONE);
        }
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
                    mTextViewNoFav.setVisibility(View.VISIBLE);
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
                            mTextViewNoFav.setVisibility(View.GONE);
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
//                String phpsessid = response.headers.get("Authorization");
//                String[] split = phpsessid.split(" ");
//                Log.i(MainApp.Tag,split[1]);
//                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bMyAdv:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_7);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyAdv.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (mLogin){

                    Intent mIntent=new Intent(mContext,MyAdvertisement.class);
                    mIntent.putExtra("UserId",MyUserId);
                    startActivity(mIntent);
                }else {
                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent);

                }
                break;
            case R.id.bMyFav:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_6);
                mButtonMyFav.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.bMyMember:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_5);
                mButtonMyMember.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
                Intent mIntent=new Intent(mContext,Members.class);
                mIntent.putExtra("MembersName",FollowesNames);
                mIntent.putExtra("MembersIds",FollowesIds);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {


    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }
}

package com.example.nour.makssab.Activity;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.current_page;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.loading;
import static com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener.previousTotal;

public class Advertisement extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

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
    private String filename="mkssab";
    private String mUserName;
    private String mPhone;
    private String mEmail;
    private boolean mLogin;
    private String token;
    private String mStateId;
    private String mCityId;
    private Spinner mSpinnerNewAccountArea;
    private Spinner mSpinnerNewAccountCity;
    private ArrayList<String> State_Id;
    private ArrayList<String> State_Name;
    private ArrayList<String> City_Id;
    private ArrayList<String> City_Name;
    private ImageView mImageViewSearch;
    private LinearLayout mLinearLayoutSearchStories;
    private Button mButtonAllAreas;
    private Button mButtonSearchStories;
    private boolean mSearch;
    private boolean mSearchLoading;
    private String MyCity_id;
    private String MyState_id;
    private int MyPostionState;
    private int MyPostionCity;


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
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        MyCity_id = mSharedPreferences.getString("city_id","");
        MyState_id = mSharedPreferences.getString("state_id","");
        mLogin = mSharedPreferences.getBoolean("Login",false);
        mDelete=false;
        mSearch=false;
        mSearchLoading=false;
        mStateId = "";
        mCityId = "";
        mButtonAllAreas = (Button) findViewById(R.id.bAllAreas);
        mButtonSearchStories = (Button) findViewById(R.id.bSearchStories);
        mButtonAllAreas.setOnClickListener(this);
        mButtonSearchStories.setOnClickListener(this);
        mSpinnerNewAccountArea = (Spinner) findViewById(R.id.NewAccountSpinnerArea);
        mSpinnerNewAccountArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mStateId = "";
                    mSpinnerNewAccountCity.setVisibility(View.GONE);
                } else {
                    mStateId = State_Id.get(position);
                    onCity(State_Id.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mImageViewSearch= (ImageView) findViewById(R.id.ivSearch);
        mImageViewSearch.setOnClickListener(this);
        if (!mLogin){
            mImageViewSearch.setVisibility(View.GONE);
        }
        mSpinnerNewAccountCity = (Spinner) findViewById(R.id.NewAccountSpinnerCity);
        mSpinnerNewAccountCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1) {
                    mCityId = "";
                } else {
                    mCityId = City_Id.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        State_Id = new ArrayList<String>();
        State_Name = new ArrayList<String>();
        City_Id = new ArrayList<String>();
        City_Name = new ArrayList<String>();

        mContext=Advertisement.this;
        mUserName = mSharedPreferences.getString("UserName", "");
        mPhone = mSharedPreferences.getString("Phone", "");
        mEmail = mSharedPreferences.getString("Email", "");
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

                        break;
                    case R.id.tab_message:
                        if (mLogin) {
                            Intent mIntent = new Intent(mContext, MyMessages.class);
                            startActivity(mIntent);
                        }else {
                            Intent mIntent = new Intent(mContext, Login.class);
                            startActivity(mIntent);
                        }
                        break;
                }
            }
        });
        mLinearLayoutSearchStories = (LinearLayout) findViewById(R.id.llSearchStories);
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
        mProgressBar.setVisibility(View.VISIBLE);
        onLoadAdv(MainApp.AdvUrl);
        mRecyclerViewAdv.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)
            {

                if (next_page_url.equals("null")){

                }else {
                    if (mSearchLoading){

                    }else {
                        mDelete = true;
                        onLoadAdv(next_page_url);
                    }
                }
            }
        });
        onStates();
    }



    public void onStates(){
        String Url=MainApp.StatesUrl;
        StringRequest mStringRequestonStates=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray = new JSONArray(response);
                    if (mJsonArray.length() == 1) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        String error = mJsonObject.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        State_Id.add(id);
                        State_Name.add(name);
                        if (MyState_id.equals(id)){
                            MyPostionState=i;
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner,R.id.tvItem,State_Name);
                        mSpinnerNewAccountArea.setAdapter(dataAdapter);
                        mSpinnerNewAccountArea.setSelection(MyPostionState);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onStates();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonStates);
    }



    public void onCity(final String position){
        if (City_Name!=null){
            City_Name.clear();
            City_Id.clear();
        }
        String Url=MainApp.CitiesUrl+position;
        StringRequest mStringRequestonCity=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray = new JSONArray(response);
                    if (mJsonArray.length() == 1) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        String error = mJsonObject.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        City_Id.add(id);
                        City_Name.add(name);
                        if (MyCity_id.equals(id)){
                            MyPostionCity=i;
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner,R.id.tvItem,City_Name);
                        mSpinnerNewAccountCity.setAdapter(dataAdapter);
                        mSpinnerNewAccountCity.setSelection(MyPostionCity);
                        mSpinnerNewAccountCity.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCity(position);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonCity);
    }


    private void onLoadAdv(final String Url) {
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewNoInternet.setVisibility(View.GONE);
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
                        String state_id = jsonObject.getString("state_id");
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
                        if (mSearch==true){
                            if (city_id.equals(mCityId)&&state_id.equals(mStateId)){
                                AdvModel advModel = new AdvModel(id, city_id, views, category_id, title, description, phone, City_Name, UserId, username, ImagesModels, created_at, CommentCount);
                                models.add(advModel);
                            }
                        }else {
                            AdvModel advModel = new AdvModel(id, city_id, views, category_id, title, description, phone, City_Name, UserId, username, ImagesModels, created_at, CommentCount);
                            models.add(advModel);
                        }
                        if (true){
                            mDelete=true;
                        }

                    }
                    mAdvAdapter.notifyDataSetChanged();
                    if (models.size()<10){
                        mSearchLoading=true;
                        onLoadAdv(next_page_url);
                    }else {
                        mSearchLoading=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.VISIBLE);
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadAdv(Url);
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_search:
//                Intent mIntentSearch=new Intent(getApplicationContext(),Search.class);
//                startActivity(mIntentSearch);
//                break;
//
//            case R.id.action_Adv_Favorites:
//                Intent mIntentAdvFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
//                mIntentAdvFavorites.putExtra("username",mUserName);
//                mIntentAdvFavorites.putExtra("email",mEmail);
//                mIntentAdvFavorites.putExtra("phone",mPhone);
//                startActivity(mIntentAdvFavorites);
//                break;
//
//            case R.id.action_New_Account:
//                Intent mIntentNewAccount=new Intent(getApplicationContext(),NewAccount.class);
//                startActivity(mIntentNewAccount);
//                break;
//
//            case R.id.action_Login:
//                Intent mIntentLogin=new Intent(getApplicationContext(),Login.class);
//                startActivity(mIntentLogin);
//                break;
//            case R.id.action_My_Adv:
//                if (mLogin){
//                    Intent mIntent=new Intent(mContext,MyAdvertisement.class);
//                    startActivity(mIntent);
//                }else {
//                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
//                    mContext.startActivity(mIntent);
//
//                }
//                break;
//            case R.id.action_Logout:
//                if (mLogin){
//                    onLogout();
//                }else {
//                    Toast.makeText(mContext, "يجب عليك تسجيل الدخول اولا !", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//
//        }
//        return true;
//    }
//    public void onLogout(){
//        String Url= MainApp.LogoutUrl+token;
//        StringRequest mStringRequestonLogut=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject mJsonObject = new JSONObject(response);
//                    Log.i(MainApp.Tag,"Logout");
//                    if (mJsonObject.has("success")){
//                        Toast.makeText(getApplicationContext(),"تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
//                        mSharedPreferences.edit().putBoolean("Login",false).commit();
//                        mSharedPreferences.edit().putString("token","").commit();
//                        mLogin=false;
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        mVolleySingletonRequestQueue.add(mStringRequestonLogut);
//
//    }


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
        mProgressBar.setVisibility(View.VISIBLE);
        onLoadAdv(MainApp.AdvUrl);
        if (mSwipeRefreshLayoutAdv.isRefreshing()){
            mSwipeRefreshLayoutAdv.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivSearch:
                mTextViewNoInternet.setVisibility(View.GONE);
                if (mLinearLayoutSearchStories.getVisibility()==View.GONE){
                    mLinearLayoutSearchStories.setVisibility(View.VISIBLE);
                }else {
                    mLinearLayoutSearchStories.setVisibility(View.GONE);
                }
                break;
            case R.id.bAllAreas:
                mSearch=false;
                mStateId="";
                mCityId="";
                current_page=1;
                previousTotal=0;
                loading=true;
                mRecyclerViewAdv.setVisibility(View.INVISIBLE);
                if (models!=null){
                    models.clear();
                }
                onLoadAdv(MainApp.AdvUrl);
                mLinearLayoutSearchStories.setVisibility(View.GONE);
                break;
            case R.id.bSearchStories:
                mSearch=true;
                current_page=1;
                previousTotal=0;
                loading=true;
                mRecyclerViewAdv.setVisibility(View.INVISIBLE);
                if (models!=null){
                    models.clear();
                }
                mProgressBar.setVisibility(View.VISIBLE);
                onLoadAdv(MainApp.AdvUrl);
                mLinearLayoutSearchStories.setVisibility(View.GONE);
                break;
        }
    }
}

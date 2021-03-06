//<<<<<<< HEAD
//package com.ibtdi.team.mkssab.Activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.ibtdi.team.mkssab.Adapter.HomeAdapter;
//import com.ibtdi.team.mkssab.MainApp.MainApp;
//import com.ibtdi.team.mkssab.Model.HomeModel;
//import com.ibtdi.team.mkssab.Network.VolleySingleton;
//import com.ibtdi.team.mkssab.R;
//import com.roughike.bottombar.BottomBar;
//import com.roughike.bottombar.OnTabSelectListener;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Home extends AppCompatActivity implements View.OnClickListener {
//    private GridView mGridView;
//    private ArrayList<HomeModel> models;
//    private Context mContext;
//    private String filename="mkssab";
//    private ImageView mImageViewPlus;
//    private Button mButtonProfile;
//    private TextView mTextViewProfileName;
//    private TextView mTextViewProfileEmail;
//    private Button mButtonShops;
//    private Button mButtonGalleries;
//    private Button mButton1;
//    private Button mButton2;
//    private Button mButton3;
//    private Button mButtonProperty;
//    private Button mButtonLoginNow;
//    private int REQUEST_CODE_INTRO=1;
//    private SharedPreferences mSharedPreferences;
//    private String token;
//    private RequestQueue mVolleySingletonRequestQueue;
//    private String username;
//    private String email;
//    private boolean mLogin;
//    private String phone_main;
//    private String mUserName;
//    private String mPassword;
//    private Button mButtonMyAdv;
//    private Button mButtonMyFav;
//    private Button mButtonMyMember;
//    private ProgressBar mProgressBar;
//    private Button mButtonLoginNow1;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        onVariables();
//        mLogin = mSharedPreferences.getBoolean("Login",false);
//        if (mLogin) {
//            mUserName = mSharedPreferences.getString("UserName", "");
//            mPassword = mSharedPreferences.getString("Password", "");
//            mButtonLoginNow.setVisibility(View.GONE);
//            onLogin(mUserName,mPassword);
//            onProfile();
//        }
//
//    }
//
//    private void onLogin(final String username, final String password) {
//        String Url= MainApp.LoginUrl;
//        StringRequest mStringRequestonLogin=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject mJsonObject=new JSONObject(response);
//                    if (mJsonObject.has("error")){
//                        Toast.makeText(getApplicationContext(),"البريد الالكترونى او كلمه المرور غير صحيحه",Toast.LENGTH_SHORT).show();
//                    }
//                    token = mJsonObject.getString("token");
//                    mSharedPreferences.edit().putBoolean("Login",true).commit();
//                    mSharedPreferences.edit().putString("token",token).commit();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String>paramter=new HashMap<String, String>();
//                paramter.put("login",username);
//                paramter.put("password",password);
//                return paramter;
//            }
//        };
//        mVolleySingletonRequestQueue.add(mStringRequestonLogin);
//    }
//
//    private void onVariables() {
//        mContext=Home.this;
//        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
//        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
//        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
//        mImageViewPlus= (ImageView) findViewById(R.id.ivPlus);
//        mImageViewPlus.setOnClickListener(this);
//        mButtonProfile= (Button) findViewById(R.id.bProfile);
//        mButtonProperty= (Button) findViewById(R.id.bProperty);
//        mButtonGalleries= (Button) findViewById(R.id.bGalleries);
//        mButtonShops= (Button) findViewById(R.id.bShops);
//        mButtonLoginNow= (Button) findViewById(R.id.bLoginNow);
//        mButtonLoginNow1= (Button) findViewById(R.id.bLoginNow1);
//        mButtonProfile.setOnClickListener(this);
//        mButtonShops.setOnClickListener(this);
//        mButtonGalleries.setOnClickListener(this);
//        mButtonProperty.setOnClickListener(this);
//        mButtonLoginNow.setOnClickListener(this);
//        mButtonLoginNow1.setOnClickListener(this);
//        mButtonMyFav= (Button) findViewById(R.id.bMyFav);
//        mButtonMyAdv= (Button) findViewById(R.id.bMyAdv);
//        mButtonMyMember= (Button) findViewById(R.id.bMyMember);
//        mButtonMyAdv.setOnClickListener(this);
//        mButtonMyFav.setOnClickListener(this);
//        mButtonMyMember.setOnClickListener(this);
//        mTextViewProfileName= (TextView) findViewById(R.id.tvProfileName);
//        mTextViewProfileEmail= (TextView) findViewById(R.id.tvProfileEmail);
//        mGridView= (GridView) findViewById(R.id.gvHome);
//        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
//        mProgressBar.setVisibility(View.GONE);
//        models=new ArrayList<HomeModel>();
//        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
//        bottomBar.setDefaultTab(R.id.tab_main);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//               switch (tabId){
//                   case R.id.tab_adv:
//                       Intent mIntentAdvertisement=new Intent(mContext,Advertisement.class);
//                       startActivity(mIntentAdvertisement);
//                       finish();
//                       break;
//                   case R.id.tab_main:
//
//                       break;
//                   case R.id.tab_notify:
//
//                       break;
//                   case R.id.tab_message:
//                       if (mLogin) {
//                           Intent mIntent = new Intent(mContext, MyMessages.class);
//                           startActivity(mIntent);
//                           finish();
//                       }else {
//                           Intent mIntent = new Intent(mContext, Login.class);
//                           startActivity(mIntent);
//                       }
//                       break;
//               }
//            }
//        });
//        onLoadHomeData();
//    }
//
//    private void onLoadHomeData() {
//        String[]Title=getResources().getStringArray(R.array.HomeGrid);
//        int[]Ids={1,6,4,5,3,8,2,7};
//        int[]Images={R.drawable.cars,R.drawable.jobs,R.drawable.house,R.drawable.fashion,R.drawable.devices,R.drawable.animals,R.drawable.property,R.drawable.servies};
//        for (int i=0;i<Title.length;i++){
//            HomeModel homeModel = new HomeModel(Title[i], Ids[i], Images[i]);
//            models.add(homeModel);
//        }
//        HomeAdapter mHomeAdapter=new HomeAdapter(mContext,R.layout.item_home,models);
//        mGridView.setAdapter(mHomeAdapter);
//    }
//
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
//                Intent mIntentSearch=new Intent(mContext,Search.class);
//                startActivity(mIntentSearch);
//                break;
//
//            case R.id.action_Adv_Favorites:
//                if (mLogin){
//                    Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
//                    mIntentFavorites.putExtra("username",username+"");
//                    mIntentFavorites.putExtra("email",email+"");
//                    mIntentFavorites.putExtra("phone",phone_main+"");
//                    mContext.startActivity(mIntentFavorites);
//                }else {
//                    Intent mIntent2=new Intent(getApplicationContext(),Login.class);
//                    mContext.startActivity(mIntent2);
//                }
//                break;
//
//            case R.id.action_New_Account:
//                Intent mIntentNewAccount=new Intent(mContext,NewAccount.class);
//                startActivity(mIntentNewAccount);
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
//
//            case R.id.action_Login:
//                Intent mIntentLogin=new Intent(mContext,Login.class);
//                startActivity(mIntentLogin);
//                break;
//            case R.id.action_Logout:
//                if (mLogin){
//                    onLogout();
//                    mButtonLoginNow1.setVisibility(View.GONE);
//                    mButtonLoginNow.setVisibility(View.VISIBLE);
//
//                }else {
//                    Toast.makeText(mContext, "يجب عليك تسجيل الدخول اولا !", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.ivPlus:
//                if (mLogin){
//                    Intent mIntentLogin3=new Intent(mContext,Add_Advertisement.class);
//                    startActivity(mIntentLogin3);
//                }else {
//                    Intent mIntentLogin=new Intent(mContext,Login.class);
//                    startActivity(mIntentLogin);
//                }
//
//                break;
//            case R.id.bProfile:
//                if (mLogin){
//                    Intent mIntent=new Intent(getApplicationContext(),Profile.class);
//                    mIntent.putExtra("username",username+"");
//                    mIntent.putExtra("email",email+"");
//                    mIntent.putExtra("phone",phone_main+"");
//                    mContext.startActivity(mIntent);
//                }else {
//                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
//                    mContext.startActivity(mIntent);
//
//                }
//
//                break;
//            case R.id.bShops:
//                Intent mIntentShops=new Intent(mContext,Stores.class);
//                startActivity(mIntentShops);
//
//                break;
//            case R.id.bGalleries:
//                Intent mIntentGalleries=new Intent(mContext,Exhibitions.class);
//                startActivity(mIntentGalleries);
//
//                break;
//            case R.id.bProperty:
//                Intent mIntentProperty=new Intent(mContext,Buildings.class);
//                startActivity(mIntentProperty);
//                break;
//            case R.id.bLoginNow:
//
//                    Intent mIntent2=new Intent(getApplicationContext(),Login.class);
//                    mContext.startActivity(mIntent2);
//               break;
//
//            case R.id.bLoginNow1:
//              Intent intentbLoginNow1=new Intent(getApplicationContext(),Add_Advertisement.class);
//                startActivity(intentbLoginNow1);
//                break;
//
//
//
//
//            case R.id.bMyAdv:
//                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
//                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_7);
//                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
//                mButtonMyAdv.setTextColor(getResources().getColor(android.R.color.background_light));
//                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
//                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
//                if (mLogin){
//
//                            Intent mIntent=new Intent(mContext,MyAdvertisement.class);
//                            startActivity(mIntent);
//                        }else {
//                            Intent mIntent=new Intent(getApplicationContext(),Login.class);
//                            mContext.startActivity(mIntent);
//
//                        }
//                break;
//            case R.id.bMyFav:
//                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_6);
//                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
//                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
//                mButtonMyFav.setTextColor(getResources().getColor(android.R.color.background_light));
//                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
//                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
//                if (mLogin){
//                            Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
//                            mIntentFavorites.putExtra("username",username+"");
//                            mIntentFavorites.putExtra("email",email);
//                            mIntentFavorites.putExtra("phone",phone_main);
//                            mContext.startActivity(mIntentFavorites);
//                        }else {
//                            Intent mIntent5=new Intent(getApplicationContext(),Login.class);
//                            mContext.startActivity(mIntent5);
//                        }
//                break;
//            case R.id.bMyMember:
//                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
//                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
//                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_5);
//                mButtonMyMember.setTextColor(getResources().getColor(android.R.color.background_light));
//                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
//                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
//                break;
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_INTRO) {
//            if (resultCode == RESULT_OK) {
//
//            } else {
//
//            }
//        }
//    }
//    public void onProfile(){
//        mProgressBar.setVisibility(View.VISIBLE);
//        String Url= MainApp.ProfileUrl+token;
//        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mProgressBar.setVisibility(View.GONE);
//                try {
//                    JSONArray mJsonArray=new JSONArray(response);
//                    for (int i=0;i<mJsonArray.length();i++){
//                        JSONObject jsonObject=mJsonArray.getJSONObject(i);
//                        JSONObject mJsonObject=jsonObject.getJSONObject("user");
//                        String id=mJsonObject.getString("id");
//                        mSharedPreferences.edit().putString("ID",id).commit();
//                         username=mJsonObject.getString("username");
//                         email=mJsonObject.getString("email");
//                        String city_id = mJsonObject.getString("city_id");
//                        String state_id = mJsonObject.getString("state_id");
//                        mSharedPreferences.edit().putString("city_id",city_id).commit();
//                        mSharedPreferences.edit().putString("state_id",state_id).commit();
//                        String created_at=mJsonObject.getString("created_at");
//                        String last_login=mJsonObject.getString("last_login");
//                         phone_main=mJsonObject.getString("phone");
//                        mSharedPreferences.edit().putString("Phone",phone_main).commit();
//                        mSharedPreferences.edit().putString("Email",email).commit();
//                        JSONObject mJsonObject1=jsonObject.getJSONObject("ads");
//                        String next_page_url=mJsonObject1.getString("next_page_url");
//                        JSONArray jsonArray=mJsonObject1.getJSONArray("data");
//                        for (int j=0;j<jsonArray.length();j++){
//                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                            String id1=jsonObject1.getString("id");
//                            String category_id=jsonObject1.getString("category_id");
//                            String sub_category_id=jsonObject1.getString("sub_category_id");
//                            String title=jsonObject1.getString("title");
//                            String description=jsonObject1.getString("description");
//                            String created_at1=jsonObject1.getString("created_at");
//
//                        }
//                        JSONObject mJsonObject2=jsonObject.getJSONObject("favoriteAds");
//                        String next_page_url1=mJsonObject2.getString("next_page_url");
//                        JSONArray jsonArray1=mJsonObject2.getJSONArray("data");
//                        for (int j=0;j<jsonArray1.length();j++){
//                            JSONObject jsonObject2=jsonArray1.getJSONObject(i);
//                            String id2=jsonObject2.getString("id");
//                            String category_id1=jsonObject2.getString("category_id");
//                            String sub_category_id1=jsonObject2.getString("sub_category_id");
//                            String title1=jsonObject2.getString("title");
//                            String description1=jsonObject2.getString("description");
//                            String created_at2=jsonObject2.getString("created_at");
//                            String phone=jsonObject2.getString("phone");
//                        }
//
//                    }
//                   mTextViewProfileName.setText(username);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//               onProfile();
//            }
//        }){
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String phpsessid = response.headers.get("Authorization");
//                String[] split = phpsessid.split(" ");
//                token=split[1];
//                mSharedPreferences.edit().putString("token",split[1]).commit();
//                return super.parseNetworkResponse(response);
//            }
//        };
//        mVolleySingletonRequestQueue.add(mStringRequestonProfile);
//    }
//
//
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
//                    mTextViewProfileName.setText("اسم العضو");
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
//}
//=======
package com.ibtdi.team.mkssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibtdi.team.mkssab.Adapter.HomeAdapter;
import com.ibtdi.team.mkssab.MainApp.MainApp;
import com.ibtdi.team.mkssab.Model.HomeModel;
import com.ibtdi.team.mkssab.Network.VolleySingleton;
import com.ibtdi.team.mkssab.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private GridView mGridView;
    private ArrayList<HomeModel> models;
    private Context mContext;
    private String filename="mkssab";
    private ImageView mImageViewPlus;
    private Button mButtonProfile;
    private TextView mTextViewProfileName;
    private TextView mTextViewProfileEmail;
    private Button mButtonShops;
    private Button mButtonGalleries;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButtonProperty;
    private Button mButtonLoginNow;
    private int REQUEST_CODE_INTRO=1;
    private SharedPreferences mSharedPreferences;
    private String token;
    private RequestQueue mVolleySingletonRequestQueue;
    private String username;
    private String email;
    private boolean mLogin;
    private String phone_main;
    private String mUserName;
    private String mPassword;
    private Button mButtonMyAdv;
    private Button mButtonMyFav;
    private Button mButtonMyMember;
    private ProgressBar mProgressBar;
    private Button mButtonLoginNow1;
    public static ArrayList<String> FollowesNames;
    public static ArrayList<String> FollowesIds;
    private String MyUssrId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
        mLogin = mSharedPreferences.getBoolean("Login",false);
        if (mLogin) {
            mUserName = mSharedPreferences.getString("UserName", "");
            mPassword = mSharedPreferences.getString("Password", "");
            mButtonLoginNow.setVisibility(View.GONE);
            onLogin(mUserName,mPassword);
            onProfile();
        }

    }

    private void onLogin(final String username, final String password) {
        String Url= MainApp.LoginUrl;
        StringRequest mStringRequestonLogin=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    if (mJsonObject.has("error")){
                        Toast.makeText(getApplicationContext(),"البريد الالكترونى او كلمه المرور غير صحيحه",Toast.LENGTH_SHORT).show();
                    }
                    token = mJsonObject.getString("token");
                    mSharedPreferences.edit().putBoolean("Login",true).commit();
                    mSharedPreferences.edit().putString("token",token).commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>paramter=new HashMap<String, String>();
                paramter.put("login",username);
                paramter.put("password",password);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonLogin);
    }

    private void onVariables() {
        mContext=Home.this;
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        FollowesNames=new ArrayList<String>();
        FollowesIds=new ArrayList<String>();
        mImageViewPlus= (ImageView) findViewById(R.id.ivPlus);
        mImageViewPlus.setOnClickListener(this);
        mButtonProfile= (Button) findViewById(R.id.bProfile);
        mButtonProperty= (Button) findViewById(R.id.bProperty);
        mButtonGalleries= (Button) findViewById(R.id.bGalleries);
        mButtonShops= (Button) findViewById(R.id.bShops);
        mButtonLoginNow= (Button) findViewById(R.id.bLoginNow);
        mButtonLoginNow1= (Button) findViewById(R.id.bLoginNow1);
        mButtonProfile.setOnClickListener(this);
        mButtonShops.setOnClickListener(this);
        mButtonGalleries.setOnClickListener(this);
        mButtonProperty.setOnClickListener(this);
        mButtonLoginNow.setOnClickListener(this);
        mButtonLoginNow1.setOnClickListener(this);
        mButtonMyFav= (Button) findViewById(R.id.bMyFav);
        mButtonMyAdv= (Button) findViewById(R.id.bMyAdv);
        mButtonMyMember= (Button) findViewById(R.id.bMyMember);
        mButtonMyAdv.setOnClickListener(this);
        mButtonMyFav.setOnClickListener(this);
        mButtonMyMember.setOnClickListener(this);
        mTextViewProfileName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewProfileEmail= (TextView) findViewById(R.id.tvProfileEmail);
        mGridView= (GridView) findViewById(R.id.gvHome);
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        models=new ArrayList<HomeModel>();
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_main);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
               switch (tabId){
                   case R.id.tab_adv:
                       Intent mIntentAdvertisement=new Intent(mContext,Advertisement.class);
                       startActivity(mIntentAdvertisement);
                       finish();
                       break;
                   case R.id.tab_main:

                       break;
                   case R.id.tab_notify:
                        Intent mIntent2=new Intent(mContext,Notifications.class);
                        startActivity(mIntent2);
                       break;
                   case R.id.tab_message:
                       if (mLogin) {
                           Intent mIntent = new Intent(mContext, MyMessages.class);
                           startActivity(mIntent);
                           finish();
                       }else {
                           Intent mIntent = new Intent(mContext, Login.class);
                           startActivity(mIntent);
                       }
                       break;
               }
            }
        });
        onLoadHomeData();
    }

    private void onLoadHomeData() {
        String[]Title=getResources().getStringArray(R.array.HomeGrid);
        int[]Ids={1,6,4,5,3,8,2,7};
        int[]Images={R.drawable.cars,R.drawable.jobs,R.drawable.house,R.drawable.fashion,R.drawable.devices,R.drawable.animals,R.drawable.property,R.drawable.servies};
        for (int i=0;i<Title.length;i++){
            HomeModel homeModel = new HomeModel(Title[i], Ids[i], Images[i]);
            models.add(homeModel);
        }
        HomeAdapter mHomeAdapter=new HomeAdapter(mContext,R.layout.item_home,models);
        mGridView.setAdapter(mHomeAdapter);
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
                Intent mIntentSearch=new Intent(mContext,Search.class);
                startActivity(mIntentSearch);
                break;

            case R.id.action_Adv_Favorites:
                if (mLogin){
                    Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                    mIntentFavorites.putExtra("username",username+"");
                    mIntentFavorites.putExtra("email",email+"");
                    mIntentFavorites.putExtra("phone",phone_main+"");
                    mIntentFavorites.putExtra("UserId",MyUssrId);
                    mContext.startActivity(mIntentFavorites);
                }else {
                    Intent mIntent2=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent2);
                }
                break;

            case R.id.action_New_Account:
                Intent mIntentNewAccount=new Intent(mContext,NewAccount.class);
                startActivity(mIntentNewAccount);
                break;
            case R.id.action_My_Adv:
                if (mLogin){
                    Intent mIntent=new Intent(mContext,MyAdvertisement.class);
                    mIntent.putExtra("UserId",MyUssrId);
                    startActivity(mIntent);
                }else {
                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent);

                }
                break;

            case R.id.action_Login:
                Intent mIntentLogin=new Intent(mContext,Login.class);
                startActivity(mIntentLogin);
                break;
            case R.id.action_Logout:
                if (mLogin){
                    onLogout();
                    mButtonLoginNow1.setVisibility(View.GONE);
                    mButtonLoginNow.setVisibility(View.VISIBLE);

                }else {
                    Toast.makeText(mContext, "يجب عليك تسجيل الدخول اولا !", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivPlus:
                if (mLogin){
                    Intent mIntentLogin3=new Intent(mContext,Add_Advertisement.class);
                    startActivity(mIntentLogin3);
                }else {
                    Intent mIntentLogin=new Intent(mContext,Login.class);
                    startActivity(mIntentLogin);
                }

                break;
            case R.id.bProfile:
                if (mLogin){
                    Intent mIntent=new Intent(getApplicationContext(),Profile.class);
                    mIntent.putExtra("username",username+"");
                    mIntent.putExtra("email",email+"");
                    mIntent.putExtra("phone",phone_main+"");
                    mContext.startActivity(mIntent);
                }else {
                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent);

                }

                break;
            case R.id.bShops:
                Intent mIntentShops=new Intent(mContext,Stores.class);
                startActivity(mIntentShops);

                break;
            case R.id.bGalleries:
                Intent mIntentGalleries=new Intent(mContext,Exhibitions.class);
                startActivity(mIntentGalleries);

                break;
            case R.id.bProperty:
                Intent mIntentProperty=new Intent(mContext,Buildings.class);
                startActivity(mIntentProperty);
                break;
            case R.id.bLoginNow:

                    Intent mIntent2=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent2);
               break;

            case R.id.bLoginNow1:
              Intent intentbLoginNow1=new Intent(getApplicationContext(),Add_Advertisement.class);
                startActivity(intentbLoginNow1);
                break;




            case R.id.bMyAdv:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_7);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyAdv.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (mLogin){

                            Intent mIntent=new Intent(mContext,MyAdvertisement.class);
                            mIntent.putExtra("UserId",MyUssrId);
                            startActivity(mIntent);
                        }else {
                            Intent mIntent=new Intent(getApplicationContext(),Login.class);
                            mContext.startActivity(mIntent);

                        }
                break;
            case R.id.bMyFav:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_6);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyFav.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (mLogin){
                            Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                            mIntentFavorites.putExtra("username",username+"");
                            mIntentFavorites.putExtra("email",email);
                            mIntentFavorites.putExtra("phone",phone_main);
                            mIntentFavorites.putExtra("UserId",MyUssrId);
                            mContext.startActivity(mIntentFavorites);
                        }else {
                            Intent mIntent5=new Intent(getApplicationContext(),Login.class);
                            mContext.startActivity(mIntent5);
                        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTRO) {
            if (resultCode == RESULT_OK) {

            } else {

            }
        }
    }
    public void onProfile(){
        mProgressBar.setVisibility(View.VISIBLE);
        String Url= MainApp.ProfileUrl+token;
        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,response);
                mProgressBar.setVisibility(View.GONE);
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    for (int i=0;i<mJsonArray.length();i++){
                        JSONObject jsonObject=mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject=jsonObject.getJSONObject("user");
                        JSONArray followers = mJsonObject.getJSONArray("followers");
                        int length = followers.length();
                        mSharedPreferences.edit().putString("Followers",length+"").commit();
                        JSONArray followees = mJsonObject.getJSONArray("followees");
                        for (int o=0;o<followees.length();o++){
                            JSONObject followe = followees.getJSONObject(o);
                            String followeusername = followe.getString("username");
                            String followeid = followe.getString("id");
                            FollowesNames.add(followeusername);
                            FollowesIds.add(followeid);
                        }
                        MyUssrId=mJsonObject.getString("id");
                        mSharedPreferences.edit().putString("ID",MyUssrId).commit();
                         username=mJsonObject.getString("username");
                        mSharedPreferences.edit().putString("username",username).commit();
                        email=mJsonObject.getString("email");
                        String city_id = mJsonObject.getString("city_id");
                        String state_id = mJsonObject.getString("state_id");
                        mSharedPreferences.edit().putString("city_id",city_id).commit();
                        mSharedPreferences.edit().putString("state_id",state_id).commit();
                        String created_at=mJsonObject.getString("created_at");
                        String last_login=mJsonObject.getString("last_login");
                         phone_main=mJsonObject.getString("phone");
                        mSharedPreferences.edit().putString("Phone",phone_main).commit();
                        mSharedPreferences.edit().putString("Email",email).commit();
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
                            String phone=jsonObject2.getString("phone");
                        }

                    }
                   mTextViewProfileName.setText(username);
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


    public void onLogout(){
        String Url= MainApp.LogoutUrl+token;
        StringRequest mStringRequestonLogut=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    Log.i(MainApp.Tag,"Logout");
                    if (mJsonObject.has("success")){
                        Toast.makeText(getApplicationContext(),"تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                        mSharedPreferences.edit().putBoolean("Login",false).commit();
                        mSharedPreferences.edit().putString("token","").commit();
                        mLogin=false;
                    }
                    mTextViewProfileName.setText("اسم العضو");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestonLogut);

    }
}


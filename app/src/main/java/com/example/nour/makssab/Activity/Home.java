package com.example.nour.makssab.Activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.HomeAdapter;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.HomeModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private GridView mGridView;
    private ArrayList<HomeModel> models;
    private Context mContext;
    private String filename="mkssab";
    private ImageView mImageViewPlus;
    private Button mButtonProfile;
    private TextView mTextViewProfileName;
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
    private boolean mLogin;


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
            onProfile();
        }

    }

    private void onVariables() {
        mContext=Home.this;
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        token = mSharedPreferences.getString("token", "");
        boolean intro = mSharedPreferences.getBoolean("Intro", false);
        if (intro){
            Intent mIntentIntro=new Intent(mContext,ActivityIntro.class);
            startActivityForResult(mIntentIntro,REQUEST_CODE_INTRO);
        }
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();


        mImageViewPlus= (ImageView) findViewById(R.id.ivPlus);
        mImageViewPlus.setOnClickListener(this);
        mButtonProfile= (Button) findViewById(R.id.bProfile);
        mButtonProperty= (Button) findViewById(R.id.bProperty);
        mButtonGalleries= (Button) findViewById(R.id.bGalleries);
        mButtonShops= (Button) findViewById(R.id.bShops);
        mButtonLoginNow= (Button) findViewById(R.id.bLoginNow);
        mButtonProfile.setOnClickListener(this);
        mButtonShops.setOnClickListener(this);
        mButtonGalleries.setOnClickListener(this);
        mButtonProperty.setOnClickListener(this);
        mButtonLoginNow.setOnClickListener(this);
        mTextViewProfileName= (TextView) findViewById(R.id.tvProfileName);
        mGridView= (GridView) findViewById(R.id.gvHome);
        models=new ArrayList<HomeModel>();
        MultiStateToggleButton button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
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
                        Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                        mIntentFavorites.putExtra("username",username+"");
                        mContext.startActivity(mIntentFavorites);
                        break;
                    case 2:
                        Intent mIntentMembers=new Intent(mContext,Members.class);
                        startActivity(mIntentMembers);
                        break;
                }
            }
        });
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
                       Intent mIntentNotification=new Intent(mContext,Notifications.class);
                       startActivity(mIntentNotification);
                       break;
                   case R.id.tab_message:

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

                Intent mIntentAdvFavorites=new Intent(mContext,MemberFavorites.class);
                startActivity(mIntentAdvFavorites);
                break;

            case R.id.action_New_Account:
                Intent mIntentNewAccount=new Intent(mContext,NewAccount.class);
                startActivity(mIntentNewAccount);
                break;

            case R.id.action_Login:
                Intent mIntentLogin=new Intent(mContext,Login.class);
                startActivity(mIntentLogin);
                break;
            case R.id.action_Logout:
                onLogout();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivPlus:
                Intent mIntentLogin=new Intent(mContext,Login.class);
                startActivity(mIntentLogin);
                break;
            case R.id.bProfile:
                Intent mIntent=new Intent(getApplicationContext(),Profile.class);
                mIntent.putExtra("username",username+"");
                mContext.startActivity(mIntent);
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
                Intent mIntentLogin3=new Intent(mContext,Add_Advertisement.class);
                startActivity(mIntentLogin3);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTRO) {
            if (resultCode == RESULT_OK) {
                // Finished the intro
            } else {
                // Cancelled the intro. You can then e.g. finish this activity too.
            }
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
                        String email=mJsonObject.getString("email");
                        String created_at=mJsonObject.getString("created_at");
                        String last_login=mJsonObject.getString("last_login");

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
               Log.i(MainApp.Tag,"Error");
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
                        Toast.makeText(getApplicationContext(),"تم تسجيل الخروج بنجاح",Toast.LENGTH_SHORT).show();

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

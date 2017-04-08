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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private  String filename2="mkssab";
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
    private SharedPreferences mSharedPreferences1;
    private String token;
    private RequestQueue mVolleySingletonRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
    }

    private void onVariables() {
        mContext=Home.this;
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        mSharedPreferences1=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences1.getString("token", "");
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

                        break;
                    case 1:
                        Intent mIntentMembersFav=new Intent(mContext,MemberFavorites.class);
                        startActivity(mIntentMembersFav);
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
//                Intent mIntentLogin2=new Intent(mContext,Login.class);
//                startActivity(mIntentLogin2);
                Intent mIntentProfile=new Intent(mContext,Profile.class);
                startActivity(mIntentProfile);
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
                Intent mIntentLogin3=new Intent(mContext,Login.class);
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

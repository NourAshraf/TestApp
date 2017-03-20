package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Adapter.HomeAdapter;
import com.example.nour.makssab.Model.HomeModel;
import com.example.nour.makssab.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private GridView mGridView;
    private ArrayList<HomeModel> models;
    private Context mContext;
    private ImageView mImageViewPlus;
    private Button mButtonProfile;
    private TextView mTextViewProfileName;
    private Button mButtonShops;
    private Button mButtonGalleries;
    private Button mButtonProperty;
    private Button mButtonLoginNow;

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
}

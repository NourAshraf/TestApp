package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nour.makssab.Adapter.BuildingsAdapter;
import com.example.nour.makssab.Adapter.ExhibitionsAdapter;
import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Buildings extends AppCompatActivity {
    private ListView BuildingsList;
    private ArrayList<BuildingsModel> buildingsModels;
    private LinearLayout mLinearLayoutStates;
    private LinearLayout mLinearLayoutCity;
    private Button mButtonSearch;
    private Button mButtonSearchAppear;
    private TextView mTextViewHidden;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        BuildingsList = (ListView) findViewById(R.id.lvBuildingsList);
        buildingsModels = new ArrayList<BuildingsModel>();
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات العرابيه ","الرياض",R.drawable.ic_user_image_car_test));
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_lion_test));
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_animals_test));
        BuildingsList.setAdapter(new BuildingsAdapter(getApplicationContext(),buildingsModels));
        onVariables();



    }
    private void onVariables() {
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        mLinearLayoutStates= (LinearLayout) findViewById(R.id.llStateBuildings);
        mLinearLayoutCity= (LinearLayout) findViewById(R.id.llCityBuildings);
        mButtonSearch= (Button) findViewById(R.id.bSearchSearch);
        mButtonSearchAppear= (Button) findViewById(R.id.bSearchBuildings);
        mTextViewHidden= (TextView) findViewById(R.id.tvHiddenBuildings);
        mTextViewHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutStates.setVisibility(v.GONE);
                mLinearLayoutCity.setVisibility(v.GONE);
                mButtonSearch.setVisibility(v.GONE);
                mTextViewHidden.setVisibility(v.GONE);
                mButtonSearchAppear.setVisibility(v.VISIBLE);
            }
        });
        mButtonSearchAppear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutStates.setVisibility(v.VISIBLE);
                mLinearLayoutCity.setVisibility(v.VISIBLE);
                mTextViewHidden.setVisibility(v.VISIBLE);
                mButtonSearch.setVisibility(v.VISIBLE);
                mButtonSearchAppear.setVisibility(v.GONE);
            }
        });


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

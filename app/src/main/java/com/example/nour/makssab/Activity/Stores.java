package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nour.makssab.Adapter.BuildingsAdapter;
import com.example.nour.makssab.Adapter.StoresAdapter;
import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.StoresModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Stores extends AppCompatActivity {
    private ListView StoresList;
    private ArrayList<StoresModel> storesModels;
    private LinearLayout mLinearLayoutStates;
    private LinearLayout mLinearLayoutCity;
    private Button mButtonSearch;
    private Button mButtonSearchAppear;
    private TextView mTextViewHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StoresList = (ListView) findViewById(R.id.lvStoresList);
        storesModels = new ArrayList<StoresModel>();
        storesModels.add(new StoresModel("221","معرض صالح للسيارات العرابيه ","الرياض",R.drawable.ic_user_image_car_test));
        storesModels.add(new StoresModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_lion_test));
        storesModels.add(new StoresModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_animals_test));
        StoresList.setAdapter(new StoresAdapter(getApplicationContext(),storesModels));
        onVariables();


    }

    private void onVariables() {
        mLinearLayoutStates= (LinearLayout) findViewById(R.id.llStateStores);
        mLinearLayoutCity= (LinearLayout) findViewById(R.id.llCityStores);
        mButtonSearch= (Button) findViewById(R.id.bSearchSearch);
        mButtonSearchAppear= (Button) findViewById(R.id.bSearchStores);
        mTextViewHidden= (TextView) findViewById(R.id.tvHiddenStores);
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

}

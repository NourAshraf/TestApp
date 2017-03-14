package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.BuildingsAdapter;
import com.example.nour.makssab.Adapter.StoresAdapter;
import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.StoresModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Stores extends AppCompatActivity {
    private ListView StoresList;
    private ArrayList<StoresModel> storesModels;

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


    }

}

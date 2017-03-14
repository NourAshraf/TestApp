package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.BuildingsAdapter;
import com.example.nour.makssab.Adapter.ExhibitionsAdapter;
import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Buildings extends AppCompatActivity {
    private ListView BuildingsList;
    private ArrayList<BuildingsModel> buildingsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BuildingsList = (ListView) findViewById(R.id.lvBuildingsList);
        buildingsModels = new ArrayList<BuildingsModel>();
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات العرابيه ","الرياض",R.drawable.ic_user_image_car_test));
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_lion_test));
        buildingsModels.add(new BuildingsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_animals_test));
        BuildingsList.setAdapter(new BuildingsAdapter(getApplicationContext(),buildingsModels));



    }

}

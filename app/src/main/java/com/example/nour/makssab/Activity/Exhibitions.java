package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.ExhibitionsAdapter;
import com.example.nour.makssab.Adapter.MemberFavoritesAdapter;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.Model.MembersFavoritesModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Exhibitions extends AppCompatActivity {
    private ListView ExhibitionsList;
    private ArrayList<ExhibitionsModel> exhibitionsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExhibitionsList = (ListView) findViewById(R.id.lvExhibitionsList);
        exhibitionsModels = new ArrayList<ExhibitionsModel>();
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_image_car_test));
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_image_car_test));
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_image_car_test));
        ExhibitionsList.setAdapter(new ExhibitionsAdapter(getApplicationContext(),exhibitionsModels));


    }

}

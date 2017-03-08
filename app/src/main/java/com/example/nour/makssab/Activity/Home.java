package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridView;

import com.example.nour.makssab.Adapter.HomeAdapter;
import com.example.nour.makssab.Model.HomeModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private GridView mGridView;
    private ArrayList<HomeModel> models;
    private Context mContext;

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
        mGridView= (GridView) findViewById(R.id.gvHome);
        models=new ArrayList<HomeModel>();
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
}

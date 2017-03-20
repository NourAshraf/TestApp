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

import com.example.nour.makssab.Adapter.ExhibitionsAdapter;
import com.example.nour.makssab.Adapter.MemberFavoritesAdapter;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.Model.MembersFavoritesModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Exhibitions extends AppCompatActivity {
    private ListView ExhibitionsList;
    private ArrayList<ExhibitionsModel> exhibitionsModels;
    private LinearLayout mLinearLayoutState;
    private LinearLayout mLinearLayoutCity;
    private LinearLayout mLinearLayoutCarMarka;
    private LinearLayout mLinearLayoutCarKind;
    private Button mButtonSearch;
    private TextView mTextViewHidden;
    private Button mButtonAppear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExhibitionsList = (ListView) findViewById(R.id.lvExhibitionsList);
        exhibitionsModels = new ArrayList<ExhibitionsModel>();
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات العرابيه ","الرياض",R.drawable.ic_user_image_car_test));
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_lion_test));
        exhibitionsModels.add(new ExhibitionsModel("221","معرض صالح للسيارات","الرياض",R.drawable.ic_user_animals_test));
        ExhibitionsList.setAdapter(new ExhibitionsAdapter(getApplicationContext(),exhibitionsModels));
        onVariables();

    }

    private void onVariables() {
        mLinearLayoutState= (LinearLayout) findViewById(R.id.llStateExhibitions);
        mLinearLayoutCity= (LinearLayout) findViewById(R.id.llCityExhibitions);
        mLinearLayoutCarMarka= (LinearLayout) findViewById(R.id.llCarMarkaExhibitions);
        mTextViewHidden= (TextView) findViewById(R.id.tvHiddenExhibitions);
        mLinearLayoutCarKind= (LinearLayout) findViewById(R.id.llCarKindExhibitions);
        mButtonAppear= (Button) findViewById(R.id.bSearch);
        mButtonSearch= (Button) findViewById(R.id.bSearchExhibitions);
        mTextViewHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutState.setVisibility(v.GONE);
                mLinearLayoutCity.setVisibility(v.GONE);
                mLinearLayoutCarMarka.setVisibility(v.GONE);
                mLinearLayoutCarKind.setVisibility(v.GONE);
                mTextViewHidden.setVisibility(v.GONE);
                mButtonSearch.setVisibility(v.GONE);
                mButtonAppear.setVisibility(v.VISIBLE);
            }
        });
        mButtonAppear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutState.setVisibility(v.VISIBLE);
                mLinearLayoutCity.setVisibility(v.VISIBLE);
                mLinearLayoutCarMarka.setVisibility(v.VISIBLE);
                mLinearLayoutCarKind.setVisibility(v.VISIBLE);
                mTextViewHidden.setVisibility(v.VISIBLE);
                mButtonSearch.setVisibility(v.VISIBLE);
                mButtonAppear.setVisibility(v.GONE);
            }
        });

    }

}

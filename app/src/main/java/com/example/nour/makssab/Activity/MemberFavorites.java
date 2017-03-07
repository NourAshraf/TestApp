package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.MemberFavoritesAdapter;
import com.example.nour.makssab.Model.MembersFavoritesModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class MemberFavorites extends AppCompatActivity {
    private ListView MembersFavoritesList;
    private ArrayList<MembersFavoritesModel> membersFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MembersFavoritesList = (ListView) findViewById(R.id.lvMembersFavoritesList);
        membersFavorites = new ArrayList<MembersFavoritesModel>();
        membersFavorites.add(new MembersFavoritesModel("سيارة بنت وسخه"," ابو نواف ","مكه","5","15","دقيقه","منذ","100",R.drawable.ic_user_image_car_test,"","","","",""));
        membersFavorites.add(new MembersFavoritesModel("سيارة بنت وسخه"," ابو نواف ","مكه","5","15","دقيقه","منذ","100",R.drawable.ic_user_image_car_test,"","","","",""));
        MembersFavoritesList.setAdapter(new MemberFavoritesAdapter(getApplicationContext(),membersFavorites));
    }

}

package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.MemberFavoritesAdapter;
import com.example.nour.makssab.Model.MembersFavoritesModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class MemberFavorites extends AppCompatActivity {
    private ListView MembersFavoritesList;
    private ArrayList<MembersFavoritesModel> membersFavorites;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        MembersFavoritesList = (ListView) findViewById(R.id.lvMembersFavoritesList);
        membersFavorites = new ArrayList<MembersFavoritesModel>();
        membersFavorites.add(new MembersFavoritesModel("  ميرسيدس"," ابو نواف ","مكه","52","15","دقيقه","منذ","100",R.drawable.ic_user_image_car_test,"","","","",""));
        membersFavorites.add(new MembersFavoritesModel("ميرسيدس  "," ابو نواف ","مكه","5","15","دقيقه","منذ","100",R.drawable.ic_user_image_car_test,"","","","",""));
        MembersFavoritesList.setAdapter(new MemberFavoritesAdapter(getApplicationContext(),membersFavorites));
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

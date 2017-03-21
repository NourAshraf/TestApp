package com.example.nour.makssab.Activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.NotifiacationsAdapter;
import com.example.nour.makssab.Model.NotificationsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    private ListView lvNotificationsList;
    private ArrayList<NotificationsModel> notifications;
    private ImageView mImageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        lvNotificationsList = (ListView) findViewById(R.id.lvNotificationsList);
        notifications = new ArrayList<NotificationsModel>();
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        lvNotificationsList.setAdapter(new NotifiacationsAdapter(getApplicationContext(),notifications));
        onVariables();
    }

    private void onVariables() {

        mImageViewBack= (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Home.class);
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

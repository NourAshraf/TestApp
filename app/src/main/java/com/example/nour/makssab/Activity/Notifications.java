package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.MembersAdapter;
import com.example.nour.makssab.Adapter.NotifiacationsAdapter;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.Model.NotificationsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    private ListView lvNotificationsList;
    private ArrayList<NotificationsModel> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvNotificationsList = (ListView) findViewById(R.id.lvNotificationsList);
        notifications = new ArrayList<NotificationsModel>();
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        notifications.add(new NotificationsModel(" اضاف كذا كذا اى كلام"," شقق كذا وكلام من ده",""));
        lvNotificationsList.setAdapter(new NotifiacationsAdapter(getApplicationContext(),notifications));






    }

}

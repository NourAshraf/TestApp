package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.nour.makssab.Adapter.MembersAdapter;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Members extends AppCompatActivity {
    private ListView lvMembersList;
    private ArrayList<MembersModel> members;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvMembersList = (ListView) findViewById(R.id.lvMembers);
        members = new ArrayList<MembersModel>();
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        lvMembersList.setAdapter(new MembersAdapter(getApplicationContext(),members));

    }

}

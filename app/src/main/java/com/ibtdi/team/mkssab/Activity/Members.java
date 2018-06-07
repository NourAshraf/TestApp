package com.ibtdi.team.mkssab.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ibtdi.team.mkssab.Adapter.MembersAdapter;
import com.ibtdi.team.mkssab.Model.MembersModel;
import com.ibtdi.team.mkssab.R;


import java.util.ArrayList;

public class Members extends AppCompatActivity {
    private ListView lvMembersList;
    private ArrayList<MembersModel> members;
    private ImageView mImageViewBack;
    private SharedPreferences mSharedPreferences;
    private String filename="mkssab";
    private String Followers;
    private String Username;
    private TextView mTextViewNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setSupportActionBar(toolbar);
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        Followers = mSharedPreferences.getString("Followers", "");
        Username = mSharedPreferences.getString("username", "");
        mTextViewNo= (TextView) findViewById(R.id.tvNo);
        TextView mTextViewFollowers= (TextView) findViewById(R.id.tvFollowers);
        TextView mTextViewProfileName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewProfileName.setText(Username);
        mTextViewFollowers.setText(Followers+" متابع");
        lvMembersList = (ListView) findViewById(R.id.lvMembers);
        ArrayList<String> membersName = getIntent().getExtras().getStringArrayList("MembersName");
        ArrayList<String> membersIds = getIntent().getExtras().getStringArrayList("MembersIds");
        members = new ArrayList<MembersModel>();
        for (int i=0;i<membersName.size();i++){
            MembersModel membersModel = new MembersModel(membersName.get(i), membersIds.get(i), "");
            members.add(membersModel);
        }
        if (members.size()==0){
            mTextViewNo.setVisibility(View.VISIBLE);
        }
        lvMembersList.setAdapter(new MembersAdapter(getApplicationContext(),members));
        onVariables();
    }
    private void onVariables() {
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


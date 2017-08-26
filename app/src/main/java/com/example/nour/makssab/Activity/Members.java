<<<<<<< HEAD
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

import com.example.nour.makssab.Adapter.MembersAdapter;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Members extends AppCompatActivity {
    private ListView lvMembersList;
    private ArrayList<MembersModel> members;
    private ImageView mImageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setSupportActionBar(toolbar);
        lvMembersList = (ListView) findViewById(R.id.lvMembers);
        members = new ArrayList<MembersModel>();
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        members.add(new MembersModel("طارق الفهد","110 متابع",""));
        lvMembersList.setAdapter(new MembersAdapter(getApplicationContext(),members));
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
=======
package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nour.makssab.Adapter.MembersAdapter;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

public class Members extends AppCompatActivity {
    private ListView lvMembersList;
    private ArrayList<MembersModel> members;
    private ImageView mImageViewBack;
    private SharedPreferences mSharedPreferences;
    private String filename="mkssab";
    private String Followers;
    private String Username;

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
>>>>>>> orgin/master

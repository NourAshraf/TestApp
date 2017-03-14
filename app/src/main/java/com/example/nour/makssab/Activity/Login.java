package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nour.makssab.R;

public class Login extends AppCompatActivity {
    private EditText mEditTextLoginUser;
    private EditText mEditTextLoginPass;
    private CheckBox mCheckBoxLogin;
    private Button mButtonLoginMember;
    private Button mButtonLoginEnter;
    private TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Allvariables();


    }

    private void Allvariables() {
        mEditTextLoginUser=(EditText)findViewById(R.id.etLoginUser);
        mEditTextLoginPass=(EditText)findViewById(R.id.etLoginPass);
        mCheckBoxLogin=(CheckBox)findViewById(R.id.cbLogin);
        mButtonLoginMember=(Button)findViewById(R.id.bLoginMember);
        mButtonLoginEnter=(Button)findViewById(R.id.bLoginEnter);
        mTextViewLogin=(TextView)findViewById(R.id.tvLogin);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivationCode.class);
                startActivity(intent);
            }
        });
        mButtonLoginMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewAccount.class);
                startActivity(intent);
            }
        });
    }

}

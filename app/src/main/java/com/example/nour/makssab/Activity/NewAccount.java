package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nour.makssab.R;

public class NewAccount extends AppCompatActivity {
    private EditText mEditTextNewAccountUser;
    private EditText mEditTextNewAccountPhone;
    private EditText mEditTextNewAccountEmail;
    private EditText mEditTextNewAccountArea;
    private EditText mEditTextNewAccountCity;
    private EditText mEditTextNewAccountPass;
    private EditText mEditTextNewAccountSurePass;
    private Button mButtonNewAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Allvariables();


    }

    private void Allvariables() {
        mEditTextNewAccountUser= (EditText) findViewById(R.id.etNewAccountUser);
        mEditTextNewAccountPhone= (EditText) findViewById(R.id.etNewAccountPhone);
        mEditTextNewAccountEmail= (EditText) findViewById(R.id.etNewAccountEmail);
        mEditTextNewAccountArea= (EditText) findViewById(R.id.etNewAccountArea);
        mEditTextNewAccountCity= (EditText) findViewById(R.id.etNewAccountCity);
        mEditTextNewAccountPass= (EditText) findViewById(R.id.etNewAccountPass);
        mEditTextNewAccountSurePass= (EditText) findViewById(R.id.etNewAccountSurePass);
        mButtonNewAccount= (Button) findViewById(R.id.bNewAccount);
    }

}

package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.R;

import java.util.ArrayList;
import java.util.List;

public class NewAccount extends AppCompatActivity {
    private EditText mEditTextNewAccountUser;
    private EditText mEditTextNewAccountPhone;
    private EditText mEditTextNewAccountEmail;
    private TextView mTextViewNewAccountArea;
    private TextView mTextViewNewAccountCity;;
    private EditText mEditTextNewAccountPass;
    private EditText mEditTextNewAccountSurePass;
    private Button mButtonNewAccount;
    private Spinner mSpinnerNewAccount;
    private Spinner mSpinnerNewAccount2;
    private String emailAddress;


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
        mTextViewNewAccountArea= (TextView) findViewById(R.id.tvNewAccountArea);
        mTextViewNewAccountCity= (TextView) findViewById(R.id.tvNewAccountCity);
        mEditTextNewAccountPass= (EditText) findViewById(R.id.etNewAccountPass);
        mEditTextNewAccountSurePass= (EditText) findViewById(R.id.etNewAccountSurePass);
        mButtonNewAccount= (Button) findViewById(R.id.bNewAccount);
        mSpinnerNewAccount= (Spinner) findViewById(R.id.NewAccountSpinner);
        mSpinnerNewAccount2= (Spinner) findViewById(R.id.NewAccountSpinner2);
        // Test Only
        List<String> list = new ArrayList<String>();
        list.add("اختر  المنطقه");
        list.add("منطقه الرياض");
        list.add(" منطقه مكه المكرمه");
        list.add("منطقه المدينه المنورة");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerNewAccount.setAdapter(dataAdapter);
        List<String> list2 = new ArrayList<String>();
        list2.add("اختر  المدينه");
        list2.add("مدينه الرياض");
        list2.add(" مدينه مكه المكرمه");
        list2.add("مدينه المدينه المنورة");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerNewAccount2.setAdapter(dataAdapter2);
         emailAddress= mEditTextNewAccountEmail.getText().toString();



    } private  boolean isValidEmailAddress() {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;


        }
        return true;
    }

}

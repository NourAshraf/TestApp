package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nour.makssab.R;

public class Search extends AppCompatActivity {
    private Button mButtonSearchAdv;
    private EditText mEditTextSearchAdv;
    private TextView mTextViewSearchMarka;
    private TextView mTextViewSearchnoo3;
    private TextView mTextViewSearchModel;
    private Button mButtonSearchSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Allvariables();

    }

    private void Allvariables() {
        mButtonSearchAdv=(Button)findViewById(R.id.bSearchAdv);
        mEditTextSearchAdv=(EditText) findViewById(R.id.etSearchAdv);
        mTextViewSearchMarka=(TextView)findViewById(R.id.tvSearchMarka);
        mTextViewSearchnoo3=(TextView)findViewById(R.id.tvSearchNoo3);
        mTextViewSearchModel=(TextView)findViewById(R.id.tvSearchModel);
        mButtonSearchSearch=(Button)findViewById(R.id.bSearchSearch);
    }

}

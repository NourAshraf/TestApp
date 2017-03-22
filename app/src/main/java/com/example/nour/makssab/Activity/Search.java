package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.R;

public class Search extends AppCompatActivity {
    private Button mButtonSearchAdv;
    private EditText mEditTextSearchAdv;
    private TextView mTextViewSearchMarka;
    private TextView mTextViewSearchNoo3;
    private TextView mTextViewSearchModel;
    private Button mButtonSearchSearch;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Allvariables();

    }

    private void Allvariables() {
        mButtonSearchAdv=(Button)findViewById(R.id.bSearchAdv);
        mEditTextSearchAdv=(EditText) findViewById(R.id.etSearchAdv);
        mTextViewSearchMarka=(TextView)findViewById(R.id.tvSearchMarka);
        mTextViewSearchNoo3=(TextView)findViewById(R.id.tvSearchNoo3);
        mTextViewSearchModel=(TextView)findViewById(R.id.tvSearchModel);
        mButtonSearchSearch=(Button)findViewById(R.id.bSearchSearch);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }

}

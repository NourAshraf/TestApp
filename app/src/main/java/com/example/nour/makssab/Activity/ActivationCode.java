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

public class ActivationCode extends AppCompatActivity {
    private EditText mEditTextActivation;
    private Button mButtonActivation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditTextActivation= (EditText) findViewById(R.id.etActivation);
        mButtonActivation= (Button) findViewById(R.id.bActivation);


    }

}

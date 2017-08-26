package com.example.nour.makssab.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivationCode extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextActivation;
    private Button mButtonActivation;
    private ImageView mImageViewBack;
    private RequestQueue mVolleySingletonRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
    }
    private void onVariables() {
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mEditTextActivation= (EditText) findViewById(R.id.etActivation);
        mButtonActivation= (Button) findViewById(R.id.bActivation);
        mButtonActivation.setOnClickListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bActivation:
                String mEmail = mEditTextActivation.getText().toString();
                onActivition(mEmail);
                break;
        }
    }

    private void onActivition(final String mEmail) {
        StringRequest mStringRequestActivition=new StringRequest(Request.Method.POST, MainApp.ChangePasswordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    if (mJsonObject.has("success")){
                        Toast.makeText(ActivationCode.this,mJsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ActivationCode.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("email",mEmail);
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestActivition);

    }
}

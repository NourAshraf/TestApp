package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText mEditTextLoginUser;
    private EditText mEditTextLoginPass;
    private CheckBox mCheckBoxLogin;
    private Button mButtonLoginMember;
    private Button mButtonLoginEnter;
    private TextView mTextViewLogin;
    private RequestQueue mVolleySingletonRequestQueue;

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
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
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
        mButtonLoginEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = mEditTextLoginUser.getText().toString();
                String Pass = mEditTextLoginPass.getText().toString();
                if (User.matches("")){
                    Toast.makeText(getApplicationContext(),"لابد من ادخال الاسم !",Toast.LENGTH_SHORT).show();
                }else if (Pass.matches("")){
                    Toast.makeText(getApplicationContext(),"لابد من ادخال كلمه المرور !",Toast.LENGTH_SHORT).show();
                }
               else if (User.matches(".{15,}+")){
                    Toast.makeText(getApplicationContext(),"الاسم غير صحيح!",Toast.LENGTH_SHORT).show();
                }else if (!Pass.matches(".{6,}+")) {
                    Toast.makeText(getApplicationContext(), "كلمه المرور غير صحيحه!", Toast.LENGTH_SHORT).show();
                }else {
                    onLogin(User,Pass);
                }

            }
        });

    }
    public void onLogin(final String user,final String pass){
        String Url= MainApp.LoginUrl;
        StringRequest mStringRequestonLogin=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,"Test");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>paramter=new HashMap<String, String>();
                paramter.put("username",user);
                paramter.put("password",pass);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonLogin);
    }

}

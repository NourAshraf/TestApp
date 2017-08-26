package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private  String filename="mkssab";
    private EditText mEditTextLoginUser;
    private EditText mEditTextLoginPass;
    private CheckBox mCheckBoxLogin;
    private Button mButtonLoginMember;
    public static Button mButtonLoginEnter;
    private TextView mTextViewLogin;
    private RequestQueue mVolleySingletonRequestQueue;
    private ImageView mImageViewBack;
   private SharedPreferences mSharedPreferences;
    private String token;
    private boolean checked;
    private String mUserName;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Allvariables();


    }

    private void Allvariables() {
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        checked = mSharedPreferences.getBoolean("Checked", false);
        token = mSharedPreferences.getString("token","");
        mUserName = mSharedPreferences.getString("UserName", "");
        mPassword = mSharedPreferences.getString("Password", "");
        mEditTextLoginUser=(EditText)findViewById(R.id.etLoginUser);
        mEditTextLoginPass=(EditText)findViewById(R.id.etLoginPass);
        mCheckBoxLogin=(CheckBox)findViewById(R.id.cbLogin);
        mCheckBoxLogin.setOnCheckedChangeListener(this);
        mButtonLoginMember=(Button)findViewById(R.id.bLoginMember);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     finish();
            }
        });
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
                    TastyToast.makeText(getApplicationContext(), "لابد من ادخال الاسم !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else if (Pass.matches("")){
                    TastyToast.makeText(getApplicationContext(), "لابد من ادخال كلمه المرور !", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }
               else {
                    onLogin(User,Pass);
                }
            }
        });
        if (checked){
            mCheckBoxLogin.setChecked(true);
            mEditTextLoginUser.setText(mUserName);
            mEditTextLoginPass.setText(mPassword);
        }else {
            mCheckBoxLogin.setChecked(false);
        }

   }


    public void onLogin(final String user,final String pass){
        String Url= MainApp.LoginUrl;
        StringRequest mStringRequestonLogin=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    if (mJsonObject.has("error")){
                        Toast.makeText(getApplicationContext(),"البريد الالكترونى او كلمه المرور غير صحيحه",Toast.LENGTH_SHORT).show();
                    }
                    mSharedPreferences.edit().putBoolean("Login",true).commit();
                    mSharedPreferences.edit().putString("token",mJsonObject.getString("token")).commit();
                    mSharedPreferences.edit().putString("UserName",user).commit();
                    mSharedPreferences.edit().putString("Password",pass).commit();
                    Intent mIntent=new Intent(Login.this,Home.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mIntent);
                    finish();


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
                Map<String,String>paramter=new HashMap<String, String>();
                paramter.put("login",user);
                paramter.put("password",pass);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonLogin);
    }

public void onLogout(){
    String Url= MainApp.LogoutUrl+token;
    StringRequest mStringRequestonLogut=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject mJsonObject = new JSONObject(response);
                Log.i(MainApp.Tag,"Logout");
                if (mJsonObject.has("success")){
                    Toast.makeText(getApplicationContext(),"تم تسجيل الخروج بنجاح",Toast.LENGTH_SHORT).show();
                    mSharedPreferences.edit().putBoolean("Login",false).commit();
                    mSharedPreferences.edit().putString("token","").commit();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    mVolleySingletonRequestQueue.add(mStringRequestonLogut);

}

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            mSharedPreferences.edit().putBoolean("Checked",true).commit();
        }else {
            mSharedPreferences.edit().putBoolean("Checked",false).commit();
        }
    }
}

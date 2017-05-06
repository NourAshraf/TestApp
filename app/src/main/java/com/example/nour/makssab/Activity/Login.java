package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Login extends AppCompatActivity {
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
         token = mSharedPreferences.getString("token","");
        mEditTextLoginUser=(EditText)findViewById(R.id.etLoginUser);
        mEditTextLoginPass=(EditText)findViewById(R.id.etLoginPass);
        mCheckBoxLogin=(CheckBox)findViewById(R.id.cbLogin);
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
                    //clicked=true;

                }

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
            case R.id.action_Logout:
              onLogout();
                break;



        }
        return true;
    }
    public void onLogin(final String user,final String pass){
        String Url= MainApp.LoginUrl;
        StringRequest mStringRequestonLogin=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    Log.i(MainApp.Tag,"Login");
                    if (mJsonObject.has("error")){
                        Toast.makeText(getApplicationContext(),"البريد الالكترونى او كلمه المرور غير صحيحه",Toast.LENGTH_SHORT).show();
                    }
                    mSharedPreferences.edit().putBoolean("Login",true).commit();
                    mSharedPreferences.edit().putString("token",mJsonObject.getString("token")).commit();
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


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
}

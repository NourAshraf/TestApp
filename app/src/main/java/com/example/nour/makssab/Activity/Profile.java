package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private  String filename2="mkssab";
    private TextView mTextViewName;
    private TextView mTextViewAds;
    private TextView mTextViewMember;
    private TextView mTextViewTime;
    private TextView mTextViewLast;
    private RequestQueue mVolleySingletonRequestQueue;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String token;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onVariables();
        onProfile();


    }

    private void onVariables() {
        mContext=Profile.this;
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token", "");
        mTextViewName= (TextView) findViewById(R.id.tvProfileName);
        mTextViewAds= (TextView) findViewById(R.id.tvProfileads);
        mTextViewMember= (TextView) findViewById(R.id.tvProfileMemebr);
        mTextViewTime= (TextView) findViewById(R.id.tvTime);
        mTextViewLast= (TextView) findViewById(R.id.Last);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();


    }
    public void onProfile(){
        String Url= MainApp.ProfileUrl+token;
        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    for (int i=0;i<mJsonArray.length();i++){
                        JSONObject jsonObject=mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject=jsonObject.getJSONObject("user");
                        String id=mJsonObject.getString("id");
                         username=mJsonObject.getString("username");
                        String email=mJsonObject.getString("email");
                        String created_at=mJsonObject.getString("created_at");
                        String last_login=mJsonObject.getString("last_login");

                        JSONObject mJsonObject1=jsonObject.getJSONObject("ads");
                        String next_page_url=mJsonObject1.getString("next_page_url");
                        JSONArray jsonArray=mJsonObject1.getJSONArray("data");
                        for (int j=0;j<jsonArray.length();j++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String id1=jsonObject1.getString("id");
                            String category_id=jsonObject1.getString("category_id");
                            String sub_category_id=jsonObject1.getString("sub_category_id");
                            String title=jsonObject1.getString("title");
                            String description=jsonObject1.getString("description");
                            String created_at1=jsonObject1.getString("created_at");

                        }
                        JSONObject mJsonObject2=jsonObject.getJSONObject("favoriteAds");
                        String next_page_url1=mJsonObject2.getString("next_page_url");
                        JSONArray jsonArray1=mJsonObject2.getJSONArray("data");
                        for (int j=0;j<jsonArray1.length();j++){
                            JSONObject jsonObject2=jsonArray1.getJSONObject(i);
                            String id2=jsonObject2.getString("id");
                            String category_id1=jsonObject2.getString("category_id");
                            String sub_category_id1=jsonObject2.getString("sub_category_id");
                            String title1=jsonObject2.getString("title");
                            String description1=jsonObject2.getString("description");
                            String created_at2=jsonObject2.getString("created_at");
                            String phone=jsonObject2.getString("phone");

                        }

                    }
                    mTextViewName.setText(username);

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonProfile);

    }

}

package com.ibtdi.team.mkssab.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibtdi.team.mkssab.Adapter.NotifiacationsAdapter;
import com.ibtdi.team.mkssab.MainApp.MainApp;
import com.ibtdi.team.mkssab.Model.NotificationsModel;
import com.ibtdi.team.mkssab.Network.VolleySingleton;
import com.ibtdi.team.mkssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    private ListView lvNotificationsList;
    private ArrayList<NotificationsModel> notifications;
    private ImageView mImageViewBack;
    private RequestQueue mVolleySingletonRequestQueue;
    private SharedPreferences mSharedPreferences;
    private String token;
    private  String filename2="mkssab";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
        onLoadNotification();
    }

    private void onLoadNotification() {
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, MainApp.NotificationsUrl+token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    JSONArray data = mJsonObject.getJSONArray("data");

                    for (int i=0;i<data.length();i++)
                    {
                        JSONObject mJsonObject2=data.getJSONObject(i);
                        String type = mJsonObject2.getString("type");
                        String data1 = mJsonObject2.getString("data");
                        if (type.equals("ads")){
                            String replace = data1.replace("{", "");
                            String replace1 = replace.replace("}", "");
                            String[] split = replace1.split(",");
                            String[] split1 = split[1].split(":");
                            String title = split1[1].replace("\"", "");
                            String[] split3 = split[3].split(":");
//                            String user = split3[1].replace("\"", "");
                            NotificationsModel model=new NotificationsModel(title,"",type);
                            notifications.add(model);
                        }else if (type.equals("follow")){
                            String replace = data1.replace("{", "");
                            String replace1 = replace.replace("}", "");
                            String[] split = replace1.split(",");
                            String[] split3 = split[1].split(":");
                            String user = split3[1].replace("\"", "");
                            NotificationsModel model=new NotificationsModel("",user,type);
                            notifications.add(model);
                        }else if (type.equals("unfollow")){
                            String replace = data1.replace("{", "");
                            String replace1 = replace.replace("}", "");
                            String[] split = replace1.split(",");
                            String[] split1 = split[0].split(":");
                            String title = split1[1].replace("\"", "");
                            String[] split3 = split[1].split(":");
                            String user = split3[1].replace("\"", "");
                            NotificationsModel model=new NotificationsModel(title,user,type);
                            notifications.add(model);
                        }


                    }
                    lvNotificationsList.setAdapter(new NotifiacationsAdapter(getApplicationContext(),notifications));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoadNotification();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                Log.i(MainApp.Tag,split[1]);
                mSharedPreferences.edit().putString("token",split[1]).commit();
                token=split[1];
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }

    private void onVariables() {

        mImageViewBack= (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvNotificationsList = (ListView) findViewById(R.id.lvNotificationsList);
        notifications = new ArrayList<NotificationsModel>();
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSharedPreferences=getSharedPreferences(filename2,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
    }

}

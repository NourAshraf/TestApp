package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.MessagesAdapter;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.MessageModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyMessages extends AppCompatActivity implements View.OnClickListener {

    private MyMessages mContext;
    private RecyclerView mRecyclerViewMessage;
    private ArrayList<MessageModel> modelsSend;
    private ArrayList<MessageModel> modelsRecive;
    private String filename="mkssab";
    private SharedPreferences mSharedPreferences;
    private String token;
    private RequestQueue mVolleySingletonRequestQueue;
    private Button mButtonSendMessages;
    private Button mButtonReciveMessages;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
    }

    @Override
    public void onBackPressed() {
        Intent Home=new Intent(mContext, com.example.nour.makssab.Activity.Home.class);
        startActivity(Home);
        finish();
    }

    private void onVariables() {
      mContext=MyMessages.this;
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        token = mSharedPreferences.getString("token","");
        mRecyclerViewMessage= (RecyclerView) findViewById(R.id.rvMesssages);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewMessage.setLayoutManager(manager);
        mRecyclerViewMessage.addItemDecoration(new VerticalSpaceItemDecoration(100));
        modelsSend=new ArrayList<MessageModel>();
        modelsRecive=new ArrayList<MessageModel>();
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mButtonSendMessages= (Button) findViewById(R.id.bSendMessages);
        mButtonReciveMessages= (Button) findViewById(R.id.bReciveMessages);
        mButtonReciveMessages.setOnClickListener(this);
        mButtonSendMessages.setOnClickListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentHome=new Intent(mContext,Home.class);
                startActivity(mIntentHome);
                finish();
            }
        });
        onLoadMessageData();
    }

    private void onLoadMessageData() {
        StringRequest mStringRequestMessages=new StringRequest(Request.Method.GET, MainApp.MessagesUrl+token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    JSONArray received = mJsonObject.getJSONArray("received");
                    JSONArray sent = mJsonObject.getJSONArray("sent");
                    for (int i=0;i<received.length();i++){
                        JSONObject jsonObject=received.getJSONObject(i);
                        String message = jsonObject.getString("message");
                        String id = jsonObject.getString("id");
                        MessageModel messageModel=new MessageModel(message,id);
                        modelsRecive.add(messageModel);
                    }
                    for (int j=0;j<sent.length();j++){
                        JSONObject jsonObject=sent.getJSONObject(j);
                        String message = jsonObject.getString("message");
                        String id = jsonObject.getString("id");
                        MessageModel messageModel=new MessageModel(message,id);
                        modelsSend.add(messageModel);
                    }
                    MessagesAdapter messagesAdapter=new MessagesAdapter(mContext,modelsRecive);
                    mRecyclerViewMessage.setAdapter(messagesAdapter);

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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                token=split[1];
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestMessages);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSendMessages:
                MessagesAdapter messagesAdapter=new MessagesAdapter(mContext,modelsSend);
                mRecyclerViewMessage.setAdapter(messagesAdapter);
                break;
            case R.id.bReciveMessages:
                MessagesAdapter messagesAdapter2=new MessagesAdapter(mContext,modelsRecive);
                mRecyclerViewMessage.setAdapter(messagesAdapter2);
                break;
        }
    }
}

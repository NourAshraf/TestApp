package com.example.nour.makssab.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.Message;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConversationMessage extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private RequestQueue mVolleySingletonRequestQueue;
    private  String filename="mkssab";
    private MessagesList messagesList;
    private MessagesListAdapter<Message> adapter;
    private ConversationMessage mContext;
    private ArrayList<Message> mMessages;
    private MessageInput input;
    private String id;
    private String token;
    private String mMyId;
    private String ads_id;
    private String mMyAdsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onVariables();
        onLoadMessages();
    }

    private void onLoadMessages() {
        String Url= MainApp.MessagesConvUrl+id+"?token="+token;
        StringRequest mStringRequestMessages=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,response);
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    for(int counter=mJsonArray.length() - 1; counter >= 0;counter--){
                        JSONObject mJsonObject=mJsonArray.getJSONObject(counter);
                        String message = mJsonObject.getString("message");
                        ads_id = mJsonObject.getString("ads_id");
                        if (ads_id.equals("0")){

                        }else {
                            Log.i(MainApp.Tag,ads_id);
                            Log.i(MainApp.Tag,"Worked");
                            mMyAdsId=ads_id;
                        }
                        String created_at = mJsonObject.getString("created_at");
                        String sender_id = mJsonObject.getString("sender_id");
                        String[] split = created_at.split(" ");
                        String mDate=split[0];
                        String Time=split[1];
                        String[] split1 = mDate.split("-");
                        int Year= Integer.parseInt(split1[0])-1900;
                        int Month= Integer.parseInt(split1[1])-1;
                        int Day= Integer.parseInt(split1[2]);
                        String[] split2 = Time.split(":");
                        int Hour= Integer.parseInt(split2[0]);
                        int Min= Integer.parseInt(split2[1]);
                        Calendar mCalendar=Calendar.getInstance();
                        mCalendar.set(Calendar.YEAR,Year);
                        mCalendar.set(Calendar.MONTH,Month);
                        mCalendar.set(Calendar.DAY_OF_MONTH,Day);
                        mCalendar.set(Calendar.HOUR,Hour);
                        mCalendar.set(Calendar.MINUTE,Min);
                        Date date=new Date(Year,Month,Day,Hour,Min);

                        if (mMyId.equals(sender_id)){
                            Message Other = new Message("0",message,"Me",null,date);
                            mMessages.add(Other);
                        }else {
                            Message Other = new Message("1",message,"Other",null, date);
                            mMessages.add(Other);
                        }
                    }
                    if (mMessages.size()==0){

                    }else {
                        adapter.addToEnd(mMessages, true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Log.i(MainApp.Tag,"Error");
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                mSharedPreferences.edit().putString("token",split[1]).commit();
                token=split[1];
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestMessages);
    }

    private void onVariables() {
        mContext=ConversationMessage.this;
        mMyAdsId="";
        id = getIntent().getExtras().getString("Id");
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        messagesList = (MessagesList) findViewById(R.id.messagesList);
        token = mSharedPreferences.getString("token","");
        mMyId = mSharedPreferences.getString("ID","");
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(mContext).load(url).into(imageView);
            }
        };
        adapter = new MessagesListAdapter<>("0",imageLoader);
        messagesList.setAdapter(adapter);
        mMessages=new ArrayList<Message>();
        input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(final CharSequence input) {
                Calendar calendar = Calendar.getInstance();
                Date time = calendar.getTime();
                Message Other = new Message("0",input.toString(),"Me",null, time);
                adapter.addToStart(Other,true);
                SendMessage(input.toString());
                return true;
            }
        });
    }

    private void SendMessage(final String Message) {
        JSONObject mJsonObject=new JSONObject();
        try {
            Log.i(MainApp.Tag,mMyAdsId);
            mJsonObject.put("ads_id",mMyAdsId);
            mJsonObject.put("message",Message);
            JsonObjectRequest mJsonObjectRequest=new JsonObjectRequest(Request.Method.POST, MainApp.SendMessageUrl + token, mJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                  Log.i(MainApp.Tag,response+"");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(MainApp.Tag,"Error");
                }
            });
            mVolleySingletonRequestQueue.add(mJsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

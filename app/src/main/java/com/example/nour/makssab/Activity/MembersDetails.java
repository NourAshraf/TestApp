package com.example.nour.makssab.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.MembersAdapter;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.nour.makssab.Activity.Home.FollowesIds;
import static com.example.nour.makssab.R.layout.members;

public class MembersDetails extends AppCompatActivity implements View.OnClickListener {

    private boolean mFollowPerson;
    private MembersDetails mContext;
    private String AdvId;
    private RequestQueue mVolleySingletonRequestQueue;
    private SharedPreferences mSharedPreferences;
    private boolean mLogin;
    private String token;
    private String mSharedPreferencesId;
    private String filename="mkssab";
    private TextView mTextViewProfileName;
    private TextView mTextViewFollowers;
    private ListView lvMembersList;
    private String username;
    private ArrayList<String> FollowesNames2;
    public static ArrayList<String> FollowesIds2;
    private String email;
    private String phone_main;
    private ArrayList<MembersModel> mMembers;
    private Button mButtonFollow;
    private AlertDialog.Builder mAlertDialog;
    private Button mButtonMyFav;
    private Button mButtonMyMember;
    private Button mButtonMyAdv;
    private String MyUssrId;
    private TextView mTextViewNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();
        onGetData();
    }

    private void onGetData() {
        String Url= MainApp.UserProfileUrl+AdvId;
        StringRequest mStringRequestonProfile=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mTextViewNo.setVisibility(View.VISIBLE);
                    JSONArray mJsonArray=new JSONArray(response);
                    for (int i=0;i<mJsonArray.length();i++){
                        JSONObject jsonObject=mJsonArray.getJSONObject(i);
                        JSONObject mJsonObject=jsonObject.getJSONObject("user");
                        JSONArray followers = mJsonObject.getJSONArray("followers");
                        int length = followers.length();
                        mTextViewFollowers.setText(length+" متابع");
                        JSONArray followees = mJsonObject.getJSONArray("followees");
                        for (int o=0;o<followees.length();o++){
                            mTextViewNo.setVisibility(View.GONE);
                            JSONObject followe = followees.getJSONObject(o);
                            String followeusername = followe.getString("username");
                            String followeid = followe.getString("id");
                            FollowesNames2.add(followeusername);
                            FollowesIds2.add(followeid);
                        }
                        MyUssrId=mJsonObject.getString("id");
                        username=mJsonObject.getString("username");
                        email=mJsonObject.getString("email");
                        String city_id = mJsonObject.getString("city_id");
                        String state_id = mJsonObject.getString("state_id");
                        String created_at=mJsonObject.getString("created_at");
                        String last_login=mJsonObject.getString("last_login");
                        phone_main=mJsonObject.getString("phone");
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
                    mTextViewProfileName.setText(username);
                    for (int i=0;i<FollowesIds2.size();i++){
                        MembersModel membersModel = new MembersModel(FollowesNames2.get(i), FollowesIds2.get(i), "");
                        mMembers.add(membersModel);
                    }
                    lvMembersList.setAdapter(new MembersAdapter(getApplicationContext(),mMembers));
                    onVariables();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGetData();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String phpsessid = response.headers.get("Authorization");
//                String[] split = phpsessid.split(" ");
//                token=split[1];
//                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonProfile);
    }

    private void onVariables() {
        mContext=MembersDetails.this;
        mFollowPerson=false;
        mTextViewNo = (TextView) findViewById(R.id.tvNo);
        mButtonFollow= (Button) findViewById(R.id.bFollow);
        mButtonFollow.setOnClickListener(this);
        mButtonMyFav= (Button) findViewById(R.id.bMyFav);
        mButtonMyAdv= (Button) findViewById(R.id.bMyAdv);
        mButtonMyMember= (Button) findViewById(R.id.bMyMember);
        mButtonMyAdv.setOnClickListener(this);
        mButtonMyFav.setOnClickListener(this);
        mButtonMyMember.setOnClickListener(this);
        AdvId = getIntent().getExtras().getString("UserId");
        for (int q=0;q<FollowesIds.size();q++){
            if (AdvId.equals(FollowesIds.get(q))){
                mFollowPerson=true;
                mButtonFollow.setText("الغاء المتابعة");
            }
        }
        FollowesNames2=new ArrayList<String>();
        FollowesIds2=new ArrayList<String>();
        mMembers = new ArrayList<MembersModel>();
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        mLogin = mSharedPreferences.getBoolean("Login",false);
        token = mSharedPreferences.getString("token", "");
        mSharedPreferencesId = mSharedPreferences.getString("ID", "");
        mTextViewFollowers= (TextView) findViewById(R.id.tvFollowers);
        mTextViewProfileName= (TextView) findViewById(R.id.tvProfileName);
        lvMembersList = (ListView) findViewById(R.id.lvMembers);
        ImageView mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void onUnFollow() {
        String Url=MainApp.UnfollowUrl+AdvId+"?token="+token;
        Log.i(MainApp.Tag,Url);
        StringRequest mStringRequestCheckFav=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,"UnFollow:-->"+response);
                Toast.makeText(mContext,"تمت الغاء متابعة العضو بنجاح",Toast.LENGTH_SHORT).show();
                mFollowPerson=false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainApp.Tag,"Error");
                onUnFollow();
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
        mVolleySingletonRequestQueue.add(mStringRequestCheckFav);
    }

    private void onFollow() {
        String Url= MainApp.followUrl+AdvId+"?token="+token;
        Log.i(MainApp.Tag,Url);
        StringRequest mStringRequestCheckFav=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,"Follow:-->"+response);
                Toast.makeText(mContext,"تمت متابعة العضو بنجاح",Toast.LENGTH_SHORT).show();
                mFollowPerson=true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainApp.Tag,"Error");
                onFollow();
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
        mVolleySingletonRequestQueue.add(mStringRequestCheckFav);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bMyAdv:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_7);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyAdv.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (mLogin){
                    Intent mIntent=new Intent(mContext,MyAdvertisement.class);
                    mIntent.putExtra("UserId",MyUssrId);
                    startActivity(mIntent);
                }else {
                    Intent mIntent=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent);

                }
                break;
            case R.id.bMyFav:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_6);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_2);
                mButtonMyFav.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyMember.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (mLogin){
                    Intent mIntentFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                    mIntentFavorites.putExtra("username",username+"");
                    mIntentFavorites.putExtra("email",email);
                    mIntentFavorites.putExtra("phone",phone_main);
                    mIntentFavorites.putExtra("UserId",MyUssrId);
                    mIntentFavorites.putExtra("Member","Member");
                    mContext.startActivity(mIntentFavorites);
                }else {
                    Intent mIntent5=new Intent(getApplicationContext(),Login.class);
                    mContext.startActivity(mIntent5);
                }
                break;
            case R.id.bMyMember:
                mButtonMyFav.setBackgroundResource(R.drawable.button_bg_3);
                mButtonMyAdv.setBackgroundResource(R.drawable.button_bg_4);
                mButtonMyMember.setBackgroundResource(R.drawable.button_bg_5);
                mButtonMyMember.setTextColor(getResources().getColor(android.R.color.background_light));
                mButtonMyFav.setTextColor(getResources().getColor(R.color.colorPrimary));
                mButtonMyAdv.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
                    case R.id.bFollow:
                if (mLogin){
                    Log.i(MainApp.Tag,mFollowPerson+"");
                    if (mFollowPerson){
                        mAlertDialog = new AlertDialog.Builder(mContext);
                        mAlertDialog.setMessage("هل تريد الغاء متابعة اعلانات " + username);
                        mAlertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mButtonFollow.setText("متابعة");
                                onUnFollow();
                            }
                        });
                        mAlertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        mAlertDialog.show();
                    }else {
                        mAlertDialog = new AlertDialog.Builder(mContext);
                        mAlertDialog.setMessage("هل تريد متابعة اعلانات " + username);
                        mAlertDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onFollow();
                                mButtonFollow.setText("الغاء المتابعة");
                            }
                        });
                        mAlertDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        mAlertDialog.show();
                    }
                }else {
                    Toast.makeText(mContext,"يجب عليك تسجيل الدخول لتسطيع متابعة هذا العضو",Toast.LENGTH_SHORT).show();
                }
                        break;
                }
    }
}

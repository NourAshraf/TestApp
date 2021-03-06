package com.ibtdi.team.mkssab.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.ibtdi.team.mkssab.MainApp.MainApp;
import com.ibtdi.team.mkssab.Network.VolleySingleton;
import com.ibtdi.team.mkssab.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ibtdi.team.mkssab.Activity.Home.FollowesIds;

public class AdvDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    private ArrayList<String> images;
    private String userName;
    private String title;
    static final Integer CALL = 0x2;
    private String city_name;
    private String description;
    private TextView mTextViewTitle;
    private TextView mTextViewName;
    private TextView mTextViewLocation;
    private TextView mTextViewDescription;
    private SliderLayout mDemoSlider;
    private String MyTime;
    private TextView mTextViewComments;
    private TextView mTextViewViews;
    private TextView mTextViewTime;
    private String mViews;
    private String mComments;
    private TextView mTextViewPhone;
    private String mPhone;
    private Button mShare;
    private Button mComplaint;
    private String Id;
    private String ShareUrl;
    private String filename="mkssab";
    private int REQUEST_CODE_INTRO=1;
    private SharedPreferences mSharedPreferences;
    private String token;
    private RequestQueue mVolleySingletonRequestQueue;
    private boolean mLogin;
    private EditText input;
    private String text;
    private EditText editText;
    private String Msg;
    private String MsgComment;
    private Button buttonCall;
    private AdvDetails mContext;
    private Button mcomment;
    private Button mButtonFav;
    private Drawable mDrawableFavOn;
    private Drawable mDrawableFavOff;
    private boolean mFav;
    private boolean mFollowed;
    private Button mButtonCommentFollow;
    private Button mSendMassage;
    private Drawable mDrawableFollowOff;
    private Drawable mDrawableFollowOn;
    private AlertDialog.Builder mAlertDialog;
    private String mSharedPreferencesId;
    private String AdvId;
    private boolean mFollowPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        onGetIntentData();
        onVariables();
        if (mLogin){
            OnCheckFav();
            OnCheckFollow();
        }
    }

    private void onVariables() {
        mContext=AdvDetails.this;
        mFav=false;
        mFollowPerson=false;
        for (int q=0;q<FollowesIds.size();q++){
            if (AdvId.equals(FollowesIds.get(q))){
                mFollowPerson=true;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableFavOn=getDrawable(R.drawable.ic_favorite_heart_button);
            mDrawableFavOff=getDrawable(R.drawable.ic_heart);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableFollowOn=getDrawable(R.drawable.ic_like);
            mDrawableFollowOff=getDrawable(R.drawable.ic_comments);
        }
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        mLogin = mSharedPreferences.getBoolean("Login",false);
        token = mSharedPreferences.getString("token", "");
        mSharedPreferencesId = mSharedPreferences.getString("ID", "");
        mButtonFav= (Button) findViewById(R.id.bFavAdv);
        mButtonFav.setOnClickListener(this);
        mTextViewTitle= (TextView) findViewById(R.id.tvAdvTitle);
        mButtonCommentFollow= (Button) findViewById(R.id.button2);
        mButtonCommentFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogin){
                    onCommentFollow();
                }else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
            }
        });

        mcomment=(Button)findViewById(R.id.comment);
        mcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogin){
                    final Dialog mDialog=new Dialog(AdvDetails.this);
                    mDialog.setCancelable(true);
                    mDialog.setContentView(R.layout.comment_dialog);
                    final EditText mEditTextComment=(EditText)mDialog.findViewById(R.id.etComment);

                    Button mButtonCancel= (Button) mDialog.findViewById(R.id.cancel);
                    mButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    Button mButtonOk= (Button) mDialog.findViewById(R.id.send);
                    mButtonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MsgComment = mEditTextComment.getText().toString();
                            onComment(Id);
                        }
                    });
                    mDialog.show();

                }else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }


            }
        });
        mSendMassage=(Button)findViewById(R.id.bSendMessage);
        mSendMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogin){

                    final Dialog mDialog=new Dialog(AdvDetails.this);
                    mDialog.setCancelable(true);
                    mDialog.setContentView(R.layout.send_massage);
                    final EditText mEditText=(EditText)mDialog.findViewById(R.id.etComplaint);
                    mEditText.setText("بخوص اعلانك رقم "+Id);
                    final TextView mTextView=(TextView) mDialog.findViewById(R.id.tvsendname);
                    mTextView.setText("ارسال رساله الى العضو "+userName);


                    Button mButtonCancel= (Button) mDialog.findViewById(R.id.cancel);
                    mButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    Button mButtonOk= (Button) mDialog.findViewById(R.id.send);
                    mButtonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Msg = mEditText.getText().toString();
                            SendMessage(Msg);

                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();

                }else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }

            }
        });
        mComplaint= (Button) findViewById(R.id.bAdvDetailsComplaint);
        mComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (mLogin){

                    final Dialog mDialog=new Dialog(AdvDetails.this);
                    mDialog.setCancelable(true);
                    mDialog.setContentView(R.layout.dialog);
                    final EditText mEditText=(EditText)mDialog.findViewById(R.id.etComplaint);

                    Button mButtonCancel= (Button) mDialog.findViewById(R.id.cancel);
                    mButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    Button mButtonOk= (Button) mDialog.findViewById(R.id.send);
                    mButtonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Msg = mEditText.getText().toString();
                            onComplaint(Id);
                        }
                    });
                    mDialog.show();

                }else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
                }



        });


        mShare= (Button) findViewById(R.id.bShare);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ShareUrl);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        mTextViewName= (TextView) findViewById(R.id.tvAdvOwner);
        mTextViewName.setOnClickListener(this);
        mTextViewComments= (TextView) findViewById(R.id.tvAdvComments);
        mTextViewViews= (TextView) findViewById(R.id.tvAdvViews);
        mTextViewTime= (TextView) findViewById(R.id.tvAdvTime);
        mTextViewLocation= (TextView) findViewById(R.id.tvAdvCity);
        mTextViewPhone= (TextView) findViewById(R.id.tvPhone);
        mTextViewDescription= (TextView) findViewById(R.id.tvDescription);
        mTextViewDescription.setText(description);
        mTextViewLocation.setText(city_name);
        mTextViewName.setText(userName);
        mTextViewTitle.setText(title);
        mTextViewComments.setText(mComments);
        mTextViewTime.setText(MyTime);
        mTextViewViews.setText(mViews);
        mTextViewPhone.setText(mPhone);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        for (int i=0;i<images.size();i++) {
            String Url=MainApp.ImagesUrl+images.get(i);
            url_maps.put(""+i,Url+"");
        }
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.movePrevPosition();
        buttonCall=(Button) findViewById(R.id.button3);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPermission(Manifest.permission.CALL_PHONE,CALL);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bFavAdv:
                if (mLogin){
                    onAddFav();
                }else {
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
                break;
            case R.id.tvAdvOwner:
                if (mLogin) {
                    Intent mIntent = new Intent(mContext, MembersDetails.class);
                    mIntent.putExtra("UserId",AdvId);
                    startActivity(mIntent);
                }
                break;
        }
    }


    private void OnCheckFav() {
        String Url=MainApp.likedAdsUrl+Id+"?token="+token;
        Log.i(MainApp.Tag,Url);
        StringRequest mStringRequestCheckFav=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,response);
                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    if (mJsonObject.getBoolean("like")){
                        mFav=true;
                        mButtonFav.setCompoundDrawablesWithIntrinsicBounds(null,mDrawableFavOn,null,null);
                    }else {
                      mFav=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainApp.Tag,"Error");
                OnCheckFav();
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
    private void OnCheckFollow() {
        String Url=MainApp.FollowedAdsUrl+Id+"?token="+token;
        StringRequest mStringRequestCheckFollow=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    if (mJsonObject.getBoolean("follow")){
                        mFollowed=true;
                       // Toast.makeText(getApplicationContext(),"TRUE",Toast.LENGTH_SHORT).show();
                        mButtonCommentFollow.setCompoundDrawablesWithIntrinsicBounds(mDrawableFollowOn,null,null,null);
                    }else {
                        mFollowed=false;
                      //  Toast.makeText(getApplicationContext(),"False",Toast.LENGTH_SHORT).show();
                        mButtonCommentFollow.setCompoundDrawablesWithIntrinsicBounds(mDrawableFollowOff,null,null,null);
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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                token=split[1];
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }

        };
        mVolleySingletonRequestQueue.add(mStringRequestCheckFollow);
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);
            }
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mPhone));
            if (ActivityCompat.checkSelfPermission(MainApp.getAppContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        }
    }


    private void onGetIntentData() {
        title = getIntent().getExtras().getString("Title");
        Id = getIntent().getExtras().getString("Id");
        AdvId = getIntent().getExtras().getString("UserId");
        ShareUrl="http://mkssab.com/ads-details/"+Id;
        city_name = getIntent().getExtras().getString("City_Name");
        description = getIntent().getExtras().getString("Description");
        userName = getIntent().getExtras().getString("UserName");
        images = getIntent().getExtras().getStringArrayList("Images");
        MyTime = getIntent().getExtras().getString("MyTime");
        mViews = getIntent().getExtras().getString("Views");
        mComments = getIntent().getExtras().getString("Comments");
        mPhone = getIntent().getExtras().getString("Phone");
    }
    private void onSendData(){
        Intent intent=new Intent(AdvDetails.this,PhotoZoom.class);
        intent.putExtra("Images",images);
        startActivity(intent);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        onSendData();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void onComplaint(final String Id){
        String Url=MainApp.ComplaintUrl+token;
        StringRequest mStringRequestonComplaint=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Log.i(MainApp.Tag,response);
                    if (jsonObject.has("success")){
                        Toast.makeText(getApplicationContext(),"تم ارسال رسالة التبليغ بنجاح",Toast.LENGTH_SHORT).show();


                    }else if (Msg.equals("")){
                        Toast.makeText(getApplicationContext(),"لابد من ادخال نص الرساله",Toast.LENGTH_SHORT).show();



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
                Map<String,String>paramter=new HashMap<String, String>();
                paramter.put("ads_id",Id);
                paramter.put("message", Msg);
                paramter.put("comment_id","0");
                paramter.put("type","0");
                return paramter;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                token=split[1];
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }

        };
        mVolleySingletonRequestQueue.add(mStringRequestonComplaint);
    }

    public void onComment(final String Id){
        String Url=MainApp.CommentUrl+token;
        StringRequest mStringRequestonComment=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Log.i(MainApp.Tag,response);
                    if (jsonObject.has("success")){
                        Toast.makeText(getApplicationContext(),"تم اضافة تعليق بنجاح",Toast.LENGTH_SHORT).show();
                    }else if (MsgComment.equals("")){
                        Toast.makeText(getApplicationContext(),"لابد من ادخال نص التعليق",Toast.LENGTH_SHORT).show();

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
                Map<String,String>paramter=new HashMap<String, String>();
                paramter.put("ads_id",Id);
                paramter.put("comment",MsgComment);

                return paramter;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String phpsessid = response.headers.get("Authorization");
                String[] split = phpsessid.split(" ");
                token=split[1];
                mSharedPreferences.edit().putString("token",split[1]).commit();
                return super.parseNetworkResponse(response);
            }

        };
        mVolleySingletonRequestQueue.add(mStringRequestonComment);
    }
    public void onCommentFollow(){
        String Url=MainApp.CommentFollowUrl+Id+"?token="+token;
        StringRequest mStringRequestonComment=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if (mFollowed){
                    mFollowed=false;
                    mButtonCommentFollow.setCompoundDrawablesWithIntrinsicBounds(mDrawableFollowOff,null,null,null);
                }else {
                    mFollowed=true;
                    mButtonCommentFollow.setCompoundDrawablesWithIntrinsicBounds(mDrawableFollowOn,null,null,null);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Log.i(MainApp.Tag,"Error 220");
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
        mVolleySingletonRequestQueue.add(mStringRequestonComment);
    }

    private void onAddFav() {
        String Url= MainApp.likeAdsUrl+Id+"?token="+token;
        StringRequest mStringRequestAddFav=new StringRequest(Request.Method.GET,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (mFav){
                        mFav=false;
                        mButtonFav.setCompoundDrawablesWithIntrinsicBounds(null,mDrawableFavOff,null,null);
                    }else {
                        mFav=true;
                        mButtonFav.setCompoundDrawablesWithIntrinsicBounds(null,mDrawableFavOn,null,null);
                    }
                    JSONObject mJsonObject=new JSONObject(response);
                    //Toast.makeText(mContext,mJsonObject.getString("success"),Toast.LENGTH_SHORT).show();
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
        mVolleySingletonRequestQueue.add(mStringRequestAddFav);
    }
    private void SendMessage(final String Message) {
        JSONObject mJsonObject=new JSONObject();
        try {
            mJsonObject.put("ads_id",Id);
            mJsonObject.put("message",Message);
            JsonObjectRequest mJsonObjectRequest=new JsonObjectRequest(Request.Method.POST, MainApp.SendMessageUrl + token, mJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                  //  Toast.makeText(mContext,response+"", Toast.LENGTH_SHORT).show();
                    Log.i(MainApp.Tag,response+"");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(MainApp.Tag,"Error");
                }
            }){
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    String phpsessid = response.headers.get("Authorization");
                    String[] split = phpsessid.split(" ");
                    token=split[1];
                    mSharedPreferences.edit().putString("token",split[1]).commit();
                    return super.parseNetworkResponse(response);
                }
            };
            mVolleySingletonRequestQueue.add(mJsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

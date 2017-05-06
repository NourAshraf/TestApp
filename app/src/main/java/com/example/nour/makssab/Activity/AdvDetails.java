package com.example.nour.makssab.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private ArrayList<String> images;
    private String userName;
    private String title;
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
        mLogin = mSharedPreferences.getBoolean("Login",false);
    }

    private void onVariables() {
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        token = mSharedPreferences.getString("token", "");
        mTextViewTitle= (TextView) findViewById(R.id.tvAdvTitle);
        mComplaint= (Button) findViewById(R.id.bAdvDetailsComplaint);
        mComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (mLogin){

                    final Dialog mDialog=new Dialog(AdvDetails.this);
                    mDialog.setCancelable(true);
                    mDialog.setContentView(R.layout.dialog);
                    EditText mEditText=(EditText)mDialog.findViewById(R.id.etComplaint);
                     Msg = mEditText.getText().toString();
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
    }


    private void onGetIntentData() {
        title = getIntent().getExtras().getString("Title");
        Id = getIntent().getExtras().getString("Id");
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
                    Log.i(MainApp.Tag,"error");
                    if (jsonObject.has("success")){
                        Toast.makeText(getApplicationContext(),"تم ارسال رسالة التبليغ بنجاح",Toast.LENGTH_SHORT).show();


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
        };
        mVolleySingletonRequestQueue.add(mStringRequestonComplaint);
    }
}

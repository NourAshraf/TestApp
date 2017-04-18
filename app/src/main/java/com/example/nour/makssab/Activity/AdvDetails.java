package com.example.nour.makssab.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.R;

import java.util.ArrayList;
import java.util.HashMap;

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
    }

    private void onVariables() {
        mTextViewTitle= (TextView) findViewById(R.id.tvAdvTitle);
        mComplaint= (Button) findViewById(R.id.bAdvDetailsComplaint);
        mComplaint.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdvDetails.this);
                alertDialog.setTitle("التبليغ عن اعلان");
                alertDialog.setMessage("نص الرساله");

                final EditText input = new EditText(AdvDetails.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_call_answer2);

                alertDialog.setPositiveButton("ارسال التبليغ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });

                alertDialog.setNegativeButton("الغاء",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
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
}

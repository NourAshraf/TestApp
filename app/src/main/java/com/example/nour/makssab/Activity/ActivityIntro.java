package com.example.nour.makssab.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nour.makssab.Fragment.FragmentIntro;
import com.example.nour.makssab.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class ActivityIntro extends IntroActivity {
    private String filename="mkssab";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean("Intro",false).commit();
        /* Tint CTA button text/background */
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .fragment(FragmentIntro.newInstance("",""))
                .build());

    }

}

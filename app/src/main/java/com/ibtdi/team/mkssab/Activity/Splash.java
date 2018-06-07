package com.ibtdi.team.mkssab.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ibtdi.team.mkssab.R;

public class Splash extends Activity {

    private Context mContext;
    private String filename="mkssab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        onVariables();
    }

    private void onVariables() {
        mContext=Splash.this;
        SharedPreferences mSharedPreferences=getSharedPreferences(filename,MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean("Intro",true).commit();
        Thread mThread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent mIntent = new Intent(mContext, Home.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        };
        mThread.start();
    }
}

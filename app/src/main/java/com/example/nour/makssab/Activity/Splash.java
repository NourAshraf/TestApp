package com.example.nour.makssab.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.nour.makssab.R;

public class Splash extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        onVariables();
        //Hello Kareem
    }

    private void onVariables() {
        mContext=Splash.this;
        Thread mThread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent mIntent = new Intent(mContext, ActivityIntro.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        };
        mThread.start();
    }
}

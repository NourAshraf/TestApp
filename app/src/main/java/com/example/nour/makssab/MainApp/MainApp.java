package com.example.nour.makssab.MainApp;

import android.app.Application;
import android.content.Context;

/**
 * Created by nour on 01-Nov-16.
 */

public class MainApp extends Application {
    private static MainApp mInstance;
    public static String Tag="MyTest";
    public static MainApp getsInstance(){

        return mInstance;
    }
    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
}

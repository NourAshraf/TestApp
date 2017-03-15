package com.example.nour.makssab.MainApp;

import android.app.Application;
import android.content.Context;

/**
 * Created by nour on 01-Nov-16.
 */

public class MainApp extends Application {
    private static MainApp mInstance;
    public static String Tag="MyTest";
    public static String CategoryUrl="http://mkssab.com/api/category/";
    public static String AdvUrl="http://mkssab.com/api/index";
    public static String RegUrl="http://mkssab.com/api/register";
    public static String CitiesUrl="http://mkssab.com/api/cities/";
    public static String StatesUrl="http://mkssab.com/api/states";

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

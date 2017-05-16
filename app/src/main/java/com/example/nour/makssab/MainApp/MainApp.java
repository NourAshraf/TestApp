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
    public static String ImagesUrl="http://mkssab.com/uploads/";
    public static String RegUrl="http://mkssab.com/api/register";
    public static String CitiesUrl="http://mkssab.com/api/cities/";
    public static String StatesUrl="http://mkssab.com/api/states";
    public static String LoginUrl="http://mkssab.com/api/login";
    public static String StoriesUrl="http://mkssab.com/api/merchants";
    public static String ExhibitionsUrl="http://www.mkssab.com/api/museums";
    public static String BuildingsUrl="http://www.mkssab.com/api/companies";
    public static String CarBrandsUrl="http://mkssab.com/api/carBrands";
    public static String CarModelsUrl="http://mkssab.com/api/carModels/";
    public static String StoresDetailsUrl="http://www.mkssab.com/api/merchant/";
    public static String CategoryDetailsUrl="http://mkssab.com/api/sub-category/";
    public static String ProfileUrl="http://mkssab.com/api/profile?token=";
    public static String BuildingDetailsUrl="http://www.mkssab.com/api/company/";
    public static String ExhibitionDetailsUrl="http://www.mkssab.com/api/museum/";
    public static String LogoutUrl="http://mkssab.com/api/logout?token=";
    public static String ComplaintUrl="http://mkssab.com/api/report-ads?token=";
    public static String CommentUrl="http://mkssab.com/api/add-comment?token=";
    public static String SearchUrl="http://mkssab.com/api/museums?state_id=";
    public static String SearchNumberUrl="http://www.mkssab.com/api/ads-details/";
    public static String ChangePasswordUrl="http://www.mkssab.com/api/password/email";



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

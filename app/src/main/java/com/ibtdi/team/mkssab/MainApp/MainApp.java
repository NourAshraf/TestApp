package com.ibtdi.team.mkssab.MainApp;

import android.app.Application;
import android.content.Context;

/**
 * Created by team on 01-Nov-16.
 */

public class MainApp extends Application {
    private static MainApp mInstance;
    public static String Tag="MyTest";
    public static String CategoryUrl="https://mkssab.com/api/category/";
    public static String AdvUrl="https://mkssab.com/api/index";
    public static String ImagesUrl="https://mkssab.com/uploads/";
    public static String RegUrl="https://mkssab.com/api/register";
    public static String CitiesUrl="https://mkssab.com/api/cities/";
    public static String StatesUrl="https://mkssab.com/api/states";
    public static String LoginUrl="https://mkssab.com/api/login";
    public static String StoriesUrl="https://mkssab.com/api/merchants";
    public static String ExhibitionsUrl="https://www.mkssab.com/api/museums";
    public static String BuildingsUrl="https://www.mkssab.com/api/companies";
    public static String CarBrandsUrl="https://mkssab.com/api/carBrands";
    public static String CarModelsUrl="https://mkssab.com/api/carModels/";
    public static String StoresDetailsUrl="https://www.mkssab.com/api/merchant/";
    public static String CategoryDetailsUrl="https://mkssab.com/api/sub-category/";
    public static String ProfileUrl="https://mkssab.com/api/profile?token=";
    public static String BuildingDetailsUrl="https://www.mkssab.com/api/company/";
    public static String ExhibitionDetailsUrl="https://www.mkssab.com/api/museum/";
    public static String LogoutUrl="https://mkssab.com/api/logout?token=";
    public static String ComplaintUrl="https://mkssab.com/api/report-ads?token=";
    public static String CommentUrl="https://mkssab.com/api/add-comment?token=";
    public static String CommentFollowUrl="https://mkssab.com/api/add-comment-follows/";
    public static String SearchUrl="https://mkssab.com/api/museums?state_id=";
    public static String SearchUrl2="https://mkssab.com/api/museums?car_id=";
    public static String SearchNumberUrl="https://www.mkssab.com/api/ads-details/";
    public static String ChangePasswordUrl="https://www.mkssab.com/api/password/email";
    public static String likeAdsUrl="https://www.mkssab.com/api/like-ads/";
    public static String MessagesUrl="https://mkssab.com/api/messages?token=";
    public static String MessagesConvUrl="https://mkssab.com/api/messages/";
    public static String likedAdsUrl="https://mkssab.com/api/liked_ads/";
    public static String FollowedAdsUrl="https://www.mkssab.com/api/followed_ads/";
    public static String SendMessageUrl="https://mkssab.com/api/send-message?token=";
    public static String AddAdsUrl="https://mkssab.com/api/add-ads?token=";
    public static String followUrl="https://mkssab.com//api/follow/";
    public static String UnfollowUrl="https://mkssab.com//api/unfollow/";
    public static String UserProfileUrl="https://mkssab.com/api/profile/";
    public static String NotificationsUrl="https://mkssab.com/api/notifications?token=";



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


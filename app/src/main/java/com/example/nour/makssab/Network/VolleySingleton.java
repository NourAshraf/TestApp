package com.example.nour.makssab.Network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.nour.makssab.MainApp.MainApp;

/**
 * Created by nour on 01-Nov-16.
 */

public class VolleySingleton {
    private final RequestQueue mRequestQueue;
    private static VolleySingleton mInstance=null;
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MainApp.getAppContext());
    }
    public static VolleySingleton getsInstance(){
           if (mInstance==null){
               mInstance=new VolleySingleton();
           }
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}

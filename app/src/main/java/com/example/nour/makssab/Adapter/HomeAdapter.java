package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.HomeModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nour on 18-Jan-17.
 */

public class HomeAdapter extends ArrayAdapter{
    private final int mLayout;
    private final ArrayList<HomeModel> mArray;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final RequestQueue mVolleySingletonRequestQueue;
    private View view;

    public HomeAdapter(Context context, int resource, ArrayList<HomeModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mLayout=resource;
        mArray=objects;
        mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView==null) {
            view = mInflater.inflate(mLayout, parent, false);
            mHolder=new ViewHolder();
            mHolder.mImageViewHomeImage= (ImageView) view.findViewById(R.id.ivHomeImage);
            mHolder.mtTextViewHomeTitle= (TextView) view.findViewById(R.id.tvHomeTitle);
            view.setTag(mHolder);
        }else {
            view =convertView;
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mtTextViewHomeTitle.setText(mArray.get(position).getTitle());
        Picasso.with(mContext).load(mArray.get(position).getImage()).fit().into(mHolder.mImageViewHomeImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetCategory(mArray.get(position).getId());
            }
        });
        return view;
    }

    private void onGetCategory(int id) {
        String Url="http://mkssab.com/api/category/"+id;
        StringRequest mStringRequestGetCategory=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(MainApp.Tag,response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainApp.Tag,"error");

            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestGetCategory);
    }

    class ViewHolder {
     ImageView mImageViewHomeImage;
     TextView mtTextViewHomeTitle;
    }
}

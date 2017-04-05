package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Model.CategoryModel;
import com.example.nour.makssab.Model.ProfileModel;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kareem on 4/3/2017.
 */

public class ProfileAdapter extends ArrayAdapter {
    private final int mLayout;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private View view;
    private ArrayList<ProfileModel> models;


    public ProfileAdapter( Context context,  int resource) {
        super(context, resource);
        mContext=context;
        mLayout=resource;
        mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProfileAdapter.ViewHolder mHolder;
        if (convertView==null) {
            view = mInflater.inflate(mLayout, parent, false);
            mHolder=new ProfileAdapter.ViewHolder();
            mHolder.mTextViewName= (TextView) view.findViewById(R.id.tvProfileName);
            view.setTag(mHolder);
        }else {
            view =convertView;

        }

        return view;
    }

    public class ViewHolder {
        TextView mTextViewName;
    }
}

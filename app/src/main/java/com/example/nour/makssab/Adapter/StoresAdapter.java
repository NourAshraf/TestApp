package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.StoresModel;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/14/2017.
 */

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoriesHolder> {

    private final ArrayList<StoresModel> mArray;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public StoresAdapter(Context context, ArrayList<StoresModel> models) {
        mArray=models;
        mContext=context;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public StoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_stories, parent, false);
        StoriesHolder storiesHolder = new StoriesHolder(view);
        return storiesHolder;
    }

    @Override
    public void onBindViewHolder(StoriesHolder holder, int position) {
        holder.mTextViewNum.setText(mArray.get(position).getAds_count());
        holder.mTextViewLocation.setText(mArray.get(position).getCity_name());
        holder.mTextViewTitle.setText(mArray.get(position).getName());
        Picasso.with(mContext).load(MainApp.ImagesUrl+mArray.get(position).getPhoto()).fit().into(holder.mImageViewImage);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class StoriesHolder extends RecyclerView.ViewHolder{

        private final TextView mTextViewTitle;
        private final TextView mTextViewLocation;
        private final TextView mTextViewNum;
        private final ImageView mImageViewImage;

        public StoriesHolder(View itemView) {
            super(itemView);
            mTextViewTitle= (TextView) itemView.findViewById(R.id.tvStoriesTitle);
            mTextViewLocation= (TextView) itemView.findViewById(R.id.tvStoriesLocation);
            mTextViewNum= (TextView) itemView.findViewById(R.id.tvStoriesNum);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivStoriesImage);
        }
    }
}

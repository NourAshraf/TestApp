package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Activity.AdvDetails;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nour on 19-Jan-17.
 */

public class AdvAdapter extends RecyclerView.Adapter<AdvAdapter.AdvHolder> {

    private final ArrayList<AdvModel> mArray;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public AdvAdapter(Context context, ArrayList<AdvModel> models) {
        mArray=models;
        mContext=context;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public AdvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_adv, parent, false);
        AdvHolder advHolder = new AdvHolder(view);
        return advHolder;
    }

    @Override
    public void onBindViewHolder(AdvHolder holder, final int position) {
       holder.mTextViewAdvViews.setText(mArray.get(position).getViews()+"");
       holder.mTextViewAdvCity.setText(mArray.get(position).getCity_Name());
       holder.mTextViewAdvTitle.setText(mArray.get(position).getTitle());
       holder.mTextViewAdvOwner.setText(mArray.get(position).getUserName());
        ArrayList<String> images = mArray.get(position).getImages();
        if (mArray.get(position).getImages().size()!=0) {
            Picasso.with(mContext).load(MainApp.ImagesUrl + images.get(0)).fit().into(holder.mImageViewImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntentAdvDetails=new Intent(mContext, AdvDetails.class);
                mIntentAdvDetails.putExtra("Title",mArray.get(position).getTitle());
                mIntentAdvDetails.putExtra("Category_id",mArray.get(position).getCategory_id());
                mIntentAdvDetails.putExtra("City_Id",mArray.get(position).getCity_Id());
                mIntentAdvDetails.putExtra("City_Name",mArray.get(position).getCity_Name());
                mIntentAdvDetails.putExtra("Description",mArray.get(position).getDescription());
                mIntentAdvDetails.putExtra("Id",mArray.get(position).getId());
                mIntentAdvDetails.putExtra("Images",mArray.get(position).getImages());
                mIntentAdvDetails.putExtra("Phone",mArray.get(position).getPhone());
                mIntentAdvDetails.putExtra("UserId",mArray.get(position).getUserId());
                mIntentAdvDetails.putExtra("UserName",mArray.get(position).getUserName());
                mIntentAdvDetails.putExtra("Views",mArray.get(position).getViews());
                mContext.startActivity(mIntentAdvDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class AdvHolder extends RecyclerView.ViewHolder{
        private final TextView mTextViewAdvTitle;
        private final TextView mTextViewAdvCity;
        private final TextView mTextViewAdvViews;
        private final TextView mTextViewAdvOwner;
        private final ImageView mImageViewImage;

        public AdvHolder(View itemView) {
            super(itemView);
            mTextViewAdvTitle= (TextView) itemView.findViewById(R.id.tvAdvTitle);
            mTextViewAdvViews= (TextView) itemView.findViewById(R.id.tvAdvViews);
            mTextViewAdvCity= (TextView) itemView.findViewById(R.id.tvAdvCity);
            mTextViewAdvOwner= (TextView) itemView.findViewById(R.id.tvAdvOwner);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivAdvImage);
        }
    }
}

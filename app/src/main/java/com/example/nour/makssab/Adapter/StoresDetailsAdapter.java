package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Activity.StoresDetails;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Model.StoresDetailsModel;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kareem on 3/27/2017.
 */

public class StoresDetailsAdapter extends RecyclerView.Adapter<StoresDetailsAdapter.StoresDetailsHolder> {
    private final ArrayList<StoresDetailsModel> mArrayList;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;


    public StoresDetailsAdapter(Context context, ArrayList<StoresDetailsModel> models) {
        mArrayList=models;
        mContext=context;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public StoresDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.stores_details, parent, false);
        StoresDetailsHolder storesDetailsHolder=new StoresDetailsHolder(view);
        return storesDetailsHolder;
    }

    @Override
    public void onBindViewHolder(StoresDetailsHolder holder, int position) {
        holder.mTextViewTitle.setText(mArrayList.get(position).getTitle());
        holder.mTextViewAdvCity.setText(mArrayList.get(position).getCity_name());
        holder.mTextViewDescription.setText(mArrayList.get(position).getDescription());
        holder.mTextViewPhone.setText(mArrayList.get(position).getPhone());
        holder.mTextViewName.setText(mArrayList.get(position).getName());
        holder.mTextViewAdvViews.setText(mArrayList.get(position).getViews());
        holder.mTextViewAdvUserName.setText(mArrayList.get(position).getUserName());
        holder.mTextViewAdvComments.setText(mArrayList.get(position).getCommentsSize());
        ArrayList<String> images = mArrayList.get(position).getImages();
        if (mArrayList.get(position).getImages().size()!=0){
            Picasso.with(mContext).load(MainApp.ImagesUrl + images.get(0)).fit().into(holder.mImageViewImage);
        }else {
            Picasso.with(mContext).load(R.drawable.no_image).fit().into(holder.mImageViewImage);
        }

    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class StoresDetailsHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewName;
        private final TextView mTextViewDescription;
        private final TextView mTextViewPhone;
        private final ImageView mImageViewImage;
        private final TextView mTextViewAdvUserName;
        private final TextView mTextViewAdvCity;
        private final TextView mTextViewTitle;
        private final TextView mTextViewAdvViews;
        private final TextView mTextViewAdvComments;
        public StoresDetailsHolder(View itemView) {
            super(itemView);
            mTextViewTitle=(TextView)itemView.findViewById(R.id.tvAdvTitle);
            mTextViewDescription=(TextView)itemView.findViewById(R.id.tvStoresDetailsDes);
            mTextViewPhone=(TextView)itemView.findViewById(R.id.tvStoresNumber);
            mTextViewName=(TextView)itemView.findViewById(R.id.tvStoresDetailsName);
            mTextViewAdvCity=(TextView)itemView.findViewById(R.id.tvAdvCity);
            mTextViewAdvViews=(TextView)itemView.findViewById(R.id.tvAdvViews);
            mTextViewAdvUserName=(TextView)itemView.findViewById(R.id.tvAdvUserName);
            mTextViewAdvComments=(TextView)itemView.findViewById(R.id.tvAdvComments);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivAdvImage);
        }
    }
}

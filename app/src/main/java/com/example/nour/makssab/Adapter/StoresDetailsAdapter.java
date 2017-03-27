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
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kareem on 3/27/2017.
 */

public class StoresDetailsAdapter extends RecyclerView.Adapter<StoresDetailsAdapter.StoresDetailsHolder> {
    private final ArrayList<StoresDetails> mArrayList;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;


    public StoresDetailsAdapter(Context context, ArrayList<StoresDetails> model) {
        mArrayList=model;
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
        holder.mTextViewAdvCity.setText(mArrayList.get(position).getAdv);

    }



    @Override
    public int getItemCount() {
        return 0;
    }


    public class StoresDetailsHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewTitle;
        private final TextView mTextViewDescription;
        private final TextView mTextViewPhone;
        private final TextView mTextViewAdvTitle;
        private final TextView mTextViewAdvCity;
        private final TextView mTextViewAdvViews;
        private final TextView mTextViewAdvOwner;
        private final ImageView mImageViewImage;
        private final TextView mTextViewAdvTime;
        private final TextView mTextViewAdvComments;
        public StoresDetailsHolder(View itemView) {
            super(itemView);
            mTextViewTitle=(TextView)itemView.findViewById(R.id.tvStoresDetails);
            mTextViewDescription=(TextView)itemView.findViewById(R.id.tv2StoresDetails);
            mTextViewPhone=(TextView)itemView.findViewById(R.id.tvStoresNumber);
            mTextViewAdvTitle=(TextView)itemView.findViewById(R.id.tvStoriesTitle);
            mTextViewAdvCity=(TextView)itemView.findViewById(R.id.tvAdvCity);
            mTextViewAdvViews=(TextView)itemView.findViewById(R.id.tvAdvViews);
            mTextViewAdvOwner=(TextView)itemView.findViewById(R.id.tvAdvOwner);
            mTextViewAdvComments=(TextView)itemView.findViewById(R.id.tvAdvComments);
            mTextViewAdvTime=(TextView)itemView.findViewById(R.id.tvAdvTime);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivAdvImage);
        }
    }
}

package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.R;
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
    public void onBindViewHolder(AdvHolder holder, int position) {
       holder.mTextViewAdvViews.setText(mArray.get(position).getViews()+"");
       holder.mTextViewAdvCity.setText(mArray.get(position).getCity_Name());
       holder.mTextViewAdvTitle.setText(mArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class AdvHolder extends RecyclerView.ViewHolder{
        private final TextView mTextViewAdvTitle;
        private final TextView mTextViewAdvCity;
        private final TextView mTextViewAdvViews;

        public AdvHolder(View itemView) {
            super(itemView);
            mTextViewAdvTitle= (TextView) itemView.findViewById(R.id.tvAdvTitle);
            mTextViewAdvViews= (TextView) itemView.findViewById(R.id.tvAdvViews);
            mTextViewAdvCity= (TextView) itemView.findViewById(R.id.tvAdvCity);
        }
    }
}

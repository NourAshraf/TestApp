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
import java.util.Calendar;

/**
 * Created by nour on 19-Jan-17.
 */

public class AdvAdapter extends RecyclerView.Adapter<AdvAdapter.AdvHolder> {

    private final ArrayList<AdvModel> mArray;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private String MyTime;

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
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int months = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minties = c.get(Calendar.MINUTE);
        int Seconds = c.get(Calendar.SECOND);
        String create_at = mArray.get(position).getCreate_at();
        String[] split = create_at.split(" ");
        String[] split1 = split[0].split("-");
        String[] split2 = split[1].split(":");
        int AdvYear= Integer.parseInt(split1[0]);
        int AdvMon= Integer.parseInt(split1[1]);
        int Advday= Integer.parseInt(split1[2]);
        int Advmin= Integer.parseInt(split2[1]);
        int Advsecond= Integer.parseInt(split2[2]);
        int Advhour= Integer.parseInt(split2[0]);
        MyTime=CalculateTime(Year,AdvYear,months,AdvMon,day,Advday,hour,Advhour,minties,Advmin,Seconds,Advsecond);
        holder.mTextViewAdvViews.setText(mArray.get(position).getViews()+"");
       holder.mTextViewAdvCity.setText(mArray.get(position).getCity_Name());
       holder.mTextViewAdvTitle.setText(mArray.get(position).getTitle());
       holder.mTextViewAdvOwner.setText(mArray.get(position).getUserName());
        holder.mTextViewImageNum.setText(mArray.get(position).getImages().size()+"");
        holder.mTextViewAdvTime.setText(MyTime);
        ArrayList<String> images = mArray.get(position).getImages();
        if (mArray.get(position).getImages().size()!=0) {
            Picasso.with(mContext).load(MainApp.ImagesUrl + images.get(0)).fit().into(holder.mImageViewImage);
        }else {
            Picasso.with(mContext).load(R.drawable.no_image).fit().into(holder.mImageViewImage);
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
                mIntentAdvDetails.putExtra("MyTime",MyTime);
                mContext.startActivity(mIntentAdvDetails);
            }
        });
    }

    private String CalculateTime(int year, int advYear, int months, int advMon, int day, int advday, int hour, int advhour, int minties, int advmin, int seconds, int advsecond) {
        if (year>advday&&year!=advYear){
            MyTime="منذ "+(year-advYear)+" سنة";
        }else if (months>advMon&&months!=advMon){
            MyTime="منذ "+(months-advMon)+"ِ شهر";
        }else if (day>advday&&day!=advday){
            MyTime="منذ "+(day-advday)+" يوم";
        }else if (hour>advhour&&hour!=advhour) {
            MyTime="منذ "+(hour-advhour)+" ساعة";
        }else if (minties>advmin&&minties!=advmin){
            MyTime="منذ "+(minties-advmin)+" دقيقة";
        }else if (seconds>advsecond&&seconds!=advsecond){
            MyTime="منذ "+(seconds-advsecond)+" ثانية";
        }

        return MyTime;
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
        private final TextView mTextViewImageNum;
        private final TextView mTextViewAdvTime;

        public AdvHolder(View itemView) {
            super(itemView);
            mTextViewAdvTitle= (TextView) itemView.findViewById(R.id.tvAdvTitle);
            mTextViewAdvViews= (TextView) itemView.findViewById(R.id.tvAdvViews);
            mTextViewAdvCity= (TextView) itemView.findViewById(R.id.tvAdvCity);
            mTextViewAdvOwner= (TextView) itemView.findViewById(R.id.tvAdvOwner);
            mTextViewImageNum= (TextView) itemView.findViewById(R.id.tvImageNum);
            mTextViewAdvTime= (TextView) itemView.findViewById(R.id.tvAdvTime);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivAdvImage);
        }
    }
}

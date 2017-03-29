package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.content.Intent;
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
    private String MyTime;


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
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int months = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minties = c.get(Calendar.MINUTE);
        int Seconds = c.get(Calendar.SECOND);
        String create_at = mArrayList.get(position).getCreated_at();
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
        Picasso.with(mContext).load(MainApp.ImagesUrl + mArrayList.get(position).getPhoto()).fit().into(holder.mImageViewImage);
        holder.mTextViewName.setText(mArrayList.get(position).getName());
        holder.mTextViewDescription.setText(mArrayList.get(position).getDescription());
        holder.mTextViewPhone.setText(mArrayList.get(position).getPhone());
        holder.mTextViewTitle.setText(mArrayList.get(position).getTitle());
        holder.mTextViewAdvCity.setText(mArrayList.get(position).getCity_name());
        holder.mTextViewAdvUserName.setText(mArrayList.get(position).getUserName());
        holder.mTextViewAdvViews.setText(mArrayList.get(position).getViews());
        holder.mTextViewTime.setText(MyTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,StoresDetails.class);
                mContext.startActivity(intent);
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
        return mArrayList.size();
    }


    public class StoresDetailsHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageViewImage;
        private final TextView mTextViewName;
        private final TextView mTextViewDescription;
        private final TextView mTextViewPhone;
        private final TextView mTextViewTitle;
        private final TextView mTextViewAdvCity;
        private final TextView mTextViewAdvUserName;
        private final TextView mTextViewAdvViews;
        private final TextView mTextViewTime;


        public StoresDetailsHolder(View itemView) {
            super(itemView);
            mImageViewImage= (ImageView) itemView.findViewById(R.id.ivStoriesDetails);
            mTextViewName=(TextView)itemView.findViewById(R.id.tvStoresDetailsName);
            mTextViewDescription=(TextView)itemView.findViewById(R.id.tvStoresDetailsDes);
            mTextViewPhone=(TextView)itemView.findViewById(R.id.tvStoresNumber);
            mTextViewTitle=(TextView)itemView.findViewById(R.id.tvStoriesTitle);
            mTextViewAdvCity=(TextView)itemView.findViewById(R.id.tvAdvCity);
            mTextViewAdvUserName=(TextView)itemView.findViewById(R.id.tvAdvUserName);
            mTextViewAdvViews=(TextView)itemView.findViewById(R.id.tvAdvViews);
            mTextViewTime=(TextView)itemView.findViewById(R.id.tvAdvTime);







        }
    }
}

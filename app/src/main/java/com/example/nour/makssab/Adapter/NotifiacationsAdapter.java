package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nour.makssab.Model.NotificationsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/7/2017.
 */

public class NotifiacationsAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotificationsModel> notifications=new ArrayList<NotificationsModel>();

    public NotifiacationsAdapter(Context context, ArrayList<NotificationsModel> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.notifications,parent,false);
        TextView NotificationsName=(TextView)view.findViewById(R.id.tvNotificationsName);
        NotificationsModel notificationsModel=notifications.get(position);
        if (notificationsModel.getType().equals("ads")) {
            NotificationsName.setText("اضاف العضو "+notificationsModel.getUserName()+" اعلان "+notificationsModel.getTitle());
        }else if (notificationsModel.getType().equals("follow")){
            NotificationsName.setText("قام العضو "+notificationsModel.getUserName()+" بمتابعتك");
        }else if (notificationsModel.getType().equals("comment")){
            NotificationsName.setText("اضاف العضو "+notificationsModel.getUserName()+" تعليق رد علي الاعلان "+notificationsModel.getTitle());
        }

        return view;
    }
}

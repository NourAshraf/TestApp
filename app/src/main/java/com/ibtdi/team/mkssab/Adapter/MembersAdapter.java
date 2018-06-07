//<<<<<<< HEAD
//package com.ibtdi.team.mkssab.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.ibtdi.team.mkssab.Model.MembersModel;
//import com.ibtdi.team.mkssab.R;
//
//import java.util.ArrayList;
//
///**
// * Created by Kareem on 3/6/2017.
// */
//
//public class MembersAdapter extends BaseAdapter {
//    Context context;
//    ArrayList<MembersModel> members=new ArrayList<MembersModel>();
//
//    public MembersAdapter(Context context, ArrayList<MembersModel> members) {
//        this.context = context;
//        this.members = members;
//    }
//
//    @Override
//    public int getCount() {
//        return members.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view= layoutInflater.inflate(R.layout.members,parent,false);
//        TextView Name=(TextView)view.findViewById(R.id.tvMembersName);
//        TextView Follow=(TextView)view.findViewById(R.id.tvMembersFollow);
//        TextView TextProfile=(TextView) view.findViewById(R.id.bMembersProfile);
//
//        MembersModel membersModel=members.get(position);
//
//        Name.setText(membersModel.getName());
//        Follow.setText(membersModel.getFollow());
//        TextProfile.setText(membersModel.getImageProfile());
//
//        return view;
//    }
//}
//=======
package com.ibtdi.team.mkssab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibtdi.team.mkssab.Activity.MembersDetails;
import com.ibtdi.team.mkssab.Model.MembersModel;
import com.ibtdi.team.mkssab.R;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/6/2017.
 */

public class MembersAdapter extends BaseAdapter {
    Context context;
    ArrayList<MembersModel> members=new ArrayList<MembersModel>();

    public MembersAdapter(Context context, ArrayList<MembersModel> members) {
        this.context = context;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.members,parent,false);
        TextView Name=(TextView)view.findViewById(R.id.tvMembersName);
        TextView Follow=(TextView)view.findViewById(R.id.tvMembersFollow);
        TextView TextProfile=(TextView) view.findViewById(R.id.bMembersProfile);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(context,MembersDetails.class);
                mIntent.putExtra("UserId",members.get(position).getFollow());
                context.startActivity(mIntent);
            }
        });
        MembersModel membersModel=members.get(position);
        Name.setText(membersModel.getName());
        return view;
    }
}

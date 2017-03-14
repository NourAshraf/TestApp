package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/14/2017.
 */

public class BuildingsAdapter extends BaseAdapter {
    Context context;
    ArrayList<BuildingsModel> buildingsModels=new ArrayList<BuildingsModel>();
    public BuildingsAdapter(Context context, ArrayList<BuildingsModel> buildingsModels) {
        this.context = context;
        this.buildingsModels = buildingsModels;
    }
    @Override
    public int getCount() {
        return buildingsModels.size();
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
        View view= layoutInflater.inflate(R.layout.buildings,parent,false);
        TextView Number= (TextView) view.findViewById(R.id.bBuildingsNumber);
        TextView Name= (TextView) view.findViewById(R.id.tvBuildingsName);
        TextView City= (TextView) view.findViewById(R.id.tvBuildingsCity);
        ImageView Image=(ImageView)view.findViewById(R.id.ivBuildingsImage);

        BuildingsModel buildingsModel=buildingsModels.get(position);

        Number.setText(buildingsModel.getNumber());
        Name.setText(buildingsModel.getName());
        City.setText(buildingsModel.getCity());
        Image.setImageResource(buildingsModel.getImage());


        //na2es el picasso

        return view;
    }
}

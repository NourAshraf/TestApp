package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Model.BuildingsModel;
import com.example.nour.makssab.Model.StoresModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/14/2017.
 */

public class StoresAdapter extends BaseAdapter {
    Context context;
    ArrayList<StoresModel> storesModels=new ArrayList<StoresModel>();
    public StoresAdapter(Context context, ArrayList<StoresModel> storesModels) {
        this.context = context;
        this.storesModels = storesModels;
    }
    @Override
    public int getCount() {
        return storesModels.size();
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
        View view= layoutInflater.inflate(R.layout.stores,parent,false);
        TextView Number= (TextView) view.findViewById(R.id.bStoresNumber);
        TextView Name= (TextView) view.findViewById(R.id.tvStoresName);
        TextView City= (TextView) view.findViewById(R.id.tvStoresCity);
        ImageView Image=(ImageView)view.findViewById(R.id.ivStoresImage);

        StoresModel storesModel=storesModels.get(position);

        Number.setText(storesModel.getNumber());
        Name.setText(storesModel.getName());
        City.setText(storesModel.getCity());
        Image.setImageResource(storesModel.getImage());


        //na2es el picasso

        return view;
    }
}

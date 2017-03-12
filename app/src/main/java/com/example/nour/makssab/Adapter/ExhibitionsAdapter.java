package com.example.nour.makssab.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nour.makssab.Model.ExhibitionsModel;
import com.example.nour.makssab.R;
import java.util.ArrayList;

/**
 * Created by Kareem on 3/12/2017.
 */

public class ExhibitionsAdapter extends BaseAdapter {
    Context context;
    ArrayList<ExhibitionsModel> exhibitionsModels=new ArrayList<ExhibitionsModel>();

    public ExhibitionsAdapter(Context context, ArrayList<ExhibitionsModel> exhibitionsModels) {
        this.context = context;
        this.exhibitionsModels = exhibitionsModels;
    }

    @Override
    public int getCount() {
        return exhibitionsModels.size();
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
        View view= layoutInflater.inflate(R.layout.exhibitions,parent,false);
        TextView Number= (TextView) view.findViewById(R.id.bExhibitionsNumber);
        TextView Name= (TextView) view.findViewById(R.id.tvExhibitionsName);
        TextView City= (TextView) view.findViewById(R.id.tvExhibitionsCity);
        ImageView Image=(ImageView)view.findViewById(R.id.ivExhibitionsImage);

        ExhibitionsModel exhibitionsModel=exhibitionsModels.get(position);

        Number.setText(exhibitionsModel.getNumber());
        Name.setText(exhibitionsModel.getName());
        City.setText(exhibitionsModel.getCity());
        Image.setImageResource(exhibitionsModel.getImage());


        //na2es el picasso

        return view;
    }
}

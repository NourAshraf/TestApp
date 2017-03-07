package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.makssab.Model.MembersFavoritesModel;
import com.example.nour.makssab.Model.MembersModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/6/2017.
 */

public class MemberFavoritesAdapter extends BaseAdapter {
    Context context;
    ArrayList<MembersFavoritesModel> membersfavorites=new ArrayList<MembersFavoritesModel>();

    public MemberFavoritesAdapter(Context context, ArrayList<MembersFavoritesModel> membersfavorites) {
        this.context = context;
        this.membersfavorites = membersfavorites;
    }

    @Override
    public int getCount() {
        return membersfavorites.size();
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
        View view= layoutInflater.inflate(R.layout.members_favorites,parent,false);
        TextView Name=(TextView)view.findViewById(R.id.tvMembersFavoritesName);
        TextView Place=(TextView)view.findViewById(R.id.tvMembersFavoritesPlace);
        TextView CommentNumber=(TextView)view.findViewById(R.id.tvMembersFavoritesCommentNumber);
        TextView SeenNumber=(TextView)view.findViewById(R.id.tvMembersFavoritesSeenNumber);
        TextView Dakeka=(TextView)view.findViewById(R.id.tvMembersFavoritesDakeka);
        TextView Monzoo=(TextView)view.findViewById(R.id.tvMembersFavoritesMonzoo);
        TextView TimeNumber=(TextView)view.findViewById(R.id.tvMembersFavoritesTimeNumber);
        TextView Title=(TextView)view.findViewById(R.id.tvMembersFavoritesTitle);
        ImageView Adv=(ImageView)view.findViewById(R.id.ivMembersFavoritesImage);
        TextView Person=(TextView) view.findViewById(R.id.tvMembersFavoritesNameImage);
        TextView Flag=(TextView) view.findViewById(R.id.tvMembersFavoritesPlaceImage);
        TextView Time=(TextView) view.findViewById(R.id.tvMembersFavoritesTimeImage);
        TextView Comment=(TextView) view.findViewById(R.id.tvMembersFavoritesCommentImage);
        TextView Eye=(TextView) view.findViewById(R.id.tvMembersFavoritesSeenImage);


        MembersFavoritesModel membersfavoritesModel=membersfavorites.get(position);

        Name.setText(membersfavoritesModel.getName());
        Place.setText(membersfavoritesModel.getPlace());
        CommentNumber.setText(membersfavoritesModel.getCommentNumber());
        SeenNumber.setText(membersfavoritesModel.getSeenNumber());
        Dakeka.setText(membersfavoritesModel.getDakeka());
        Monzoo.setText(membersfavoritesModel.getMonzoo());
        TimeNumber.setText(membersfavoritesModel.getTimeNumber());
        Title.setText(membersfavoritesModel.getTitle());
        //Adv.setImageResource(Integer.parseInt(membersfavoritesModel.getAdv()));
        Person.setText(membersfavoritesModel.getPerson());
        Flag.setText(membersfavoritesModel.getFlag());
        Time.setText(membersfavoritesModel.getTime());
        Comment.setText(membersfavoritesModel.getComment());
        Eye.setText(membersfavoritesModel.getEye());
        Adv.setImageResource(membersfavoritesModel.getAdv());


        return view;
    }
}

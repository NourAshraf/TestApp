package com.example.nour.makssab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nour.makssab.Activity.ConversationMessage;
import com.example.nour.makssab.Model.MessageModel;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by nour on 19-Jan-17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder> {

    private final ArrayList<MessageModel> mArray;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public MessagesAdapter(Context context, ArrayList<MessageModel> models) {
        mArray=models;
        mContext=context;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_messages, parent, false);
        MessageHolder advHolder = new MessageHolder(view);
        return advHolder;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, final int position) {
       holder.mTextViewMessage.setText(mArray.get(position).getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(mContext, ConversationMessage.class);
                mIntent.putExtra("Id",mArray.get(position).getId());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        private final TextView mTextViewMessage;

        public MessageHolder(View itemView) {
            super(itemView);
            mTextViewMessage= (TextView) itemView.findViewById(R.id.tvLastMessage);
        }
    }
}

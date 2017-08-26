package com.example.nour.makssab.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nour.makssab.Activity.Add_Advertisement;
import com.example.nour.makssab.R;

import java.util.ArrayList;

/**
 * Created by nour on 02-Nov-16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesHolder>{
    private final int mSize;
    private final ArrayList<Bitmap> mArray;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int PICK_IMAGE_CAMERA=4;
    private int PICK_IMAGE=3;
    private Bitmap mBitmapProfileImage;

    public ImageAdapter(Context context,int Size,ArrayList<Bitmap> Images) {
          mContext=context;
          mArray=Images;
          mSize=Size;
        mLayoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ImagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_sub_images, parent, false);
        ImagesHolder mCastHolder=new ImagesHolder(view);
        return mCastHolder;
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ImagesHolder holder, final int position) {
        if (mArray.size()>0&&position!=mSize-1){
            holder.mImageViewMyImage.setImageBitmap(mArray.get(position));
        }
        holder.mImageViewMyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mAlertDialog=new AlertDialog.Builder(mContext);
                mAlertDialog.setMessage("اختار صوره عن طريق");
                mAlertDialog.setTitle("اضافة صوره");
                mAlertDialog.setNeutralButton("الكاميرا",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        ((Add_Advertisement)mContext).startActivityForResult(mIntent,PICK_IMAGE_CAMERA);
                        dialog.dismiss();
                    }
                });
                mAlertDialog.setPositiveButton("الاستديو",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");
                        Intent chooserIntent = Intent.createChooser(getIntent, "Select Profile Picture");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                        ((Add_Advertisement)mContext).startActivityForResult(chooserIntent, PICK_IMAGE);
                        dialog.dismiss();
                    }
                });
                mAlertDialog.create().show();
            }
        });
    }

   public class ImagesHolder extends RecyclerView.ViewHolder{

       private final ImageView mImageViewMyImage;

       public ImagesHolder(View itemView) {
            super(itemView);
           mImageViewMyImage= (ImageView) itemView.findViewById(R.id.ivMyImage);
        }
    }
}

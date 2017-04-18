package com.example.nour.makssab.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

public class Share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       String message=("hhh");
        Intent share = new Intent(Intent.ACTION_PICK_ACTIVITY);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER, message);
        startActivity(Intent.createChooser(share,""));

    }



}

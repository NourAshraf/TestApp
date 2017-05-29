package com.example.nour.makssab.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.ImageAdapter;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.CategoryModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Advertisement extends AppCompatActivity implements View.OnClickListener {

    private  ArrayList<String>ID;
    private Add_Advertisement mContext;
    private ImageView mImageViewMainImage;
    private int PICK_IMAGE_CAMERA=1;
    private int PICK_IMAGE=0;
    private Bitmap mBitmapProfileImage;
    private RecyclerView mRecyclerViewSubImages;
    private ArrayList<Bitmap> mBitmaps;
    private int mSize;
    private ImageAdapter mImageAdapter;
    private String mStateId;
    private String mCityId;
    private String mID;
    private Spinner mSpinnerNewAccountArea;
    private Spinner mSpinnerNewAccountCity;
    private ArrayList<String> State_Name;
    private ArrayList<String> City_Id;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String> State_Id;
    private ArrayList<String> City_Name;
    private Spinner mSpinnerMain;
    private Spinner mSpinnerSub;
    private String[] arraySpinner;
    private ArrayList<CategoryModel> models;
    private ArrayList<String> Names;
    private int idSelected;
    private int[] CategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__advertisement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onVariables();
    }


    private void onVariables() {
        mContext=Add_Advertisement.this;
        mSize=1;
        mStateId = "";
        mCityId = "";
        mSpinnerMain= (Spinner) findViewById(R.id.spMainSection);
        mSpinnerSub= (Spinner) findViewById(R.id.spSubSection);
        arraySpinner = new String[] {
                "السيارات", "متفرقات", "المنزل", "الموضه", "اجهزه" ,"حيوانات","عقارات","خدمات"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.item_spinner2,R.id.tvItem ,arraySpinner);
        mSpinnerMain.setAdapter(adapter);
        mSpinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 idSelected=CategoryID[position];
                onGetCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mImageViewMainImage= (ImageView) findViewById(R.id.ivMainImage);
        mImageViewMainImage.setOnClickListener(this);
        mSpinnerNewAccountArea = (Spinner) findViewById(R.id.NewAccountSpinnerArea);
        mSpinnerNewAccountArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mStateId = "";
                    mSpinnerNewAccountCity.setVisibility(View.GONE);
                } else {
                    mStateId = State_Id.get(position);
                    onCity(State_Id.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerNewAccountCity = (Spinner) findViewById(R.id.NewAccountSpinnerCity);
        mSpinnerNewAccountCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1) {
                    mCityId = "";
                } else {
                    mCityId = City_Id.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Names = new ArrayList<String>();
        CategoryID= new int[]{1, 6, 4, 5, 3, 8, 2, 7};
        models = new ArrayList<CategoryModel>();
        ID = new ArrayList<String>();
        State_Id = new ArrayList<String>();
        State_Name = new ArrayList<String>();
        City_Id = new ArrayList<String>();
        City_Name = new ArrayList<String>();
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewSubImages= (RecyclerView)findViewById(R.id.rvSubImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
        mRecyclerViewSubImages.setLayoutManager(layoutManager);
        mBitmaps=new ArrayList<Bitmap>();
        mImageAdapter=new ImageAdapter(mContext,mSize,mBitmaps);
        mRecyclerViewSubImages.setAdapter(mImageAdapter);
        onStates();
    }
    private void onGetCategory() {
        if (Names!=null){
            Names.clear();

        }
        String Url=MainApp.CategoryUrl+idSelected;
        StringRequest mStringRequestGetCategory=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray=new JSONArray(response);
                    if (mJsonArray.length()==1){
                        JSONObject mJsonObject=mJsonArray.getJSONObject(0);
                        String error = mJsonObject.getString("error");
                        Toast.makeText(mContext,error,Toast.LENGTH_SHORT).show();
                    }else {
                        for (int i=0;i<mJsonArray.length();i++){
                            JSONObject mJsonObject=mJsonArray.getJSONObject(i);
                            String name = mJsonObject.getString("name");
                            String id2 = mJsonObject.getString("id");
                            String category_id = mJsonObject.getString("category_id");
                            CategoryModel categoryModel=new CategoryModel(id2,name,category_id);
                            models.add(categoryModel);
                            Names.add(name);
                            ID.add(id2);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.item_spinner2,R.id.tvItem,Names);
                            mSpinnerSub.setAdapter(dataAdapter);

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainApp.Tag,"Worked Error");
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestGetCategory);
    }

    public void onStates(){
        String Url= MainApp.StatesUrl;
        StringRequest mStringRequestonStates=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray = new JSONArray(response);
                    if (mJsonArray.length() == 1) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        String error = mJsonObject.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        State_Id.add(id);
                        State_Name.add(name);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner2,R.id.tvItem,State_Name);
                        mSpinnerNewAccountArea.setAdapter(dataAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onStates();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonStates);
    }



    public void onCity(final String position){
        if (City_Name!=null){
            City_Name.clear();
            City_Id.clear();
        }
        String Url=MainApp.CitiesUrl+position;
        StringRequest mStringRequestonCity=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mJsonArray = new JSONArray(response);
                    if (mJsonArray.length() == 1) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        String error = mJsonObject.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        City_Id.add(id);
                        City_Name.add(name);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner2,R.id.tvItem,City_Name);
                        mSpinnerNewAccountCity.setAdapter(dataAdapter);
                        mSpinnerNewAccountCity.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCity(position);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonCity);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMainImage:
                final AlertDialog.Builder mAlertDialog=new AlertDialog.Builder(mContext);
                mAlertDialog.setMessage("Select Profile Picture From");
                mAlertDialog.setTitle("Select Image");
                mAlertDialog.setNeutralButton("Camera",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(mIntent,PICK_IMAGE_CAMERA);
                        dialog.dismiss();
                    }
                });
                mAlertDialog.setPositiveButton("Exists Image",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        getIntent.setType("image/*");
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");
                        Intent chooserIntent = Intent.createChooser(getIntent, "Select Profile Picture");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                        startActivityForResult(chooserIntent, PICK_IMAGE);
                        dialog.dismiss();
                    }
                });
                mAlertDialog.create().show();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==0){
                Uri filePath = data.getData();
                try {
                    //Getting the Bitmap from Gallery

                    mBitmapProfileImage = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    mBitmapProfileImage= mBitmapProfileImage.createScaledBitmap(mBitmapProfileImage, 300, 300, false);
                    mImageViewMainImage.setImageBitmap(mBitmapProfileImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if (requestCode==1){
                mBitmapProfileImage= (Bitmap) data.getExtras().get("data");
                mBitmapProfileImage= mBitmapProfileImage.createScaledBitmap(mBitmapProfileImage, 300, 300, false);
                mImageViewMainImage.setImageBitmap(mBitmapProfileImage);
            }else if (requestCode==3){
                Uri filePath = data.getData();
                try {
                    //Getting the Bitmap from Gallery

                    mBitmapProfileImage = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    mBitmapProfileImage= mBitmapProfileImage.createScaledBitmap(mBitmapProfileImage, 300, 300, false);
                    mBitmaps.add(mBitmapProfileImage);
                    mSize=mSize+1;
                    mImageAdapter=new ImageAdapter(mContext,mSize,mBitmaps);
                    mRecyclerViewSubImages.setAdapter(mImageAdapter);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if (requestCode==4){
                mBitmapProfileImage= (Bitmap) data.getExtras().get("data");
                mBitmapProfileImage= mBitmapProfileImage.createScaledBitmap(mBitmapProfileImage, 300, 300, false);
                mBitmaps.add(mBitmapProfileImage);
                mSize=mSize+1;
                mImageAdapter=new ImageAdapter(mContext,mSize,mBitmaps);
                mRecyclerViewSubImages.setAdapter(mImageAdapter);

            }
        }
    }
}

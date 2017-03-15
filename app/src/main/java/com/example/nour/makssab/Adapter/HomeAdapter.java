package com.example.nour.makssab.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.CategoryModel;
import com.example.nour.makssab.Model.HomeModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by nour on 18-Jan-17.
 */

public class HomeAdapter extends ArrayAdapter{
    private final int mLayout;
    private final ArrayList<HomeModel> mArray;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final RequestQueue mVolleySingletonRequestQueue;
    private View view;
    private ArrayList<CategoryModel> models;
    private ArrayList<String> Names;

    public HomeAdapter(Context context, int resource, ArrayList<HomeModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mLayout=resource;
        mArray=objects;
        mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView==null) {
            view = mInflater.inflate(mLayout, parent, false);
            mHolder=new ViewHolder();
            mHolder.mImageViewHomeImage= (ImageView) view.findViewById(R.id.ivHomeImage);
            mHolder.mtTextViewHomeTitle= (TextView) view.findViewById(R.id.tvHomeTitle);
            view.setTag(mHolder);
        }else {
            view =convertView;
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mtTextViewHomeTitle.setText(mArray.get(position).getTitle());
        Picasso.with(mContext).load(mArray.get(position).getImage()).fit().into(mHolder.mImageViewHomeImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models = new ArrayList<CategoryModel>();
                Names = new ArrayList<String>();
                onGetCategory(mArray.get(position).getId(),mArray.get(position).getTitle());

            }
        });
        return view;
    }

    private void onGetCategory(int id, final String title) {
        String Url=MainApp.CategoryUrl+id;
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
                            String id = mJsonObject.getString("id");
                            String category_id = mJsonObject.getString("category_id");
                            CategoryModel categoryModel=new CategoryModel(id,name,category_id);
                            models.add(categoryModel);
                            Names.add(name);
                        }
                        final Dialog mDialog=new Dialog(mContext);
                        mDialog.setCancelable(true);
                        mDialog.setContentView(R.layout.dialog_category);
                        TextView mTextViewDialog= (TextView) mDialog.findViewById(R.id.tvCategoryName);
                        mTextViewDialog.setText(title);
                        MaterialSpinner materialSpinner= (MaterialSpinner) mDialog.findViewById(R.id.spSelectCategory);
                        ArrayAdapter mArrayAdapter=new ArrayAdapter(mContext,android.R.layout.simple_spinner_dropdown_item,Names);
                        materialSpinner.setAdapter(mArrayAdapter);
                        materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        Button mButtonCancel= (Button) mDialog.findViewById(R.id.bCancel);
                        mButtonCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        });
                        Button mButtonOk= (Button) mDialog.findViewById(R.id.bOk);
                        mButtonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        mDialog.show();
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

    class ViewHolder {
     ImageView mImageViewHomeImage;
     TextView mtTextViewHomeTitle;
    }
}

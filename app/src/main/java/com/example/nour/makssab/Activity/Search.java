package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.AdvAdapter;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.AdvModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String> carBrands_Id;
    private ArrayList<String> carBrands_Name;
    private ArrayList<AdvModel> models;
    private String mCarBrandsId;
    private ArrayList<String> carModels_Id;
    private String mCarModelsId;
    private Spinner mSpinnerSearchMarka;
    private Spinner mSpinnerSearchModel;
    private Spinner mSpinnerSearchKind;
    private ArrayList<String> carModels_Name;
    private Button mButtonSearch;
    private ArrayList<String>ModelSearch;
    private String next_page_url;
    private ArrayList<String> ImagesModels;
    private boolean mDelete;
    private String mId;
    private String mCity;
    private String mUserName;
    private String mUserId;
    private AdvAdapter mAdvAdapter;
    private Button SearchAdv;
    private RecyclerView mRecyclerViewAdv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Allvariables();

    }

    private void Allvariables() {
        mContext = Search.this;
        mCarBrandsId = "";
        mCarModelsId = "";
        carBrands_Id = new ArrayList<String>();
        carBrands_Name = new ArrayList<String>();
        carModels_Id = new ArrayList<String>();
        carModels_Name = new ArrayList<String>();
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvAdv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewAdv.setLayoutManager(layoutManager);
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(100));
        models=new ArrayList<AdvModel>();
        ImagesModels=new ArrayList<String>();
        mAdvAdapter=new AdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        mButtonSearch= (Button) findViewById(R.id.bSearchSearch);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j=0;j<carModels_Id.size();j++){
                    if (mCarModelsId.equals(carModels_Id.get(j)));
                    ModelSearch.add(carModels_Id.get(j));

                }

            }
        });
        mSpinnerSearchMarka = (Spinner) findViewById(R.id.SearchSpinnerMarka);
        mSpinnerSearchKind = (Spinner) findViewById(R.id.SearchSpinnerKind);

        mSpinnerSearchKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mCarModelsId = "";
                } else {
                    mCarModelsId = carModels_Id.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerSearchMarka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mCarBrandsId = "";

                } else {

                    mCarBrandsId = carBrands_Id.get(position);
                    onCarModels(carBrands_Id.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        onCarBrands();
        SearchAdv= (Button) findViewById(R.id.bSearchAdv);
        SearchAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewAdv.setVisibility(View.VISIBLE);
                onLoadAdv();
            }
        });



    }
    public void onCarModels(final String position){
        if (carModels_Name!=null){
            carModels_Name.clear();
            carModels_Id.clear();
        }
        String Url= MainApp.CarModelsUrl+position;
        StringRequest mStringRequestonCarModels=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
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
                        carModels_Id.add(id);
                        carModels_Name.add(name);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner_test,R.id.tvItemSpinner,carModels_Name);
                        mSpinnerSearchKind.setAdapter(dataAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCarModels(position);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonCarModels);
    }
    public void onCarBrands(){
        String Url=MainApp.CarBrandsUrl;
        StringRequest mStringRequestonCarBrands=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
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
                        carBrands_Id.add(id);
                        carBrands_Name.add(name);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.item_spinner_test,R.id.tvItemSpinner,carBrands_Name);
                        mSpinnerSearchMarka.setAdapter(dataAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCarBrands();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                return params;
            }
        };
        mVolleySingletonRequestQueue.add(mStringRequestonCarBrands);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void onLoadAdv() {
        final String Url=MainApp.SearchNumberUrl;
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray mJsonArray = new JSONArray(response);
                    for (int i = 0; i < mJsonArray.length(); i++){
                        if (mDelete) {
                            mDelete = false;
                            ImagesModels = new ArrayList<String>();
                        }
                        JSONObject jsonObject = mJsonArray.getJSONObject(i);
                        JSONArray comments_count = jsonObject.getJSONArray("comments");
                        int CommentCount=comments_count.length();
                        String id=jsonObject.getString("id");
                        String created_at = jsonObject.getString("created_at");
                        jsonObject.getString("id");
                        String category_id=jsonObject.getString("category_id");
                        String city_id = jsonObject.getString("city_id");
                        String title=jsonObject.getString("title");
                        String description=jsonObject.getString("description");
                        String phone=jsonObject.getString("phone");
                        String views=jsonObject.getString("views");
                        JSONObject city = jsonObject.getJSONObject("city");
                        String City_Name = city.getString("name");

                        JSONArray images = jsonObject.getJSONArray("images");
                        for (int j=0;j<images.length();j++) {
                            JSONObject jsonObject2 = images.getJSONObject(j);
                            String photo = jsonObject2.getString("photo");
                            ImagesModels.add(photo);
                        }

                        JSONObject user = jsonObject.getJSONObject("user");
                        String UserId = user.getString("id");
                        String username = user.getString("username");


                        AdvModel advModel=new AdvModel(id,city_id,views,category_id,title,description,phone,mCity,mUserId,mUserName,ImagesModels,created_at,CommentCount);
                        models.add(advModel);
                        if (true){
                            mDelete=true;
                        }
                    }
                    mAdvAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoadAdv();
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestAdv);
    }






}

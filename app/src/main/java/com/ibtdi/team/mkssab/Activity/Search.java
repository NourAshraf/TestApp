package com.ibtdi.team.mkssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ibtdi.team.mkssab.Adapter.AdvAdapter;
import com.ibtdi.team.mkssab.Adapter.StoresAdapter;
import com.ibtdi.team.mkssab.Decoration.VerticalSpaceItemDecoration;
import com.ibtdi.team.mkssab.MainApp.MainApp;
import com.ibtdi.team.mkssab.Model.AdvModel;
import com.ibtdi.team.mkssab.Model.StoresModel;
import com.ibtdi.team.mkssab.Network.VolleySingleton;
import com.ibtdi.team.mkssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<AdvModel> models;
    private ArrayList<String> carBrands_Id;
    private ArrayList<String> carBrands_Name;
    private String mCarBrandsId;
    private ArrayList<String> carModels_Id;
    private String mCarModelsId;
    private Spinner mSpinnerSearchMarka;
    private Spinner mSpinnerSearchKind;
    private Spinner mSpinnerSearchModel;
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
    private LinearLayout mLinearLayoutSearch;
    private EditText mEditTextIdSearch;
    private ArrayList<StoresModel> modelsSearch;
    private StoresAdapter mStoresAdapter;
    private ImageView mImageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        AllVariables();

    }

    private void AllVariables() {
        mContext = Search.this;
        mCarBrandsId = "";
        mDelete=true;
        mCarModelsId = "";
        mEditTextIdSearch= (EditText) findViewById(R.id.etSearchAdv);
        carBrands_Id = new ArrayList<String>();
        carBrands_Name = new ArrayList<String>();
        modelsSearch = new ArrayList<StoresModel>();
        carModels_Id = new ArrayList<String>();
        carModels_Name = new ArrayList<String>();
        mImageViewBack = (ImageView) findViewById(R.id.ivBack);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentHome=new Intent(mContext,Home.class);
                startActivity(mIntentHome);
                finish();
            }
        });
        mLinearLayoutSearch= (LinearLayout) findViewById(R.id.llSearch);
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewAdv= (RecyclerView) findViewById(R.id.rvAdv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewAdv.setLayoutManager(layoutManager);
        mRecyclerViewAdv.addItemDecoration(new VerticalSpaceItemDecoration(30));
        models=new ArrayList<AdvModel>();
        ImagesModels=new ArrayList<String>();
        mAdvAdapter=new AdvAdapter(mContext,models);
        mRecyclerViewAdv.setAdapter(mAdvAdapter);
        mButtonSearch= (Button) findViewById(R.id.bSearchSearch);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
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


    public void onSearch(){
        final String Url=MainApp.SearchUrl2+mCarBrandsId+"&model_id="+mCarModelsId;
        StringRequest mStringRequestonSearch=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mJsonObject=new JSONObject(response);
                    Log.i(MainApp.Tag,response);
                    if (modelsSearch!=null){
                        modelsSearch.clear();
                    }
                    next_page_url = mJsonObject.getString("next_page_url");
                    JSONArray data = mJsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        JSONObject jsonObject=data.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String photo = jsonObject.getString("photo");
                        String description = jsonObject.getString("description");
                        String phone = jsonObject.getString("phone");
                        String longitude = jsonObject.getString("longitude");
                        String latitude = jsonObject.getString("latitude");
                        String ads_count = jsonObject.getString("ads_count");
                        JSONObject user = jsonObject.getJSONObject("user");
                        JSONObject city = user.getJSONObject("city");
                        String name1 = city.getString("name");
                        String id1 = city.getString("id");
                        String username = user.getString("username");
                        String UserId = user.getString("id");
                        StoresModel storesModel = new StoresModel(id, name, photo, description, phone, longitude, latitude, ads_count, name1, id1,UserId,username);
                        modelsSearch.add(storesModel);
                    }
                    mLinearLayoutSearch.setVisibility(View.GONE);
                    mStoresAdapter=new StoresAdapter(mContext,modelsSearch, "3");
                    mRecyclerViewAdv.setAdapter(mStoresAdapter);
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSearch();
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestonSearch);
    }



    private void onLoadAdv() {
        String MyId = mEditTextIdSearch.getText().toString();
        final String Url=MainApp.SearchNumberUrl+MyId;
        StringRequest mStringRequestAdv=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray comments_count = jsonObject.getJSONArray("comments");
                        int CommentCount=comments_count.length();
                        String id=jsonObject.getString("id");
                        String created_at = jsonObject.getString("created_at");
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
                        AdvModel advModel=new AdvModel(id,city_id,views,category_id,title,description,phone,City_Name,UserId,username,ImagesModels,created_at,CommentCount);
                        models.add(advModel);

                    Log.i(MainApp.Tag,models.size()+"");
                    mRecyclerViewAdv.setVisibility(View.VISIBLE);
                    mLinearLayoutSearch.setVisibility(View.GONE);
                    mAdvAdapter=new AdvAdapter(mContext,models);
                    mRecyclerViewAdv.setAdapter(mAdvAdapter);
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

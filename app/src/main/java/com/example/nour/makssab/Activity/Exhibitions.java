package com.example.nour.makssab.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.StoresAdapter;
import com.example.nour.makssab.Decoration.VerticalSpaceItemDecoration;
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Model.StoresModel;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Exhibitions extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private Context mContext;
    private RecyclerView mRecyclerViewStories;
    private ArrayList<StoresModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String>State_Id;
    private ArrayList<String>carBrands_Id;
    private ArrayList<String>State_Name;
    private ArrayList<String>carBrands_Name;
    private ArrayList<String>City_Id;
    private ArrayList<String>carModels_Id;
    private ArrayList<String>City_Name;
    private ArrayList<String>carModels_Name;
    private MaterialSpinner mSpinnerNewAccountMarka;
    private MaterialSpinner mSpinnerNewAccountKind;
    private MaterialSpinner mSpinnerNewAccountArea;
    private MaterialSpinner mSpinnerNewAccountCity;
    private String mStateId;
    private String mCarBrandsId;
    private String mCarModelsId;
    private String mCityId;
    private ImageView mImageViewSearch;
    private LinearLayout mLinearLayoutSearchStories;
    private Button mButtonAllAreas;
    private Button mButtonSearchStories;
    private ArrayList<StoresModel> modelsSearch;
    private SwipeRefreshLayout mSwipeRefreshLayoutStories;
    private ProgressBar mProgressBar;
    private TextView mTextViewNoInternet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();

    }

    private void onVariables() {
        mContext = Exhibitions.this;
        mStateId = "";
        mCarBrandsId = "";
        mCityId = "";
        mCarModelsId = "";
        mTextViewNoInternet = (TextView) findViewById(R.id.tvNoInternet);
        mSpinnerNewAccountArea = (MaterialSpinner) findViewById(R.id.NewAccountSpinnerArea);
        mSpinnerNewAccountMarka = (MaterialSpinner) findViewById(R.id.NewAccountSpinnerMarka);
        mSpinnerNewAccountMarka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mCarBrandsId = "";
                    mSpinnerNewAccountKind.setVisibility(View.GONE);
                } else {
                    mSpinnerNewAccountKind.setVisibility(View.VISIBLE);
                    mCarBrandsId = carBrands_Id.get(position);
                    onCarModels(carBrands_Id.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerNewAccountKind = (MaterialSpinner) findViewById(R.id.NewAccountSpinnerKind);
        mSpinnerNewAccountKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mSpinnerNewAccountArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    mStateId = "";
                    mSpinnerNewAccountCity.setVisibility(View.GONE);
                } else {
                    mSpinnerNewAccountCity.setVisibility(View.VISIBLE);
                    mStateId = State_Id.get(position);
                    onCity(State_Id.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerNewAccountCity = (MaterialSpinner) findViewById(R.id.NewAccountSpinnerCity);
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
        State_Id = new ArrayList<String>();
        carBrands_Id = new ArrayList<String>();
        State_Name = new ArrayList<String>();
        carBrands_Name = new ArrayList<String>();
        City_Id = new ArrayList<String>();
        carModels_Id = new ArrayList<String>();
        City_Name = new ArrayList<String>();
        carModels_Name = new ArrayList<String>();
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewStories = (RecyclerView) findViewById(R.id.rvStories);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewStories.setLayoutManager(manager);
        mRecyclerViewStories.addItemDecoration(new VerticalSpaceItemDecoration(60));
        models = new ArrayList<StoresModel>();
        modelsSearch = new ArrayList<StoresModel>();
        mImageViewSearch = (ImageView) findViewById(R.id.ivSearchStories);
        mImageViewSearch.setOnClickListener(this);
        mLinearLayoutSearchStories = (LinearLayout) findViewById(R.id.llSearchStories);
        mButtonAllAreas = (Button) findViewById(R.id.bAllAreas);
        mButtonSearchStories = (Button) findViewById(R.id.bSearchStories);
        mButtonAllAreas.setOnClickListener(this);
        mButtonSearchStories.setOnClickListener(this);
        mSwipeRefreshLayoutStories = (SwipeRefreshLayout) findViewById(R.id.srlStories);
        mSwipeRefreshLayoutStories.setOnRefreshListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        onLoadExhibitionsData();
        onStates();
        onCarBrands();
    }
    private void onLoadExhibitionsData() {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequestExhibitions=new StringRequest(Request.Method.GET, MainApp.ExhibitionsUrl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mTextViewNoInternet.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    JSONObject mJsonObject=new JSONObject(response);
                    JSONArray data = mJsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++){
                        JSONObject jsonObject=data.getJSONObject(i);
                        int id = jsonObject.getInt("id");
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
                        StoresModel storesModel = new StoresModel(id, name, photo, description, phone, longitude, latitude, ads_count, name1, id1);
                        models.add(storesModel);
                    }
                    StoresAdapter mStoresAdapter=new StoresAdapter(mContext,models);
                    mRecyclerViewStories.setAdapter(mStoresAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadExhibitionsData();
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestExhibitions);
    }
    public void onStates(){
        String Url=MainApp.StatesUrl;
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
                                R.layout.item_spinner,R.id.tvItem,State_Name);
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
                                R.layout.item_spinner,R.id.tvItem,carBrands_Name);
                        mSpinnerNewAccountMarka.setAdapter(dataAdapter);

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
                                R.layout.item_spinner,R.id.tvItem,City_Name);
                        mSpinnerNewAccountCity.setAdapter(dataAdapter);

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
    public void onCarModels(final String position){
        if (carModels_Name!=null){
            carModels_Name.clear();
            carModels_Id.clear();
        }
        String Url=MainApp.CarModelsUrl+position;
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
                                R.layout.item_spinner,R.id.tvItem,carModels_Name);
                        mSpinnerNewAccountKind.setAdapter(dataAdapter);

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



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivSearchStories:
                if (mLinearLayoutSearchStories.getVisibility()==View.GONE){
                    mLinearLayoutSearchStories.setVisibility(View.VISIBLE);
                }else {
                    mLinearLayoutSearchStories.setVisibility(View.GONE);
                }
                break;
            case R.id.bAllAreas:
                StoresAdapter mStoresAdapter=new StoresAdapter(mContext,models);
                mRecyclerViewStories.setAdapter(mStoresAdapter);
                mLinearLayoutSearchStories.setVisibility(View.GONE);
                break;
            case R.id.bSearchStories:
                if (modelsSearch!=null){
                    Log.i(MainApp.Tag,"Worked");
                    modelsSearch.clear();
                }
                for (int i=0;i<models.size();i++) {
                    if (mCityId.equals(models.get(i).getCity_id())) {
                        modelsSearch.add(models.get(i));
                    }
                }
                        if (models==null){



                        }else {
                            //Toast.makeText(mContext,"لا يوجد نتائج للبحث",Toast.LENGTH_SHORT).show();
                            mLinearLayoutSearchStories.setVisibility(View.GONE);

                        }



                StoresAdapter mStoresAdapter2=new StoresAdapter(mContext,modelsSearch);
                mRecyclerViewStories.setAdapter(mStoresAdapter2);
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (models!=null){
            models.clear();
        }
        onLoadExhibitionsData();
        if (mSwipeRefreshLayoutStories.isRefreshing()){
            mSwipeRefreshLayoutStories.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Intent mIntentSearch=new Intent(getApplicationContext(),Search.class);
                startActivity(mIntentSearch);
                break;

            case R.id.action_Adv_Favorites:
                Intent mIntentAdvFavorites=new Intent(getApplicationContext(),MemberFavorites.class);
                startActivity(mIntentAdvFavorites);
                break;

            case R.id.action_New_Account:
                Intent mIntentNewAccount=new Intent(getApplicationContext(),NewAccount.class);
                startActivity(mIntentNewAccount);
                break;

            case R.id.action_Login:
                Intent mIntentLogin=new Intent(getApplicationContext(),Login.class);
                startActivity(mIntentLogin);
                break;
        }
        return true;
    }


}

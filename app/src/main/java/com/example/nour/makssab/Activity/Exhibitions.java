package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nour.makssab.Adapter.StoresAdapter;
import com.example.nour.makssab.Decoration.EndlessRecyclerOnScrollListener;
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

public class Exhibitions extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private Context mContext;
    private RecyclerView mRecyclerViewStories;
    private ArrayList<StoresModel> models;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String>State_Id;
    private ArrayList<String>State_Name;
    private ArrayList<String>City_Id;
    private ArrayList<String>City_Name;
    private Spinner mSpinnerNewAccountArea;
    private Spinner mSpinnerNewAccountCity;
    private String mStateId;
    private String mCityId;
    private TextView mImageViewSearch;
    private LinearLayout mLinearLayoutSearchStories;
    private Button mButtonAllAreas;
    private Button mButtonSearchStories;
    private ArrayList<StoresModel> modelsSearch;
    private SwipeRefreshLayout mSwipeRefreshLayoutStories;
    private ProgressBar mProgressBar;
    private TextView mTextViewNoInternet;
    private ImageView mImageViewBack;
    private TextView mTextViewTitle;
    private String mCarBrandsId;
    private String mCarModelsId;
    private Spinner mSpinnerNewAccountMarka;
    private Spinner mSpinnerNewAccountKind;
    private ArrayList<String>carModels_Name;
    private ArrayList<String>carModels_Id;
    private ArrayList<String>carBrands_Id;
    private ArrayList<String>carBrands_Name;
    private StoresAdapter mStoresAdapter;
    private String next_page_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        onVariables();



    }
    private void onVariables() {
        mContext = Exhibitions.this;
        mCarBrandsId = "";
        mCarModelsId = "";
        carBrands_Id = new ArrayList<String>();
        carBrands_Name = new ArrayList<String>();
        carModels_Id = new ArrayList<String>();
        carModels_Name = new ArrayList<String>();

        mSpinnerNewAccountMarka = (Spinner) findViewById(R.id.NewAccountSpinnerMarka);
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
        mSpinnerNewAccountKind = (Spinner) findViewById(R.id.NewAccountSpinnerKind);
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

        mStateId = "";
        mCityId = "";
        mTextViewNoInternet = (TextView) findViewById(R.id.tvNoInternet);
        mTextViewTitle = (TextView) findViewById(R.id.tvTitle);
        mTextViewTitle.setText("معارض فى السعوديه");
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
        State_Id = new ArrayList<String>();
        State_Name = new ArrayList<String>();
        City_Id = new ArrayList<String>();
        City_Name = new ArrayList<String>();
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mRecyclerViewStories = (RecyclerView) findViewById(R.id.rvStories);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerViewStories.setLayoutManager(manager);
        mRecyclerViewStories.addItemDecoration(new VerticalSpaceItemDecoration(60));
        mRecyclerViewStories.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page)

            {
                Log.i(MainApp.Tag,next_page_url);
                if (next_page_url.equals("null")){

                }else {
                    onLoadBuildingsData(next_page_url);
                }
            }
        });
        models = new ArrayList<StoresModel>();
        modelsSearch = new ArrayList<StoresModel>();
        mImageViewSearch = (TextView) findViewById(R.id.ivSearchStories);
        mImageViewSearch.setOnClickListener(this);
        mImageViewBack = (ImageView) findViewById(R.id.ivBackButton);
        mImageViewBack.setOnClickListener(this);
        mLinearLayoutSearchStories = (LinearLayout) findViewById(R.id.llSearchStories);
        mButtonAllAreas = (Button) findViewById(R.id.bAllAreas);
        mButtonSearchStories = (Button) findViewById(R.id.bSearchStories);
        mButtonSearchStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });
        mButtonAllAreas.setOnClickListener(this);
//        mButtonSearchStories.setOnClickListener(this);
        mSwipeRefreshLayoutStories = (SwipeRefreshLayout) findViewById(R.id.srlStories);
        mSwipeRefreshLayoutStories.setOnRefreshListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mStoresAdapter=new StoresAdapter(mContext,models, "3");
        mRecyclerViewStories.setAdapter(mStoresAdapter);
        onLoadBuildingsData(MainApp.ExhibitionsUrl);
        onStates();
        onCarBrands();
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

    private void onLoadBuildingsData(final String exhibitionsUrl) {
        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest mStringRequestBuildings=new StringRequest(Request.Method.GET, exhibitionsUrl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mTextViewNoInternet.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    JSONObject mJsonObject=new JSONObject(response);
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
                        models.add(storesModel);
                    }
                    mStoresAdapter.notifyDataSetChanged();
                    mRecyclerViewStories.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewNoInternet.setText("لا يوجد انترنت !");
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onLoadBuildingsData(exhibitionsUrl);
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestBuildings);
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
            case R.id.ivSearchStories:
                mTextViewNoInternet.setVisibility(View.GONE);
                if (mLinearLayoutSearchStories.getVisibility()==View.GONE){
                    mLinearLayoutSearchStories.setVisibility(View.VISIBLE);
                }else {
                    mLinearLayoutSearchStories.setVisibility(View.GONE);
                }
                break;
            case R.id.bAllAreas:
                mTextViewNoInternet.setVisibility(View.GONE);
                StoresAdapter mStoresAdapter=new StoresAdapter(mContext,models, "3");
                mRecyclerViewStories.setAdapter(mStoresAdapter);
                mLinearLayoutSearchStories.setVisibility(View.GONE);
                break;
//            case R.id.bSearchStories:
//                mTextViewNoInternet.setVisibility(View.GONE);
//                if (modelsSearch!=null){
//                    modelsSearch.clear();
//                }
//                for (int i=0;i<models.size();i++){
//                    if (mCityId.equals(models.get(i).getCity_id())){
//                        modelsSearch.add(models.get(i));
//                    }
//                }
//                if (modelsSearch.size()==0){
//                    mTextViewNoInternet.setText("لا يوجد معارض في هذه المنطقة!");
//                    mTextViewNoInternet.setVisibility(View.VISIBLE);
//                }
//                StoresAdapter mStoresAdapter2=new StoresAdapter(mContext,modelsSearch, "3");
//                mRecyclerViewStories.setAdapter(mStoresAdapter2);
//                mLinearLayoutSearchStories.setVisibility(View.GONE);
//                break;
            case R.id.ivBackButton:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mRecyclerViewStories.setVisibility(View.GONE);
        if (models!=null){
            models.clear();
        }
        onLoadBuildingsData(MainApp.ExhibitionsUrl);
        if (mSwipeRefreshLayoutStories.isRefreshing()){
            mSwipeRefreshLayoutStories.setRefreshing(false);
        }
    }
    public void onSearch(){
        final String Url=MainApp.SearchUrl+mStateId+"&city_id="+mCityId+"&car_id="+mCarBrandsId+"&model_id="+mCarModelsId;
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
                    mStoresAdapter=new StoresAdapter(mContext,modelsSearch, "3");
                    mRecyclerViewStories.setAdapter(mStoresAdapter);
                                    mLinearLayoutSearchStories.setVisibility(View.GONE);
                    mRecyclerViewStories.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextViewNoInternet.setText("لا يوجد انترنت !");
                mTextViewNoInternet.setVisibility(View.VISIBLE);
                onSearch();
            }
        });
        mVolleySingletonRequestQueue.add(mStringRequestonSearch);
    }

    @Override
    public void onBackPressed() {
        if (mLinearLayoutSearchStories.getVisibility()==View.VISIBLE){
            mLinearLayoutSearchStories.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }

    }
}

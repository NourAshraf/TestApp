package com.example.nour.makssab.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.nour.makssab.MainApp.MainApp;
import com.example.nour.makssab.Network.VolleySingleton;
import com.example.nour.makssab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String> carBrands_Id;
    private ArrayList<String> carBrands_Name;
    private String mCarBrandsId;
    private ArrayList<String> carModels_Id;
    private String mCarModelsId;
    private Spinner mSpinnerSearchMarka;
    private Spinner mSpinnerSearchModel;
    private Spinner mSpinnerSearchKind;
    private ArrayList<String> carModels_Name;
    private Button mButtonSearch;
    private ArrayList<String>ModelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        mSpinnerSearchModel = (Spinner) findViewById(R.id.SearchSpinnerModel);
        mSpinnerSearchModel.setOnItemSelectedListener( Search.this);
        List<String>years=new ArrayList<String>();
        for (int i=2020;i>=1970;i--){
            years.add(""+i);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.item_spinner_test,R.id.tvItemSpinner,years);
        mSpinnerSearchModel.setAdapter(dataAdapter);

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
}

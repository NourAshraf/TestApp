package com.example.nour.makssab.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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

public class NewAccount extends AppCompatActivity {
    private EditText mEditTextNewAccountUser;
    private EditText mEditTextNewAccountPhone;
    private EditText mEditTextNewAccountEmail;
    private TextView mTextViewNewAccountArea;
    private TextView mTextViewNewAccountCity;;
    private EditText mEditTextNewAccountPass;
    private EditText mEditTextNewAccountSurePass;
    private Button mButtonNewAccount;
    private Spinner mSpinnerNewAccountArea;
    private Spinner mSpinnerNewAccountCity;
    private  RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String>State_Id;
    private ArrayList<String>State_Name;
    private ArrayList<String>City_Id;
    private ArrayList<String>City_Name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Allvariables();
        onStates();


    }

    private void Allvariables() {
        mEditTextNewAccountUser= (EditText) findViewById(R.id.etNewAccountUser);
        mEditTextNewAccountPhone= (EditText) findViewById(R.id.etNewAccountPhone);
        mEditTextNewAccountEmail= (EditText) findViewById(R.id.etNewAccountEmail);
        mTextViewNewAccountArea= (TextView) findViewById(R.id.tvNewAccountArea);
        mTextViewNewAccountCity= (TextView) findViewById(R.id.tvNewAccountCity);
        mEditTextNewAccountPass= (EditText) findViewById(R.id.etNewAccountPass);
        mEditTextNewAccountSurePass= (EditText) findViewById(R.id.etNewAccountSurePass);
        mButtonNewAccount= (Button) findViewById(R.id.bNewAccount);
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSpinnerNewAccountArea= (Spinner) findViewById(R.id.NewAccountSpinnerArea);
        mSpinnerNewAccountArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onCity(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerNewAccountCity= (Spinner) findViewById(R.id.NewAccountSpinnerCity);
        State_Id=new ArrayList<String>();
        State_Name=new ArrayList<String>();


    }
    public void onStates(){
        String Url=MainApp.StatesUrl;
        StringRequest mStringRequestonStates=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
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
                              android.R.layout.simple_spinner_item, State_Name);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
    public void onCity(final int position){
        String Url=MainApp.CitiesUrl;
        StringRequest mStringRequestonCity=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
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
                                android.R.layout.simple_spinner_item, City_Name);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
//l
    }

}

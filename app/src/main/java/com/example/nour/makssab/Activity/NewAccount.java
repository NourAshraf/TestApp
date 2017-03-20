package com.example.nour.makssab.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.Map;
import fr.ganfra.materialspinner.MaterialSpinner;

public class NewAccount extends AppCompatActivity {
    private EditText mEditTextNewAccountUser;
    private EditText mEditTextNewAccountPhone;
    private EditText mEditTextNewAccountEmail;
    private TextView mTextViewNewAccountArea;
    private TextView mTextViewNewAccountCity;;
    private EditText mEditTextNewAccountPass;
    private EditText mEditTextNewAccountSurePass;
    private Button mButtonNewAccount;
    private MaterialSpinner mSpinnerNewAccountArea;
    private MaterialSpinner mSpinnerNewAccountCity;
    private  RequestQueue mVolleySingletonRequestQueue;
    private ArrayList<String>State_Id;
    private ArrayList<String>State_Name;
    private ArrayList<String>City_Id;
    private ArrayList<String>City_Name;
    private LinearLayout mLinearLayoutCity;
    private String mStateId;
    private String mCityId;


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
        mStateId="";
        mCityId="";
        mLinearLayoutCity= (LinearLayout) findViewById(R.id.llCity);
        mEditTextNewAccountUser= (EditText) findViewById(R.id.etNewAccountUser);
        mEditTextNewAccountPhone= (EditText) findViewById(R.id.etNewAccountPhone);
        mEditTextNewAccountEmail= (EditText) findViewById(R.id.etNewAccountEmail);
        mTextViewNewAccountArea= (TextView) findViewById(R.id.tvNewAccountArea);
        mTextViewNewAccountCity= (TextView) findViewById(R.id.tvNewAccountCity);
        mEditTextNewAccountPass= (EditText) findViewById(R.id.etNewAccountPass);
        mEditTextNewAccountSurePass= (EditText) findViewById(R.id.etNewAccountSurePass);
        mButtonNewAccount= (Button) findViewById(R.id.bNewAccount);
        mButtonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = mEditTextNewAccountEmail.getText().toString();
                String Pass = mEditTextNewAccountPass.getText().toString();
                String SurePass = mEditTextNewAccountSurePass.getText().toString();
                String User = mEditTextNewAccountUser.getText().toString();
                String Phone = mEditTextNewAccountPhone.getText().toString();
                if (User.matches("")){
                    Toast.makeText(getApplicationContext(),"لابد من ادخال الاسم !",Toast.LENGTH_SHORT).show();
                }
                else if (User.matches(".{15,}+")){
                    Toast.makeText(getApplicationContext(),"الاسم لا يزيد عن 15 حرف!",Toast.LENGTH_SHORT).show();
                }
                else if (Email.matches("")){
                    Toast.makeText(getApplicationContext(),"لابد من ادخال البريد الالكترونى !",Toast.LENGTH_SHORT).show();
                }
                else if (!Email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                    Toast.makeText(getApplicationContext(),"اكتب البريد الالكترونى بصوره صحيحه!",Toast.LENGTH_SHORT).show();
                }else if (Pass.matches("")){
                    Toast.makeText(getApplicationContext(),"لابد من ادخال كلمه المرور !",Toast.LENGTH_SHORT).show();
                }
                else if (!Pass.matches(".{6,}+")){
                    Toast.makeText(getApplicationContext(),"كلمه المرور لا تقل عن 6 احرف او ارقام!",Toast.LENGTH_SHORT).show();
                }else if (!Phone.matches(".{3,20}+")){
                    Toast.makeText(getApplicationContext(),"رقم الجوال الا يقل عن 3 ولا يزيد عن 20 رقم!",Toast.LENGTH_LONG).show();
                }else if (!Pass.equals(SurePass)){
                    Toast.makeText(getApplicationContext(),"كلمتى المرور لابد ان يكونوا متطابقيين!",Toast.LENGTH_LONG).show();
                }else if (mStateId.equals("")){
                    Toast.makeText(getApplicationContext(),"لابد من اختيار المنطقه!",Toast.LENGTH_LONG).show();
                }else if (mCityId.equals("")){
                    Toast.makeText(getApplicationContext(),"لابد من اختيار المدينه!",Toast.LENGTH_LONG).show();
                }else {
                  onRegister(Email,Pass,Phone,SurePass,User,mCityId,mStateId);
                }
            }
        });
        VolleySingleton mVolleySingleton=VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        mSpinnerNewAccountArea= (MaterialSpinner) findViewById(R.id.NewAccountSpinnerArea);
        mSpinnerNewAccountArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==-1){
                    mStateId="";
                }else {
                    mStateId=State_Id.get(position);
                    mLinearLayoutCity.setVisibility(View.VISIBLE);
                    onCity(State_Id.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerNewAccountCity= (MaterialSpinner) findViewById(R.id.NewAccountSpinnerCity);
        mSpinnerNewAccountCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==-1){
                    mCityId="";
                }else {
                    mCityId=City_Id.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        State_Id=new ArrayList<String>();
        State_Name=new ArrayList<String>();
        City_Id=new ArrayList<String>();
        City_Name=new ArrayList<String>();
    }

    private void onRegister(final String email, final String pass, final String phone, final String surePass, final String user, final String mCityId, final String mStateId) {
        String Url=MainApp.RegUrl;
       StringRequest mStringRequestonRegister=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {



           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {


           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String>paramter=new HashMap<String, String>();
               paramter.put("username",user);
               paramter.put("phone",phone);
               paramter.put("state_id",mStateId);
               paramter.put("city_id",mCityId);
               paramter.put("email",email);
               paramter.put("password",pass);
               paramter.put("password_confirmation",surePass);
               return paramter;
           }
       };
       mVolleySingletonRequestQueue.add(mStringRequestonRegister);
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

}

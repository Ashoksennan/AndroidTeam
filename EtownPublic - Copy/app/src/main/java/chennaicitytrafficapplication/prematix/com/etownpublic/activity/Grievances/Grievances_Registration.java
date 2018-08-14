package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Grievances;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.BuildConfig;
import chennaicitytrafficapplication.prematix.com.etownpublic.HomeActivity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.StreetAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.listener.RecyclerClickListener;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.District_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Street_Pojo;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Grievances_Registration extends AppCompatActivity {

    //views

    TextInputEditText griev_tie_district, griev_tie_panchayat, griev_tie_grievance_type,
            griev_tie_wardno, griev_tie_streetname;

    EditText griev_et_city, griev_et_name, griev_et_doorno, griev_et_mobileno, griev_et_email_id, griev_et_grievance_desc,
            griev_tie_others;

    TextInputLayout griev_et_district_layout, griev_et_panchayat_layout, griev_et_grievancetype_layout,
            griev_tie_layout_city, griev_et_wardno_layout, griev_et_street_name_layout, griev_tie_layout_name,
            griev_tie_layout_doorno, griev_tie_layout_mobileno, griev_tie_layout_email_id, griev_et_grievance_other_layout;

    TextView griev_tv_grievance_character_error;

    Button griev_btn_submit;

    Toolbar griev_Toolbar;

    RelativeLayout rl_root;

    //----------------------------

    //arraylist

    ArrayList<District_Pojo> arrayListDistrict;
    ArrayList<String> sArrayList_DistrictNames;

    ArrayList<Street_Pojo> arrayListStreet;
    ArrayList<String> sArrayList_StreetNames;

    ArrayList<String> sArrayList_String;

    //progress
    SpinnerDialog spinnerDialog;
    ProgressDialog pd;


    //typeface
    Typeface typeTamil;

    //adapter
    StreetAdapter streetAdapter;

    //string
    String TAG = Grievances_Registration.class.getName();
    String errorType, selected_DistrictName, selected_PanchayatName, selected_GrievanceType, selected_WardNo,
            selected_StreetName, selected_streetId, sRequestNo, sSMS_Message_User;
    String EOMobileNumber;
    String[] sSplitRequestNo;

    //int
    int selected_DistrictId, selected_PanchayatId;

    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievances);
        //toolbar
        griev_Toolbar = findViewById(R.id.griev_toolbar);
        setSupportActionBar(griev_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //typeface
        typeTamil = ResourcesCompat.getFont(this, R.font.avvaiyar);

        //arraylists
        arrayListDistrict = new ArrayList<>();
        sArrayList_DistrictNames = new ArrayList<>();

        arrayListStreet = new ArrayList<>();
        sArrayList_StreetNames = new ArrayList<>();


        sArrayList_String = new ArrayList<>();

        //id's

        griev_tie_district = findViewById(R.id.griev_tie_district);
        griev_tie_panchayat = findViewById(R.id.griev_tie_panchayat);
        griev_tie_grievance_type = findViewById(R.id.griev_tie_grievance_type);
        griev_tie_wardno = findViewById(R.id.griev_tie_wardno);
        griev_tie_streetname = findViewById(R.id.griev_tie_streetname);


        griev_et_city = findViewById(R.id.griev_et_city);
        griev_et_name = findViewById(R.id.griev_et_name);
        griev_et_doorno = findViewById(R.id.griev_et_doorno);
        griev_et_mobileno = findViewById(R.id.griev_et_mobileno);
        griev_et_email_id = findViewById(R.id.griev_et_email_id);
        griev_et_grievance_desc = findViewById(R.id.griev_et_grievance_desc);
        griev_tie_others = findViewById(R.id.griev_tie_others);

        griev_et_district_layout = findViewById(R.id.griev_et_district_layout);
        griev_et_panchayat_layout = findViewById(R.id.griev_et_panchayat_layout);
        griev_et_grievancetype_layout = findViewById(R.id.griev_et_grievancetype_layout);
        griev_tie_layout_city = findViewById(R.id.griev_tie_layout_city);
        griev_et_wardno_layout = findViewById(R.id.griev_et_wardno_layout);
        griev_et_street_name_layout = findViewById(R.id.griev_et_street_name_layout);
        griev_tie_layout_name = findViewById(R.id.griev_tie_layout_name);
        griev_tie_layout_doorno = findViewById(R.id.griev_tie_layout_doorno);
        griev_tie_layout_mobileno = findViewById(R.id.griev_tie_layout_mobileno);
        griev_tie_layout_email_id = findViewById(R.id.griev_tie_layout_email_id);
        griev_et_grievance_other_layout = findViewById(R.id.griev_et_grievance_other_layout);

        griev_tv_grievance_character_error = findViewById(R.id.griev_tv_grievance_character_error);

        griev_btn_submit = findViewById(R.id.griev_btn_submit);

        rl_root = findViewById(R.id.rl_root);

        //method

        onClicks();

    }


    public void onClicks() {

        griev_tie_district.addTextChangedListener(new cTextWatcher(griev_tie_district));
        griev_tie_panchayat.addTextChangedListener(new cTextWatcher(griev_tie_panchayat));
        griev_tie_grievance_type.addTextChangedListener(new cTextWatcher(griev_tie_grievance_type));
        griev_et_name.addTextChangedListener(new cTextWatcher(griev_et_name));
        griev_et_doorno.addTextChangedListener(new cTextWatcher(griev_et_doorno));
        griev_tie_wardno.addTextChangedListener(new cTextWatcher(griev_tie_wardno));
        griev_tie_streetname.addTextChangedListener(new cTextWatcher(griev_tie_streetname));
        griev_et_city.addTextChangedListener(new cTextWatcher(griev_et_city));
        griev_et_mobileno.addTextChangedListener(new cTextWatcher(griev_et_mobileno));
        griev_et_email_id.addTextChangedListener(new cTextWatcher(griev_et_email_id));

        griev_Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(Grievances_Registration.this, HomeActivity.class);
                startActivity(ik);
            }
        });


        griev_tie_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {
                    getDistricts();
                    griev_tie_panchayat.setText("");
                } else {
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        griev_tie_panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {

                    if (validateEdittext(griev_tie_district, griev_et_district_layout, "Please Select District")) {

                        getPanchayat();

                    } else {
                        Snackbar.make(rl_root, "Please Select District !", Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        griev_tie_grievance_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    getComplaintType();
                } else {
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        griev_tie_wardno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {

                    if (validateEdittext(griev_tie_panchayat, griev_et_panchayat_layout, "Please Select Panchayat")) {

                        getWardNo();
                    } else {
                        Snackbar.make(rl_root, "Please Select Panchayat !", Snackbar.LENGTH_SHORT).show();


                    }
                } else {
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        griev_tie_streetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {

                    if (validateEdittext(griev_tie_wardno, griev_et_wardno_layout, "Please Select Ward No")) {

                        getStreets();
                    } else {
                        Snackbar.make(rl_root, "Please Select Ward No !", Snackbar.LENGTH_SHORT).show();


                    }
                } else {
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }


            }
        });

        griev_et_grievance_desc.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});

        griev_et_grievance_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (griev_et_grievance_desc.getText().toString().length() >= 500) {
                    griev_tv_grievance_character_error.setText("Limit Exceeded");
                    griev_tv_grievance_character_error.setTextColor(getResources().getColor(R.color.red));

                    griev_et_grievance_desc.setBackgroundResource(R.drawable.edittext_background_red);

                } else {
                    griev_tv_grievance_character_error.setText(" " + String.valueOf(500 - griev_et_grievance_desc.getText().length()) + " Character(s) Remaining");
                    griev_tv_grievance_character_error.setTextColor(getResources().getColor(R.color.background_color));

                    griev_et_grievance_desc.setBackgroundResource(R.drawable.edittext_background_violet);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void getDistricts() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getDistrict(Common.ACCESS_TOKEN);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "url" + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONArray jsonArray = new JSONArray(sResponse);

                        if (jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String sDistrictName = jsonObject.getString("DistrictName");

                                int sDistrictId = jsonObject.getInt("DistrictId");

                                arrayListDistrict.add(new District_Pojo(sDistrictName, sDistrictId));

                                sArrayList_DistrictNames.add(arrayListDistrict.get(i).getdName());
                                if (BuildConfig.DEBUG) {

                                    Log.e(TAG, "" + sDistrictName + " -- " + sDistrictId);
                                }
                            }


                            setSpinner(sArrayList_DistrictNames);

                        } else {
                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();


                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void getPanchayat() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getPanchayat(Common.ACCESS_TOKEN, selected_DistrictId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "" + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONArray jsonArray = new JSONArray(sResponse);

                        if (jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String sPanchayatName = jsonObject.getString("PanchayatName");

                                int sPanchayatId = jsonObject.getInt("PanchayatId");

                                arrayListDistrict.add(new District_Pojo(sPanchayatName, sPanchayatId));

                                sArrayList_DistrictNames.add(arrayListDistrict.get(i).getdName());
                                if (BuildConfig.DEBUG) {

                                    Log.e(TAG, "" + sPanchayatName + " -- " + sPanchayatId);
                                }
                            }


                            setSpinnerForPanchayat(sArrayList_DistrictNames);

                        } else {
                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();


                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void getWardNo() {

        sArrayList_String.clear();

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getWard_Street(Common.ACCESS_TOKEN, "Ward", "", selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "url " + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);


                        if (jsonObject.length() > 0) {


                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);

                                        if (jsonArray1.length() > 0) {
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONObject jsonObject1 = (JSONObject) jsonArray1.get(j);


                                                String WardNo = jsonObject1.optString("WardNo");

                                                sArrayList_String.add(WardNo);


                                            }

                                            setSpinnerStatic(sArrayList_String, "Select Ward No ",
                                                    griev_tie_wardno);


                                        } else {
                                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                                        }
                                    }

                                }
                            } else {
                                Snackbar.make(rl_root, "Something went Wrong", Snackbar.LENGTH_SHORT).show();

                            }


                        } else {
                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();


                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void getStreets() {

        arrayListStreet.clear();
        sArrayList_StreetNames.clear();

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getWard_Street(Common.ACCESS_TOKEN, "Street", selected_WardNo, selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "url " + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);


                        if (jsonObject.length() > 0) {


                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);

                                        if (jsonArray1.length() > 0) {
                                            for (int j = 0; j < jsonArray1.length(); j++) {

                                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

                                                String StreetNo = jsonObject1.getString("StreetNo");

                                                String StreetName = jsonObject1.getString("StreetName");

                                                arrayListStreet.add(new Street_Pojo(StreetName, StreetNo));

                                                sArrayList_StreetNames.add(arrayListStreet.get(j).getStreetName());


                                                if (BuildConfig.DEBUG) {

                                                    Log.e(TAG, "" + StreetNo + " -- " + StreetName);
                                                }


                                            }

                                            showStreetValuesInAlert(arrayListStreet);

                                        } else {
                                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                                        }
                                    }

                                }
                            } else {
                                Snackbar.make(rl_root, "Something went Wrong", Snackbar.LENGTH_SHORT).show();

                            }


                        } else {
                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();


                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void getComplaintType() {

        sArrayList_String.clear();

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getWard_Street(Common.ACCESS_TOKEN, "CType",
                "", "", "");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "url " + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);


                        if (jsonObject.length() > 0) {


                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);

                                        if (jsonArray1.length() > 0) {
                                            for (int j = 0; j < jsonArray1.length(); j++) {

                                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

                                                String ComplaintType = jsonObject1.getString("ComplaintType");

                                                sArrayList_String.add(ComplaintType);


                                                if (BuildConfig.DEBUG) {

                                                    Log.e(TAG, " " + ComplaintType);
                                                }


                                            }

                                            setSpinnerStatic(sArrayList_String, "Select Grievance Type", griev_tie_grievance_type);

                                        } else {
                                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                                        }
                                    }

                                }
                            } else {
                                Snackbar.make(rl_root, "Something went Wrong", Snackbar.LENGTH_SHORT).show();

                            }


                        } else {
                            Snackbar.make(rl_root, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void submitGrievanceValues() {

        if (griev_tie_others.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(griev_tie_others.getText().toString())) {
                selected_GrievanceType = griev_tie_others.getText().toString();
            }
        }

        pd = new ProgressDialog(Grievances_Registration.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.submitGrievances(Common.ACCESS_TOKEN, selected_DistrictName, selected_PanchayatName,
                selected_GrievanceType, griev_et_grievance_desc.getText().toString(), griev_et_name.getText().toString(),
                griev_et_doorno.getText().toString(), selected_WardNo, selected_StreetName, griev_et_city.getText().toString(), griev_et_mobileno.getText().toString(),
                griev_et_email_id.getText().toString(), "Android"
        );

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "" + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);

                        int code = jsonObject.optInt("code");
                        String message = jsonObject.optString("message");

                        if (message.contains("Success")) {

                            sSplitRequestNo = message.split("~");

                            sRequestNo = sSplitRequestNo[1];

//                            sSMS_Message_User = "Hi " + griev_et_name.getText().toString() + ", Grievance has been Registered, Your Reference No : " + sRequestNo + " Thank you. -- " + selected_PanchayatName + " TP.";

                            showRegistrationResponse_Alert("Success", "Grievance Submitted Successfully", "ic_success");


                        } else {

                            showRegistrationResponse_Alert("Failed", "Sorry, Submission Failed", "ic_error");

                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(rl_root, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void getEODetails() {

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getEODetails(Common.ACCESS_TOKEN, selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "url " + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);


                        if (jsonObject.length() > 0) {


                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {


                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                        String EOMobileNo = jsonObject1.getString("EOMobileNo");
                                        String EmailId = jsonObject1.getString("EmailId");

                                        String msg = "Hi Sir/Madam, Grievance Complaint has been Registered our " + selected_PanchayatName + " TP. Reference No : " + sRequestNo + ". Please visit our website www.etownpanchayat.com -- Prematix";

                                        if (!TextUtils.isEmpty(EOMobileNo)) {
//                                            sendSMS(EOMobileNo,msg);
                                        }


                                        if (BuildConfig.DEBUG) {

                                            Log.e(TAG, " " + EOMobileNo);
                                            Log.e(TAG, " " + EmailId);
                                        }


                                    }

                                }

                            }


                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rl_root, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void setSpinner(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(Grievances_Registration.this, arrayList, "Select District", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                griev_tie_district.setText(arrayListDistrict.get(i).getdName());

                selected_DistrictId = arrayListDistrict.get(i).getdID();
                selected_DistrictName = arrayListDistrict.get(i).getdName();

                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_DistrictId);
                }
            }
        });
    }


    public void setSpinnerForPanchayat(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(Grievances_Registration.this, arrayList, "Select Panchayat", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                griev_tie_panchayat.setText(arrayListDistrict.get(i).getdName());
                griev_et_city.setText(arrayListDistrict.get(i).getdName());
                selected_PanchayatId = arrayListDistrict.get(i).getdID();
                selected_PanchayatName = arrayListDistrict.get(i).getdName();

                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_PanchayatId);
                }
            }
        });
    }

    public void setSpinnerStatic(final ArrayList<String> arrayList, final String sTitleSpinner,
                                 final EditText editText) {

        spinnerDialog = new SpinnerDialog(Grievances_Registration.this, arrayList,
                sTitleSpinner, R.style.TextAppearance_TableTitle, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                editText.setText(arrayList.get(i));


                switch (sTitleSpinner) {
                    case "Select Grievance Type":
                        selected_GrievanceType = arrayList.get(i);

                        if (selected_GrievanceType.equalsIgnoreCase("Other Complaints"))

                        {
                            griev_et_grievance_other_layout.setVisibility(View.VISIBLE);
                        } else {
                            griev_et_grievance_other_layout.setVisibility(View.GONE);
                        }
                        break;
                    case "Select Ward No ":
                        selected_WardNo = arrayList.get(i);
                        break;

                }


                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "" + arrayList.get(i));
                }
            }
        });
    }


    public void showRegistrationResponse_Alert(String sTitle, String sDesc, String img) {

        int image = getResources().getIdentifier(img, "drawable", this.getPackageName());
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Grievances_Registration.this);
        View v2 = getLayoutInflater().inflate(R.layout.alert_response, null);
        mBuilder.setView(v2);
        mBuilder.setCancelable(false);

        TextView tv_title = v2.findViewById(R.id.tv_title);
        TextView tv_desc = v2.findViewById(R.id.tv_desc);
        Button btn_ok = v2.findViewById(R.id.btn_ok);
        ImageView iv_res = v2.findViewById(R.id.iv_res);

        iv_res.setImageResource(image);
        tv_title.setText(sTitle);
        tv_desc.setText(sDesc);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                finish();
              /*  getEODetails();

                if (!griev_et_mobileno.getText().toString().isEmpty() && sSMS_Message_User != null) {
                    sendSMS(griev_et_mobileno.getText().toString(), sSMS_Message_User);

                }
*/
//                Intent i = new Intent(Grievances_Registration.this, HomeActivity.class);
//                startActivity(i);
//                finish();
            }
        });

        dialog = mBuilder.create();
        if (!Grievances_Registration.this.isFinishing()) {
            dialog.show();
        }


    }

    public void showStreetValuesInAlert(final ArrayList<Street_Pojo> data_list) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Grievances_Registration.this);
        View v2 = getLayoutInflater().inflate(R.layout.recycler_alert, null);


        mBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        RecyclerView recyclerView = v2.findViewById(R.id.recycler);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v2.getContext());
        recyclerView.setLayoutManager(layoutManager);


        streetAdapter = new StreetAdapter(data_list);
        recyclerView.setAdapter(streetAdapter);
        streetAdapter.notifyDataSetChanged();

        mBuilder.setView(v2);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        recyclerView.addOnItemTouchListener(new RecyclerClickListener(Grievances_Registration.this, new RecyclerClickListener.OnItemTouchListener() {
            @Override
            public void OnItemCLikc(View view, int position) {
                griev_tie_streetname.setText(streetAdapter.getList().get(position).getStreetName());
                selected_streetId = streetAdapter.getList().get(position).getStreetNo();
                griev_tie_streetname.setTypeface(typeTamil);
                selected_StreetName = streetAdapter.getList().get(position).getStreetName();
                dialog.dismiss();
            }
        }));
    }

    public void spinnerSearch(String s) {

        ArrayList<Street_Pojo> filterdNames = new ArrayList<>();

        for (int i = 0; i < arrayListStreet.size(); i++) {
            if (arrayListStreet.get(i).getStreetName().toLowerCase().contains(s.toLowerCase())) {
                filterdNames.add(arrayListStreet.get(i));
            }

        }

        streetAdapter.filterList(filterdNames);

    }

    private void validationSuccess() {

        if (!validateDistrict()) {
            return;
        }

        if (!validatePanchayat()) {
            return;
        }

        if (!validateGrievanceType()) {
            return;
        }

        if (!validateName()) {
            return;
        }

        if (!validateEdittext_With_Textview(griev_et_grievance_desc, griev_tv_grievance_character_error, "Enter Grievance Description")) {
            return;
        }

        if (!validateEdittext(griev_et_name, griev_tie_layout_name, "Enter Name")) {
            return;
        }

        if (!validateEdittext(griev_et_doorno, griev_tie_layout_doorno, "Enter Door No")) {
            return;
        }
        if (!validateEdittext(griev_tie_wardno, griev_et_wardno_layout, "Enter Ward No")) {
            return;
        }
        if (!validateEdittext(griev_tie_streetname, griev_et_street_name_layout, "Enter Street")) {
            return;
        }
        if (!validateEdittext(griev_et_city, griev_tie_layout_city, "Enter City")) {
            return;
        }

        if (!validateMobileNo()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }


        if (Common.isNetworkAvailable(getApplicationContext())) {

            submitGrievanceValues();
            griev_btn_submit.setClickable(false);

        } else {
            Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

        }

    }

    public static void buttonEffect(View button) {

        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private boolean validateDistrict() {
        if (griev_tie_district.getText().toString().trim().isEmpty()) {
            griev_et_district_layout.setError("Please Select district ");
            griev_et_district_layout.setHintEnabled(false);
            requestFocus(griev_tie_district);
            return false;
        } else {
            griev_et_district_layout.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePanchayat() {
        if (griev_tie_panchayat.getText().toString().isEmpty()) {
            griev_et_panchayat_layout.setError("Please Select Panchayat ");
            griev_et_panchayat_layout.setHintEnabled(false);
            requestFocus(griev_tie_panchayat);
            return false;
        } else {
            griev_et_panchayat_layout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateGrievanceType() {
        if (griev_tie_grievance_type.getText().toString().isEmpty()) {
            griev_et_grievancetype_layout.setError("Please Select Grievance Type ");
            griev_et_grievancetype_layout.setHintEnabled(false);
            requestFocus(griev_tie_grievance_type);
            return false;
        } else {
            griev_et_grievancetype_layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (griev_et_name.getText().toString().isEmpty()) {
            griev_tie_layout_name.setError("Please Enter Name ");
            griev_tie_layout_name.setHintEnabled(false);
            requestFocus(griev_et_name);
            return false;
        } else {
            griev_tie_layout_name.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateEmail() {
        String email = griev_et_email_id.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            griev_tie_layout_email_id.setError("Invalid Email Id");
            griev_tie_layout_email_id.setHintEnabled(false);
            requestFocus(griev_et_email_id);
            return false;
        } else {
            griev_tie_layout_email_id.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateEdittext(EditText et, TextInputLayout layout, String errorMessage) {
        if (et.getText().toString().trim().isEmpty()) {
            layout.setError(errorMessage);
            layout.setHintEnabled(false);
            requestFocus(et);
            return false;
        } else {
            layout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEdittext_With_Textview(EditText et, TextView tvError, String errorMessage) {
        if (et.getText().toString().trim().isEmpty()) {
            tvError.setText(errorMessage);
            tvError.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else {
            tvError.setText("");
        }
        return true;
    }

    public boolean validateMobileNo()

    {
        String s = griev_et_mobileno.getText().toString().trim();

        String reg = "^(\\d)(?!\\1+$)\\d{10}$";

        String regex = "^[5-9]\\d{9}$";

        if (!s.matches(regex) && !s.matches(reg)) {

            griev_tie_layout_mobileno.setError("Invalid Mobile Number");
            griev_tie_layout_mobileno.setHintEnabled(false);
            requestFocus(griev_et_mobileno);

            return false;
        } else {
            griev_tie_layout_mobileno.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {

        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }


    public void submitGrievance(View v) {
        buttonEffect(griev_btn_submit);
        if (Common.isNetworkAvailable(getApplicationContext())) {

            if (griev_btn_submit.isClickable()) {
                buttonEffect(griev_btn_submit);
                validationSuccess();
            }

        } else {
            Snackbar.make(rl_root, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

        }

    }


    private class cTextWatcher implements TextWatcher {

        private View view;

        private cTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.griev_tie_district:
                    validateDistrict();
                    break;
                case R.id.griev_tie_panchayat:
                    validatePanchayat();
                    break;
                case R.id.griev_tie_grievance_type:
                    validateGrievanceType();
                    break;
                case R.id.griev_et_name:
                    validateName();
                    break;
                case R.id.griev_et_doorno:
                    validateEdittext(griev_et_doorno, griev_tie_layout_doorno, "Enter Door No");
                    break;
                case R.id.griev_tie_wardno:
                    validateEdittext(griev_tie_wardno, griev_et_wardno_layout, "Enter Ward No");
                    break;
                case R.id.griev_tie_streetname:
                    validateEdittext(griev_tie_streetname, griev_et_street_name_layout, "Enter Street Name");
                    break;
                case R.id.griev_et_city:
                    validateEdittext(griev_et_city, griev_tie_layout_city, "Enter City");

                    break;
                case R.id.griev_et_mobileno:
                    validateMobileNo();
                    break;
                case R.id.griev_et_email_id:
                    validateEmail();
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Birth_Death;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.BuildConfig;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.BirthCertificateSearch_Adapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.DeathCertificateSearch_Adapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.DateSelect;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthCertificateSearch_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.District_Pojo;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BirthCertificateSearch_Activity extends AppCompatActivity implements BirthCertificateSearch_Adapter.callback, DeathCertificateSearch_Adapter.callback {

    //views
    TextInputEditText bcs_et_district, bcs_et_panchayat;

    TextView bcs_tv_rv_title;

    EditText bcs_et_dob;

    TextInputLayout bcs_et_panchayat_layout, bcs_et_district_layout, bcs_et_dob_layout;

    RecyclerView bcs_rv;

    RadioGroup bcs_rg_certificate_type, bcs_rg_gender;

    Toolbar mToolbar;

    Button bcs_btn_generate;

    //arraylist
    ArrayList<District_Pojo> arrayListDistrict;
    ArrayList<String> sArrayList_DistrictNames;
    ArrayList<BirthCertificateSearch_Pojo> arrayListValues;

    //spinner
    ProgressDialog pd;
    SpinnerDialog spinnerDialog;

    //string
    String errorType;
    String TAG = "BirthCertificateSearch--> ";
    String selected_DistrictName, selected_PanchayatName, selected_RbGender, selected_RbCertificateType, selectedDivNo, selectedCertificateType_Raw;
    String sDate;
    String sPreferenceType;

    //int
    int selected_DistrictId, selected_PanchayatId;
    int selectedRegYear, selectedRegNo;


    Intent intent;
    //adapter
    BirthCertificateSearch_Adapter adapter;
    DeathCertificateSearch_Adapter death_Adapter;

    //date
    DateSelect dateObject;
              RelativeLayout rootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_certificate_search);
        mToolbar = findViewById(R.id.bcs_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //toolbar


        intent = getIntent();


        if (intent != null) {

            sPreferenceType = intent.getStringExtra("Tax_Type");

        }

        //arraylists
        arrayListDistrict = new ArrayList<>();
        sArrayList_DistrictNames = new ArrayList<>();
        arrayListValues = new ArrayList<>();

        //editexts
        bcs_et_district = findViewById(R.id.bcs_et_district);
        bcs_et_panchayat = findViewById(R.id.bcs_et_panchayat);
        bcs_et_dob = findViewById(R.id.bcs_et_dob);

        //edittext layout
        bcs_et_district_layout = findViewById(R.id.bcs_et_district_layout);
        bcs_et_panchayat_layout = findViewById(R.id.bcs_et_panchayat_layout);
        bcs_et_dob_layout = findViewById(R.id.bcs_et_dob_layout);

        //radio button
        bcs_rg_gender = findViewById(R.id.bcs_rg_gender);
        bcs_rg_certificate_type = findViewById(R.id.bcs_rg_certificate_type);

        //button
        bcs_btn_generate = findViewById(R.id.bcs_btn_generate);


        //recyclerview
        bcs_rv = findViewById(R.id.bcs_rv_birth_details);

        //textview
        bcs_tv_rv_title = findViewById(R.id.bcs_tv_rv_title);


        dateObject = new DateSelect(BirthCertificateSearch_Activity.this);


        if (sPreferenceType.equalsIgnoreCase("Birth")) {
            mToolbar.setTitle("Birth Certificate Search");
            bcs_et_dob_layout.setHint("Date of Birth");
            getSupportActionBar().setTitle("Birth Certificate Search");

        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
            mToolbar.setTitle("Death Certificate Search");
            bcs_et_dob_layout.setHint("Date of Death");
            getSupportActionBar().setTitle("Death Certificate Search");

        }


        onClicks();

    }


    public void onClicks() {


        //------
        if (bcs_rv != null) {
            bcs_rv.setHasFixedSize(true);

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        bcs_rv.setLayoutManager(linearLayoutManager);

        //-------




        bcs_et_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    getDistricts();
                    bcs_et_panchayat.setText("");
                } else {
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Check your Internet Connection !", Toast.LENGTH_SHORT).show();

                    Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();


                }
            }
        });


        bcs_et_panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {

                    if (validateEdittext(bcs_et_district, bcs_et_district_layout, "Please Select District")) {

                        getPanchayat();

                    } else {
                        Toast.makeText(BirthCertificateSearch_Activity.this, "Please Select District !", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Check your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bcs_et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateObject.getDate(bcs_et_dob);
            }
        });


        bcs_rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);

                if (null != rb) {

                    if (rb.getText().toString().equalsIgnoreCase("Male")) {
                        selected_RbGender = "M";
                    } else if (rb.getText().toString().equalsIgnoreCase("Female")) {
                        selected_RbGender = "F";
                    }
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "" + selected_RbGender);
                    }
                }

            }
        });

        bcs_rg_certificate_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);

                if (null != rb) {

                    if (rb.getText().toString().equalsIgnoreCase("English")) {

                        selectedCertificateType_Raw = "English";

                        if (sPreferenceType.equalsIgnoreCase("Birth")) {
                            selected_RbCertificateType = "BirthEnglish";
                        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                            selected_RbCertificateType = "DeathEnglish";
                        }


                    } else if (rb.getText().toString().equalsIgnoreCase("Tamil")) {

                        selectedCertificateType_Raw = "Tamil";

                        if (sPreferenceType.equalsIgnoreCase("Birth")) {
                            selected_RbCertificateType = "BirthTamil";
                        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                            selected_RbCertificateType = "DeathTamil";
                        }
                    }

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "" + selected_RbCertificateType);
                    }
                }
            }
        });


        bcs_btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {
                    validationSuccess();
                } else {
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Check your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getDistricts() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(BirthCertificateSearch_Activity.this);
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

                            Toast.makeText(BirthCertificateSearch_Activity.this, "No data found !", Toast.LENGTH_SHORT).show();

                            bcs_tv_rv_title.setVisibility(View.GONE);
                            bcs_rv.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                bcs_tv_rv_title.setVisibility(View.GONE);
                bcs_rv.setVisibility(View.GONE);
                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Please Check network Connection", Toast.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void getPanchayat() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(BirthCertificateSearch_Activity.this);
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

                            Toast.makeText(BirthCertificateSearch_Activity.this, "No data found !", Toast.LENGTH_SHORT).show();
                            bcs_tv_rv_title.setVisibility(View.GONE);
                            bcs_rv.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(BirthCertificateSearch_Activity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                bcs_tv_rv_title.setVisibility(View.GONE);
                bcs_rv.setVisibility(View.GONE);
                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Please Check network Connection", Toast.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void getBirthCertificateSearchDatas() {

        if (!bcs_et_dob.getText().toString().isEmpty()) {
            sDate = parseDate(bcs_et_dob.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");
        }

        arrayListValues.clear();

        pd = new ProgressDialog(BirthCertificateSearch_Activity.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getBirthCertificateSearchDetails(Common.ACCESS_TOKEN, selected_RbCertificateType, sDate, selected_RbGender, selected_DistrictName, selected_PanchayatName);

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

                                                if (sPreferenceType.equalsIgnoreCase("Birth")) {

                                                    int Sno = jsonObject1.optInt("Sno");
                                                    String DivNo = jsonObject1.optString("DivNo");
                                                    int RegNo = jsonObject1.optInt("RegNo");
                                                    int RegYear = jsonObject1.optInt("RegYear");
                                                    String RegDate = jsonObject1.optString("RegDate");
                                                    String NameRegDate = jsonObject1.optString("NameRegDate");
                                                    String ChildName = jsonObject1.optString("ChildName");
                                                    String DateOfBirth = jsonObject1.optString("DateOfBirth");
                                                    String Sex = jsonObject1.optString("Sex");
                                                    String FatherName = jsonObject1.optString("FatherName");
                                                    String MotherName = jsonObject1.optString("MotherName");
                                                    String BirthType = jsonObject1.optString("BirthType");

                                                    arrayListValues.add(new BirthCertificateSearch_Pojo(Sno, RegNo,
                                                            RegYear, DivNo, RegDate, NameRegDate, ChildName, DateOfBirth,
                                                            Sex, FatherName, MotherName, BirthType));

                                                } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                                                    int Sno = jsonObject1.optInt("Sno");
                                                    String DivNo = jsonObject1.optString("DivNo");
                                                    int RegNo = jsonObject1.optInt("RegNo");
                                                    int RegYear = jsonObject1.optInt("RegYear");
                                                    String RegDate = jsonObject1.optString("RegDate");
                                                    String Name = jsonObject1.optString("Name");
                                                    String DeathDate = jsonObject1.optString("DeathDate");
                                                    String Sex = jsonObject1.optString("Sex");
                                                    String Age = jsonObject1.optString("Age");
                                                    String FatherHusbandName = jsonObject1.optString("FatherHusbandName");
                                                    String PlaceOfDeath = jsonObject1.optString("PlaceOfDeath");
                                                    String Address = jsonObject1.optString("Address");

                                                    arrayListValues.add(new BirthCertificateSearch_Pojo(Sno, RegNo,
                                                            RegYear, DivNo, RegDate, "", Name, DeathDate,
                                                            Sex, FatherHusbandName, "", Address));

                                                }

                                            }

                                            if (sPreferenceType.equalsIgnoreCase("Birth")) {
                                                addAdapter(arrayListValues);
                                            } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                                                addDeathAdapter(arrayListValues);
                                            }


                                            if (sPreferenceType.equalsIgnoreCase("Birth")) {
                                                bcs_tv_rv_title.setText("Birth Details");
                                            } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                                                bcs_tv_rv_title.setText("Death Details");
                                            }
                                            bcs_tv_rv_title.setVisibility(View.VISIBLE);
                                            bcs_rv.setVisibility(View.VISIBLE);

                                        } else {
                                            Toast.makeText(BirthCertificateSearch_Activity.this, "No data found !", Toast.LENGTH_SHORT).show();
                                            bcs_tv_rv_title.setVisibility(View.GONE);
                                            bcs_rv.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(BirthCertificateSearch_Activity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                                bcs_tv_rv_title.setVisibility(View.GONE);
                                bcs_rv.setVisibility(View.GONE);
                            }


                        } else {
                            Toast.makeText(BirthCertificateSearch_Activity.this, "No data found !", Toast.LENGTH_SHORT).show();
                            bcs_tv_rv_title.setVisibility(View.GONE);
                            bcs_rv.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                bcs_tv_rv_title.setVisibility(View.GONE);
                bcs_rv.setVisibility(View.GONE);
                pd.dismiss();

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Toast.makeText(BirthCertificateSearch_Activity.this, "Please Check network Connection", Toast.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Toast.makeText(BirthCertificateSearch_Activity.this, errorType, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addAdapter(ArrayList<BirthCertificateSearch_Pojo> beanlist) {
        adapter = new BirthCertificateSearch_Adapter(beanlist, BirthCertificateSearch_Activity.this, BirthCertificateSearch_Activity.this);
        bcs_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        bcs_rv.setNestedScrollingEnabled(false);

    }

    public void addDeathAdapter(ArrayList<BirthCertificateSearch_Pojo> beanlist) {
        death_Adapter = new DeathCertificateSearch_Adapter(beanlist, BirthCertificateSearch_Activity.this, BirthCertificateSearch_Activity.this);
        bcs_rv.setAdapter(death_Adapter);
        death_Adapter.notifyDataSetChanged();
        bcs_rv.setNestedScrollingEnabled(false);

    }


    private void validationSuccess() {

        if (!validateDistrict()) {
            return;
        }

        if (!validatePanchayat()) {
            return;
        }

        if (!validateDOB()) {
            return;
        }

        if (!validateGenderCheckBox()) {
            return;
        }

        if (!validateCertificateType_CheckBox()) {
            return;
        }


        if (Common.isNetworkAvailable(getApplicationContext())) {

            getBirthCertificateSearchDatas();

        } else {
            Toast.makeText(BirthCertificateSearch_Activity.this, "Check your Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean validateDistrict() {
        if (bcs_et_district.getText().toString().trim().isEmpty()) {
            bcs_et_district_layout.setError("Please Select district ");
            bcs_et_district_layout.setHintEnabled(false);
            requestFocus(bcs_et_district);
            return false;
        } else {
            bcs_et_district_layout.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePanchayat() {
        if (bcs_et_panchayat.getText().toString().isEmpty()) {
            bcs_et_panchayat_layout.setError("Please Select Panchayat ");
            bcs_et_panchayat_layout.setHintEnabled(false);
            requestFocus(bcs_et_panchayat);
            return false;
        } else {
            bcs_et_panchayat_layout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDOB() {
        if (bcs_et_dob.getText().toString().isEmpty()) {
            bcs_et_dob_layout.setError("Please Enter DOB ");
            bcs_et_dob_layout.setHintEnabled(false);
            requestFocus(bcs_et_dob);
            return false;
        } else {
            bcs_et_dob_layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateGenderCheckBox() {
        if (bcs_rg_gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateCertificateType_CheckBox() {
        if (bcs_rg_certificate_type.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Certificate Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void setSpinner(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(BirthCertificateSearch_Activity.this, arrayList, "Select District", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                bcs_et_district.setText(arrayListDistrict.get(i).getdName());

                selected_DistrictId = arrayListDistrict.get(i).getdID();
                selected_DistrictName = arrayListDistrict.get(i).getdName();
                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_DistrictId);
                }
            }
        });
    }


    public void setSpinnerForPanchayat(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(BirthCertificateSearch_Activity.this, arrayList, "Select Panchayat", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                bcs_et_panchayat.setText(arrayListDistrict.get(i).getdName());

                selected_PanchayatId = arrayListDistrict.get(i).getdID();
                selected_PanchayatName = arrayListDistrict.get(i).getdName();
                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_PanchayatId);
                }
            }
        });
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


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Override
    public void downloadSelected_FromAdapter(String divNo, int regYear, int regNo) {

        selectedDivNo = divNo;
        selectedRegYear = regYear;
        selectedRegNo = regNo;

        String sURL = "http://www.etownpanchayat.com/PublicServices/WebView/LandingPage.aspx?RType=Birth&qDivNo=" + selectedDivNo + "&qRegYear=" + selectedRegYear + "&qRegNo=" + selectedRegNo
                + "&qPType=" + selectedCertificateType_Raw + "&qDistrict=" + selected_DistrictName + "&qPanchayat=" + selected_PanchayatName + "";


        openDownloadBrowser(BirthCertificateSearch_Activity.this, sURL);
    }


    @Override
    public void downloadDeathSelected_FromAdapter(String divNo, int regYear, int regNo) {

        selectedDivNo = divNo;
        selectedRegYear = regYear;
        selectedRegNo = regNo;

        String sURL = "http://www.etownpanchayat.com/PublicServices/WebView/LandingPage.aspx?RType=Death&qDivNo=" + selectedDivNo + "&qRegYear=" + selectedRegYear + "&qRegNo=" + selectedRegNo
                + "&qPType=" + selectedCertificateType_Raw + "&qDistrict=" + selected_DistrictName + "&qPanchayat=" + selected_PanchayatName + "";


        openDownloadBrowser(BirthCertificateSearch_Activity.this, sURL);
    }

    public String parseDate(String date, String givenformat, String resultformat) {

        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;

        try {
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            result = sdf1.format(sdf.parse(date));

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            sdf = null;
            sdf1 = null;
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }


    public void openDownloadBrowser(Activity activity, String url) {
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(activity.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }
        activity.startActivity(i);
    }

}

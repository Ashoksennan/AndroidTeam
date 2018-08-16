package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Birth_Death;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.BuildConfig;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.RootNavigation;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.BirthAbstract_Adapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.BirthAbstract_BirthDetailsAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.BirthAbstract_Wardwise_Adapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstractWardWise_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstract_BirthDetails_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstract_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.District_Pojo;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BirthAbstract_Activity extends AppCompatActivity implements BirthAbstract_Adapter.callback, BirthAbstract_Wardwise_Adapter.callback,
        BirthAbstract_BirthDetailsAdapter.callback {

    private static String TAG = BirthAbstract_Activity.class.getName();

    TextInputEditText ba_et_district, ba_et_panchayat;

    TextInputLayout ba_et_panchayat_layout, ba_et_district_layout;

    SpinnerDialog spinnerDialog;

    RecyclerView ba_rv, ba_birth_details_rv;

    TextView ba_tv_title_change, bcs_tv_first_Column_title;
    TextView bcs_tv_live_birth, bcs_tv_still_birth, bcs_tv_total;

    CardView ba_cd_first_title, ba_cd_first_rv, ba_cd_birthdetails_title, ba_cd_birthdetails_rv, bcs_cd_total;

    Toolbar mToolbar;

    ArrayList<District_Pojo> arrayListDistrict;
    ArrayList<String> sArrayList_DistrictNames;

    ArrayList<BirthAbstract_Pojo> arrayListValues;
    ArrayList<BirthAbstractWardWise_Pojo> arrayListWardWise;
    ArrayList<BirthAbstract_BirthDetails_Pojo> arrayListBirthDetails;


    int selected_DistrictId, selected_PanchayatId;
    String selected_DistrictName, selected_PanchayatName, selectedYear_YearWise, selectedWard_wardWise, errorType;


    BirthAbstract_Adapter adapter;
    BirthAbstract_Wardwise_Adapter wardwise_adapter;
    BirthAbstract_BirthDetailsAdapter birthdetails_adapter;

    AlertDialog dialogBirthDetails;

    ProgressDialog pd;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "TypePreference";
     LinearLayout rootlayout;
    String sPreferenceType, sType;
    Intent intent;

    // textview title for table
    TextView ba_tv_t1_col2, ba_tv_t1_col3;
    TextView ba_tv_t2_col2, ba_tv_t2_col3;
    //----


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_abstract);
        mToolbar =(Toolbar) findViewById(R.id.ba_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intent = getIntent();


        if (intent != null) {

            sPreferenceType = intent.getStringExtra("Tax_Type");

        }

        if (sPreferenceType.equalsIgnoreCase("Birth")) {
           getSupportActionBar().setTitle("Birth Abstract");

        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
            getSupportActionBar().setTitle("Death Abstract");

        }
          rootlayout=(LinearLayout)findViewById(R.id.rootlayout);

        //arraylists
        arrayListDistrict = new ArrayList<>();
        sArrayList_DistrictNames = new ArrayList<>();

        arrayListValues = new ArrayList<>();
        arrayListWardWise = new ArrayList<>();
        arrayListBirthDetails = new ArrayList<>();


        //id's
        ba_et_district = findViewById(R.id.ba_et_district);
        ba_et_panchayat = findViewById(R.id.ba_et_panchayat);

        ba_rv = findViewById(R.id.ba_rv);
        ba_birth_details_rv = findViewById(R.id.ba_birth_details_rv);

        ba_tv_title_change = findViewById(R.id.ba_tv_title_change);
        bcs_tv_first_Column_title = findViewById(R.id.bcs_tv_first_Column_title);
        bcs_tv_live_birth = findViewById(R.id.bcs_tv_live_birth);
        bcs_tv_still_birth = findViewById(R.id.bcs_tv_still_birth);
        bcs_tv_total = findViewById(R.id.bcs_tv_total);
        ba_tv_t1_col2 = findViewById(R.id.ba_tv_t1_col2);
        ba_tv_t1_col3 = findViewById(R.id.ba_tv_t1_col3);
        ba_tv_t2_col2 = findViewById(R.id.ba_tv_t2_col2);
        ba_tv_t2_col3 = findViewById(R.id.ba_tv_t2_col3);

        ba_cd_first_title = findViewById(R.id.ba_cd_first_title);
        ba_cd_first_rv = findViewById(R.id.ba_cd_first_rv);
        ba_cd_birthdetails_title = findViewById(R.id.ba_cd_birthdetails_title);
        ba_cd_birthdetails_rv = findViewById(R.id.ba_cd_birthdetails_rv);
        bcs_cd_total = findViewById(R.id.bcs_cd_total);


        ba_et_district_layout = findViewById(R.id.ba_et_district_layout);
        ba_et_panchayat_layout = findViewById(R.id.ba_et_panchayat_layout);

        //methods
        onClicks();

    }


    public void onClicks() {


        //onClicks

        ba_et_district.addTextChangedListener(new cTextWatcher(ba_et_district));
        ba_et_panchayat.addTextChangedListener(new cTextWatcher(ba_et_panchayat));

        //-----
        if (ba_rv != null) {
            ba_rv.setHasFixedSize(true);

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ba_rv.setLayoutManager(linearLayoutManager);

        //-----

        //----
        if (ba_birth_details_rv != null) {
            ba_birth_details_rv.setHasFixedSize(true);

        }
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ba_birth_details_rv.setLayoutManager(linearLayoutManager1);
        //----

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sPreferenceType.equalsIgnoreCase("Birth")) {
                    Intent ik = new Intent(BirthAbstract_Activity.this, RootNavigation.class);
                    startActivity(ik);
                } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                    Intent ik = new Intent(BirthAbstract_Activity.this, RootNavigation.class);
                    startActivity(ik);
                }
            }
        });


        ba_et_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    getDistricts();
                    ba_et_panchayat.setText("");
                } else {

                    Snackbar.make(rootlayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }
            }
        });


        ba_et_panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {
                    if (validateEdittext(ba_et_district, ba_et_district_layout, "Please Select District")) {

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                            getPanchayat();
                        } else {
                            Snackbar.make(rootlayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                        }


                    } else {

                        Snackbar.make(rootlayout, "Please Select District !", Snackbar.LENGTH_SHORT).show();


                    }

                } else {


                    Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void getDistricts() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(BirthAbstract_Activity.this);
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

                            if (sArrayList_DistrictNames.size() > 0) {
                                setSpinner(sArrayList_DistrictNames);
                            } else {
                                Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();


                              }
                        } else {
                            Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                     }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                if (t instanceof IOException) {
                    errorType = "Timeout";
               
                    Snackbar.make(rootlayout, "Please Check network Connection", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void getPanchayat() {

        arrayListDistrict.clear();
        sArrayList_DistrictNames.clear();

        pd = new ProgressDialog(BirthAbstract_Activity.this);
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


                            if (sArrayList_DistrictNames.size() > 0) {
                                setSpinnerForPanchayat(sArrayList_DistrictNames);
                            } else {
                                Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();


                            }


                        } else {
                            Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();
                   }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();
                if (t instanceof IOException) {
                    errorType = "Timeout";

                    Snackbar.make(rootlayout, "Please Check network Connection", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void setSpinner(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(BirthAbstract_Activity.this, arrayList, "Select District", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                ba_et_district.setText(arrayListDistrict.get(i).getdName());

                selected_DistrictId = arrayListDistrict.get(i).getdID();
                selected_DistrictName = arrayListDistrict.get(i).getdName();
                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_DistrictId);
                }
            }
        });
    }


    public void setSpinnerForPanchayat(ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(BirthAbstract_Activity.this, arrayList, "Select Panchayat", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                ba_et_panchayat.setText(arrayListDistrict.get(i).getdName());

                selected_PanchayatId = arrayListDistrict.get(i).getdID();
                selected_PanchayatName = arrayListDistrict.get(i).getdName();
                if (BuildConfig.DEBUG) {

                    Log.e(TAG, "" + arrayListDistrict.get(i).getdName() + " -- " + selected_PanchayatId);
                }
            }
        });
    }


    public void getYearWiseDatas() {

        if (sPreferenceType.equalsIgnoreCase("Death")) {
            sType = "DeathYear";
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            sType = "BirthYear";
        }


        arrayListValues.clear();

        pd = new ProgressDialog(BirthAbstract_Activity.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getBirthDetailsDatas(Common.ACCESS_TOKEN, sType, "", "", selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {

                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "year wise url " + response.raw().toString());
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
                                                    int RegYear = jsonObject1.getInt("RegYear");
                                                    int LiveBirth = jsonObject1.getInt("LiveBirth");
                                                    int StillBirth = jsonObject1.getInt("StillBirth");
                                                    int Total = jsonObject1.getInt("Total");
                                                    arrayListValues.add(new BirthAbstract_Pojo(RegYear, LiveBirth, StillBirth, Total));

                                                } else if (sPreferenceType.equalsIgnoreCase("Death")) {

                                                    int RegYear = jsonObject1.getInt("RegYear");
                                                    int Male = jsonObject1.getInt("Male");
                                                    int Female = jsonObject1.getInt("Female");
                                                    int Total = jsonObject1.getInt("Total");
                                                    arrayListValues.add(new BirthAbstract_Pojo(RegYear, Male, Female, Total));


                                                }


                                            }

                                            addAdapter(arrayListValues);


                                            ba_cd_first_rv.setVisibility(View.VISIBLE);
                                            ba_cd_first_title.setVisibility(View.VISIBLE);
                                            ba_tv_title_change.setVisibility(View.VISIBLE);
                                            ba_tv_title_change.setText("Year Wise");
                                            ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                                            bcs_tv_first_Column_title.setText("Year");
                                            bcs_cd_total.setVisibility(View.GONE);
                                            if (BuildConfig.DEBUG) {

                                                Log.e(TAG, "Year wise Size " + arrayListValues.size());
                                            }

                                        } else {

                                            Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();

                                            ba_cd_first_rv.setVisibility(View.GONE);
                                            ba_cd_first_title.setVisibility(View.GONE);
                                            ba_tv_title_change.setVisibility(View.GONE);
                                            ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                                            bcs_cd_total.setVisibility(View.GONE);
                                        }
                                    }
                                }

                            } else {

                                Snackbar.make(rootlayout, "Something went Wrong !", Snackbar.LENGTH_SHORT).show();

                               ba_cd_first_rv.setVisibility(View.GONE);
                                ba_cd_first_title.setVisibility(View.GONE);
                                ba_tv_title_change.setVisibility(View.GONE);
                                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                ba_cd_birthdetails_title.setVisibility(View.GONE);
                                ba_cd_birthdetails_title.setVisibility(View.GONE);
                                bcs_cd_total.setVisibility(View.GONE);
                            }

                        } else {
                           Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();

                            ba_cd_first_rv.setVisibility(View.GONE);
                            ba_cd_first_title.setVisibility(View.GONE);
                            ba_tv_title_change.setVisibility(View.GONE);
                            ba_cd_birthdetails_rv.setVisibility(View.GONE);
                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                            bcs_cd_total.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {

                        ba_cd_first_rv.setVisibility(View.GONE);
                        ba_cd_first_title.setVisibility(View.GONE);
                        ba_tv_title_change.setVisibility(View.GONE);
                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                        bcs_cd_total.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                } else {
                    Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                    ba_cd_first_rv.setVisibility(View.GONE);
                    ba_cd_first_title.setVisibility(View.GONE);
                    ba_tv_title_change.setVisibility(View.GONE);
                    ba_cd_birthdetails_rv.setVisibility(View.GONE);
                    ba_cd_birthdetails_title.setVisibility(View.GONE);
                    ba_cd_birthdetails_title.setVisibility(View.GONE);
                    bcs_cd_total.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                ba_cd_first_rv.setVisibility(View.GONE);
                ba_cd_first_title.setVisibility(View.GONE);
                ba_tv_title_change.setVisibility(View.GONE);
                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                ba_cd_birthdetails_title.setVisibility(View.GONE);
                bcs_cd_total.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    errorType = "Timeout";

                    Snackbar.make(rootlayout, "Please Check network Connection", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void getWardWiseDatas() {

        if (sPreferenceType.equalsIgnoreCase("Death")) {
            sType = "DeathWard";
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            sType = "BirthWard";
        }


        pd = new ProgressDialog(BirthAbstract_Activity.this);
        pd.setMessage("Loading..");
        pd.show();

        arrayListWardWise.clear();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getBirthDetailsDatas(Common.ACCESS_TOKEN, sType,
                selectedYear_YearWise, "", selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "year wise url " + response.raw().toString());

                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);
                        if (jsonObject.length() > 0) {

                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {


                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                    if (jsonArray1.length() > 0) {
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(j);


                                            if (sPreferenceType.equalsIgnoreCase("Birth")) {
                                                String WardNo = jsonObject1.optString("WardNo");
                                                int LiveBirth = jsonObject1.optInt("LiveBirth");
                                                int StillBirth = jsonObject1.optInt("StillBirth");
                                                int Total = jsonObject1.optInt("Total");
                                                arrayListWardWise.add(new BirthAbstractWardWise_Pojo(WardNo, LiveBirth, StillBirth, Total));

                                            } else if (sPreferenceType.equalsIgnoreCase("Death")) {

                                                String WardNo = jsonObject1.optString("WardNo");
                                                int Male = jsonObject1.optInt("Male");
                                                int Female = jsonObject1.optInt("Female");
                                                int Total = jsonObject1.optInt("Total");
                                                arrayListWardWise.add(new BirthAbstractWardWise_Pojo(WardNo, Male, Female, Total));


                                            }


                                        }

                                        addAdapterWardWise(arrayListWardWise);
                                        long sLiveBirth = getWardWise_Column_TotalLive(arrayListWardWise);
                                        long sStillBirth = getWardWise_Column_TotalStill(arrayListWardWise);
                                        long sTotalBirth = getWardWise_Column_Total(arrayListWardWise);

                                        bcs_tv_live_birth.setText(String.valueOf(sLiveBirth));
                                        bcs_tv_still_birth.setText(String.valueOf(sStillBirth));
                                        bcs_tv_total.setText(String.valueOf(sTotalBirth));


                                        ba_cd_first_rv.setVisibility(View.VISIBLE);
                                        ba_cd_first_title.setVisibility(View.VISIBLE);
                                        ba_tv_title_change.setVisibility(View.VISIBLE);
                                        ba_tv_title_change.setText("Year : " + selectedYear_YearWise + " ~ Ward Wise");
                                        bcs_tv_first_Column_title.setText("Ward No");
                                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                                        bcs_cd_total.setVisibility(View.VISIBLE);

                                    } else {

                                        Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();


                                        ba_cd_first_rv.setVisibility(View.GONE);
                                        ba_cd_first_title.setVisibility(View.GONE);
                                        ba_tv_title_change.setVisibility(View.GONE);
                                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                                        bcs_cd_total.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();

                                ba_cd_first_rv.setVisibility(View.GONE);
                                ba_cd_first_title.setVisibility(View.GONE);
                                ba_tv_title_change.setVisibility(View.GONE);
                                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                ba_cd_birthdetails_title.setVisibility(View.GONE);
                                ba_cd_birthdetails_title.setVisibility(View.GONE);
                                bcs_cd_total.setVisibility(View.GONE);
                            }

                        } else {
                            Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                            ba_cd_first_rv.setVisibility(View.GONE);
                            ba_cd_first_title.setVisibility(View.GONE);
                            ba_tv_title_change.setVisibility(View.GONE);
                            ba_cd_birthdetails_rv.setVisibility(View.GONE);
                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                            bcs_cd_total.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {

                        ba_cd_first_rv.setVisibility(View.GONE);
                        ba_cd_first_title.setVisibility(View.GONE);
                        ba_tv_title_change.setVisibility(View.GONE);
                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                        bcs_cd_total.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {

                    Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                    ba_cd_first_rv.setVisibility(View.GONE);
                    ba_cd_first_title.setVisibility(View.GONE);
                    ba_tv_title_change.setVisibility(View.GONE);
                    ba_cd_birthdetails_rv.setVisibility(View.GONE);
                    ba_cd_birthdetails_title.setVisibility(View.GONE);
                    ba_cd_birthdetails_title.setVisibility(View.GONE);
                    bcs_cd_total.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();

                ba_cd_first_rv.setVisibility(View.GONE);
                ba_cd_first_title.setVisibility(View.GONE);
                ba_tv_title_change.setVisibility(View.GONE);
                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                ba_cd_birthdetails_title.setVisibility(View.GONE);
                ba_cd_birthdetails_title.setVisibility(View.GONE);
                bcs_cd_total.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    errorType = "Timeout";

                    Snackbar.make(rootlayout, "Please Check network Connection", Snackbar.LENGTH_SHORT).show();


                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void getBirthDetailsDatas() {


        if (sPreferenceType.equalsIgnoreCase("Death")) {
            sType = "Death";
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            sType = "Birth";
        }


        arrayListBirthDetails.clear();

        pd = new ProgressDialog(BirthAbstract_Activity.this);
        pd.setMessage("Loading..");
        pd.show();

        RetrofitInterface apiInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);

        Call call = apiInterface.getBirthDetailsDatas(Common.ACCESS_TOKEN, sType,
                selectedYear_YearWise, selectedWard_wardWise, selected_DistrictName, selected_PanchayatName);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                pd.dismiss();

                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {

                        Log.e(TAG, "birth details url " + response.raw().toString());
                    }
                    String sResponse = new Gson().toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(sResponse);
                        if (jsonObject.length() > 0) {
                            if (jsonObject.has("recordsets") && !(jsonObject.isNull("recordsets"))) {

                                JSONArray jsonArray = new JSONArray(jsonObject.getString("recordsets"));

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);

                                    if (jsonArray1.length() > 0) {
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(j);
                                            if (sPreferenceType.equalsIgnoreCase("Birth")) {
                                                String DivNo = jsonObject1.optString("DivNo");
                                                int RegNo = jsonObject1.optInt("RegNo");
                                                String RegDate = jsonObject1.optString("RegDate");
                                                String ChildName = jsonObject1.optString("ChildName");
                                                String DateOfBirth = jsonObject1.optString("DateOfBirth");
                                                String Gender = jsonObject1.optString("Sex");
                                                String FatherName = jsonObject1.optString("FatherName");
                                                String MotherName = jsonObject1.optString("MotherName");
                                                String PlaceofBirth = jsonObject1.optString("PlaceofBirth");
                                                String BirthType = jsonObject1.optString("BirthType");

                                                arrayListBirthDetails.add(new BirthAbstract_BirthDetails_Pojo(DivNo, RegDate, ChildName, DateOfBirth,
                                                        Gender, FatherName, MotherName, PlaceofBirth, BirthType, RegNo));
                                            } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                                                String DivNo = jsonObject1.optString("DivNo");
                                                int RegNo = jsonObject1.optInt("RegNo");
                                                String RegDate = jsonObject1.optString("RegDate");
                                                String Name = jsonObject1.optString("Name");
                                                String DeathDate = jsonObject1.optString("DeathDate");
                                                String Gender = jsonObject1.optString("Sex");
                                                int Age = jsonObject1.optInt("Age");
                                                String FatherHusbandName = jsonObject1.optString("FatherHusbandName");
                                                String PlaceOfDeath = jsonObject1.optString("PlaceOfDeath");
                                                String Address = jsonObject1.optString("Address");

                                                arrayListBirthDetails.add(new BirthAbstract_BirthDetails_Pojo(DivNo, RegDate,
                                                        Name, DeathDate,
                                                        Gender, FatherHusbandName, String.valueOf(Age), PlaceOfDeath, Address, RegNo));

                                            }

                                        }

                                        addAdapterBirthDetails(arrayListBirthDetails);

                                        ba_cd_first_rv.setVisibility(View.GONE);
                                        ba_cd_first_title.setVisibility(View.GONE);
                                        ba_cd_birthdetails_title.setVisibility(View.VISIBLE);
                                        ba_tv_title_change.setVisibility(View.VISIBLE);
                                        ba_cd_birthdetails_rv.setVisibility(View.VISIBLE);
                                        bcs_cd_total.setVisibility(View.GONE);
                                        if (sPreferenceType.equalsIgnoreCase("Birth")) {
                                            ba_tv_title_change.setText("Year : " + selectedYear_YearWise + " ~ Ward Wise : " + selectedWard_wardWise + "~ Birth Details");
                                        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
                                            ba_tv_title_change.setText("Year : " + selectedYear_YearWise + " ~ Ward Wise : " + selectedWard_wardWise + "~ Death Details");

                                        }
                                    } else {

                                        Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();

                                        ba_cd_first_rv.setVisibility(View.GONE);
                                        ba_cd_first_title.setVisibility(View.GONE);
                                        ba_tv_title_change.setVisibility(View.GONE);
                                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                                        bcs_cd_total.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                Snackbar.make(rootlayout, "No data found !", Snackbar.LENGTH_SHORT).show();


                                ba_cd_first_rv.setVisibility(View.GONE);
                                ba_cd_first_title.setVisibility(View.GONE);
                                ba_tv_title_change.setVisibility(View.GONE);
                                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                                ba_cd_birthdetails_title.setVisibility(View.GONE);
                                bcs_cd_total.setVisibility(View.GONE);
                            }

                        } else {
                            Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                            ba_cd_first_rv.setVisibility(View.GONE);
                            ba_cd_first_title.setVisibility(View.GONE);
                            ba_tv_title_change.setVisibility(View.GONE);
                            ba_cd_birthdetails_rv.setVisibility(View.GONE);
                            ba_cd_birthdetails_title.setVisibility(View.GONE);
                            bcs_cd_total.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {

                        ba_cd_first_rv.setVisibility(View.GONE);
                        ba_cd_first_title.setVisibility(View.GONE);
                        ba_tv_title_change.setVisibility(View.GONE);
                        ba_cd_birthdetails_rv.setVisibility(View.GONE);
                        ba_cd_birthdetails_title.setVisibility(View.GONE);
                        bcs_cd_total.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {

                    Snackbar.make(rootlayout, "Something went wrong !", Snackbar.LENGTH_SHORT).show();

                    ba_cd_first_rv.setVisibility(View.GONE);
                    ba_cd_first_title.setVisibility(View.GONE);
                    ba_tv_title_change.setVisibility(View.GONE);
                    ba_cd_birthdetails_rv.setVisibility(View.GONE);
                    ba_cd_birthdetails_title.setVisibility(View.GONE);
                    bcs_cd_total.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                pd.dismiss();

                ba_cd_first_rv.setVisibility(View.GONE);
                ba_cd_first_title.setVisibility(View.GONE);
                ba_tv_title_change.setVisibility(View.GONE);
                ba_cd_birthdetails_rv.setVisibility(View.GONE);
                ba_cd_birthdetails_title.setVisibility(View.GONE);
                bcs_cd_total.setVisibility(View.GONE);

                if (t instanceof IOException) {
                    errorType = "Timeout";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                } else {
                    errorType = "Error";
                    Snackbar.make(rootlayout, errorType, Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void Submit(View v) {
        if (Common.isNetworkAvailable(getApplicationContext())) {

            if (validateEdittext(ba_et_district, ba_et_district_layout, "Please Select District")
                    && validateEdittext(ba_et_panchayat, ba_et_panchayat_layout, "Please Select Panchayat")) {

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    getYearWiseDatas();
                } else {
                     Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }
            }
        } else {
            Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();



        }

    }


    public void addAdapter(ArrayList<BirthAbstract_Pojo> beanlist) {

        if (sPreferenceType.equalsIgnoreCase("Death")) {
            ba_tv_t1_col2.setText("Male");
            ba_tv_t1_col3.setText("Female");
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            ba_tv_t1_col2.setText("Live Birth");
            ba_tv_t1_col3.setText("Still Birth");
        }

        adapter = new BirthAbstract_Adapter(beanlist, BirthAbstract_Activity.this, BirthAbstract_Activity.this);
        ba_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ba_rv.setNestedScrollingEnabled(false);

    }

    public void addAdapterWardWise(ArrayList<BirthAbstractWardWise_Pojo> beanlist) {

        if (sPreferenceType.equalsIgnoreCase("Death")) {
            ba_tv_t1_col2.setText("Male");
            ba_tv_t1_col3.setText("Female");
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            ba_tv_t1_col2.setText("Live Birth");
            ba_tv_t1_col3.setText("Still Birth");
        }

        wardwise_adapter = new BirthAbstract_Wardwise_Adapter(beanlist, BirthAbstract_Activity.this, BirthAbstract_Activity.this);
        ba_rv.setAdapter(wardwise_adapter);
        wardwise_adapter.notifyDataSetChanged();
        ba_rv.setNestedScrollingEnabled(false);

    }

    public void addAdapterBirthDetails(ArrayList<BirthAbstract_BirthDetails_Pojo> beanlist) {

        if (sPreferenceType.equalsIgnoreCase("Death")) {
            ba_tv_t2_col2.setText("Name");
            ba_tv_t2_col3.setText("DeathDate");
        } else if (sPreferenceType.equalsIgnoreCase("Birth")) {
            ba_tv_t2_col2.setText("ChildName");
            ba_tv_t2_col3.setText("DOB");
        }

        birthdetails_adapter = new BirthAbstract_BirthDetailsAdapter(beanlist, BirthAbstract_Activity.this, BirthAbstract_Activity.this);
        ba_birth_details_rv.setAdapter(birthdetails_adapter);
        birthdetails_adapter.notifyDataSetChanged();
        ba_birth_details_rv.setNestedScrollingEnabled(false);

    }

    @Override
    public void yearSelectedFromAdapter(String year) {
        selectedYear_YearWise = year;
        if (BuildConfig.DEBUG) {

            Log.e(TAG, "year " + selectedYear_YearWise);

        }
        if (Common.isNetworkAvailable(getApplicationContext())) {
            getWardWiseDatas();
        } else {

            Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();

        }

    }

    @Override
    public void wardSelectedFromAdapter(String ward) {
        selectedWard_wardWise = ward;
        if (BuildConfig.DEBUG) {

            Log.e(TAG, "ward " + selectedWard_wardWise);

        }
        if (Common.isNetworkAvailable(getApplicationContext())) {
            getBirthDetailsDatas();

        } else {
            Snackbar.make(rootlayout, "Please Check your Internet Connection !", Snackbar.LENGTH_SHORT).show();


        }
    }

    @Override
    public void birthDetailsAlert_FromAdapter(String open, int position) {
        specific_birth_details(position);
    }

    public void specific_birth_details(int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(BirthAbstract_Activity.this);
        View v2 = getLayoutInflater().inflate(R.layout.birth_details_abstract_alert, null);


        LinearLayout ba_adap_ll_name = v2.findViewById(R.id.ba_adap_ll_name);
        LinearLayout ba_adap_ll_mother_name = v2.findViewById(R.id.ba_adap_ll_mother_name);

        ImageView ba_alt_iv_cancel = v2.findViewById(R.id.ba_alt_iv_cancel);
        ImageView ba_alt_iv_gender = v2.findViewById(R.id.ba_alt_iv_gender);

        TextView ba_alt_tv_title = v2.findViewById(R.id.ba_alt_tv_title);
        TextView ba_alt_tv_name = v2.findViewById(R.id.ba_alt_tv_name);
        TextView ba_alt_tv_father_name = v2.findViewById(R.id.ba_alt_tv_father_name);
        TextView ba_alt_tv_regDate = v2.findViewById(R.id.ba_alt_tv_regDate);
        TextView ba_alt_tv_mother_name = v2.findViewById(R.id.ba_alt_tv_mother_name);
        TextView ba_alt_tv_dob = v2.findViewById(R.id.ba_alt_tv_dob);
        TextView ba_alt_tv_birthType = v2.findViewById(R.id.ba_alt_tv_birthType);
        TextView ba_alt_tv_placeOfBirth = v2.findViewById(R.id.ba_alt_tv_placeOfBirth);
        TextView ba_alt_tv_gender = v2.findViewById(R.id.ba_alt_tv_gender);
        TextView ba_alt_tv_regNo = v2.findViewById(R.id.ba_alt_tv_regNo);
        TextView ba_adap_tv_title_name = v2.findViewById(R.id.ba_adap_tv_title_name);
        TextView ba_adap_tv_title_last = v2.findViewById(R.id.ba_adap_tv_title_last);
        TextView ba_adap_tv_title_dateof = v2.findViewById(R.id.ba_adap_tv_title_dateof);
        TextView ba_adap_tv_title_placeof = v2.findViewById(R.id.ba_adap_tv_title_placeof);


        if (sPreferenceType.equalsIgnoreCase("Birth")) {

            ba_alt_tv_title.setText("Year : " + selectedYear_YearWise + " ~ Ward No : " + selectedWard_wardWise + " ~ Birth Details");

            ba_adap_ll_name.setVisibility(View.VISIBLE);
            ba_adap_ll_mother_name.setVisibility(View.VISIBLE);
            ba_adap_tv_title_name.setText("Father Name");
            ba_adap_tv_title_last.setText("Birth Type");
            ba_adap_tv_title_placeof.setText("Place Of Birth");
            ba_adap_tv_title_dateof.setText("Date Of Birth");

            ba_alt_tv_name.setText(arrayListBirthDetails.get(position).getChildName());
            ba_alt_tv_father_name.setText(arrayListBirthDetails.get(position).getFatherName());
            ba_alt_tv_regDate.setText(arrayListBirthDetails.get(position).getRegDate());
            ba_alt_tv_mother_name.setText(arrayListBirthDetails.get(position).getMotherName());
            ba_alt_tv_dob.setText(arrayListBirthDetails.get(position).getDateOfBirth());
            ba_alt_tv_birthType.setText(arrayListBirthDetails.get(position).getBirthType());
            ba_alt_tv_placeOfBirth.setText(arrayListBirthDetails.get(position).getPlaceofBirth());

            if (arrayListBirthDetails.get(position).getGender().equalsIgnoreCase("M")) {
                ba_alt_tv_gender.setText("Male");
            } else if (arrayListBirthDetails.get(position).getGender().equalsIgnoreCase("F")) {
                ba_alt_tv_gender.setText("Female");
            }

            ba_alt_tv_regNo.setText(String.valueOf(arrayListBirthDetails.get(position).getRegNo()));


        } else if (sPreferenceType.equalsIgnoreCase("Death")) {
            ba_alt_tv_title.setText("Year : " + selectedYear_YearWise + " ~ Ward No : " + selectedWard_wardWise + " ~ Death Details");

            ba_adap_ll_name.setVisibility(View.VISIBLE);
            ba_adap_ll_mother_name.setVisibility(View.GONE);
            ba_adap_tv_title_name.setText("Father / Husband Name");
            ba_adap_tv_title_last.setText("Address");
            ba_adap_tv_title_placeof.setText("Place Of Death");
            ba_adap_tv_title_dateof.setText("Date Of Death");

            ba_alt_tv_father_name.setText(arrayListBirthDetails.get(position).getFatherName());
            ba_alt_tv_name.setText(arrayListBirthDetails.get(position).getChildName());
            ba_alt_tv_regNo.setText(String.valueOf(arrayListBirthDetails.get(position).getRegNo()));
            ba_alt_tv_regDate.setText(arrayListBirthDetails.get(position).getRegDate());
            ba_alt_tv_placeOfBirth.setText(arrayListBirthDetails.get(position).getPlaceofBirth());
            ba_alt_tv_dob.setText(arrayListBirthDetails.get(position).getDateOfBirth());
            ba_alt_tv_birthType.setText(arrayListBirthDetails.get(position).getBirthType());
            ba_alt_tv_gender.setText(arrayListBirthDetails.get(position).getGender());
        }

        if (arrayListBirthDetails.get(position).getGender().equalsIgnoreCase("M") || arrayListBirthDetails.get(position).getGender().equalsIgnoreCase("Male")) {
            ba_alt_iv_gender.setImageResource(R.drawable.ic_male);
        } else {
            ba_alt_iv_gender.setImageResource(R.drawable.ic_female);

        }

        ba_alt_iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBirthDetails.dismiss();
            }
        });
        mBuilder.setView(v2);
        dialogBirthDetails = mBuilder.create();
        dialogBirthDetails.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialogBirthDetails.show();

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


    public long getWardWise_Column_TotalLive(ArrayList<BirthAbstractWardWise_Pojo> arrayList) {
        long result = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            result += arrayList.get(i).getLiveBirth();

        }

        return result;
    }

    public long getWardWise_Column_TotalStill(ArrayList<BirthAbstractWardWise_Pojo> arrayList) {
        long result = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            result += arrayList.get(i).getStillBirth();

        }

        return result;
    }

    public long getWardWise_Column_Total(ArrayList<BirthAbstractWardWise_Pojo> arrayList) {
        long result = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            result += arrayList.get(i).getTotal();

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
                case R.id.ba_et_district:
                    validateEdittext(ba_et_district, ba_et_district_layout, "Please Select District");
                    break;
                case R.id.ba_et_panchayat:
                    validateEdittext(ba_et_panchayat, ba_et_panchayat_layout, "Please Select Panchayat");
                    break;

            }
        }
    }


}

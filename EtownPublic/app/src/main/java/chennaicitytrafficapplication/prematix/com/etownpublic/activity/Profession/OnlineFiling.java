package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Profession;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Birth_Death.Districts;
import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Organisations;
import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Panchayats;
import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax.OnlineFilingDemandEntity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax.OnlineFilingDemandAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax.OrganisationAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.listener.RecyclerClickListener;
import dmax.dialog.SpotsDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_DISTRICT_DETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_FinancialDates;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_GET_PROFESSION_DEMAND;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_ONLINE_FILING_SAVE;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_ORGANISATIONS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TOWNPANCHAYAT;


public class OnlineFiling extends AppCompatActivity {

    //Views
    EditText etDistrict,etPanchayat,etFinancialYear,etName,etMobileNo,etEmailId,etTaxNo,etOrgName;

    TextView tvTaxNo,tvName,tvDoorNo,tvWardNo,tvOrgName,tvCurrentYear,tvAmount;

    LinearLayout llOrg,llAssessmentDetails,llAssessmentRadio,llYear;

    RelativeLayout rootLayout;

    RecyclerView recyclerViewDemand;

    CheckBox checkAll;

    Button buttonSubmit,buttonSave;

    //Arraylist
    ArrayList<String> distict_items=new ArrayList<>();
    ArrayList<String> panchayat_items=new ArrayList<>();
    ArrayList<String> finYear_items=new ArrayList<>();
    ArrayList<OnlineFilingDemandEntity> onlineFilingDemandEntities=new ArrayList<>();
    ArrayList<chennaicitytrafficapplication.prematix.com.etownpublic.Model.Birth_Death.Districts> districts=new ArrayList<>();
    ArrayList<Panchayats> panchayats=new ArrayList<>();
    ArrayList<Organisations> organisations=new ArrayList<>();

    //Strings
    String districtName = "",panchayatName="",name="",organisationName="",saveType="",tradeName="",type = "",
            selected_years="",selected_balances="",selected_sNos="",selectedTaxNos="";

    String TAG = "OnlineFiling--> ";

    //Integers
    int districtId,panchayatId,orgCode,p_amount;

    //Spinners
    SpinnerDialog spinnerDialogDistrict;
    SpinnerDialog spinnerDialogPanchayat;
    SpinnerDialog spinnerDialogFinYear;
    SpotsDialog progressDialog;

    //Adapters
    OrganisationAdapter organisationAdapter;
    OnlineFilingDemandAdapter onlineFilingDemandAdapter;

    //Toolbar
    Toolbar mtoolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_filing);

        //Toolbar
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.profession_online_filing);

        //EditTexts
        etDistrict = (EditText)findViewById(R.id.et_district);
        etPanchayat = (EditText)findViewById(R.id.et_panchayat);
        etFinancialYear = (EditText)findViewById(R.id.et_financialyear);
        etName = (EditText)findViewById(R.id.et_name);
        etMobileNo = (EditText)findViewById(R.id.et_mobile_no);
        etEmailId = (EditText)findViewById(R.id.et_email_id);
        etTaxNo = (EditText)findViewById(R.id.et_taxno);
        etOrgName = (EditText)findViewById(R.id.et_orgname);

        //TextViews
        tvTaxNo = (TextView) findViewById(R.id.tv_taxno);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDoorNo = (TextView) findViewById(R.id.tv_doornoo);
        tvWardNo = (TextView) findViewById(R.id.tv_wardno);
        tvOrgName = (TextView) findViewById(R.id.tv_orgname);
        tvCurrentYear = (TextView) findViewById(R.id.tv_current_year);
        tvAmount = (TextView) findViewById(R.id.tv_amount);

        //Buttons
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSave = (Button) findViewById(R.id.button_save);

        //LinearLayouts
        llOrg = (LinearLayout) findViewById(R.id.ll_org);
        llAssessmentDetails = (LinearLayout) findViewById(R.id.ll_assesmentdetails);
        llAssessmentRadio = (LinearLayout) findViewById(R.id.assesstype_radio);
        llYear = (LinearLayout) findViewById(R.id.ll_year);

        //RecyclerView
        recyclerViewDemand = (RecyclerView) findViewById(R.id.rv_demand);

        //CheckBox
        checkAll = (CheckBox)findViewById(R.id.check_all);

        //RelativeLayout
        rootLayout = (RelativeLayout)findViewById(R.id.root_layout);

        if (recyclerViewDemand != null) {
            recyclerViewDemand.setHasFixedSize(true);
        }
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(OnlineFiling.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDemand.setLayoutManager(linearLayoutManager1);




        if (Common.isNetworkAvailable(getApplicationContext())) {
            getFinyear();

        } else {

            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
        }

//        finYear_items.add("2018-2019");
//        finYear_items.add("2017-2018");

        spinnerDialogPanchayat=new SpinnerDialog(OnlineFiling.this,panchayat_items,"Select or Search Panchayat","Close");// With No Animation
      

        spinnerDialogDistrict=new SpinnerDialog(OnlineFiling.this,distict_items,"Select or Search District","Close");// With No Animation

        spinnerDialogFinYear=new SpinnerDialog(OnlineFiling.this,finYear_items,"Select or Search FinYear","Close");// With No Animation

        spinnerDialogDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                etDistrict.setText(item);
                districtName = item;
                districtId = districts.get(position).getmDistrictId();

                etPanchayat.setText("");
                etOrgName.setText("");

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    panchayatList(districtId);

                } else {

                    Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                }


            }
        });

        etDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogDistrict.showSpinerDialog();
                panchayat_items.clear();
                organisations.clear();

                llOrg.setVisibility(View.GONE);
                etTaxNo.setVisibility(View.GONE);
                buttonSubmit.setVisibility(View.GONE);

            }
        });

        spinnerDialogPanchayat.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int position) {
                etPanchayat.setText(s);

                panchayatName = s;
                panchayatId = panchayats.get(position).getPanchayatId();

                if (Common.isNetworkAvailable(getApplicationContext())) {
                    organisationList(districtName,panchayatName);

                } else {

                    Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        etPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDistrict.getText().toString().isEmpty()){
                    spinnerDialogPanchayat.showSpinerDialog();

                }else{
                    Snackbar.make(rootLayout, "Select District First", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        etOrgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty()){
//                    spinnerDialogOrganisations.showSpinerDialog();

                    if (organisations.size()>0){

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                            setOrganisations();

                        } else {

                            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                        }
                    }else{

                        Snackbar.make(rootLayout, "No Organisations Found", Snackbar.LENGTH_SHORT).show();
                    }

                }else{

                    Snackbar.make(rootLayout, "Select District and Panchayat", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        spinnerDialogFinYear.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int position) {
                etFinancialYear.setText(s);

            }
        });

        etFinancialYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty()){
                    spinnerDialogFinYear.showSpinerDialog();

                }else{

                    Snackbar.make(rootLayout, "Select District and Panchayat First", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        etTaxNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDistrict.getText().toString().isEmpty() && etPanchayat.getText().toString().isEmpty()){

                    Snackbar.make(rootLayout, "Select District and Panchayat", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equalsIgnoreCase("Trade")){

                    if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty() &&
                            !etTaxNo.getText().toString().isEmpty() && !etFinancialYear.getText().toString().isEmpty()){

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                            getTrade(districtName,panchayatName,etFinancialYear.getText().toString(),type,etTaxNo.getText().toString());

                        } else {

                            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                        }
                    }else {

                        Snackbar.make(rootLayout, "Enter all Values", Snackbar.LENGTH_SHORT).show();
                    }
                }else if (type.equalsIgnoreCase("Individual")){

                    if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty() &&
                            !etTaxNo.getText().toString().isEmpty() && !etFinancialYear.getText().toString().isEmpty()){

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                            getOrgIndividual(districtName,panchayatName,etFinancialYear.getText().toString(),type,
                                    etTaxNo.getText().toString(),orgCode);
                        } else {

                            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                        }

                    }else {

                        Snackbar.make(rootLayout, "Enter all Values", Snackbar.LENGTH_SHORT).show();
                    }
                }else if (type.equalsIgnoreCase("Group")){

                    if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty() &&
                            !etFinancialYear.getText().toString().isEmpty()){

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                            getOrgGroup(districtName,panchayatName,etFinancialYear.getText().toString(),type,orgCode);

                        } else {

                            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                        }

                    }else {

                        Snackbar.make(rootLayout, "Enter all Values", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etName.getText().toString().isEmpty() && !etMobileNo.getText().toString().isEmpty() &&
                        !etEmailId.getText().toString().isEmpty()){

                    if (Common.isNetworkAvailable(getApplicationContext())) {
                        savingOnlineFiling(etName.getText().toString(),etMobileNo.getText().toString(),
                                etEmailId.getText().toString(),organisationName,saveType,selected_sNos,
                                selectedTaxNos,districtName,panchayatName,etFinancialYear.getText().toString(),
                                String.valueOf(orgCode),tradeName);

                    } else {

                        Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                    }

                }else{
                    Snackbar.make(rootLayout, "Enter all Values", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void getFinyear() {
        String REQUEST_TAG = "apigetFinYear";

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();
        progressDialog.setCancelable(false);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, API_FinancialDates, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                        String FinYear = jsonObject.getString("FinYear");

                                        finYear_items.add(FinYear);
                                    }

                                }

                            }
                            etFinancialYear.setText(finYear_items.get(0));

                        }
                        districtList();
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest, REQUEST_TAG);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_trade:
                if (checked)

                    type = "Trade";
                    saveType = "Trade";
                llOrg.setVisibility(View.GONE);
                etTaxNo.setVisibility(View.VISIBLE);
                buttonSubmit.setVisibility(View.VISIBLE);
                etOrgName.setText("");
                llAssessmentRadio.setVisibility(View.GONE);
                llAssessmentDetails.setVisibility(View.GONE);
                break;

            case R.id.radio_org:
                if (checked)

                    type ="Organisation";
                    saveType = "Organisation";
                llOrg.setVisibility(View.VISIBLE);
                etTaxNo.setText("");
                etTaxNo.setVisibility(View.GONE);
                buttonSubmit.setVisibility(View.VISIBLE);
                llAssessmentDetails.setVisibility(View.GONE);

                break;

            case R.id.radio_firsthalf:
                if (checked)

                break;

            case R.id.radio_secondhalf:
                if (checked)

                break;

            case R.id.radio_individual:
                if (checked)

                    type="Individual";

                llOrg.setVisibility(View.VISIBLE);
                etTaxNo.setVisibility(View.VISIBLE);
                buttonSubmit.setVisibility(View.VISIBLE);

                break;

            case R.id.radio_group:
                if (checked)

                    type="Group";

                llOrg.setVisibility(View.VISIBLE);
                etTaxNo.setVisibility(View.GONE);
                buttonSubmit.setVisibility(View.VISIBLE);

                break;
        }
    }

    public void districtList(){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url =  API_DISTRICT_DETAILS;

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String districtName = jsonObject.getString("DistrictName");
                                int districtId = jsonObject.getInt("DistrictId");

                                distict_items.add(districtName);

                                districts.add(new Districts(districtId,districtName));
                                progressDialog.dismiss();

                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            SnackShowTop("Time out", rootLayout);
                        } else if (error instanceof AuthFailureError) {
                            SnackShowTop("Connection Time out", rootLayout);
                        } else if (error instanceof ServerError) {
                            SnackShowTop("Could not connect server", rootLayout);
                        } else if (error instanceof NetworkError) {
                            SnackShowTop("Please check the internet connection", rootLayout);
                        } else if (error instanceof ParseError) {
                            SnackShowTop("Parse Error", rootLayout);
                        } else {
                            SnackShowTop(error.getMessage(), rootLayout);
                        }
                    }
                }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
    }

    public void panchayatList(int districtId){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url =  API_TOWNPANCHAYAT+districtId;

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String panchayatName = jsonObject.getString("PanchayatName");
                                int panchayatId = jsonObject.getInt("PanchayatId");

                                panchayat_items.add(panchayatName);

                                panchayats.add(new Panchayats(panchayatName,panchayatId));
                                progressDialog.dismiss();

                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            SnackShowTop("Time out", rootLayout);
                        } else if (error instanceof AuthFailureError) {
                            SnackShowTop("Connection Time out", rootLayout);
                        } else if (error instanceof ServerError) {
                            SnackShowTop("Could not connect server", rootLayout);
                        } else if (error instanceof NetworkError) {
                            SnackShowTop("Please check the internet connection", rootLayout);
                        } else if (error instanceof ParseError) {
                            SnackShowTop("Parse Error", rootLayout);
                        } else {
                            SnackShowTop(error.getMessage(), rootLayout);
                        }
                    }
                }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
    }

    public void organisationList(String districtName, String panchayatName){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url =  API_ORGANISATIONS+districtName+"&Panchayat="+panchayatName;

        Log.e(TAG,"---"+url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG,"----"+response.toString());
                        try {
                            JSONObject recoredSet = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recoredSet.getString("recordsets"));

                            if (firstarray.length() > 0) {

                            for (int i = 0; i < firstarray.length(); i++) {

                                JSONArray secondArray = firstarray.getJSONArray(0);

                                Log.e(TAG, "----" + secondArray.toString());

                                if (secondArray.length() > 0) {

                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);

                                        int organizationCode = jsonObject.getInt("OrganizationCode");
                                        String organizationName = jsonObject.getString("OrganizationName");

                                        organisations.add(new Organisations(organizationName, organizationCode));
                                        progressDialog.dismiss();

                                    }

                                    progressDialog.dismiss();
                                } else {

                                    progressDialog.dismiss();
                                    Snackbar.make(rootLayout, "No Organisations Found", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                                progressDialog.dismiss();
                                etOrgName.setEnabled(false);
                                Snackbar.make(rootLayout, "No Organisations Found", Snackbar.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }
            }

        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;

            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);
    }

    public void setOrganisations(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View dview = li.inflate(R.layout.alert_org_names,null);

        builder.setView(dview);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(true);

        RecyclerView recyclerView = (RecyclerView)dview.findViewById(R.id.org_recycler);

        organisationAdapter =new OrganisationAdapter(OnlineFiling.this,organisations);

        RecyclerView.LayoutManager layoutManager  =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(organisationAdapter);
        organisationAdapter.notifyDataSetChanged();

        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, organisationAdapter.getItemCount());

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(OnlineFiling.this, new RecyclerClickListener.OnItemTouchListener() {
            @Override
            public void OnItemCLikc(View view, int position) {
                etOrgName.setText(organisations.get(position).getorgName());
                organisationName = organisations.get(position).getorgName();

                orgCode = organisations.get(position).getorgId();

                dialog.dismiss();

                llAssessmentRadio.setVisibility(View.VISIBLE);

            }
        }));
    }

    public void addAdapter() {
        onlineFilingDemandAdapter = new OnlineFilingDemandAdapter(OnlineFiling.this, onlineFilingDemandEntities,
                checkAll);
        recyclerViewDemand.setAdapter(onlineFilingDemandAdapter);
        onlineFilingDemandAdapter.notifyDataSetChanged();
        recyclerViewDemand.setNestedScrollingEnabled(false);
    }

    public void setTotalFromAdapter(String total, String year, String balance, String sno, String taxno) {
        this.p_amount = Integer.parseInt(total);

//        tvAmount.setText(""+p_amount);

        this.selected_years = year;
        this.selected_balances = balance;
        this.selected_sNos = sno;
        this.selectedTaxNos = taxno;

    }

    public void getTrade(String districtName, String panchayatName, String finYear, String type, final String taxNo){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        Log.e(TAG,"----"+type);

        String url =  API_GET_PROFESSION_DEMAND+districtName+"&Panchayat="+panchayatName+"&" +
                "FinYear="+finYear+"&AssType="+type+"&TaxNo="+taxNo+"&OrgCode=";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject recordset = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recordset.getString("recordsets"));
                            for (int i = 0; i < firstarray.length(); i++) {

                                Log.e(TAG,"---"+firstarray.toString());

                                JSONArray secondArray = (JSONArray) firstarray.get(i);

                                if (secondArray.length() > 0) {
                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);

                                        String assessmentNo = jsonObject.getString("AssessmentNo");
                                        String assessmentName = jsonObject.getString("AssessmentName");
                                        String doorNo = jsonObject.getString("DoorNo");
                                        String wardNo = jsonObject.getString("WardNo");
                                        tradeName = jsonObject.getString("TradeName");
                                        String financialYear = jsonObject.getString("FinancialYear");
                                        int demand = jsonObject.getInt("Demand");
                                        int sno = jsonObject.getInt("Sno");

                                        tvTaxNo.setText(Html.fromHtml("<b>Tax No : </b> " +assessmentNo));
                                        tvName.setText(Html.fromHtml("<b>Name : </b> " +assessmentName));
                                        tvDoorNo.setText(Html.fromHtml("<b>Door No : </b> " +doorNo));
                                        tvWardNo.setText(Html.fromHtml("<b>Ward No : </b> " +wardNo));
                                        tvOrgName.setText(Html.fromHtml(tradeName));

                                        onlineFilingDemandEntities.add(new OnlineFilingDemandEntity(assessmentNo,
                                                assessmentName,doorNo,wardNo,tradeName,financialYear,demand,sno));

                                        llAssessmentDetails.setVisibility(View.VISIBLE);
                                        llYear.setVisibility(View.VISIBLE);

                                        progressDialog.dismiss();
                                        Log.e(TAG,"---"+jsonObject.toString());

                                    }
                                } else {
                                    Snackbar.make(rootLayout, "No Data Found ", Snackbar.LENGTH_SHORT).show();
                                    llAssessmentDetails.setVisibility(View.GONE);
                                    progressDialog.dismiss();

                                }
                            }

                            addAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }
            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);
    }

    public void getOrgIndividual(String districtName, String panchayatName, String finYear, String type,
                                 final String taxNo, int orgCode){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        Log.e(TAG,"----"+type);

        String url =  API_GET_PROFESSION_DEMAND+districtName+"&Panchayat="+panchayatName+"&" +
                "FinYear="+finYear+"&AssType=OrgSingle&TaxNo="+taxNo+"&OrgCode="+orgCode;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject recordset = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recordset.getString("recordsets"));
                            for (int i = 0; i < firstarray.length(); i++) {

                                Log.e(TAG,"---"+firstarray.toString());

                                JSONArray secondArray = (JSONArray) firstarray.get(i);

                                if (secondArray.length() > 0) {
                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);

                                        String assessmentNo = jsonObject.getString("AssessmentNo");
                                        String assessmentName = jsonObject.getString("AssessmentName");
                                        String doorNo = jsonObject.getString("DoorNo");
                                        String wardNo = jsonObject.getString("WardNo");
                                        String organizationName = jsonObject.getString("OrganizationName");
                                        String financialYear = jsonObject.getString("FinancialYear");
                                        int demand = jsonObject.getInt("Demand");
                                        int sno = jsonObject.getInt("Sno");

                                        tvTaxNo.setText(Html.fromHtml("<b>Tax No : </b> " +assessmentNo));
                                        tvName.setText(Html.fromHtml("<b>Name : </b> " +assessmentName));
                                        tvDoorNo.setText(Html.fromHtml("<b>Door No : </b> " +doorNo));
                                        tvWardNo.setText(Html.fromHtml("<b>Ward No : </b> " +wardNo));
                                        tvOrgName.setText(Html.fromHtml(organizationName));

                                        onlineFilingDemandEntities.add(new OnlineFilingDemandEntity(assessmentNo,
                                                assessmentName,doorNo,wardNo,organizationName,financialYear,demand,sno));

                                        llAssessmentDetails.setVisibility(View.VISIBLE);
                                        llYear.setVisibility(View.VISIBLE);

                                        progressDialog.dismiss();
                                        Log.e(TAG,"---"+jsonObject.toString());

                                    }
                                } else {
                                    Snackbar.make(rootLayout, "No Data Found ", Snackbar.LENGTH_SHORT).show();

                                    progressDialog.dismiss();
                                }
                            }
                            addAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }
            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);

    }

    public void getOrgGroup(String districtName, String panchayatName, String finYear, String type, int orgCode){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        Log.e(TAG,"----"+type);

        String url =  API_GET_PROFESSION_DEMAND+districtName+"&Panchayat="+panchayatName+"&" +
                "FinYear="+finYear+"&AssType=OrgGroup&TaxNo=&OrgCode="+orgCode;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject recordset = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recordset.getString("recordsets"));
                            for (int i = 0; i < firstarray.length(); i++) {

                                Log.e(TAG,"---"+firstarray.toString());

                                JSONArray secondArray = (JSONArray) firstarray.get(i);

                                if (secondArray.length() > 0) {
                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);

                                        String assessmentNo = jsonObject.getString("AssessmentNo");
                                        String assessmentName = jsonObject.getString("AssessmentName");
                                        String doorNo = jsonObject.getString("DoorNo");
                                        String wardNo = jsonObject.getString("WardNo");
                                        String organizationName = jsonObject.getString("OrganizationName");
                                        String financialYear = jsonObject.getString("FinancialYear");
                                        int demand = jsonObject.getInt("Demand");
                                        int sno = jsonObject.getInt("Sno");

                                        tvTaxNo.setText(Html.fromHtml("<b>Tax No : </b> " +assessmentNo));
                                        tvName.setText(Html.fromHtml("<b>Name : </b> " +assessmentName));
                                        tvDoorNo.setText(Html.fromHtml("<b>Door No : </b> " +doorNo));
                                        tvWardNo.setText(Html.fromHtml("<b>Ward No : </b> " +wardNo));
                                        tvOrgName.setText(Html.fromHtml(organizationName));

                                        onlineFilingDemandEntities.add(new OnlineFilingDemandEntity(assessmentNo,
                                                assessmentName,doorNo,wardNo,organizationName,financialYear,demand,sno));

                                        llAssessmentDetails.setVisibility(View.VISIBLE);
                                        llYear.setVisibility(View.VISIBLE);

                                        progressDialog.dismiss();
                                        Log.e(TAG,"---"+jsonObject.toString());

                                    }
                                } else {

                                    Snackbar.make(rootLayout, "No Data Found ", Snackbar.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            }

                            addAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }
            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);
    }

    public void savingOnlineFiling(String name, String mobileNo, String email, String organisationName, String type,
                                   String selected_sNos, String selectedTaxNos, String districtName, String panchayatName,
                                   String finYear, String orgCode, String tradeName){

        progressDialog = new SpotsDialog(OnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String enName="",enMobileNo="",enEmailId="",enOrgName="";

        try{

            enName = URLEncoder.encode(name,"UTF-8");
            enMobileNo = URLEncoder.encode(mobileNo,"UTF-8");
            enEmailId = URLEncoder.encode(email,"UTF-8");
            enOrgName = URLEncoder.encode(organisationName,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = API_ONLINE_FILING_SAVE+districtName+"&Panchayat="+panchayatName+"&" +
                "FinYear="+finYear+"&Name="+enName+"&MobileNo="+enMobileNo+"&EmailId="+enEmailId+"&AssType="+type+"&" +
                "TradeName="+tradeName+"&OrgCode="+orgCode+"&OrgName="+enOrgName+"&TaxSno="+selected_sNos+"&TaxNo="+selectedTaxNos+"&" +
                "HalfYear=2&EntryType=Android";

        Log.e(TAG,"--"+url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject recordset = new JSONObject(response.toString());

                            String message = recordset.getString("message");

                            String[] messageResponse = message.split("~");

                            if (messageResponse[0].equalsIgnoreCase("Success")){

                                Snackbar.make(rootLayout, "Saved Successfully ", Snackbar.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                finish();
                            }

                            addAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rootLayout);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rootLayout);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rootLayout);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rootLayout);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rootLayout);
                } else {
                    SnackShowTop(error.getMessage(), rootLayout);
                }

            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);
    }

    private void SnackShowTop(String message, View view) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View view_layout = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view_layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        view_layout.setLayoutParams(params);
        snack.show();
    }

}

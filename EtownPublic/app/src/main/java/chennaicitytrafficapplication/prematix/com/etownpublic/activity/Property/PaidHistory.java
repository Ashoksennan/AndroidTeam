package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Property;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Property_Tax.PaidHostoryAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory.TaxBalancePayment;
import dmax.dialog.SpotsDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_DISTRICT_DETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TAXBALANCEPAYMENTDETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TOWNPANCHAYAT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTDISTRICT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTPANCHAYAT;

public class PaidHistory extends AppCompatActivity {

    public static String TAG = PaidHistory.class.getSimpleName();

    ArrayList<String> distict_items = new ArrayList<>();
    ArrayList<String> panchayat_items = new ArrayList<>();
    SpinnerDialog spinnerDialogDistrict;
    SpinnerDialog spinnerDialogPanchayat;

    TextInputEditText etDistrict, etPanchayat;
    ImageView imBack;

    RecyclerView recyclerView;
    PaidHostoryAdapter paidHostoryAdapter;
    List<chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.PaidHistory> paidHistoryList = new ArrayList<>();

    RelativeLayout rootlayout;
    android.app.AlertDialog waitingDialog;

    private String volley_ReceiptNo, volley_TaxPaid, volley_TaxNO;
    private String volley_ReceiptDate, volley_PaymentMode, volley_financialyear;


    LinearLayout mLinear_Userdata;
    TextView mtextView_UserStreetNo, mtextView_UserWardNo, mtextView_UserDoorNo, mtextView_User_Name;
    SharedPreferences sharedPreference;
    String MyPREFERENCES = "User";
    private String mDistrictId, mDistrictName;
    private String mPanchayatId, mPanchayatName;
    private EditText medittext_Taxno;
    private Button mbutton_Submit;
    private HashMap<Integer, String> mDistrictHashmapitems = new LinkedHashMap<>();
    private HashMap<Integer, String> mPanchayatHashmapitems = new LinkedHashMap<>();
    Intent intent;
    private ArrayList<String> mDistrictList = new ArrayList<String>();
    private ArrayList<String> mPanchayatList = new ArrayList<String>();
    private ArrayList<TaxBalancePayment> mTaxBalancePayment = new ArrayList<TaxBalancePayment>();
    private String mVolley_Url;
    private TextView muserTitle;
    String mIntent_Type;

    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_history);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_property_payment_history);
        districtApiCall();
        intent = getIntent();

        rootlayout = findViewById(R.id.rootlayout);

        recyclerView = findViewById(R.id.recyclerPH);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        muserTitle = findViewById(R.id.user_title);
        medittext_Taxno = findViewById(R.id.edittext_taxno);

        etDistrict = findViewById(R.id.et_district);
        etPanchayat = findViewById(R.id.et_panchayat);


        sharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreference.getString(PREF_SELECTDISTRICT, "").isEmpty()) {
            etDistrict.setText(sharedPreference.getString(PREF_SELECTDISTRICT, ""));
            medittext_Taxno.setEnabled(true);
        } else {
            medittext_Taxno.setEnabled(false);
        }
        if (!sharedPreference.getString(PREF_SELECTPANCHAYAT, "").isEmpty())
            etPanchayat.setText(sharedPreference.getString(PREF_SELECTPANCHAYAT, ""));

        mtextView_User_Name = findViewById(R.id.textview_user_name);

        mtextView_UserStreetNo = findViewById(R.id.textview_user_streetno);

        mtextView_UserDoorNo = findViewById(R.id.textview_user_doorno);

        mtextView_UserWardNo = findViewById(R.id.textview_user_wardno);
        mLinear_Userdata = findViewById(R.id.linear_userdata);


        mbutton_Submit = findViewById(R.id.btn_submit);


        mbutton_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDistrict.getText().toString().isEmpty()) {
                    Snackbar.make(rootlayout, R.string.snack_district_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (etPanchayat.getText().toString().isEmpty()) {
                    Snackbar.make(rootlayout, R.string.snack_panchayat_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (medittext_Taxno.getText().toString().isEmpty()) {
                    Snackbar.make(rootlayout, R.string.snack_taxno_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (Common.isNetworkAvailable(getApplicationContext())) {


                    try {
                        taxBalancePayment(Integer.parseInt(medittext_Taxno.getText().toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else
                    Snackbar.make(rootlayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


            }
        });


        spinnerDialogPanchayat = new SpinnerDialog(PaidHistory.this, mPanchayatList, "Select or Search Panchayat", "Close");// With No Animation


        spinnerDialogDistrict = new SpinnerDialog(PaidHistory.this, mDistrictList, "Select or Search District", "Close");// With No Animation


        spinnerDialogDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                etDistrict.setText(item);


                for (Map.Entry<Integer, String> entry : mDistrictHashmapitems.entrySet()) {

                    if (entry.getValue().equals(item)) {
                        Log.e(TAG, "District Id ===> : : " + entry.getKey() + " Count : " + entry.getValue());

                        townPanchayat(entry.getKey());

                    }


                }


            }
        });
        etDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanchayatList.clear();

                medittext_Taxno.setEnabled(false);

                etPanchayat.setText("");
                etDistrict.setText("");

                spinnerDialogDistrict.showSpinerDialog();

            }
        });


        spinnerDialogPanchayat.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int i) {


                etPanchayat.setText(item);
                medittext_Taxno.setEnabled(true);

                for (Map.Entry<Integer, String> entry : mPanchayatHashmapitems.entrySet()) {

                    if (entry.getValue().equals(item)) {
                        Log.e(TAG, "Panchayat Id ===> : : " + entry.getKey() + " Count : " + entry.getValue());
                    }


                }

            }
        });

        etPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDistrict.getText().toString().equals("")) {
                    Snackbar.make(rootlayout, R.string.snack_district_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }


                spinnerDialogPanchayat.showSpinerDialog();
            }
        });


    }


    private void districtApiCall() {

        waitingDialog = new SpotsDialog(PaidHistory.this);
        waitingDialog.show();
        waitingDialog.setCancelable(false);
        String REQUEST_TAG = "apiDistrictDetails_Request";


        JsonArrayRequest apiDistrictDetails_Request = new JsonArrayRequest(Request.Method.GET, API_DISTRICT_DETAILS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e(TAG, response.toString());


                            if (!response.isNull(0)) {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject district_jsonObject = response.getJSONObject(i);

                                    mDistrictId = district_jsonObject.getString("DistrictId");
                                    mDistrictName = district_jsonObject.getString("DistrictName");

                                    mDistrictHashmapitems.put(Integer.parseInt(mDistrictId), mDistrictName);

                                    mDistrictList.add(mDistrictName);
                                }


                            } else Snackbar.make(rootlayout, "Error", Snackbar.LENGTH_SHORT).show();


                            waitingDialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(rootlayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                            waitingDialog.dismiss();
                        }

                    }
                }).start();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Log.e(TAG, error.toString());


                waitingDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", rootlayout);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", rootlayout);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", rootlayout);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", rootlayout);

                } else {


                    SnackShowTop(error.getMessage(), rootlayout);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);


                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(apiDistrictDetails_Request, REQUEST_TAG);


    }


    private void townPanchayat(Integer districtId) {

        waitingDialog = new SpotsDialog(PaidHistory.this);
        waitingDialog.show();
        waitingDialog.setCancelable(false);
        String REQUEST_TAG = "townPanchayat";


        JsonArrayRequest api_townPanchayat_Request = new JsonArrayRequest(Request.Method.GET, API_TOWNPANCHAYAT + districtId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.e(TAG, response.toString());


                    if (!response.isNull(0)) {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject district_jsonObject = response.getJSONObject(i);

                            mPanchayatId = district_jsonObject.getString("PanchayatId");
                            mPanchayatName = district_jsonObject.getString("PanchayatName");

                            mPanchayatHashmapitems.put(Integer.parseInt(mPanchayatId), mPanchayatName);

                            mPanchayatList.add(mPanchayatName);
                        }


                    } else Snackbar.make(rootlayout, "Error", Snackbar.LENGTH_SHORT).show();


                    waitingDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Snackbar.make(rootlayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    waitingDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Log.e(TAG, error.toString());


                waitingDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", rootlayout);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", rootlayout);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", rootlayout);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", rootlayout);

                } else {


                    SnackShowTop(error.getMessage(), rootlayout);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);


                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(api_townPanchayat_Request, REQUEST_TAG);


    }


    private void taxBalancePayment(Integer taxNo) throws UnsupportedEncodingException {
        mTaxBalancePayment.clear();
        waitingDialog = new SpotsDialog(PaidHistory.this);
        waitingDialog.show();
        String REQUEST_TAG = "taxBalancePayment";


        String district = URLEncoder.encode(etDistrict.getText().toString(), "UTF-8");
        String panchayat = URLEncoder.encode(etPanchayat.getText().toString(), "UTF-8");


        if (intent != null) {
            mIntent_Type = intent.getStringExtra("Tax_Type");

            switch (mIntent_Type) {
                case "Property":
                    mVolley_Url = API_TAXBALANCEPAYMENTDETAILS + "Type=PHistory&" + "TaxNo=" + taxNo + "&FinYear=&District=" + district + "&Panchayat=" + panchayat + "";

                    break;
                case "Water":
                    mVolley_Url = API_TAXBALANCEPAYMENTDETAILS + "Type=WHistory&" + "TaxNo=" + taxNo + "&FinYear=&District=" + district + "&Panchayat=" + panchayat + "";
                    muserTitle.setText("Connection");
                    break;
                case "Birth":

                    break;
            }
        }

        Log.e(TAG, mVolley_Url);
        JsonObjectRequest api_taxBalancePayment_Request = new JsonObjectRequest(Request.Method.GET, mVolley_Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG, response.toString());


                    String recordsetsarray = response.getString("recordsets");

                    JSONArray jsonArray = new JSONArray(recordsetsarray);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONArray insideJsonArray = jsonArray.getJSONArray(i);


                        if (insideJsonArray.length() > 0 && i == 0) {
                            JSONObject json_userdetails = insideJsonArray.getJSONObject(0);
                            String Name = json_userdetails.getString("Name");
                            String DoorNo = json_userdetails.getString("DoorNo");
                            String WardNo = json_userdetails.getString("WardNo");
                            String StreetName = json_userdetails.getString("StreetName");


                            mtextView_User_Name.setText(Name);

                            mtextView_UserStreetNo.setText(StreetName);

                            mtextView_UserDoorNo.setText(DoorNo);

                            mtextView_UserWardNo.setText(WardNo);


                            Log.e(TAG, "Name===>" + Name);

                        } else if (insideJsonArray.length() > 0) {


                            for (int j = 0; j < insideJsonArray.length(); j++) {
                                JSONObject json_taxdetails = insideJsonArray.getJSONObject(j);

                                volley_TaxNO = json_taxdetails.getString("TaxNo");
                                volley_financialyear = json_taxdetails.getString("FinancialYear");
                                volley_ReceiptDate = json_taxdetails.getString("ReceiptDate");
                                volley_PaymentMode = json_taxdetails.getString("PaymentMode");
                                volley_ReceiptNo = json_taxdetails.getString("ReceiptNo");
                                volley_TaxPaid = json_taxdetails.getString("TaxPaid");

                                Log.e(TAG, "Name TAX===>" + volley_TaxPaid);
                                mTaxBalancePayment.add(new TaxBalancePayment(volley_financialyear, volley_ReceiptDate, volley_PaymentMode, volley_TaxNO, volley_ReceiptNo, volley_TaxPaid));

                                mLinear_Userdata.setVisibility(View.VISIBLE);
                            }


                            paidHostoryAdapter = new PaidHostoryAdapter(PaidHistory.this, mTaxBalancePayment);


                            recyclerView.setAdapter(paidHostoryAdapter);

                            paidHostoryAdapter.notifyDataSetChanged();


                        } else {
                            mLinear_Userdata.setVisibility(View.GONE);
                            Snackbar.make(rootlayout, "No data found", Snackbar.LENGTH_SHORT).show();

                        }


                    }


                    waitingDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Snackbar.make(rootlayout, "error" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                    waitingDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Log.e(TAG, error.toString());


                waitingDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", rootlayout);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", rootlayout);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", rootlayout);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", rootlayout);

                } else {


                    SnackShowTop(error.getMessage(), rootlayout);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);


                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(api_taxBalancePayment_Request, REQUEST_TAG);


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

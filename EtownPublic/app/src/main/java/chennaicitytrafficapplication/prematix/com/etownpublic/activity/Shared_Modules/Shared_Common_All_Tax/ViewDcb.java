package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Common_All_Tax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import dmax.dialog.SpotsDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_DISTRICT_DETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_FinancialDates;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TOWNPANCHAYAT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_VIEW_DCB;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTDISTRICT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTPANCHAYAT;

public class ViewDcb extends AppCompatActivity {

    public static String TAG = ViewDcb.class.getSimpleName();
    Button btnSubmit;

    ArrayList<String> finyear_items = new ArrayList<>();

    String mIntent_Type;
    String Volley_Url;

    SpinnerDialog spinnerDialogDistrict;
    SpinnerDialog spinnerDialogPanchayat;
    SpinnerDialog spinnerDialogFinYear;


    TextInputEditText etDistrict, etPanchayat, etFinYear;
    ImageView imBack;

    RelativeLayout rootlayout;
    android.app.AlertDialog waitingDialog;

    Toolbar mtoolbar;
    private String mDistrictId, mDistrictName;
    private String mPanchayatId, mPanchayatName;
    SharedPreferences sharedPreference;
    String MyPREFERENCES = "User";


    private HashMap<Integer, String> mDistrictHashmapitems = new LinkedHashMap<>();

    private HashMap<Integer, String> mPanchayatHashmapitems = new LinkedHashMap<>();

    private ArrayList<String> mDistrictList = new ArrayList<String>();
    private ArrayList<String> mPanchayatList = new ArrayList<String>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dcb);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_viewdcb);

        intent = getIntent();
        etDistrict = findViewById(R.id.et_district);
        etPanchayat = findViewById(R.id.et_panchayat);

        rootlayout = findViewById(R.id.rootlayout);
        sharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreference.getString(PREF_SELECTDISTRICT, "").isEmpty())
            etDistrict.setText(sharedPreference.getString(PREF_SELECTDISTRICT, ""));

        if (!sharedPreference.getString(PREF_SELECTPANCHAYAT, "").isEmpty())
            etPanchayat.setText(sharedPreference.getString(PREF_SELECTPANCHAYAT, ""));


        etFinYear = findViewById(R.id.et_finyear);
        getFinyear();

        btnSubmit = findViewById(R.id.btn_submit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.isNetworkAvailable(getApplicationContext())) {


                    if (etDistrict.getText().toString().equals("")) {
                        Snackbar.make(rootlayout, R.string.snack_district_search, Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (etPanchayat.getText().toString().equals("")) {
                        Snackbar.make(rootlayout, R.string.snack_panchayat_search, Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (etFinYear.getText().toString().equals("")) {
                        Snackbar.make(rootlayout, R.string.snack_financial_search, Snackbar.LENGTH_SHORT).show();
                        return;
                    }


                    if (intent != null) {
                        mIntent_Type = intent.getStringExtra("Tax_Type");

                        switch (mIntent_Type) {
                            case "Property":
                                Volley_Url = API_VIEW_DCB + "qTaxType=Property&qDistrict=" + etDistrict.getText().toString().trim() + "&qPanchayat=" + etPanchayat.getText().toString().trim() + "&qFinYear=" + etFinYear.getText().toString().trim() + "";
                                openDownloadBrowser(ViewDcb.this, Volley_Url);

                                break;
                            case "Profession":

                                Volley_Url = API_VIEW_DCB + "qTaxType=Profession&qDistrict=" + etDistrict.getText().toString().trim() + "&qPanchayat=" + etPanchayat.getText().toString().trim() + "&qFinYear=" + etFinYear.getText().toString().trim() + "";
                                openDownloadBrowser(ViewDcb.this, Volley_Url);

                                break;
                            case "NonTax":
                                Volley_Url = API_VIEW_DCB + "qTaxType=NonTax&qDistrict=" + etDistrict.getText().toString().trim() + "&qPanchayat=" + etPanchayat.getText().toString().trim() + "&qFinYear=" + etFinYear.getText().toString().trim() + "";

                                openDownloadBrowser(ViewDcb.this, Volley_Url);

                                break;
                            case "Water":
                                Volley_Url = API_VIEW_DCB + "qTaxType=Water&qDistrict=" + etDistrict.getText().toString().trim() + "&qPanchayat=" + etPanchayat.getText().toString().trim() + "&qFinYear=" + etFinYear.getText().toString().trim() + "";

                                openDownloadBrowser(ViewDcb.this, Volley_Url);

                                break;
                        }


                    }
                } else {
                    Snackbar.make(rootlayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();

                }

            }
        });


        spinnerDialogPanchayat = new SpinnerDialog(ViewDcb.this, mPanchayatList, "Select or Search Panchayat", "Close");// With No Animation

        spinnerDialogFinYear = new SpinnerDialog(ViewDcb.this, finyear_items, "Select or Search Financial Year", "Close");// With 	Animation


        spinnerDialogDistrict = new SpinnerDialog(ViewDcb.this, mDistrictList, "Select or Search District", "Close");// With No Animation


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
                if (etFinYear.getText().toString().equals("")) {
                    Snackbar.make(rootlayout, R.string.snack_financial_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                etPanchayat.setText("");
                etDistrict.setText("");

                spinnerDialogDistrict.showSpinerDialog();

            }
        });


        spinnerDialogFinYear.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                etFinYear.setText(s);

            }
        });

        spinnerDialogPanchayat.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int i) {


                etPanchayat.setText(item);


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

        etFinYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogFinYear.showSpinerDialog();
            }
        });
    }


    private void getFinyear() {
        String REQUEST_TAG = "apigetFinYear";

        waitingDialog = new SpotsDialog(ViewDcb.this);
        waitingDialog.show();
        waitingDialog.setCancelable(false);

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

                                        finyear_items.add(FinYear);
                                    }

                                }

                            }
                            etFinYear.setText(finyear_items.get(0));

                        }

                    }
                    waitingDialog.dismiss();
                    districtApiCall();


                } catch (JSONException e) {
                    e.printStackTrace();
                    waitingDialog.dismiss();

                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest, REQUEST_TAG);

    }


    private void districtApiCall() {

        waitingDialog = new SpotsDialog(ViewDcb.this);
        waitingDialog.show();
        waitingDialog.setCancelable(false);
        String REQUEST_TAG = "apiDistrictDetails_Request";


        JsonArrayRequest apiDistrictDetails_Request = new JsonArrayRequest(Request.Method.GET, API_DISTRICT_DETAILS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

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

        waitingDialog = new SpotsDialog(ViewDcb.this);
        waitingDialog.show();
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


    private void SnackShowTop(String message, View view) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View view_layout = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view_layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        view_layout.setLayoutParams(params);
        snack.show();
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


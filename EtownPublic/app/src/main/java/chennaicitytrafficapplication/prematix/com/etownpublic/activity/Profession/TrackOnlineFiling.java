package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Profession;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax.TrackOnlineFilingEntity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax.TrackOnlineFilingAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import dmax.dialog.SpotsDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.TRACKNEWASSESSMENT;

public class TrackOnlineFiling extends AppCompatActivity {

    //Views
    RecyclerView recyclerView;
    EditText etMobileNo;
    Button buttonTrack;
    LinearLayout llAssessmentDetails;
    RelativeLayout rootLayout;

    //Adapter
    TrackOnlineFilingAdapter trackOnlineFilingAdapter;

    //ArrayList
    List<TrackOnlineFilingEntity> entityList =new ArrayList<>();

    //Dialog
    SpotsDialog progressDialog;

    //Toolbar
    Toolbar mtoolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_onlinefiling);

        //Toolbar
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.profession_track_online_filing);

        //RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerTrackAss);

        //EditText
        etMobileNo = (EditText)findViewById(R.id.et_mobile_no);

        //Button
        buttonTrack = (Button)findViewById(R.id.botton_track);

        //LinearLayout
        llAssessmentDetails = (LinearLayout)findViewById(R.id.ll_assesmentdetails);

        //RelativeLayout
        rootLayout = (RelativeLayout)findViewById(R.id.root_layout);

        llAssessmentDetails.setVisibility(View.GONE);

        buttonTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etMobileNo.getText().toString().isEmpty()){

                    if (Common.isNetworkAvailable(getApplicationContext())) {
                        addTrack(etMobileNo.getText().toString());
                    } else {

                        Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                    }

                }else{
                    Snackbar.make(rootLayout, "Enter Request No or Mobile Number", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private  void addTrack(String mobileNo){

        progressDialog = new SpotsDialog(TrackOnlineFiling.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url = TRACKNEWASSESSMENT+"Type=ProfessionFiling&MobileNo="+mobileNo+"&RequestNo=&RequestDate=&District=&" +
                "Panchayat=";

        Log.e("ooo","---"+url);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("inside","----"+response.toString());
                    try{
                        JSONObject recoredSet = new JSONObject(response.toString());

                        JSONArray firstarray = new JSONArray(recoredSet.getString("recordsets"));

                        for (int i=0;i<firstarray.length();i++){

                            JSONArray secondArray = firstarray.getJSONArray(0);

                            Log.e("insidearray","----"+secondArray.toString());

                            if (secondArray.length()>0){

                                for (int j=0;j<secondArray.length();j++){

                                    JSONObject jsonObject = (JSONObject) secondArray.get(j);

                                    String requestNo = jsonObject.getString("RequestNo");
                                    String requestDate = jsonObject.getString("RequestDate");
                                    String district = jsonObject.getString("District");
                                    String panchayat = jsonObject.getString("Panchayat");
                                    String finYear = jsonObject.getString("FinYear");
                                    String name = jsonObject.getString("Name");
                                    String assessmentType = jsonObject.getString("AssessmentType");
                                    String tradeName = jsonObject.getString("TradeName");
                                    String organizationCode = jsonObject.getString("OrganizationCode");
                                    String organizationName = jsonObject.getString("OrganizationName");
                                    String taxSno = jsonObject.getString("TaxSno");
                                    String tradeOrgName = jsonObject.getString("TradeOrgName");
                                    String taxNo = jsonObject.getString("TaxNo");
                                    String status = jsonObject.getString("Status");
                                    String mobileNo = jsonObject.getString("MobileNo");
                                    String emailId = jsonObject.getString("EmailId");
                                    String date = jsonObject.getString("Date");
//
                                    entityList.add(new TrackOnlineFilingEntity(requestNo,requestDate,district,
                                            panchayat,finYear,name,assessmentType,tradeName,organizationCode,organizationName,
                                            taxSno,tradeOrgName,taxNo,status,mobileNo,emailId,date));
//
                                }

                                progressDialog.dismiss();

                                llAssessmentDetails.setVisibility(View.VISIBLE);

                                trackOnlineFilingAdapter =new TrackOnlineFilingAdapter(TrackOnlineFiling.this,entityList);

                                RecyclerView.LayoutManager layoutManager  =new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(trackOnlineFilingAdapter);
                                trackOnlineFilingAdapter.notifyDataSetChanged();
                            }else{

                                progressDialog.dismiss();
                                llAssessmentDetails.setVisibility(View.GONE);
                                Snackbar.make(rootLayout, "No data Found", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("val","----"+error.toString());

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

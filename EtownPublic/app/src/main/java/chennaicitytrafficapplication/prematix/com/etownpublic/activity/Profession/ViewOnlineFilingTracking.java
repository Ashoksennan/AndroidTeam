package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Profession;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.ApprovalAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import dmax.dialog.SpotsDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TRACKNEWASSESSMENT;

public class ViewOnlineFilingTracking extends AppCompatActivity {

    //Strings
    String reqNo="",reqDate="",district="",panchayat="",finYear="",name="",assessmentType="",tradeName="",orgCode="",
            orgName="",taxSNo="",tradeOrgName="",taxNo="",status="",mobileNo="",emailId="",date="";

    //Views
    TextView tvReqNo,tvMobileNo,tvEmailId,tvDistrict,tvPanchayat,tvName,tvFinYear,tvAssessmentType,tvTradeName,
            tvOrgCode,tvOrgName,tvTaxSNo,tvTradeOrgName,tvTaxNo,tvStatus;
    RecyclerView recyclerViewApproval;
    LinearLayout llApprovalStatus;
    RelativeLayout rootLayout;

    //Dialog
    SpotsDialog progressDialog;

    //Adapter
    ApprovalAdapter approvalAdapter;

    //ArrayList
    List<chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.ApprovalEntity> approvalList =new ArrayList<>();

    //Toolbar
    Toolbar mtoolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_onlinefiling);

        //Toolbar
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.profession_online_filing);

        //TextViews
        tvReqNo = (TextView)findViewById(R.id.tv_req_no);
        tvMobileNo = (TextView)findViewById(R.id.tv_mobileno);
        tvEmailId = (TextView)findViewById(R.id.tv_emailid);
        tvDistrict = (TextView)findViewById(R.id.tv_district);
        tvPanchayat = (TextView)findViewById(R.id.tv_panchayat);
        tvName = (TextView)findViewById(R.id.tv_name);
        tvFinYear = (TextView)findViewById(R.id.tv_finyear);
        tvAssessmentType = (TextView)findViewById(R.id.tv_assessmenttype);
        tvTradeName = (TextView)findViewById(R.id.tv_tradename);
        tvOrgCode = (TextView)findViewById(R.id.tv_orgcode);
        tvOrgName = (TextView)findViewById(R.id.tv_orgname);
        tvTaxSNo = (TextView)findViewById(R.id.tv_taxsno);
        tvTradeOrgName = (TextView)findViewById(R.id.tv_tradeorgname);
        tvTaxNo = (TextView)findViewById(R.id.tv_taxno);
        tvStatus = (TextView)findViewById(R.id.tv_status);

        //RecyclerView
        recyclerViewApproval = (RecyclerView)findViewById(R.id.recyclerApproval);

        //LinearLayout
        llApprovalStatus = (LinearLayout)findViewById(R.id.ll_approval_status);

        //RelativeLayout
        rootLayout = (RelativeLayout)findViewById(R.id.root_layout);

        Intent i = getIntent();

        reqNo = i.getStringExtra("RequestNo");
        reqDate = i.getStringExtra("RequestDate");
        district = i.getStringExtra("District");
        panchayat = i.getStringExtra("Panchayat");
        finYear = i.getStringExtra("FinYear");
        name = i.getStringExtra("Name");
        assessmentType = i.getStringExtra("AssessmentType");
        tradeName = i.getStringExtra("TradeName");
        orgCode = i.getStringExtra("OrganizationCode");
        orgName = i.getStringExtra("OrganizationName");
        taxSNo = i.getStringExtra("TaxSno");
        tradeOrgName = i.getStringExtra("TradeOrgName");
        taxNo = i.getStringExtra("TaxNo");
        status = i.getStringExtra("Status");
        mobileNo = i.getStringExtra("MobileNo");
        emailId = i.getStringExtra("EmailId");
        date = i.getStringExtra("Date");

        tvReqNo.setText(reqNo);
        tvMobileNo.setText(mobileNo);
        tvEmailId.setText(emailId);
        tvDistrict.setText(district);
        tvPanchayat.setText(panchayat);
        tvName.setText(name);
        tvFinYear.setText(finYear);
        tvAssessmentType.setText(assessmentType);
        tvTradeName.setText(tradeName);
        tvOrgCode.setText(orgCode);
        tvOrgName.setText(orgName);
        tvTaxSNo.setText(taxSNo);
        tvTradeOrgName.setText(tradeOrgName);
        tvTaxNo.setText(taxNo);
        tvStatus.setText(status);

        if (Common.isNetworkAvailable(getApplicationContext())) {
            getApprovalStatus(mobileNo,reqNo,reqDate,district,panchayat);

        } else {

            Snackbar.make(rootLayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
        }


    }

    public void getApprovalStatus(String mobileNo, String requestNo, String reqDate, String district, String panchayat){

        progressDialog = new SpotsDialog(ViewOnlineFilingTracking.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String retDate = formatdate(reqDate);

        String url = API_TRACKNEWASSESSMENT+"Type=SinglePTFiling&MobileNo="+mobileNo+"&" +
                "RequestNo="+requestNo+"&RequestDate="+retDate+"&District="+district+"&Panchayat="+panchayat;

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
                                        String date = jsonObject.getString("Date");
                                        String remarks = jsonObject.getString("Remarks");
                                        String status = jsonObject.getString("Status");
                                        String orderDate = jsonObject.getString("OrderDate");
                                        String approvalBy = jsonObject.getString("ApprovalBy");

                                        approvalList.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.ApprovalEntity(requestNo,date,remarks,status,orderDate,approvalBy));
                                        Log.e("val","----"+requestNo);

                                    }

                                    progressDialog.dismiss();

                                    approvalAdapter =new ApprovalAdapter(ViewOnlineFilingTracking.this,approvalList);

                                    RecyclerView.LayoutManager layoutManager  =new LinearLayoutManager(getApplicationContext());
                                    recyclerViewApproval.setLayoutManager(layoutManager);
                                    recyclerViewApproval.setAdapter(approvalAdapter);
                                    approvalAdapter.notifyDataSetChanged();

                                }else{

                                    progressDialog.dismiss();
                                    llApprovalStatus.setVisibility(View.GONE);
                                    Snackbar.make(rootLayout, "No Approval Status Found", Snackbar.LENGTH_SHORT).show();

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

    public String formatdate(String fdate){
        String datetime=null;
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;


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

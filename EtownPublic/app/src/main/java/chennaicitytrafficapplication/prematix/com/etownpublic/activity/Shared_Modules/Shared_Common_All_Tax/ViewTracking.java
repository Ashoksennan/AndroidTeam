package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Common_All_Tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
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
import chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.ApprovalEntity;
import dmax.dialog.SpotsDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_NEW_TRACK_ASSESSMENTORCONNECTION;

public class ViewTracking extends AppCompatActivity {
    Toolbar mtoolbar;
    String requestNo = "", mobileNo = "", emailId = "", district = "", panchayat = "", name = "", blNo = "", blockNo = "", wardNo = "",
            streetName = "", status = "", reqDate = "", Tax_Type;

    TextView tvReqNo, tvMobileNo, tvEmailId, tvDistrict, tvPanchayat, tvName, tvBlNo, tvBlockNo, tvWardNo, tvStreetName,
            tv_designationname,

    title_tv_trade, title_tv_organisationcode, title_tv_organisationname, title_tv_designationname, tv_tradename, tv_organisationcode, tv_organisationname, tvStatus, mconnectionType_or_txt_building_type, mblocktype_or_doorno;
        RelativeLayout rootlayout;
    RecyclerView recyclerViewApproval;
    String url;
    SpotsDialog progressDialog;

    ApprovalAdapter approvalAdapter;
    List<ApprovalEntity> approvalList = new ArrayList<>();

    LinearLayout llApprovalStatus, linearApprovalStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tracking);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_viewconnection);

                rootlayout = (RelativeLayout)findViewById(R.id.rootlayout);

        tvReqNo = findViewById(R.id.txt_buildingblock_or_connectiontype);
        tvReqNo = findViewById(R.id.txt_block_or_doorno);
        tvReqNo = findViewById(R.id.tv_req_no);
        tvMobileNo = findViewById(R.id.tv_mobileno);
        tvEmailId = findViewById(R.id.tv_emailid);
        tvDistrict = findViewById(R.id.tv_district);
        tvPanchayat = findViewById(R.id.tv_panchayat);
        tvName = findViewById(R.id.tv_name);
        tvBlNo = findViewById(R.id.tv_blno);
        tvBlockNo = findViewById(R.id.tv_blockno);
        tvWardNo = findViewById(R.id.tv_wardno);
        tvStreetName = findViewById(R.id.tv_streetname);
        tvStatus = findViewById(R.id.tv_status);
        mblocktype_or_doorno = findViewById(R.id.txt_block_or_doorno);
        linearApprovalStatus = findViewById(R.id.linear_approval_status_card);


        tv_designationname = findViewById(R.id.assessment_designationname);
        tv_tradename = findViewById(R.id.assessment_tradename);
        tv_organisationcode = findViewById(R.id.assessment_organisationcode);
        tv_organisationname = findViewById(R.id.assessment_organisationname);

        title_tv_trade = findViewById(R.id.txt_tradename);
        title_tv_organisationcode = findViewById(R.id.txt_organisationcode);
        title_tv_organisationname = findViewById(R.id.txt_txt_organisationname);
        title_tv_designationname = findViewById(R.id.txt_txt_designationname);


        mconnectionType_or_txt_building_type = findViewById(R.id.txt_buildingblock_or_connectiontype);
        recyclerViewApproval = findViewById(R.id.recyclerApproval);

        llApprovalStatus = findViewById(R.id.ll_approval_status);


        Intent i = getIntent();
        if (i != null) {
            requestNo = i.getStringExtra("requestNo");
            mobileNo = i.getStringExtra("mobileNo");
            emailId = i.getStringExtra("emailId");
            district = i.getStringExtra("district");
            panchayat = i.getStringExtra("panchayat");
            name = i.getStringExtra("name");
            blNo = i.getStringExtra("blNo");
            blockNo = i.getStringExtra("blockNo");
            wardNo = i.getStringExtra("wardNo");
            streetName = i.getStringExtra("streetName");
            status = i.getStringExtra("status");
            reqDate = i.getStringExtra("reqDate");
            Tax_Type = i.getStringExtra("Tax_Type");


                   if(blockNo.isEmpty()){
                       mblocktype_or_doorno.setVisibility(View.GONE);
                       tvBlockNo.setVisibility(View.GONE);

                   }

            if (Tax_Type.equals("Water")) {

                mblocktype_or_doorno.setText(R.string.tracknewassessment_viewtracking_doorno);
                mconnectionType_or_txt_building_type.setText(R.string.tracknewassessment_viewtracking_connectionType);

            } else if (Tax_Type.equals("NonTax")) {


                mblocktype_or_doorno.setText(R.string.tracknewassessment_viewtracking_doorno);
                mconnectionType_or_txt_building_type.setText(R.string.tracknewassessment_viewtracking_leasename);

            } else if (Tax_Type.equals("Profession")) {

                String trade_name = Tax_Type = i.getStringExtra("TradeName");
                String organizationCode_value = i.getStringExtra("OrganizationCode");
                String organizationname_value = i.getStringExtra("OrganizationName");
                String organizationdesig_value = i.getStringExtra("DesignationName");

                mblocktype_or_doorno.setText(R.string.tracknewassessment_viewtracking_doorno);
                mconnectionType_or_txt_building_type.setText(R.string.tracnewassessment_AssessmentType);



                tv_designationname = findViewById(R.id.assessment_designationname);
                tv_tradename = findViewById(R.id.assessment_tradename);
                tv_organisationcode = findViewById(R.id.assessment_organisationcode);
                tv_organisationname = findViewById(R.id.assessment_organisationname);




                if (!trade_name.isEmpty()) {
                    tv_tradename.setText(trade_name);
                    tv_tradename.setVisibility(View.VISIBLE);
                    title_tv_trade.setVisibility(View.VISIBLE);
                }
                if (!organizationname_value.isEmpty()) {
                    tv_organisationname.setVisibility(View.VISIBLE);
                    title_tv_organisationname.setVisibility(View.VISIBLE);

                    tv_organisationname.setText(organizationname_value);
                }
                if (!organizationdesig_value.isEmpty()) {
                    title_tv_designationname.setVisibility(View.VISIBLE);
                    tv_designationname.setVisibility(View.VISIBLE);
                    tv_designationname.setText(organizationdesig_value);

                }
                if (!organizationCode_value.isEmpty()) {
                    tv_organisationcode.setText(organizationCode_value);

                    tv_organisationcode.setVisibility(View.VISIBLE);
                    title_tv_organisationcode.setVisibility(View.VISIBLE);

                }
            }

            tvReqNo.setText(requestNo);
            tvMobileNo.setText(mobileNo);
            tvEmailId.setText(emailId);
            tvDistrict.setText(district);
            tvPanchayat.setText(panchayat);
            tvName.setText(name);
            tvBlNo.setText(blNo);
            tvBlockNo.setText(blockNo);
            tvWardNo.setText(wardNo);
            tvStreetName.setText(streetName);
            tvStatus.setText(status);
            if (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Processed") ) {
                linearApprovalStatus.setVisibility(View.GONE);
            } else {

                getApprovalStatus(mobileNo, requestNo, reqDate, district, panchayat);

            }


        }
    }

    public void getApprovalStatus(String mobileNo, String requestNo, String reqDate, String district, String panchayat) {

        progressDialog = new SpotsDialog(ViewTracking.this);
        progressDialog.show();


        String retDate = formatdate(reqDate);


        if (Tax_Type.equals("Property")) {
            url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=SingleProperty&MobileNo=" + mobileNo + "&" +
                    "RequestNo=" + requestNo + "&RequestDate=" + retDate + "&District=" + district + "&Panchayat=" + panchayat;

        } else if (Tax_Type.equals("Water")) {
            url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=SingleWater&MobileNo=9600694856&RequestNo=27454&RequestDate=2018-08-09&District=Krishnagiri&Panchayat=Mathigiri";

        } else if (Tax_Type.equals("Profession")) {
            url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=SingleProfession&MobileNo=9600694856&RequestNo=27454&RequestDate=2018-08-09&District=Krishnagiri&Panchayat=Mathigiri";

        } else if (Tax_Type.equals("NonTax")) {
            url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=SingleNonTax&MobileNo=9600694856&RequestNo=27454&RequestDate=2018-08-09&District=Krishnagiri&Panchayat=Mathigiri";

        }
        Log.e("ooo", "---" + url);


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("inside", "----" + response.toString());
                        try {
                            JSONObject recoredSet = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recoredSet.getString("recordsets"));

                            for (int i = 0; i < firstarray.length(); i++) {

                                JSONArray secondArray = firstarray.getJSONArray(0);

                                Log.e("insidearray", "----" + secondArray.toString());

                                if (secondArray.length() > 0) {

                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);
                                        String requestNo = jsonObject.getString("RequestNo");
                                        String date = jsonObject.getString("Date");
                                        String remarks = jsonObject.getString("Remarks");
                                        String status = jsonObject.getString("Status");
                                        String orderDate = jsonObject.getString("OrderDate");
                                        String approvalBy = jsonObject.getString("ApprovalBy");

                                        approvalList.add(new ApprovalEntity(requestNo, date, remarks, status, orderDate, approvalBy));
                                        Log.e("val", "----" + requestNo);
//
                                    }

                                    approvalAdapter = new ApprovalAdapter(ViewTracking.this, approvalList);

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerViewApproval.setLayoutManager(layoutManager);
                                    recyclerViewApproval.setAdapter(approvalAdapter);
                                    approvalAdapter.notifyDataSetChanged();

                                } else {

                                    progressDialog.dismiss();
                                    llApprovalStatus.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "No Approval Status Found", Toast.LENGTH_SHORT).show();
                                }

                                progressDialog.dismiss();

                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof AuthFailureError) {


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

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, "API_NEW_TRACK_ASSESSMENTORCONNECTION");


    }
    private void SnackShowTop(String message, View view) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View view_layout = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view_layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        view_layout.setLayoutParams(params);
        snack.show();
    }

    public String formatdate(String fdate) {
        String datetime = null;
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        } catch (ParseException e) {

        }
        return datetime;


    }

}

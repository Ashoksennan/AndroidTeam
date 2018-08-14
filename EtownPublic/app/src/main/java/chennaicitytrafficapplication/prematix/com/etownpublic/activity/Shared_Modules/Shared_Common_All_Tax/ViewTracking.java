package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Common_All_Tax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.ApprovalAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.ApprovalEntity;

import dmax.dialog.SpotsDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_NEW_TRACK_ASSESSMENTORCONNECTION;

public class ViewTracking extends AppCompatActivity {
    Toolbar mtoolbar;
    String requestNo = "", mobileNo = "", emailId = "", district = "", panchayat = "", name = "", blNo = "", blockNo = "", wardNo = "",
            streetName = "", status = "", reqDate = "", Tax_Type;

    TextView tvReqNo, tvMobileNo, tvEmailId, tvDistrict, tvPanchayat, tvName, tvBlNo, tvBlockNo, tvWardNo, tvStreetName, tvStatus, mconnectionType_or_txt_building_type, mblocktype_or_doorno;

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
            if (Tax_Type.equals("Water")) {

                mblocktype_or_doorno.setText(R.string.tracknewassessment_viewtracking_doorno);
                mconnectionType_or_txt_building_type.setText(R.string.tracknewassessment_viewtracking_connectionType);

            } else if (Tax_Type.equals("NonTax")) {


                mblocktype_or_doorno.setText(R.string.tracknewassessment_viewtracking_doorno);
                mconnectionType_or_txt_building_type.setText(R.string.tracknewassessment_viewtracking_leasename);

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
            if (status.equalsIgnoreCase("Pending")) {
                linearApprovalStatus.setVisibility(View.GONE);
            }

            getApprovalStatus(mobileNo, requestNo, reqDate, district, panchayat);


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

        }


        Log.e("ooo", "---" + url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("inside", "----" + response.toString());
                        try {
                            JSONObject recoredSet = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recoredSet.getString("recordsets"));

                            for (int i = 0; i < firstarray.length(); i++) {
//
                                JSONArray secondArray = firstarray.getJSONArray(0);
//
                                Log.e("insidearray", "----" + secondArray.toString());

                                if (secondArray.length() > 0) {
//
                                    for (int j = 0; j < secondArray.length(); j++) {
//
                                        JSONObject jsonObject = (JSONObject) secondArray.get(j);
//
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

                                    progressDialog.dismiss();

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
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("val", "----" + error.toString());

            }


        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", "eyJhbGciOiJIUzI1NiJ9.UHJlbWF0aXg.VnYf5L2bruAL3IhIbhOCnqW3SADSM2qjWrZAV0yrB94");

                return params;

            }
        };

        requestQueue.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        progressDialog.dismiss();

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

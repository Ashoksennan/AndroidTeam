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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.SharedBean.TrackAssmentNoEntity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.TrackAssessmentAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import dmax.dialog.SpotsDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_NEW_TRACK_ASSESSMENTORCONNECTION;

public class TrackNewAssessmentNo extends AppCompatActivity {
    Toolbar mtoolbar;
    RecyclerView recyclerView;
    TrackAssessmentAdapter trackAssessmentAdapter;
    List<TrackAssmentNoEntity> entityList = new ArrayList<>();

    SpotsDialog progressDialog;
    String mIntent_Type;
    EditText etMobileNo;
    String url;
    Button buttonTrack;
    private String TAG = TrackNewAssessmentNo.class.getName();
    RelativeLayout rootlayout;
    LinearLayout llAssessmentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_new_assessment_no);
        mtoolbar = findViewById(R.id.toolbar);


        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent intent = getIntent();
        if (intent != null) {
            mIntent_Type = intent.getStringExtra("Tax_Type");
            if (mIntent_Type != null) {
                switch (mIntent_Type) {
                    case "Property":

                        getSupportActionBar().setTitle(R.string.toolbar_property_track_assessment);

                        break;
                    case "Profession":
                        getSupportActionBar().setTitle(R.string.toolbar_professiontax);


                        break;
                    case "NonTax":
                        getSupportActionBar().setTitle(R.string.toolbar_property_track_assessment);

                        break;

                    case "Water":

                        getSupportActionBar().setTitle(R.string.toolbar_water_track_assessment);

                        break;
                }
            }

        }

        rootlayout = findViewById(R.id.rootlayout);

        recyclerView = findViewById(R.id.recyclerTrackAss);

        etMobileNo = findViewById(R.id.et_mobile_no);

        buttonTrack = findViewById(R.id.botton_track);

        llAssessmentDetails = findViewById(R.id.ll_assesmentdetails);

        llAssessmentDetails.setVisibility(View.GONE);

        buttonTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etMobileNo.getText().toString().isEmpty()) {


                    if (Common.isNetworkAvailable(getApplicationContext())) {


                        addTrack(etMobileNo.getText().toString());

                    } else
                        Snackbar.make(rootlayout, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();


                } else {

                    Snackbar.make(rootlayout, "Enter Request No or Mobile Number", Snackbar.LENGTH_SHORT).show();


                }
            }
        });

    }

    private void addTrack(String mobileNo) {

        progressDialog = new SpotsDialog(TrackNewAssessmentNo.this);
        progressDialog.show();


        switch (mIntent_Type) {
            case "Property":

                url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=Property&MobileNo=" + mobileNo + "&RequestNo=&" +
                        "RequestDate=&District=&Panchayat=";

                break;
            case "Profession":

                url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=Profession&MobileNo=" + mobileNo + "&RequestNo=&" +
                        "RequestDate=&District=&Panchayat=";

                break;
            case "NonTax":
                url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=NonTax&MobileNo=" + mobileNo + "&RequestNo=&" +
                        "RequestDate=&District=&Panchayat=";
                break;

            case "Water":

                url = API_NEW_TRACK_ASSESSMENTORCONNECTION + "Type=Water&MobileNo=" + mobileNo + "&RequestNo=&" +
                        "RequestDate=&District=&Panchayat=";

                break;
        }

        Log.e(TAG, url);


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
                                        String requestDate = jsonObject.getString("RequestDate");
                                        String district = jsonObject.getString("District");
                                        String panchayat = jsonObject.getString("Panchayat");
                                        String name = jsonObject.getString("Name");
                                        String wardNo = jsonObject.getString("WardNo");
                                        String streetName = jsonObject.getString("StreetName");
                                        String mobileNo = jsonObject.getString("MobileNo");
                                        String emailId = jsonObject.getString("EmailId");
                                        String date = jsonObject.getString("Date");
                                        String status = jsonObject.getString("Status");








                                        switch (mIntent_Type) {
                                            case "Property":
                                                String leaseName = jsonObject.getString("BuildingLicenceNo");
                                                String blockNo = jsonObject.getString("BlockNo");

                                                entityList.add(new TrackAssmentNoEntity(requestNo, requestDate, district, panchayat, name,
                                                        leaseName, blockNo, wardNo, streetName, status, mobileNo, emailId, mIntent_Type,"","","",""));

                                                break;

                                            case "Water":

                                                String connectionType = jsonObject.getString("ConnectionType");
                                                String doorNo = jsonObject.getString("DoorNo");
                                                entityList.add(new TrackAssmentNoEntity(requestNo, requestDate, district, panchayat, name,
                                                        connectionType, doorNo, wardNo, streetName, status, mobileNo, emailId, mIntent_Type,"","","",""));


                                                break;
                                            case "NonTax":

                                                String lease_name = jsonObject.getString("LeaseName");
                                                String non_Tax_doorno = jsonObject.getString("DoorNo");
                                                entityList.add(new TrackAssmentNoEntity(requestNo, requestDate, district, panchayat, name,
                                                        lease_name, non_Tax_doorno, wardNo, streetName, status, mobileNo, emailId, mIntent_Type,"","","",""));


                                                break;

                                            case "Profession":




                                                String prof_TradeName = jsonObject.getString("TradeName");
                                                String prof_OrganizationCode = jsonObject.getString("OrganizationCode");
                                                String prof_OrganizationName = jsonObject.getString("OrganizationName");
                                                String prof_DesignationName = jsonObject.getString("DesignationName");


                                                String profession_assessmentType = jsonObject.getString("AssessmentType");
                                                String profession_doorno = jsonObject.getString("DoorNo");
                                                entityList.add(new TrackAssmentNoEntity(requestNo, requestDate, district, panchayat, name,
                                                        profession_assessmentType, profession_doorno, wardNo, streetName,
                                                        status, mobileNo, emailId, mIntent_Type,prof_TradeName,prof_OrganizationCode,prof_OrganizationName,prof_DesignationName));


                                                break;


                                        }


                                    }

                                    progressDialog.dismiss();

                                    llAssessmentDetails.setVisibility(View.VISIBLE);
                                    trackAssessmentAdapter = new TrackAssessmentAdapter(TrackNewAssessmentNo.this, entityList);

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(trackAssessmentAdapter);
                                    trackAssessmentAdapter.notifyDataSetChanged();
                                } else {

                                    progressDialog.dismiss();
                                    llAssessmentDetails.setVisibility(View.GONE);
                                    entityList.clear();
                                    trackAssessmentAdapter.notifyDataSetChanged();

                                    Snackbar.make(rootlayout, "No data Found", Snackbar.LENGTH_SHORT).show();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

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

}

package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Birth_Modules;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Birth.TrackBirthRegistration_Adapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.TrackBirthRegistration_Pojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class TrackBirthRegistration_Activity extends AppCompatActivity implements TrackBirthRegistration_Adapter.SpecificDetails {

    TrackBirthRegistration_Adapter adapter;
    RecyclerView tbr_rv;
    AlertDialog dialogBirthDetails;

    final static String TAG = TrackBirthRegistration_Activity.class.getSimpleName();

    ArrayList<TrackBirthRegistration_Pojo> beanlist;

    @Nullable
    @BindView(R.id.et_req_no)
    EditText et_req_no;
    @Nullable
    @BindView(R.id.et_mob_no)
    EditText et_mob_no;
    @Nullable
    @BindView(R.id.btn_track)
    Button btn_track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_birth_registration);
        ButterKnife.bind(this);
        beanlist = new ArrayList<>();

        tbr_rv = findViewById(R.id.tbr_rv);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        tbr_rv.setLayoutManager(linearLayoutManager);

        if (tbr_rv != null) {
            tbr_rv.setHasFixedSize(true);
        }


        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_req_no.getText().toString().isEmpty() || !et_mob_no.getText().toString().isEmpty())
                    getTrackDetails();
                else
                    Toast.makeText(TrackBirthRegistration_Activity.this, "Please Enter Above Request no or mobile no", Toast.LENGTH_SHORT).show();
            }
        });

//        getTrackDetails();

    }

    public void getTrackDetails() {

        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(TrackBirthRegistration_Activity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getTrackingDetails(Common.ACCESS_TOKEN, "Birth", et_mob_no.getText().toString(), et_req_no.getText().toString(), "", "", "");
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd", response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result", response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    JSONArray jsonArray = records.getJSONArray("recordsets");
                    if (jsonArray.length() > 0) {
                        JSONArray trackArray = jsonArray.getJSONArray(0);
                        if (trackArray.length() > 0) {
                            for (int i = 0; i < trackArray.length(); i++) {
                                JSONObject trackObjects = trackArray.getJSONObject(i);
                                beanlist.add(new TrackBirthRegistration_Pojo(trackObjects.getString("RequestNo"), trackObjects.getString("RequestDate"),
                                        trackObjects.getString("District"), trackObjects.getString("Panchayat"), trackObjects.getString("ChildName"),
                                        trackObjects.getString("DoorNo"), trackObjects.getString("WardNo"), trackObjects.getString("Status"),
                                        trackObjects.getString("StreetName"), trackObjects.getString("MobileNo"), trackObjects.getString("EmailId"), trackObjects.getString("FatherName"), trackObjects.getString("Gender"),
                                        trackObjects.getString("BirthPlace"), trackObjects.getString("MotherName"), trackObjects.getString("DOB")));

                            }
                            pd.dismiss();
                            addAdapter();

                        } else {
                            Toast.makeText(TrackBirthRegistration_Activity.this, "No Data found!!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    } else {
                        Toast.makeText(TrackBirthRegistration_Activity.this, "No Data found!!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void addAdapter() {
        adapter = new TrackBirthRegistration_Adapter(beanlist, getApplicationContext(), TrackBirthRegistration_Activity.this);
        tbr_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tbr_rv.setNestedScrollingEnabled(false);
    }

    @Override
    public void getpos(int pos) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TrackBirthRegistration_Activity.this);
        View v2 = getLayoutInflater().inflate(R.layout.item_track_birth, null);
        ImageView img_cancel = v2.findViewById(R.id.img_cancel);
        TextView tv_childname = v2.findViewById(R.id.tv_childname);
        TextView tv_req_no = v2.findViewById(R.id.tv_req_no);
        TextView tv_gender = v2.findViewById(R.id.tv_gender);
        TextView tv_father_name = v2.findViewById(R.id.tv_father_name);
        TextView tv_father_mob_no = v2.findViewById(R.id.tv_father_mob_no);
        TextView tv_email = v2.findViewById(R.id.tv_email);
        TextView tv_birthplace = v2.findViewById(R.id.tv_birthplace);
        TextView tv_dob = v2.findViewById(R.id.tv_dob);
        TextView tv_district = v2.findViewById(R.id.tv_district);
        TextView tv_panchayat = v2.findViewById(R.id.tv_panchayat);
        TextView tv_status = v2.findViewById(R.id.tv_status);
        if (!beanlist.get(pos).getmRequestNo().isEmpty()) {
            tv_req_no.setText(beanlist.get(pos).getmRequestNo());
        } else {
            tv_req_no.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmChildName().isEmpty()) {
            tv_childname.setText(beanlist.get(pos).getmChildName());
        } else {
            tv_childname.setVisibility(View.GONE);
        }
//        tv_gender.setText(beanlist.get(pos).getGender());
        if (!beanlist.get(pos).getmGender().isEmpty()) {
            tv_gender.setText(beanlist.get(pos).getmGender());
        } else {
            tv_gender.setVisibility(View.GONE);
        }
//        tv_father_name.setText(beanlist.get(pos).getFatherName());
        if (!beanlist.get(pos).getmFatherName().isEmpty()) {
            tv_father_name.setText(beanlist.get(pos).getmFatherName());
        } else {
            tv_father_name.setVisibility(View.GONE);
        }
//        tv_father_mob_no.setText(beanlist.get(pos).getMobileNo());
        if (!beanlist.get(pos).getmMobileNo().isEmpty()) {
            tv_father_mob_no.setText(beanlist.get(pos).getmMobileNo());
        } else {
            tv_father_mob_no.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmEmailId().isEmpty()) {
            tv_email.setText(beanlist.get(pos).getmEmailId());
        } else {
            tv_email.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmBirthPlace().isEmpty()) {
            tv_birthplace.setText(beanlist.get(pos).getmBirthPlace());
        } else {
            tv_birthplace.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmDOB().isEmpty()) {
            tv_dob.setText(beanlist.get(pos).getmDOB());
        } else {
            tv_dob.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmDistrict().isEmpty()) {
            tv_district.setText(beanlist.get(pos).getmDistrict());
        } else {
            tv_district.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmPanchayat().isEmpty()) {
            tv_panchayat.setText(beanlist.get(pos).getmPanchayat());
        } else {
            tv_panchayat.setVisibility(View.GONE);
        }
        if (!beanlist.get(pos).getmStatus().isEmpty()) {
            tv_status.setText(beanlist.get(pos).getmStatus());
        } else {
            tv_status.setVisibility(View.GONE);
        }
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBirthDetails.dismiss();
            }
        });
        mBuilder.setView(v2);
        dialogBirthDetails = mBuilder.create();
        dialogBirthDetails.show();
    }
}

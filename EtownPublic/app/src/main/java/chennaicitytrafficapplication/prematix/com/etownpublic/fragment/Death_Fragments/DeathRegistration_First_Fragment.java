package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Death_Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Birth_Modules.BirthRegistration_Activity;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Death_Modules.DeathRegistration_Activity;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.DateSelect;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class DeathRegistration_First_Fragment extends Fragment implements
        Validator.ValidationListener{
    public static final  String TAG = BirthRegistration_Activity.class.getSimpleName();
    View v;
    Validator validator;
    boolean isvalidated;

    @NotEmpty
    @Order(1)
    @Nullable
    @BindView(R.id.et_district) EditText et_district;

    @NotEmpty
    @Order(2)
    @Nullable
    @BindView(R.id.et_panchayat) EditText et_panchayat;

    @NotEmpty
    @Order(3)
    @Length(max = 10,min = 10,message = "Mobile Number length should be 10 digit")
    @Nullable
    @BindView(R.id.et_mobileno) EditText et_mobileno;

    @NotEmpty
    @Order(4)
    @Email
    @Nullable
    @BindView(R.id.et_emailid) EditText et_emailid;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_fathername) EditText et_fathername;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_husbandwifename) EditText et_husbandwifename;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_mothername) EditText et_mothername;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_permanent_address) EditText et_permanent_address;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_pincode) EditText et_pincode;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_name) EditText et_name;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_per_permanent_address) EditText et_per_permanent_address;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_per_pincode) EditText et_per_pincode;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_deathdate) EditText et_deathdate;

    @Nullable
    @BindView(R.id.li_parent_lay) LinearLayout li_parent_lay;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_gender) EditText et_gender;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_age) EditText et_age;

    @NotEmpty
    @Nullable
    @BindView(R.id.et_age_years) EditText et_age_years;

    @Nullable
    @BindView(R.id.btn_next) Button btn_next;
    @Nullable
    @BindView(R.id.check_above_address) CheckBox check_above_address;

    //List
    ArrayList<String> mDistrictsList = new ArrayList<>();
    ArrayList<String> mPanchayatList = new ArrayList<>();
    ArrayList<String> mGenderListList = new ArrayList<>();
    ArrayList<String> mYearsListList = new ArrayList<>();

    List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts> mPanchayatsList = new ArrayList<>();
    List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts> mDistrictList = new ArrayList();



    //Spinner variables
    SpinnerDialog spinnerDialog;


    //String integertypes
    public String district_Name;
    public String  panchayat_Name;
    public String selectedGender;

    //Objects used
    DateSelect dateObject;
    SharedPreferenceHelper sharedPreferenceHelpher;

    public int districtid;
    public int panchayatid;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_deathregistration_first, container, false);
        ButterKnife.bind(this,v);



        //object creation
        dateObject = new DateSelect(getActivity());
        sharedPreferenceHelpher = SharedPreferenceHelper.getInstance(getActivity());

        mGenderListList.add("Male");
        mGenderListList.add("Female");

        mYearsListList.add("Years");
        mYearsListList.add("Months");
        mYearsListList.add("Weeks");
        mYearsListList.add("Days");



        et_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDistricts();
            }
        });
        et_panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("d==>",districtid+"");
                if(districtid!=0){
                    getPanchayat();
                }else{
//                    Toast.makeText(getActivity(), "Please select the District!!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(li_parent_lay,"Please select the District!!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* int current = DeathRegistration_Activity.death_registration_pager.getCurrentItem();
                DeathRegistration_Activity.death_registration_pager.setCurrentItem(current+1);*/
                validator.validate();
            }
        });
        et_deathdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateObject.getDate(et_deathdate);
            }
        });
        et_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerGender(mGenderListList);
            }
        });
        et_age_years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerYears(mYearsListList);
            }
        });
        check_above_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    et_per_permanent_address.setText(et_permanent_address.getText().toString());
                    et_per_pincode.setText(et_pincode.getText().toString());
                }else{
                    et_per_permanent_address.getText().clear();
                    et_pincode.getText().clear();
                }
            }
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        return v;
    }

    public void getDistricts(){
        mDistrictsList.clear();
        mDistrictList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getDistricts(Common.ACCESS_TOKEN);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONArray jsonArray = new JSONArray(response1);
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int districtid = jsonObject.getInt("DistrictId");
                            String districtname = jsonObject.getString("DistrictName");
                            Log.e(TAG,jsonObject.getString("DistrictName"));
                            mDistrictsList.add(districtname);
                            mDistrictList.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts(districtid,districtname));
                        }
                        pd.dismiss();
                        setSpinnerDistrict(mDistrictsList);
                    }else{
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Log.e(TAG,e.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                Log.e(TAG,t.toString());
            }
        });

    }
    public void setSpinnerDistrict(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select District", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_district.setText(arrayList.get(i));
                et_panchayat.getText().clear();
                Log.e("distt== ", "" + mDistrictList.get(i).getmDistrictName() + " -- " + mDistrictList.get(i).getmDistrictId());

                districtid = mDistrictList.get(i).getmDistrictId();
                district_Name = mDistrictList.get(i).getmDistrictName();

            }
        });
    }
    public void setSpinnerPanchayat(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Panchayat", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                et_panchayat.setText(arrayList.get(i));

                panchayatid = mPanchayatsList.get(i).getmDistrictId();
                panchayat_Name = mPanchayatsList.get(i).getmDistrictName();

                Log.e("panchayat ", "" + mPanchayatsList.get(i).getmDistrictName() + " -- " + panchayatid);
            }
        });
    }

    public void setSpinnerYears(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                et_age_years.setText(arrayList.get(i));

            }
        });
    }
    public void setSpinnerGender(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Gender", R.style.DialogAnimations_SmileWindow, "CLOSE");

        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                et_gender.setText(arrayList.get(i));
                if(arrayList.get(i).equalsIgnoreCase("Male")){
                    selectedGender = "M";
                }else{
                    selectedGender = "F";
                }
            }
        });
    }



    public void getPanchayat(){
        mPanchayatsList.clear();
        mPanchayatList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getPanchayat(Common.ACCESS_TOKEN,districtid);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONArray jsonArray = new JSONArray(response1);
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int panchayatid = jsonObject.getInt("PanchayatId");
                            String panchayatName = jsonObject.getString("PanchayatName");
                            Log.e(TAG,jsonObject.getString("PanchayatName"));
                            mPanchayatList.add(panchayatName);
                            mPanchayatsList.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts(panchayatid,panchayatName));
                        }
                        pd.dismiss();
                        setSpinnerPanchayat(mPanchayatList);
                    }else{
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Log.e(TAG,e.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                Log.e(TAG,t.toString());
            }
        });
    }


    @Override
    public void onValidationSucceeded() {
//        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
        Snackbar.make(li_parent_lay,"success",Snackbar.LENGTH_SHORT).show();
        sharedPreferenceHelpher.putPersonalInfo(et_district.getText().toString(),
                et_panchayat.getText().toString(),
                et_mobileno.getText().toString(),
                et_emailid.getText().toString(),
                et_fathername.getText().toString(),
                et_mothername.getText().toString(),
                et_husbandwifename.getText().toString(),
                et_permanent_address.getText().toString(),
                et_pincode.getText().toString(),
                et_per_permanent_address.getText().toString(),
                et_per_pincode.getText().toString(),
                parseDate(et_deathdate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd"),
                selectedGender,
                et_name.getText().toString(),
                et_age.getText().toString(),
                et_age_years.getText().toString()
                );

        int current = DeathRegistration_Activity.death_registration_pager.getCurrentItem();
        DeathRegistration_Activity.death_registration_pager.setCurrentItem(current+1);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isvalidated = false;
        for(ValidationError error : errors){

            View view = error.getView();

            String message = error.getCollatedErrorMessage(getActivity());

            //display the error message
            if(view instanceof EditText){
                ((EditText) view).setError(message);
                view.requestFocus();

            }else{
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                Snackbar.make(li_parent_lay,message,Snackbar.LENGTH_SHORT).show();

            }
        }
    }
    public String parseDate(String date, String givenformat, String resultformat) {

        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;

        try {
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            result = sdf1.format(sdf.parse(date));

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            sdf = null;
            sdf1 = null;
        }
        return result;
    }
}

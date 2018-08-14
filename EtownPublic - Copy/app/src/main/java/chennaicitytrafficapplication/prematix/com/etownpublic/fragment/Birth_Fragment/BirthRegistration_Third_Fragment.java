package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Birth_Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Birth_Modules.BirthRegistration_Activity;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Delivery;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.EducationBean;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.OccupationBean;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.RelegionBean;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.StateBean;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Town;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class BirthRegistration_Third_Fragment extends Fragment implements Validator.ValidationListener {
    public static final String TAG = BirthRegistration_Third_Fragment.class.getSimpleName();
    @NotEmpty
    @Nullable
    @BindView(R.id.et_stateName)EditText et_stateName;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_districtName)EditText et_districtName;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_town_village)EditText et_town_village;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_town_villageName)EditText et_town_villageName;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_religion)EditText et_religion;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_fatherEdu)EditText et_fatherEdu;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_mother_Edu)EditText et_mother_Edu;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_father_occ)EditText et_father_occ;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_mother_occ)EditText et_mother_occ;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_mother_age_marriage)EditText et_mother_age_marriage;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_mother_age_birth)EditText et_mother_age_birth;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_no_children_alive)EditText et_no_children_alive;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_child_weight)EditText et_child_weight;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_method_delivery)EditText et_method_delivery;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_type_atten_delivery)EditText et_type_atten_delivery;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_duration_pregnancy)EditText et_duration_pregnancy;
    @NotEmpty
    @Nullable
    @BindView(R.id.btn_final)Button btn_final;

    //int declarations
    public int stateCode;
    public int district_code;
    public int town_code;
    public int religion_code;
    public int father_edu_code;
    public int mother_edu_code;
    public int father_occ_code;
    public int mother_occ_code;
    public int attn_code;

    //String declarations
    public String selected_district,selected_panchayat,string_town_village;

    //Bean declarations
    List<StateBean> mStateBeanlist = new ArrayList<>();
    List<Districts> mStateDistrictBeanlist = new ArrayList<>();
    List<Town> mTownBeanlist = new ArrayList<>();
    List<RelegionBean> mReligionBeanlist = new ArrayList<>();
    List<EducationBean> mEducationBeanlist = new ArrayList<>();
    List<OccupationBean> mOccupationBeanlist = new ArrayList<>();
    ArrayList<Delivery> mAtntypeBeanList = new ArrayList<>();

    //String list declarations
    ArrayList<String> mStatestringlist = new ArrayList<>();
    ArrayList<String> mStateDistrictstringlist = new ArrayList<>();
    ArrayList<String> mTown_village = new ArrayList<>();
    ArrayList<String> mTownStringList = new ArrayList<>();
    ArrayList<String> mReligionStringList = new ArrayList<>();
    ArrayList<String> mEducationStringList = new ArrayList<>();
    ArrayList<String> mOccupationStringList = new ArrayList<>();
    ArrayList<String> mDeliverymethodList = new ArrayList<>();
    ArrayList<String> mAtntypeList = new ArrayList<>();

    //Object declarations
    SharedPreferenceHelper sharedPreferenceHelpher;
    Validator validator;

    //SpinnerDialog
    SpinnerDialog spinnerDialog;

    private String  mMobileNo, mEmailId, mDOB, mGender,mChildName,mFatherName, mMotherName,mFatherAadhaarNo,mMotherAadhaarNo, mPerAddress,
            mPerPincode, mBirthAddress,mBirthPincode, mBirthPlace, mHospitalCode,mHospitalName, mDoorNo,mWardNo,mStreetCode,mStreetName;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_birthregistration_third,container,false);
        ButterKnife.bind(this,v);
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferenceHelpher = SharedPreferenceHelper.getInstance(getActivity());

        selected_district = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_District");
        selected_panchayat = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_Panchayat");
        mMobileNo = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_Mobno");
        mEmailId = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_EmailId");
        mDOB = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_DOB");
        mGender = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_Gender");
        mChildName = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_ChildName");
        mFatherName = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_FatherName");
        mMotherName = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_MotherName");
        mFatherAadhaarNo = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_FatherAadharNo");
        mMotherAadhaarNo = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_MotherAadharNo");

        mPerPincode = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_Pincode");
        mBirthAddress = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_per_PermenantAddress");
        mBirthPincode = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_per_Pincode");
        mBirthPlace = sharedPreferenceHelpher.getSpecificValues("PREF_BirthPlace");
        mPerAddress = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_PermenantAddress");
        mHospitalCode = sharedPreferenceHelpher.getSpecificValues("PREF_hospital_code");
        mHospitalName = sharedPreferenceHelpher.getSpecificValues("PREF_HospitalName");
        mDoorNo = sharedPreferenceHelpher.getSpecificValues("PREF_DoorNo");
        mWardNo = sharedPreferenceHelpher.getSpecificValues("PREF_WardNo");
        mStreetCode = sharedPreferenceHelpher.getSpecificValues("PREF_streetcode");
        mStreetName = sharedPreferenceHelpher.getSpecificValues("PREF_streetName");

        et_stateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStateNames();
            }
        });
        et_districtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateCode!=0){
                    getDistricts();
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mDeliverymethodList.add("NORMAL");
        mDeliverymethodList.add("CESAREAN");
        mDeliverymethodList.add("FORCEPS");
        mDeliverymethodList.add("VACCUM DELIVERY EPSIOTOMY");
        mDeliverymethodList.add("LABOUR NATURAL");



        mTown_village.add("Town");
        mTown_village.add("Village");
        et_town_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerTownVillage(mTown_village);
            }
        });
        et_town_villageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(district_code!=0){
                    if(et_town_village.getText().toString().equalsIgnoreCase("Town")){
                        getTownNames();
                        et_town_villageName.setClickable(false);
                    }else if(et_town_village.getText().toString().equalsIgnoreCase("Village")){
                        Log.e("xfhjfg","<=Village=>");
                        et_town_villageName.setClickable(true);
                    }
                }else{
                    Toast.makeText(getActivity(), "Please select the district!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReligionStringList.size()>0){
                    setSpinnerReligion(mReligionStringList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_fatherEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEducationStringList.size()>0){
                    setSpinnerFatherEdu(mEducationStringList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_mother_Edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEducationStringList.size()>0){
                    setSpinnerMotherEdu(mEducationStringList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_father_occ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOccupationStringList.size()>0){
                    setSpinnerFatherOccu(mOccupationStringList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_mother_occ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOccupationStringList.size()>0){
                    setSpinnerMotherOccu(mOccupationStringList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_method_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDeliverymethodList.size()>0){
                    setSpinnerMethodDelivery(mDeliverymethodList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        et_type_atten_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAtntypeList.size()>0){
                    setSpinnerattendant(mAtntypeList);
                }else{
                    Toast.makeText(getActivity(), "Please select the state", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
        return v;
    }
    public void getTownNames(){
        mTownBeanlist.clear();
        mTownStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"DistrictTown",String.valueOf(Math.round(stateCode)),String.valueOf(Math.round(district_code)),"");
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    JSONArray jsonArray = records.getJSONArray("recordsets");
                    if(jsonArray.length()>0){
                        JSONArray townArray = jsonArray.getJSONArray(0);
                        if(townArray.length()>0){
                            for(int i=0;i<townArray.length();i++){
                                JSONObject townObjects = townArray.getJSONObject(i);
                                mTownBeanlist.add(new Town(townObjects.getInt("TownCode"),townObjects.getString("TownName")));
                                mTownStringList.add(townObjects.getString("TownName"));
                            }
                            pd.dismiss();
                            setSpinnerTown(mTownStringList);

                        }else{
                            pd.dismiss();
                        }
                    }else{
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
                Log.e(TAG,t.toString());
            }
        });
    }
    public void getStateNames(){
        mStateBeanlist.clear();
        mStatestringlist.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"BirthDeath","",selected_district,selected_panchayat);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    JSONArray jsonArray = records.getJSONArray("recordsets");
                    if(jsonArray.length()>0){

                        JSONArray religionArray = jsonArray.getJSONArray(0);
                        if(religionArray.length()>0){
                            for(int i=0;i<religionArray.length();i++){
                                JSONObject religionObjects = religionArray.getJSONObject(i);
                                mReligionBeanlist.add(new RelegionBean(religionObjects.getInt("ReligionCode"),religionObjects.getString("ReligionDescription")));
                                mReligionStringList.add(religionObjects.getString("ReligionDescription"));
                            }
                        }
                        JSONArray educationArray = jsonArray.getJSONArray(1);
                        if(educationArray.length()>0){
                            for(int i=0;i<educationArray.length();i++){
                                JSONObject educationObject = educationArray.getJSONObject(i);
                                mEducationBeanlist.add(new EducationBean(educationObject.getInt("EducationCode"),educationObject.getString("EducationDescription")));
                                mEducationStringList.add(educationObject.getString("EducationDescription"));
                            }
                        }
                        JSONArray occupationArray = jsonArray.getJSONArray(2);
                        if(occupationArray.length()>0){
                            for(int i=0;i<occupationArray.length();i++){
                                JSONObject occupationObject = occupationArray.getJSONObject(i);
                                mOccupationBeanlist.add(new OccupationBean(occupationObject.getInt("OccupationCode"),occupationObject.getString("OccupationDescription")));
                                mOccupationStringList.add(occupationObject.getString("OccupationDescription"));
                            }
                        }
                        JSONArray delatnArray = jsonArray.getJSONArray(4);
                        if(delatnArray.length()>0){
                            for(int i=0;i<delatnArray.length();i++){
                                JSONObject delatnObject = delatnArray.getJSONObject(i);
                                mAtntypeBeanList.add(new Delivery(delatnObject.getInt("DelAtnCode"),delatnObject.getString("DelAtnDescription")));
                                mAtntypeList.add(delatnObject.getString("DelAtnDescription"));
                            }
                        }
                        JSONArray stateArray = jsonArray.getJSONArray(5);
                        if(stateArray.length()>0){
                            for(int i=0;i<stateArray.length();i++){
                                JSONObject stateObjects = stateArray.getJSONObject(i);
                                mStateBeanlist.add(new StateBean(stateObjects.getInt("StateCode"),stateObjects.getString("StateName")));
                                mStatestringlist.add(stateObjects.getString("StateName"));
                            }


                        }
                        //put at the end
                            pd.dismiss();
                            setSpinnerState(mStatestringlist);

                    }else{
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
                Log.e(TAG,t.toString());
            }
        });
    }
    public void setSpinnerState(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select State", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_stateName.setText(arrayList.get(i));
                stateCode = mStateBeanlist.get(i).getmStatecode();

            }
        });
    }
    public void setSpinnerTown(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Town", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_town_villageName.setText(arrayList.get(i));
                town_code = mTownBeanlist.get(i).getmTowncode();

            }
        });
    }
    public void setSpinnerTownVillage(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                if(arrayList.get(i).equalsIgnoreCase("Town")){
                    et_town_villageName.setClickable(false);
                    et_town_villageName.getText().clear();
                }else if(arrayList.get(i).equalsIgnoreCase("Village")){
                    et_town_villageName.setClickable(true);
                    et_town_villageName.getText().clear();
                }
                et_town_village.setText(arrayList.get(i));
                string_town_village = arrayList.get(i);

            }
        });
    }
    public void getDistricts(){
        mStateDistrictBeanlist.clear();
        mStateDistrictstringlist.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"StateDistrict",String.valueOf(Math.round(stateCode)),selected_district,selected_panchayat);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    JSONArray jsonArray = records.getJSONArray("recordsets");
                    if(jsonArray.length()>0){
                        JSONArray districtArray = jsonArray.getJSONArray(0);
                        if(districtArray.length()>0){
                            for(int i=0;i<districtArray.length();i++){
                                JSONObject streetObjects = districtArray.getJSONObject(i);
                                mStateDistrictBeanlist.add(new Districts(streetObjects.getInt("DistrictCode"),streetObjects.getString("DistrictName")));
                                mStateDistrictstringlist.add(streetObjects.getString("DistrictName"));
                            }
                            pd.dismiss();
                            setSpinnerDistrict(mStateDistrictstringlist);

                        }else{
                            pd.dismiss();
                        }
                    }else{
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
                Log.e(TAG,t.toString());
            }
        });
    }
    public void setSpinnerDistrict(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Hospital", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_districtName.setText(arrayList.get(i));
                district_code = mStateDistrictBeanlist.get(i).getmDistrictId();
               /* et_panchayat.getText().clear();
                Log.e("distt== ", "" + mdistrictList.get(i).getMdistrictName() + " -- " + mdistrictList.get(i).getMdistrictId());

                districtid = mdistrictList.get(i).getMdistrictId();*/

            }
        });
    }
    public void setSpinnerReligion(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Religion", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_religion.setText(arrayList.get(i));
                religion_code = mReligionBeanlist.get(i).getmReligioncode();

            }
        });
    }
    public void setSpinnerFatherEdu(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Education", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_fatherEdu.setText(arrayList.get(i));
                father_edu_code = mEducationBeanlist.get(i).getmEducationcode();

            }
        });
    }
    public void setSpinnerMotherEdu(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Education", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_mother_Edu.setText(arrayList.get(i));
                mother_edu_code = mEducationBeanlist.get(i).getmEducationcode();

            }
        });
    }
    public void setSpinnerFatherOccu(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Occupation", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_father_occ.setText(arrayList.get(i));
                father_occ_code = mOccupationBeanlist.get(i).getMoccupationcode();

            }
        });
    }
    public void setSpinnerMotherOccu(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Occupation", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_mother_occ.setText(arrayList.get(i));
                mother_occ_code = mOccupationBeanlist.get(i).getMoccupationcode();

            }
        });
    }
    public void setSpinnerMethodDelivery(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Delivary Method", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_method_delivery.setText(arrayList.get(i));

            }
        });
    }
    public void setSpinnerattendant(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Attendant", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_type_atten_delivery.setText(arrayList.get(i));
                attn_code = mAtntypeBeanList.get(i).getDelcode();

            }
        });
    }

    public void birthSaveApi(){
       String nameT_v = null;
        if(et_town_village.getText().toString().equalsIgnoreCase("Town")){
            nameT_v = String.valueOf(Math.round(town_code));
        }else if(et_town_village.getText().toString().equalsIgnoreCase("Village")){
            nameT_v = et_town_villageName.getText().toString();
        }
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.saveBirthDetails(Common.ACCESS_TOKEN,selected_district,selected_panchayat,mMobileNo,mEmailId,mDOB,
                mGender,mChildName,mFatherName,mMotherName,mFatherAadhaarNo,mMotherAadhaarNo,mPerAddress,
                mPerPincode,mBirthAddress,mBirthPincode,mBirthPlace,mHospitalCode,mHospitalName,
                mDoorNo,mWardNo,mStreetCode,mStreetName,String.valueOf(Math.round(stateCode)),
                String.valueOf(Math.round(district_code)),et_town_village.getText().toString(),nameT_v,
                String.valueOf(Math.round(religion_code)),"",String.valueOf(Math.round(father_edu_code)),String.valueOf(Math.round(mother_edu_code)),String.valueOf(Math.round(father_occ_code)),String.valueOf(Math.round(mother_occ_code)),
                et_mother_age_marriage.getText().toString(),et_mother_age_birth.getText().toString(),et_no_children_alive.getText().toString(),et_child_weight.getText().toString(),et_method_delivery.getText().toString(),
                String.valueOf(Math.round(attn_code)),et_duration_pregnancy.getText().toString(),"Android");
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd",response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result",response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    String resp = records.getString( "message");
                    Log.e("response=>",resp);
                    pd.dismiss();
                    if(resp.startsWith("Success")){
                        Toast.makeText(getActivity(),"Registered successfully!!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),BirthRegistration_Activity.class);
                        startActivity(intent);
//                        sendMessage("9952102045",resp);
                    }else{
                        Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                   Log.e("resulttt",records.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
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

        birthSaveApi();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){

            View view = error.getView();

            String message = error.getCollatedErrorMessage(getActivity());

            //display the error message
            if(view instanceof EditText){
                ((EditText) view).setError(message);
                view.requestFocus();

            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

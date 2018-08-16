package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Death_Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Death_Modules.DeathRegistration_Activity;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
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

public class DeathRegistration_Third_Fragment extends Fragment implements Validator.ValidationListener {
    public static final String TAG = DeathRegistration_Third_Fragment.class.getSimpleName();
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


    @Nullable
    @BindView(R.id.et_Deceased_occ)EditText et_Deceased_occ;

    @Nullable
    @BindView(R.id.et_type_atten_death)EditText et_type_atten_death;

    @Nullable
    @BindView(R.id.li_parent_lay)LinearLayout li_parent_lay;

    @Nullable
    @BindView(R.id.et_act_disease)EditText et_act_disease;

    @Nullable
    @BindView(R.id.btn_final)Button btn_final;

    @Nullable
    @BindView(R.id.tv_medically_certify_no)TextView tv_medically_certify_no;
    @Nullable
    @BindView(R.id.tv_medically_certify_yes)TextView tv_medically_certify_yes;
    @Nullable
    @BindView(R.id.tv_death_occur_pregnant_no)TextView tv_death_occur_pregnant_no;
    @Nullable
    @BindView(R.id.tv_death_occur_pregnant_yes)TextView tv_death_occur_pregnant_yes;
    @Nullable
    @BindView(R.id.tv_Chew_Tobacco_no)TextView tv_Chew_Tobacco_no;
    @Nullable
    @BindView(R.id.tv_Chew_Tobacco_yes)TextView tv_Chew_Tobacco_yes;
    @Nullable
    @BindView(R.id.tv_Chew_arecaunt_no)TextView tv_Chew_arecaunt_no;
    @Nullable
    @BindView(R.id.tv_Chew_arecaunt_yes)TextView tv_Chew_arecaunt_yes;

    @Nullable
    @BindView(R.id.tv_smoke_no)TextView tv_smoke_no;
    @Nullable
    @BindView(R.id.tv_smoke_yes)TextView tv_smoke_yes;
    @Nullable
    @BindView(R.id.tv_alcohol_no)TextView tv_alcohol_no;
    @Nullable
    @BindView(R.id.tv_alcohol_yes)TextView tv_alcohol_yes;

    @Nullable
    @BindView(R.id.tv_year_Chew_Tobacco)LinearLayout tv_year_Chew_Tobacco;
    @Nullable
    @BindView(R.id.tv_year_Chew_arecaunt)LinearLayout tv_year_Chew_arecaunt;
    @Nullable
    @BindView(R.id.tv_year_smoking)LinearLayout tv_year_smoking;
    @Nullable
    @BindView(R.id.tv_year_alcohol)LinearLayout tv_year_alcohol;


    @Nullable
    @BindView(R.id.et_year_Chew_Tobacco)EditText et_year_Chew_Tobacco;
    @Nullable
    @BindView(R.id.et_year_Chew_arecaunt)EditText et_year_Chew_arecaunt;
    @Nullable
    @BindView(R.id.et_year_smoking)EditText et_year_smoking;
    @Nullable
    @BindView(R.id.et_year_alcohol)EditText et_year_alcohol;

    public String mMedically_certified,mispregnant,mchew_toboco,marecaunt,msmoke,malcohol;

    //int declarations
    public int stateCode;
    public int district_code;
    public int diceasecode;
    public int town_code;
    public int religion_code;
    public int father_edu_code;
    public int mother_edu_code;
    public int father_occ_code;
    public int mother_occ_code;
    public int attn_code;

    //String declarations
    String selected_district,selected_panchayat,string_town_village;

    //Bean declarations
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.StateBean> stateBeanlist = new ArrayList<>();
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts> stateDistrictBeanlist = new ArrayList<>();
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Town> townBeanlist = new ArrayList<>();
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.RelegionBean> religionBeanlist = new ArrayList<>();
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.EducationBean> educationBeanlist = new ArrayList<>();
    public List<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.OccupationBean> occupationBeanlist = new ArrayList<>();
    public ArrayList<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Delivery> atntypeBeanList = new ArrayList<>();
    public ArrayList<chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Disease> diseasetypeBeanList = new ArrayList<>();

    //String list declarations
    public ArrayList<String> statestringlist = new ArrayList<>();
    public ArrayList<String> stateDistrictstringlist = new ArrayList<>();
    public ArrayList<String> town_village = new ArrayList<>();
    public ArrayList<String> townStringList = new ArrayList<>();
    public ArrayList<String> religionStringList = new ArrayList<>();
    public ArrayList<String> educationStringList = new ArrayList<>();
    public ArrayList<String> occupationStringList = new ArrayList<>();
    public ArrayList<String> deliverymethodList = new ArrayList<>();
    public ArrayList<String> atntypeList = new ArrayList<>();
    public ArrayList<String> diseasetypeList = new ArrayList<>();


    //Object declarations
    SharedPreferenceHelper sharedPreferenceHelpher;
    Validator validator;

    //SpinnerDialog
    SpinnerDialog spinnerDialog;


    public String mobileNo, emailId, dOD, gender,deseasedName,fatherName, motherName,age,ageType, perAddress,husbandwifename,
    PerPincode, deathAddress,deathPincode, deathPlace, hospitalCode,hospitalName, doorNo,wardNo,streetCode,streetName,
    otheraddress,otherpincode;




    View v;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_deathregistration_third,container,false);
        ButterKnife.bind(this,v);
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferenceHelpher = SharedPreferenceHelper.getInstance(getActivity());



        selected_district = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_District");
        selected_panchayat = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Panchayat");
        mobileNo = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Mobno");
        emailId = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Emailid");
        fatherName = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_FatherName");
        motherName = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_MotherName");
        husbandwifename = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_husband_wife_name");

        deathAddress = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_PermenantAddress");
        deathPincode = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Pincode");
        perAddress = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_per_PermenantAddress");
        PerPincode = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_per_Pincode");

        dOD = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_DOD");
        gender = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Gender");
        deseasedName = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_Name");
        age = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_age");
        ageType = sharedPreferenceHelpher.getSpecificValues("PREF_death_reg_age_type");


        deathPlace = sharedPreferenceHelpher.getSpecificValues("PREF_death_place");
        hospitalName = sharedPreferenceHelpher.getSpecificValues("PREF_death_hospitalName");
        doorNo = sharedPreferenceHelpher.getSpecificValues("PREF_death_doorNo");
        wardNo = sharedPreferenceHelpher.getSpecificValues("PREF_death_wardNo");
        streetCode = sharedPreferenceHelpher.getSpecificValues("PREF_death_streetcode");
        streetName = sharedPreferenceHelpher.getSpecificValues("PREF_death_streetName");

        hospitalCode = sharedPreferenceHelpher.getSpecificValues("PREF_death_hospital_code");
        otheraddress = sharedPreferenceHelpher.getSpecificValues("PREF_death_other_address");
        otherpincode = sharedPreferenceHelpher.getSpecificValues("PREF_death_other_pincode");

        mMedically_certified = "No";
        mispregnant = "No";
        mchew_toboco = "No";
        marecaunt = "No";
        msmoke = "No";
        malcohol="No";


//        getStateNames();

        et_stateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStateNames();
            }
        });
        et_districtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDistricts();
            }
        });
        deliverymethodList.add("NORMAL");
        deliverymethodList.add("CESAREAN");
        deliverymethodList.add("FORCEPS");
        deliverymethodList.add("VACCUM DELIVERY EPSIOTOMY");
        deliverymethodList.add("LABOUR NATURAL");

       /* atntypeList.add("DOCTOR NURSE OR TRAINED MIDWIFE");
        atntypeList.add("TRADITIONAL BIRTH ATTENDENT");
        atntypeList.add("RELATIVES AND OTHERS");
        atntypeList.add("NO MEDICAL ATTENSION");
        atntypeList.add("INSTITUTIONAL - GOVERMENT");
        atntypeList.add("INSTITUTIONAL - PRIVATE OR NON GOVERMENT");*/

        town_village.add("Town");
        town_village.add("Village");
        et_town_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerTownVillage(town_village);
            }
        });
        et_town_villageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(string_town_village.equalsIgnoreCase("Town")){
                    getTownNames();

                }
            }
        });
        et_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(religionStringList.size()>0){
                    setSpinnerReligion(religionStringList);
                }

            }
        });

        et_Deceased_occ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerFatherOccu(occupationStringList);
            }
        });
        et_act_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerDisease(diseasetypeList);
            }
        });
        et_type_atten_death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSpinnerattendant(atntypeList);
            }
        });

        tv_medically_certify_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMedically_certified = "No";
                tv_medically_certify_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_medically_certify_no.setTextColor(getResources().getColor(R.color.white));
                tv_medically_certify_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_medically_certify_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_medically_certify_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMedically_certified = "Yes";
                tv_medically_certify_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_medically_certify_yes.setTextColor(getResources().getColor(R.color.white));
                tv_medically_certify_no.setTextColor(getResources().getColor(R.color.black));
                tv_medically_certify_no.setBackgroundResource(R.drawable.ed_border_box);
            }
        });

        tv_death_occur_pregnant_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mispregnant = "No";
                tv_death_occur_pregnant_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_death_occur_pregnant_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_death_occur_pregnant_no.setTextColor(getResources().getColor(R.color.white));
                tv_death_occur_pregnant_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_death_occur_pregnant_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mispregnant = "Yes";
                tv_death_occur_pregnant_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_death_occur_pregnant_no.setBackgroundResource(R.drawable.ed_border_box);

                tv_death_occur_pregnant_yes.setTextColor(getResources().getColor(R.color.white));
                tv_death_occur_pregnant_no.setTextColor(getResources().getColor(R.color.black));
            }
        });



        tv_Chew_Tobacco_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mchew_toboco = "No";
                tv_Chew_Tobacco_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_Chew_Tobacco_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_Chew_Tobacco.setVisibility(View.GONE);
                et_year_Chew_Tobacco.setText("0");

                tv_Chew_Tobacco_no.setTextColor(getResources().getColor(R.color.white));
                tv_Chew_Tobacco_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_Chew_Tobacco_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mchew_toboco = "Yes";
                tv_Chew_Tobacco_no.setBackgroundResource(R.drawable.ed_border_box);
                tv_Chew_Tobacco_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_year_Chew_Tobacco.setVisibility(View.VISIBLE);
                et_year_Chew_Tobacco.setText("0");

                tv_Chew_Tobacco_yes.setTextColor(getResources().getColor(R.color.white));
                tv_Chew_Tobacco_no.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_Chew_arecaunt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marecaunt = "No";
                tv_Chew_arecaunt_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_Chew_arecaunt_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_Chew_arecaunt.setVisibility(View.GONE);
                et_year_Chew_arecaunt.setText("0");

                tv_Chew_arecaunt_no.setTextColor(getResources().getColor(R.color.white));
                tv_Chew_arecaunt_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_Chew_arecaunt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marecaunt = "Yes";
                tv_Chew_arecaunt_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_Chew_arecaunt_no.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_Chew_arecaunt.setVisibility(View.VISIBLE);
                et_year_Chew_arecaunt.setText("0");

                tv_Chew_arecaunt_yes.setTextColor(getResources().getColor(R.color.white));
                tv_Chew_arecaunt_no.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_smoke_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msmoke = "No";
                tv_smoke_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_smoke_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_smoking.setVisibility(View.GONE);
                et_year_smoking.setText("0");

                tv_smoke_no.setTextColor(getResources().getColor(R.color.white));
                tv_smoke_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_smoke_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msmoke = "Yes";
                tv_smoke_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_smoke_no.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_smoking.setVisibility(View.VISIBLE);
                et_year_smoking.setText("0");

                tv_smoke_yes.setTextColor(getResources().getColor(R.color.white));
                tv_smoke_no.setTextColor(getResources().getColor(R.color.black));

            }
        });
        tv_alcohol_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                malcohol="No";
                tv_alcohol_no.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_alcohol_yes.setBackgroundResource(R.drawable.ed_border_box);
                tv_year_alcohol.setVisibility(View.GONE);
                et_year_alcohol.setText("0");

                tv_alcohol_no.setTextColor(getResources().getColor(R.color.white));
                tv_alcohol_yes.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tv_alcohol_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                malcohol="Yes";
                tv_alcohol_no.setBackgroundResource(R.drawable.ed_border_box);
                tv_alcohol_yes.setBackgroundResource(R.drawable.ed_border_box_selected);
                tv_year_alcohol.setVisibility(View.VISIBLE);
                et_year_alcohol.setText("0");

                tv_alcohol_yes.setTextColor(getResources().getColor(R.color.white));
                tv_alcohol_no.setTextColor(getResources().getColor(R.color.black));
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
        townBeanlist.clear();
        townStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"DistrictTown",String.valueOf((int)Math.round(stateCode)),String.valueOf((int)Math.round(district_code)),"");
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
                                townBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Town(townObjects.getInt("TownCode"),townObjects.getString("TownName")));
                                townStringList.add(townObjects.getString("TownName"));
                            }
                            pd.dismiss();
                            setSpinnerTown(townStringList);

                        }else{
                            pd.dismiss();
                        }
                    }
                    pd.dismiss();
//                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    Snackbar.make(li_parent_lay,"Data not found",Snackbar.LENGTH_SHORT).show();
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
        stateBeanlist.clear();
        statestringlist.clear();
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
                                religionBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.RelegionBean(religionObjects.getInt("ReligionCode"),religionObjects.getString("ReligionDescription")));
                                religionStringList.add(religionObjects.getString("ReligionDescription"));
                            }
                        }
                        JSONArray educationArray = jsonArray.getJSONArray(1);
                        if(educationArray.length()>0){
                            for(int i=0;i<educationArray.length();i++){
                                JSONObject educationObject = educationArray.getJSONObject(i);
                                educationBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.EducationBean(educationObject.getInt("EducationCode"),educationObject.getString("EducationDescription")));
                                educationStringList.add(educationObject.getString("EducationDescription"));
                            }
                        }
                        JSONArray occupationArray = jsonArray.getJSONArray(2);
                        if(occupationArray.length()>0){
                            for(int i=0;i<occupationArray.length();i++){
                                JSONObject occupationObject = occupationArray.getJSONObject(i);
                                occupationBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.OccupationBean(occupationObject.getInt("OccupationCode"),occupationObject.getString("OccupationDescription")));
                                occupationStringList.add(occupationObject.getString("OccupationDescription"));
                            }
                        }
                        JSONArray delatnArray = jsonArray.getJSONArray(4);
                        if(delatnArray.length()>0){
                            for(int i=0;i<delatnArray.length();i++){
                                JSONObject delatnObject = delatnArray.getJSONObject(i);
                                atntypeBeanList.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Delivery(delatnObject.getInt("DelAtnCode"),delatnObject.getString("DelAtnDescription")));
                                atntypeList.add(delatnObject.getString("DelAtnDescription"));
                            }
                        }
                        JSONArray stateArray = jsonArray.getJSONArray(5);
                        if(stateArray.length()>0){
                            for(int i=0;i<stateArray.length();i++){
                                JSONObject stateObjects = stateArray.getJSONObject(i);
                                stateBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.StateBean(stateObjects.getInt("StateCode"),stateObjects.getString("StateName")));
                                statestringlist.add(stateObjects.getString("StateName"));
                            }


                        }
                        JSONArray diseaseArray = jsonArray.getJSONArray(6);
                        if(diseaseArray.length()>0){
                            for(int i=0;i<diseaseArray.length();i++){
                                JSONObject diseaseObjects = diseaseArray.getJSONObject(i);
                                diseasetypeBeanList.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Disease(diseaseObjects.getInt("DiseaseCode"),diseaseObjects.getString("DiseaseDescription")));
                                diseasetypeList.add(diseaseObjects.getString("DiseaseDescription"));
                            }


                        }
                        //put at the end
                            pd.dismiss();
                        if(statestringlist.size()>0){
                            setSpinnerState(statestringlist);
                        }


                    }else{
                        pd.dismiss();
//                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                        Snackbar.make(li_parent_lay,"Data not found",Snackbar.LENGTH_SHORT).show();
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
                stateCode = stateBeanlist.get(i).getmStatecode();

            }
        });
    }
    public void setSpinnerDisease(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Disease", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_act_disease.setText(arrayList.get(i));
                diceasecode = diseasetypeBeanList.get(i).getmDiseasecode();

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
                town_code = townBeanlist.get(i).getmTowncode();

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
                    et_town_villageName.setFocusable(false);
                }else{
                    et_town_villageName.setFocusable(false);
                }
                et_town_village.setText(arrayList.get(i));
                string_town_village = arrayList.get(i);

            }
        });
    }
    public void getDistricts(){
        stateDistrictBeanlist.clear();
        stateDistrictstringlist.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"StateDistrict",String.valueOf((int)Math.round(stateCode)),selected_district,selected_panchayat);
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
                                stateDistrictBeanlist.add(new chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts(streetObjects.getInt("DistrictCode"),streetObjects.getString("DistrictName")));
                                stateDistrictstringlist.add(streetObjects.getString("DistrictName"));
                            }
                            pd.dismiss();
                            setSpinnerDistrict(stateDistrictstringlist);

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
                district_code = stateDistrictBeanlist.get(i).getmDistrictId();
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
                religion_code = religionBeanlist.get(i).getmReligioncode();

            }
        });
    }
    public void setSpinnerFatherOccu(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Occupation", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_Deceased_occ.setText(arrayList.get(i));
                father_occ_code = occupationBeanlist.get(i).getMoccupationcode();

            }
        });
    }
    public void setSpinnerattendant(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Attendant", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_type_atten_death.setText(arrayList.get(i));
                attn_code = atntypeBeanList.get(i).getDelcode();

            }
        });
    }

    public void deathSaveApi(){
       String nameT_v = null;
        if(et_town_village.getText().toString().equalsIgnoreCase("Town")){
            nameT_v = String.valueOf((int)Math.round(town_code));
        }else if(et_town_village.getText().toString().equalsIgnoreCase("Village")){
            nameT_v = et_town_villageName.getText().toString();
        }
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.saveDeathDetails(Common.ACCESS_TOKEN,selected_district,selected_panchayat,mobileNo,emailId,dOD,
                gender,age,ageType,deseasedName,husbandwifename,fatherName,motherName,deathAddress,deathPincode,perAddress,
                PerPincode,deathPlace,hospitalCode,hospitalName,
                doorNo,wardNo,streetCode,streetName,otheraddress,otherpincode,String.valueOf((int)Math.round(stateCode)),
                String.valueOf((int)Math.round(district_code)),et_town_village.getText().toString(),nameT_v,
                String.valueOf((int)Math.round(religion_code)),"",String.valueOf((int)Math.round(father_occ_code)),
                et_type_atten_death.getText().toString(),mMedically_certified, String.valueOf((int)Math.round(diceasecode)),mispregnant,msmoke,
                et_year_smoking.getText().toString(),mchew_toboco,et_year_Chew_Tobacco.getText().toString(),marecaunt,et_year_Chew_arecaunt.getText().toString(),malcohol,et_year_alcohol.getText().toString(),"Android");
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
//                        Toast.makeText(getActivity(),"Registered successfully!!",Toast.LENGTH_SHORT).show();
                        Snackbar.make(li_parent_lay,"Registered successfully!!",Snackbar.LENGTH_SHORT).show();
//                        sendMessage("9952102045",resp);
                        Intent intent = new Intent(getActivity(),DeathRegistration_Activity.class);
                        startActivity(intent);
                    }else{
//                        Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
                        Snackbar.make(li_parent_lay,resp,Snackbar.LENGTH_SHORT).show();
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

    public void sendMessage(String phoneNumber,String msg)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String message = "hii "+msg;

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading");
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        String SMS_URL = "http://smsstreet.in/websms/sendsms.aspx?" +
                "userid=prematix&password=matixpre&" +
                "sender=PAYPRE&" +
                "mobileno=" + phoneNumber + "&" +
                "msg=" + message + "";


      StringRequest stringRequest = new StringRequest(Request.Method.GET, SMS_URL, new com.android.volley.Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
                if(response.equalsIgnoreCase("Success")){
//                    Toast.makeText(getActivity(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
                    Snackbar.make(li_parent_lay,"SMS sent successfully",Snackbar.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(getActivity(), "Track your status using mobile number", Toast.LENGTH_SHORT).show();
                    Snackbar.make(li_parent_lay,"Track your status using mobile number",Snackbar.LENGTH_SHORT).show();
                }
              dialog.dismiss();
          }
      }, new com.android.volley.Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
                dialog.dismiss();
          }
      });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onValidationSucceeded() {
       /* sharedPreferenceHelpher.putMotherResidence(et_stateName.getText().toString(),
                et_districtName.getText().toString(),
                et_town_village.getText().toString(),
                et_town_villageName.getText().toString(),
                et_religion.getText().toString(),
                et_fatherEdu.getText().toString(),
                et_mother_Edu.getText().toString(),
                et_father_occ.getText().toString(),
                et_mother_occ.getText().toString(),
                et_mother_age_marriage.getText().toString(),
                et_mother_age_birth.getText().toString(),
                et_no_children_alive.getText().toString(),
                et_child_weight.getText().toString(),
                et_method_delivery.getText().toString(),
                et_type_atten_delivery.getText().toString(),
                et_duration_pregnancy.getText().toString()
        );*/
        deathSaveApi();
      /*  int current = BirthRegistration_Activity.birth_registration_pager.getCurrentItem();
        BirthRegistration_Activity.birth_registration_pager.setCurrentItem(current+1);*/
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
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                Snackbar.make(li_parent_lay,message,Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}

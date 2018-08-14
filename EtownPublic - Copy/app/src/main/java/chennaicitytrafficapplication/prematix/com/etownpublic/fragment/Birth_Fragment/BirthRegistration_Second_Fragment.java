package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Birth_Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter.StreetAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper;
import chennaicitytrafficapplication.prematix.com.etownpublic.listener.RecyclerClickListener;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.HospitalBean;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Street_Pojo;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class BirthRegistration_Second_Fragment extends Fragment implements Validator.ValidationListener {
    View v;
    Typeface avvaiyarfont;
    Validator validator;
    SpinnerDialog spinnerDialog;
    public final String TAG = BirthRegistration_Second_Fragment.class.getSimpleName();

    //Hospital List
    List<HospitalBean> mhospitalBeanslist = new ArrayList<>();
    ArrayList<Street_Pojo> mstreetBeansList = new ArrayList<>();

    //String Hospital list
    ArrayList<String> mhospitalStringList = new ArrayList<>();
    ArrayList<String> mwardStringList = new ArrayList<>();
    ArrayList<String> mstreetStringList = new ArrayList<>();

    ArrayList<String> mplace_birth_list = new ArrayList<>();
    @NotEmpty
    @Nullable
    @BindView(R.id.et_placeofbirth)EditText et_placeofbirth;
    @Nullable
    @BindView(R.id.et_hospname)EditText et_hospname;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_doorno)EditText et_doorno;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_wardno)EditText et_wardno;
    @NotEmpty
    @Nullable
    @BindView(R.id.et_streetno)EditText et_streetno;
    @NotEmpty
    @Nullable
    @BindView(R.id.btn_next_second)Button btn_next_second;

    //String declarations
    public String selected_district;
    public String selected_panchayat;
    public String selected_ward;

    //integer declarations
    public int selected_hospital_code;
    public String street_code;

    //Object creation
    SharedPreferenceHelper sharedPreferenceHelpher;
    StreetAdapter streetAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_birthregistration_second,container,false);
        avvaiyarfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/avvaiyar.ttf");
        ButterKnife.bind(this,v);
        onClicks();

        //Object initialization
        sharedPreferenceHelpher = SharedPreferenceHelper.getInstance(getActivity());

        selected_district = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_District");
        selected_panchayat = sharedPreferenceHelpher.getSpecificValues("PREF_brth_reg_Panchayat");

        //lis items for place of birth
        mplace_birth_list.add("House");
        mplace_birth_list.add("Hospital");

//        getStreetNameHo();

        et_placeofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerPlaceBirth(mplace_birth_list);
            }
        });
        et_hospname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_placeofbirth.getText().toString().equalsIgnoreCase("House")){
                    if(mhospitalStringList.size()>0){
                        setSpinnerHospital(mhospitalStringList);
                    }else{
                        getHospitalNames();
                    }
                }
            }
        });
        et_wardno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mwardStringList.size()>0){
                    setSpinnerWard(mwardStringList);
                }else{
                    getWardNo();
                }
            }
        });
        et_streetno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(selected_ward!=null){
                       getStreetName();
               }else{
                   Toast.makeText(getActivity(), "Please select the ward!!", Toast.LENGTH_SHORT).show();
               }

            }
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        return v;
    }


    public void onClicks()
    {
        btn_next_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               validator.validate();
            }
        });
    }
    public void getStreetNameList(){
        mstreetStringList.clear();
        mstreetBeansList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"Street",selected_ward,selected_district,selected_panchayat);
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
                        JSONArray streetArray = jsonArray.getJSONArray(0);
                        if(streetArray.length()>0){
                            for(int i=0;i<streetArray.length();i++){
                                JSONObject streetObjects = streetArray.getJSONObject(i);
                                mstreetBeansList.add(new Street_Pojo(streetObjects.getString("StreetName"),streetObjects.getString("StreetNo")));
                                mstreetStringList.add(streetObjects.getString("StreetName"));
                            }
                            for(int k=0;k<mstreetBeansList.size();k++){
                                Log.e("street code",mstreetBeansList.get(k).getStreetNo());
                                Log.e("street code==>",street_code);
                                Log.e("street name==>",mstreetBeansList.get(k).getStreetName());
                                if(street_code.equalsIgnoreCase(mstreetBeansList.get(k).getStreetNo())){
                                    et_streetno.setText(mstreetBeansList.get(k).getStreetName());
                                    et_streetno.setTypeface(avvaiyarfont);
                                }
                            }
                            pd.dismiss();

                        }
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
    public void setSpinnerPlaceBirth(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Place of Birth", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_placeofbirth.setText(arrayList.get(i));
                if(arrayList.get(i).equalsIgnoreCase("House")){
                    et_hospname.getText().clear();
                    et_hospname.setFocusable(false);
                    et_doorno.getText().clear();
                    et_wardno.getText().clear();
                    et_streetno.getText().clear();
                }

            }
        });
    }
    public void setSpinnerHospital(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select Hospital", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_hospname.setText(arrayList.get(i));
                selected_hospital_code = mhospitalBeanslist.get(i).getmHospitalCode();
                getSpecificHospitalDetails();

            }
        });
    }
    public void setSpinnerWard(final ArrayList<String> arrayList) {

        spinnerDialog = new SpinnerDialog(getActivity(), arrayList, "Select WardNo", R.style.DialogAnimations_SmileWindow, "CLOSE");



        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                et_wardno.setText(arrayList.get(i));
                selected_ward=arrayList.get(i);
               /* et_panchayat.getText().clear();
                Log.e("distt== ", "" + mdistrictList.get(i).getMdistrictName() + " -- " + mdistrictList.get(i).getMdistrictId());

                districtid = mdistrictList.get(i).getMdistrictId();*/

            }
        });
    }
    public void getHospitalNames(){
        mhospitalStringList.clear();
        mhospitalBeanslist.clear();
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
                        JSONArray hospitalArray = jsonArray.getJSONArray(7);
                        if(hospitalArray.length()>0){
                            for(int i=0;i<hospitalArray.length();i++){
                                JSONObject hospitalObjects = hospitalArray.getJSONObject(i);
                                mhospitalBeanslist.add(new HospitalBean(hospitalObjects.getInt("HospitalCode"),hospitalObjects.getString("HospitalName")));
                                mhospitalStringList.add(hospitalObjects.getString("HospitalName"));
                            }
                            pd.dismiss();
                            setSpinnerHospital(mhospitalStringList);

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
    public void getWardNo(){
        mwardStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"Ward","",selected_district,selected_panchayat);
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
                        JSONArray wardArray = jsonArray.getJSONArray(0);
                        if(wardArray.length()>0){
                            for(int i=0;i<wardArray.length();i++){
                                JSONObject wardObjects = wardArray.getJSONObject(i);
                                mwardStringList.add(wardObjects.getString("WardNo"));
                            }
                            pd.dismiss();
                            setSpinnerWard(mwardStringList);

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
    public void getStreetName(){
        mstreetBeansList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"Street",selected_ward,selected_district,selected_panchayat);
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
                        JSONArray streetArray = jsonArray.getJSONArray(0);
                        if(streetArray.length()>0){
                            for(int i=0;i<streetArray.length();i++){
                                JSONObject streetObjects = streetArray.getJSONObject(i);
                                mstreetBeansList.add(new Street_Pojo(streetObjects.getString("StreetName"),streetObjects.getString("StreetNo")));
                                mstreetStringList.add(streetObjects.getString("StreetName"));
                            }
                            pd.dismiss();
                            showStreetValuesInAlert(mstreetBeansList);

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
    public void getSpecificHospitalDetails(){
        mstreetStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN,"Hospital",String.valueOf(Math.round(selected_hospital_code)),selected_district,selected_panchayat);
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
                        JSONArray detailsArray = jsonArray.getJSONArray(0);
                        if(detailsArray.length()>0){
                            for(int i=0;i<detailsArray.length();i++){
                                JSONObject detailsObjects = detailsArray.getJSONObject(i);
                                et_doorno.setText(detailsObjects.getString("DoorNo"));
                                et_wardno.setText(detailsObjects.getString("WardCode"));
                                selected_ward = detailsObjects.getString("WardCode");
                                street_code =detailsObjects.getString("StreetCode");
                            }
                            for(int k=0;k<mstreetBeansList.size();k++){
                                Log.e("street code",mstreetBeansList.get(k).getStreetNo());
                                Log.e("street code==>",street_code);
                                Log.e("street name==>",mstreetBeansList.get(k).getStreetName());
                                if(street_code.equalsIgnoreCase(mstreetBeansList.get(k).getStreetNo())){
                                    et_streetno.setText(mstreetBeansList.get(k).getStreetName());
                                }
                            }
                            pd.dismiss();
                            getStreetNameList();
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

    public void showStreetValuesInAlert(final ArrayList<Street_Pojo> data_list) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View v2 = getLayoutInflater().inflate(R.layout.recycler_alert, null);



        mBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });



        RecyclerView recyclerView = v2.findViewById(R.id.recycler);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v2.getContext());
        recyclerView.setLayoutManager(layoutManager);


        streetAdapter = new StreetAdapter(data_list);
        recyclerView.setAdapter(streetAdapter);
        streetAdapter.notifyDataSetChanged();

        mBuilder.setView(v2);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemTouchListener() {
            @Override
            public void OnItemCLikc(View view, int position) {
                et_streetno.setText(streetAdapter.getList().get(position).getStreetName());
                street_code = streetAdapter.getList().get(position).getStreetNo();
                et_streetno.setTypeface(avvaiyarfont);
                dialog.dismiss();
            }
        }));
    }


    @Override
    public void onValidationSucceeded() {
        sharedPreferenceHelpher.putPlaceofBirth(et_placeofbirth.getText().toString(),
                                                String.valueOf(Math.round(selected_hospital_code)),

                                                et_hospname.getText().toString(),
                                                et_doorno.getText().toString(),
                                                et_wardno.getText().toString(),
                                                street_code,
                                                et_streetno.getText().toString()
                );
        int current = BirthRegistration_Activity.birth_registration_pager.getCurrentItem();
        BirthRegistration_Activity.birth_registration_pager.setCurrentItem(current+1);
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

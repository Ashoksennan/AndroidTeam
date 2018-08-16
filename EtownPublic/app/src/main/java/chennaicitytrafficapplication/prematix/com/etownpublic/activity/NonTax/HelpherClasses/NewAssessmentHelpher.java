package chennaicitytrafficapplication.prematix.com.etownpublic.activity.NonTax.HelpherClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Birth_Death.Districts;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInstance;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.RetrofitInterface;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.StreetBean;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 8/10/2018.
 */

public class NewAssessmentHelpher {
    public static NewAssessmentHelpher newAssessmentHelpher = null;
    public static final String TAG = NewAssessmentHelpher.class.getSimpleName();
    private Activity activity;
    Typeface avvaiyarfont;
    ArrayList<Districts> mDistrictsList = new ArrayList<>();
    ArrayList<StreetBean> mStreetBeansList = new ArrayList<>();
    ArrayList<String> mDistrictList = new ArrayList<>();
    ArrayList<Districts> mPanchayatList = new ArrayList<>();
    ArrayList<String> mPanchayatsList = new ArrayList<>();
    ArrayList<String> mWardStringList = new ArrayList<>();
    ArrayList<String> mStreetStringList = new ArrayList<>();
    int mdistrictCode;
    int mpanchayatCode;
    String mselectedWard;
    String mselectedStreetCode;
    String mselectedStreet;
    String mselectedDistrict;
    String mselectedPanchayat;
    SpinnerDialog spinnerDialog;

    private NewAssessmentHelpher(Activity activity) {
        this.activity = activity;
        this.avvaiyarfont = Typeface.createFromAsset(activity.getAssets(), "fonts/avvaiyar.ttf");
    }

    public static NewAssessmentHelpher getInstance(Activity activity) {
        if (newAssessmentHelpher == null) {
            newAssessmentHelpher = new NewAssessmentHelpher(activity);
        }
        return newAssessmentHelpher;
    }

    public void getDistricts(final EditText editText) {
        mDistrictsList.clear();
        mDistrictList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getDistricts(Common.ACCESS_TOKEN);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd", response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result", response1);
                try {
                    JSONArray jsonArray = new JSONArray(response1);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int districtid = jsonObject.getInt("DistrictId");
                            String districtname = jsonObject.getString("DistrictName");
                            Log.e(TAG, jsonObject.getString("DistrictName"));
                            mDistrictList.add(districtname);
                            mDistrictsList.add(new Districts(districtid, districtname));
                        }
                        pd.dismiss();
                        setSpinnerComman(mDistrictList, editText, "District");

                    } else {
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, t.toString());
            }
        });

    }

    public void getPanchayat(final EditText editText) {
        mPanchayatsList.clear();
        mPanchayatList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getPanchayat(Common.ACCESS_TOKEN, mdistrictCode);
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd", response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result", response1);
                try {
                    JSONArray jsonArray = new JSONArray(response1);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int panchayatid = jsonObject.getInt("PanchayatId");
                            String panchayatName = jsonObject.getString("PanchayatName");
                            Log.e(TAG, jsonObject.getString("PanchayatName"));
                            mPanchayatList.add(new Districts(panchayatid, panchayatName));
                            mPanchayatsList.add(panchayatName);
                        }
                        pd.dismiss();
                        setSpinnerComman(mPanchayatsList, editText, "Panchayat");
                    } else {
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    pd.dismiss();
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, t.toString());
            }
        });
    }

    public void getWardNo(final EditText editText) {
        mWardStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Log.e("Panchayat", mselectedPanchayat + "dghbdf");
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN, "Ward", "", mselectedDistrict, mselectedPanchayat);
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
                        JSONArray wardArray = jsonArray.getJSONArray(0);
                        if (wardArray.length() > 0) {
                            for (int i = 0; i < wardArray.length(); i++) {
                                JSONObject wardObjects = wardArray.getJSONObject(i);
                                mWardStringList.add(wardObjects.getString("WardNo"));
                            }
                            pd.dismiss();
                            setSpinnerComman(mWardStringList, editText, "WardNo");

                        }
                    } else {
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

    public void getStreetName(final EditText editText) {
        mStreetBeansList.clear();
        mStreetStringList.clear();
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.getMasterDetails(Common.ACCESS_TOKEN, "Street", mselectedWard, mselectedDistrict, mselectedPanchayat);
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
                        JSONArray streetArray = jsonArray.getJSONArray(0);
                        if (streetArray.length() > 0) {
                            for (int i = 0; i < streetArray.length(); i++) {
                                JSONObject streetObjects = streetArray.getJSONObject(i);
                                mStreetBeansList.add(new StreetBean(streetObjects.getString("StreetNo"), streetObjects.getString("StreetName")));
                                mStreetStringList.add(streetObjects.getString("StreetName"));
                            }
                            pd.dismiss();
                            setSpinnerComman(mStreetStringList, editText, "StreetName");

                        }
                    } else {
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

    public void saveNewAssessmentNonTax(String name, String mobileno, String emailid, String leasename, String doorno) {
        RetrofitInterface retrofitInterface = RetrofitInstance.getRetrofit().create(RetrofitInterface.class);
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        Call districtresult = retrofitInterface.saveNonTaxDetails(Common.ACCESS_TOKEN, mselectedDistrict, mselectedPanchayat, name, mobileno, emailid, leasename, doorno, mselectedWard, mselectedStreetCode, mselectedStreet, "Android");
        districtresult.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("fgd", response.toString());
                String response1 = new Gson().toJson(response.body());
                Log.e("result", response1);
                try {
                    JSONObject records = new JSONObject(response1);
                    String resp = records.getString("message");
                    Log.e("response=>", resp);
                    pd.dismiss();
                    if (resp.startsWith("Success")) {
                        Toast.makeText(activity, "Registered successfully!!", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("resulttt", records.toString());
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

    public void setSpinnerComman(final ArrayList<String> arrayList, final EditText editText, final String title) {


        spinnerDialog = new SpinnerDialog(activity, arrayList, "Select " + title, R.style.DialogAnimations_SmileWindow, "CLOSE");


        spinnerDialog.showSpinerDialog();

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                editText.setText(arrayList.get(i));
//                Log.e("distt== ", "" + mDistrictList.get(i).getMdistrictName() + " -- " + mDistrictList.get(i).getMdistrictId());
                switch (title) {
                    case "District":
                        mdistrictCode = mDistrictsList.get(i).getmDistrictId();
                        mselectedDistrict = arrayList.get(i);
                        break;
                    case "Panchayat":
                        mpanchayatCode = mPanchayatList.get(i).getmDistrictId();
                        mselectedPanchayat = arrayList.get(i);
                        break;
                    case "WardNo":
                        mselectedWard = arrayList.get(i);
                        break;
                    case "StreetName":
                        mselectedStreetCode = mStreetBeansList.get(i).getmStreetNum();
                        editText.setText(arrayList.get(i));
                        mselectedStreet = arrayList.get(i);
                        editText.setTypeface(avvaiyarfont);
                        break;
                }

//                district_Name = mDistrictList.get(i).getMdistrictName();

            }
        });
    }

}

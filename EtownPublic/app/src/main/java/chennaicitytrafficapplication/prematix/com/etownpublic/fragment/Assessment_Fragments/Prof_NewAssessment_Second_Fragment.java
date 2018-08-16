package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Service;
import dmax.dialog.SpotsDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


/**
 * import com.e
 * Created by priyadharshini on 31/07/2018.
 */

public class Prof_NewAssessment_Second_Fragment extends Fragment {
    View v;
    Button btn_next;
    TextInputLayout inputLayoutSlabRate, inputLayoutDoorNo, inputLayoutTrade_name, inputLayoutWardNo, inputLayoutStreetName;
    LinearLayout liOranization;
    RadioGroup rgProfession;
    EditText etOraganizationName, etDesinationName, etSlabRate, etDoorNo, etTradeName, etWardNo, etStreetName;
    TextView tvChooseTrade, tvUploadTrade, tvChooseIncomeDoc, tvIncomeUploadDoc;
    LinearLayout rootlayout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String prefername = "pager";

    HashMap<String, String> buildingzoneHash = new HashMap<>();
    HashMap<String, String> buildingusageHash = new HashMap<>();
    HashMap<String, String> buildingtypeHash = new HashMap<>();
    HashMap<String, String> streetHash = new HashMap<>();
    HashMap<String, String> organizationHash = new HashMap<>();
    HashMap<String, String> slabHash = new HashMap<>();


    SpinnerDialog spinnerDialogWardNo;
    SpinnerDialog spinnerDialogStreetName;
    SpinnerDialog spinnerDialogSlabRate;
    SpinnerDialog spinnerDialogOrganizationName;


    ArrayList<String> buildinglocation_items = new ArrayList<>();
    ArrayList<String> buildingusage_items = new ArrayList<>();
    ArrayList<String> buildingtype_items = new ArrayList<>();
    ArrayList<String> wardno_items = new ArrayList<>();
    ArrayList<String> streetName_items = new ArrayList<>();
    ArrayList<String> organization_items = new ArrayList<>();
    ArrayList<String> slabDesc_items = new ArrayList<>();


    String districtName, panchayatName, mobileNo, emailId, OrganizationVal, SlabVal, AssType = "Trade", name, StreetCode;

    Service service;
    File fileTradle;
    File fileIncome;

    int PICK_TRADE_REQUEST = 100;
    int PICK_INCOME_REQUEST = 101;

    SpotsDialog spotsDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.prof_fragment_new_ass_second, container, false);

        btn_next = (Button) v.findViewById(R.id.btn_next);


        init(v);
        onClicks();

        return v;
    }

    private void init(View v) {


        inputLayoutWardNo = (TextInputLayout) v.findViewById(R.id.input_layout_wardNo);
        inputLayoutStreetName = (TextInputLayout) v.findViewById(R.id.input_layout_streetName);
        inputLayoutSlabRate = (TextInputLayout) v.findViewById(R.id.input_layout_slabrate);
        inputLayoutDoorNo = (TextInputLayout) v.findViewById(R.id.input_layout_doorNo);
        inputLayoutTrade_name = (TextInputLayout) v.findViewById(R.id.input_layout_trade_name);

        rootlayout = (LinearLayout) v.findViewById(R.id.rootLinear);

        liOranization = (LinearLayout) v.findViewById(R.id.li_organization);
        rgProfession = (RadioGroup) v.findViewById(R.id.rg_profession);


        etWardNo = (EditText) v.findViewById(R.id.input_wardNo);
        etStreetName = (EditText) v.findViewById(R.id.input_streetName);
        etSlabRate = (EditText) v.findViewById(R.id.input_slabrate);
        etDoorNo = (EditText) v.findViewById(R.id.input_doorNo);
        etTradeName = (EditText) v.findViewById(R.id.input_tradlename);
        etOraganizationName = (EditText) v.findViewById(R.id.input_organizationname);
        etDesinationName = (EditText) v.findViewById(R.id.input_designationname);


        etTradeName.addTextChangedListener(new MyTextWatcher(etTradeName));
        etDoorNo.addTextChangedListener(new MyTextWatcher(etDoorNo));
        etWardNo.addTextChangedListener(new MyTextWatcher(etWardNo));
        etStreetName.addTextChangedListener(new MyTextWatcher(etStreetName));
        etSlabRate.addTextChangedListener(new MyTextWatcher(etSlabRate));


        preferences = getActivity().getSharedPreferences(prefername, Context.MODE_PRIVATE);
        editor = preferences.edit();

        districtName = preferences.getString(Common.param_distrct, "");
        panchayatName = preferences.getString(Common.param_panchayat, "");
        mobileNo = preferences.getString(Common.param_mobileNo, "");
        emailId = preferences.getString(Common.param_emailId, "");
        name = preferences.getString(Common.param_name, "");

        tvChooseTrade = (TextView) v.findViewById(R.id.tv_choose_trade);
        tvUploadTrade = (TextView) v.findViewById(R.id.tv_upload_trade);
        tvChooseIncomeDoc = (TextView) v.findViewById(R.id.tv_choose_incomeDoc);
        tvIncomeUploadDoc = (TextView) v.findViewById(R.id.tv_upload_incomeDoc);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(Common.baseUrl).client(client).build().create(Service.class);


        getWardDetails(districtName, panchayatName);
        getBuildingMaster(districtName, panchayatName);
        getSlabRate(districtName, panchayatName);
        getOrganizationDetails(districtName, panchayatName);

        spinnerDialogWardNo = new SpinnerDialog(getActivity(), wardno_items, "Select or Search WardNo", "Close");// With No Animation
        spinnerDialogStreetName = new SpinnerDialog(getActivity(), streetName_items, "Select or Search Street", "Close");// With No Animation
        spinnerDialogSlabRate = new SpinnerDialog(getActivity(), slabDesc_items, "Select Slab Rate", "Close");
        spinnerDialogOrganizationName = new SpinnerDialog(getActivity(), organization_items, "Select Organization", "Close");


    }


    public void onClicks() {


        tvChooseTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_TRADE_REQUEST);
            }
        });


        tvChooseIncomeDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_INCOME_REQUEST);
            }
        });


        tvUploadTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(fileTradle);
            }
        });


        tvIncomeUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(fileIncome);

            }
        });


        etSlabRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogSlabRate.showSpinerDialog();
            }
        });


        spinnerDialogSlabRate.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                for (Map.Entry<String, String> entry : slabHash.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(s)) {
//                        Log.e(TAG, "building locatiom " + entry.getKey());
                        SlabVal = entry.getKey();
                    }
                }
                etSlabRate.setText(s);
            }
        });

        etOraganizationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogOrganizationName.showSpinerDialog();
            }
        });


        spinnerDialogOrganizationName.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {


                for (Map.Entry<String, String> entry : organizationHash.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(s)) getOrganizationById(entry.getKey(),districtName,panchayatName);
//                        Log.e(TAG, "building locatiom " + entry.getKey());
                        OrganizationVal = entry.getKey();
//                        getOrganizationById(OrganizationVal,districtName,panchayatName);

                }
                etOraganizationName.setText(s);
            }
        });


        spinnerDialogStreetName.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                for (Map.Entry<String, String> entry : streetHash.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(s)) {
//                        Log.e(TAG, "building locatiom " + entry.getKey());
                        StreetCode = entry.getKey();
                    }
                }
                etStreetName.setText(s);
                etStreetName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/avvaiyar.ttf"));
//                    }
//                }
            }
        });

        etStreetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinnerDialogStreetName.showSpinerDialog();
            }
        });


        spinnerDialogWardNo.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                etWardNo.setText(s);

                System.out.println("ggggg" + s.toString());
                getStreetDetails(s, districtName, panchayatName);

            }
        });

        etWardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinnerDialogWardNo.showSpinerDialog();
            }
        });


        rgProfession.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_tradle:
                        liOranization.setVisibility(View.GONE);
                        inputLayoutTrade_name.setVisibility(View.VISIBLE);
                        AssType = "Trade";
                        etDoorNo.setText("");
                        etStreetName.setText("");
                        etWardNo.setText("");
                        etDoorNo.setEnabled(true);
                        etStreetName.setEnabled(true);
                        etWardNo.setEnabled(true);
                        break;



                    case R.id.rb_organization:
                        liOranization.setVisibility(View.VISIBLE);
                        inputLayoutTrade_name.setVisibility(View.GONE);
                        AssType = "Organization";
                        etDoorNo.setText("");
                        etStreetName.setText("");
                        etWardNo.setText("");
                        etDoorNo.setEnabled(false);
                        etStreetName.setEnabled(false);
                        etWardNo.setEnabled(false);
                        break;

                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_TRADE_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            fileTradle = new File(filePath);

            tvChooseTrade.setText(String.valueOf(fileTradle.getName()));

        }


        if (requestCode == PICK_INCOME_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            fileIncome = new File(filePath);

            tvChooseIncomeDoc.setText(String.valueOf(fileIncome.getName()));

        }
    }


    public void uploadImage(File file) {


        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("uploading");
        dialog.show();


        retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body, name);
        req.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.e("respomnse", "" + response.message());

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("res", "" + t.getMessage());
                dialog.dismiss();

            }
        });
    }


    private void showFileChooser(int PICK_IMAGE_REQUEST) {
        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "image/*",
                        "text/html",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), PICK_IMAGE_REQUEST);

    }

    private void submit() {

        if (!validateTradleName()) {
            return;
        }
        if (!validateDoorNo()) {
            return;
        }
        if (!validateWardNo()) {
            return;
        }
        if (!validateStreetName()) {
            return;
        }
        if (!validateSlabRate()) {
            return;
        }

        NewProfessionAssessement(districtName, panchayatName, name, mobileNo, emailId, AssType, etTradeName.getText().toString(), OrganizationVal, etOraganizationName.getText().toString(), etDesinationName.getText().toString(), etDoorNo.getText().toString(), etWardNo.getText().toString(), StreetCode, etStreetName.getText().toString(), SlabVal, etSlabRate.getText().toString());

    }

    public class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_tradlename:
                    validateTradleName();
                    break;
                case R.id.input_doorNo:
                    validateDoorNo();
                    break;
                case R.id.input_wardNo:
                    validateWardNo();
                    break;
                case R.id.input_streetName:
                    validateStreetName();
                    break;
                case R.id.input_slabrate:
                    validateSlabRate();
                    break;


            }
        }
    }


    private boolean validateSlabRate() {
        if (etSlabRate.getText().toString().trim().isEmpty()) {
            inputLayoutSlabRate.setError(getString(R.string.err_msg_slabrate));
            requestFocus(etSlabRate);
            return false;
        } else {
            inputLayoutSlabRate.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateDoorNo() {
        if (etDoorNo.getText().toString().trim().isEmpty()) {
            inputLayoutDoorNo.setError(getString(R.string.err_msg_doorno));
            requestFocus(etDoorNo);
            return false;
        } else {
            inputLayoutDoorNo.setErrorEnabled(true);
        }
        return true;
    }

    public boolean validateTradleName() {
        if (etTradeName.getText().toString().trim().isEmpty()) {
            inputLayoutTrade_name.setError(getString(R.string.err_msg_tradlename));
            requestFocus(etTradeName);
            return false;
        } else {
            inputLayoutTrade_name.setErrorEnabled(true);
        }
        return true;
    }


    private boolean validateWardNo() {
        if (etWardNo.getText().toString().trim().isEmpty()) {
            inputLayoutWardNo.setError(getString(R.string.err_msg_wardno));
            requestFocus(etWardNo);
            return false;
        } else {
            inputLayoutWardNo.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateStreetName() {
        if (etStreetName.getText().toString().trim().isEmpty()) {
            inputLayoutStreetName.setError(getString(R.string.err_msg_streetname));
            requestFocus(etStreetName);
            return false;
        } else {
            inputLayoutStreetName.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void getWardDetails(String district, String panchayat) {
        String url = null;

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        url = Common.API_TAX_MASTER_DETAILS + "Type=Ward&Input=&District=" + district + "&Panchayat=" + panchayat;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);

                                        String WardNo = jsonObject.getString("WardNo");
                                        wardno_items.add(WardNo);
                                    }

                                }

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                spotsDialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest, "EtownRequest");

    }

    private void getOrganizationDetails(String district, String panchayat) {

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        String url = Common.API_TAX_MASTER_DETAILS +"Type=Organization" +
                "&Input=&District=" + district + "&Panchayat=" + panchayat + "";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                        String OrganizationCode = jsonObject.getString("OrganizationCode");
                                        String OrganizationName = jsonObject.getString("OrganizationName");

                                        organizationHash.put(OrganizationCode, OrganizationName);
                                        organization_items.add(OrganizationName);
                                    }

                                }

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                spotsDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest, "EtownRequest");

    }


    private void getSlabRate(String district, String panchayat) {

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();
        String url = Common.API_TAX_MASTER_DETAILS +"Type=SlabRate&Input=&District=" + district + "&Panchayat=" + panchayat + "";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                        String SlabCode = jsonObject.getString("SlabCode");
                                        String SlabDescription = jsonObject.getString("SlabDescription");

                                        System.out.println("222222222" + SlabDescription);
                                        slabHash.put(SlabCode, SlabDescription);
                                        slabDesc_items.add(SlabDescription);
                                    }

                                }

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                spotsDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest, "EtownRequest");


    }

    private void getStreetDetails(String wardNo, String district, String panchayat) {
        String url = null;


        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        url = Common.API_TAX_MASTER_DETAILS +"Type=Street&Input=" + wardNo + "&District=" + district + "&Panchayat=" + panchayat;

        System.out.println("gggggg" + url);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("222222222" + response.toString());

                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                        String StreetNo = jsonObject.getString("StreetNo");
                                        String StreetName = jsonObject.getString("StreetName");

                                        streetHash.put(StreetNo, StreetName);
                                        streetName_items.add(StreetName);
                                    }

                                }

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                spotsDialog.dismiss();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                spotsDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };

        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest, "EtownRequest");

    }


    private void getOrganizationById(String orgId,String district,String panchayat){


        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        String url="http://www.predemos.com/Etown/EPSTaxMasterDetails?Type=OrgDetails&Input="+orgId+"&District="+district+"&Panchayat="+panchayat+"";

        JsonObjectRequest api_json_organization = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            String DoorNo="",Pincode="",StreetCode="",StreetName="",Wardno="";

            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.getString("recordsets"));


                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);


                        if (jsonArray1.length() > 0) {
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                 DoorNo = jsonObject.getString("DoorNo");
                                 Pincode = jsonObject.getString("Pincode");
                                 StreetCode = jsonObject.getString("StreetCode");
                                 StreetName = jsonObject.getString("StreetName");
                                 Wardno =  jsonObject.getString("WardNo");

                            }

                        }

                    }

                    etDoorNo.setText(DoorNo);
                    etStreetName.setText(StreetName);
                    etStreetName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/avvaiyar.ttf"));
                    etWardNo.setText(Wardno);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                spotsDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };

        AppSingleton.getInstance(getActivity()).addToRequestQueue(api_json_organization, "EtownRequest");

    }


    private void getBuildingMaster(String district, String panchayat) {

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();
        String url = null;
        try {
            url = Common.API_TAX_MASTER_DETAILS +"Type=Property&Input=&District=" + URLEncoder.encode(district, "utf-8") + "&Panchayat=" + URLEncoder.encode(panchayat, "utf-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        buildinglocation_items.clear();
        buildingusage_items.clear();
        buildingtype_items.clear();


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("222222222" + response.toString());

                try {
                    if (response.length() > 0) {

                        JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                        if (jsonArray.length() > 0) {
                            JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);
                            JSONArray jsonArray2 = (JSONArray) jsonArray.get(1);
                            JSONArray jsonArray3 = (JSONArray) jsonArray.get(2);


                            if (jsonArray1.length() > 0) {
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray1.get(j);

                                    String ZoneCode = jsonObject.getString("ZoneCode");
                                    String ZoneName = jsonObject.getString("ZoneName");
                                    buildingzoneHash.put(ZoneCode, ZoneName);
                                    buildinglocation_items.add(ZoneName);
                                }

                                for (Map.Entry<String, String> entry : buildingzoneHash.entrySet()) {
//                                    Log.e(TAG,"hhhhhh"+entry.getKey()+""+entry.getValue());
                                }
                            }

                            if (jsonArray2.length() > 0) {
                                for (int j = 0; j < jsonArray2.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray2.get(j);

                                    String UsageCode = jsonObject.getString("UsageCode");
                                    String UsageName = jsonObject.getString("UsageName");
                                    buildingusageHash.put(UsageCode, UsageName);
                                    buildingusage_items.add(UsageName);
                                }
                            }


                            if (jsonArray3.length() > 0) {
                                for (int j = 0; j < jsonArray3.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray3.get(j);
                                    String TypeCode = jsonObject.getString("TypeCode");
                                    String TypeName = jsonObject.getString("TypeName");
                                    buildingtypeHash.put(TypeCode, TypeName);
                                    buildingtype_items.add(TypeName);

                                }
                            }


                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                spotsDialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };

        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest, "EtownRequest");

    }


    private void NewProfessionAssessement(String district, String panchayat, String name, String mobileNo, String emailId,
                                          String assType, String tradeName, String orgCode, String orgname, String DesgName,
                                          String doorNo, String wardNo, String streetCode, String streetName, String slabCode,
                                          String slabAmount) {

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        Random random = new Random();

        int number = random.nextInt(50) + 1;


        String slabAmt[] = slabAmount.split("-");

        String sla = slabAmt[3];


        System.out.println("kkkkkkkk" + sla);

        String url = null;
        try {
            url = Common.API_NEW_PROFESSIONASS+"RequestNo=" + number + "" +
                    "&District=" + URLEncoder.encode(district, "utf-8") + "&Panchayat=" + URLEncoder.encode(panchayat, "utf-8") + "&Name=" + URLEncoder.encode(name, "utf-8") + "&MobileNo=" + mobileNo + "&" +
                    "EmailId=" + emailId + "&" +
                    "AssType=" + assType + "&TradeName=" + URLEncoder.encode(tradeName, "utf-8") + "&OrgCode=" + orgCode + "&OrgName=" + URLEncoder.encode(orgname, "utf-8") + "" +
                    "&DesgName=" + URLEncoder.encode(DesgName, "utf-8") + "&DoorNo=" + doorNo + "&WardNo=" + wardNo + "&StreetCode=" + streetCode + "&StreetName=" + URLEncoder.encode(streetName, "utf-8") + "&SlabCode=" + slabCode + "&" +
                    "SlabAmount=" + sla + "&EntryType=Android";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String message = response.getString("message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                spotsDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", rootlayout);


                } else if (error instanceof AuthFailureError) {


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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", Common.ACCESS_TOKEN);
                return params;
            }
        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest, "EtownRequest");


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

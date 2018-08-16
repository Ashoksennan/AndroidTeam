package chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Water_NewAssessment_Second_Fragment extends Fragment {
    //    components
    View v;
    Button btn_next;
    TextView tvChooseFile, tvUpload;
    TextInputLayout inputLayoutDoorNo, inputLayoutPropertyTaxNo, inputLayoutTaxName, inputLayoutName, inputLayoutConnectionType, inputLayoutWardNo, inputLayoutStreetName;
    EditText etDoorNo, etPropertTaxNo, etTaxName, etName, etConnectionType, etWardNo, etStreetName;
LinearLayout rootlayout ;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String prefername = "pager";


    HashMap<String, String> streetHash = new HashMap<>();


    SpinnerDialog spinnerDialogWardNo;
    SpinnerDialog spinnerDialogStreetName;
    SpinnerDialog spinnerDialogConnectionType;


    ArrayList<String> wardno_items = new ArrayList<>();
    ArrayList<String> streetName_items = new ArrayList<>();
    ArrayList<String> connectiontype_items = new ArrayList<>();

    String districtName, panchayatName, mobileNo, emailId, name, StreetCode;
    String DoorNo, WardNo, Name, StreetName;

    int PICK_IMAGE_REQUEST = 100;

    File file;

    Service service;

    SpotsDialog spotsDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.water_fragment_new_ass_second, container, false);

        btn_next = (Button) v.findViewById(R.id.btn_next);


        init(v);
        onClicks();

        return v;
    }

    private void init(View v) {


        inputLayoutWardNo = (TextInputLayout) v.findViewById(R.id.input_layout_wardNo);
        inputLayoutStreetName = (TextInputLayout) v.findViewById(R.id.input_layout_streetName);
        inputLayoutPropertyTaxNo = (TextInputLayout) v.findViewById(R.id.input_layout_propertytax_no);
        inputLayoutTaxName = (TextInputLayout) v.findViewById(R.id.input_layout_taxname);
        inputLayoutName = (TextInputLayout) v.findViewById(R.id.input_layout_name);
        inputLayoutConnectionType = (TextInputLayout) v.findViewById(R.id.input_layout_connectiontype);
        inputLayoutDoorNo = (TextInputLayout) v.findViewById(R.id.input_layout_doorNo);
        rootlayout =(LinearLayout)v.findViewById(R.id.rootLinear);

        etWardNo = (EditText) v.findViewById(R.id.input_wardNo);
        etStreetName = (EditText) v.findViewById(R.id.input_streetName);
        etPropertTaxNo = (EditText) v.findViewById(R.id.input_propertytax_no);
        etTaxName = (EditText) v.findViewById(R.id.input_taxname);
        etName = (EditText) v.findViewById(R.id.input_name);
        etConnectionType = (EditText) v.findViewById(R.id.input_connectiontype);
        etDoorNo = (EditText) v.findViewById(R.id.input_doorNo);


        tvChooseFile = (TextView) v.findViewById(R.id.tvChooseFile);
        tvUpload = (TextView) v.findViewById(R.id.tvUpload);


        etPropertTaxNo.addTextChangedListener(new MyTextWatcher(etPropertTaxNo));
        etTaxName.addTextChangedListener(new MyTextWatcher(etTaxName));
        etName.addTextChangedListener(new MyTextWatcher(etName));
        etDoorNo.addTextChangedListener(new MyTextWatcher(etDoorNo));
        etWardNo.addTextChangedListener(new MyTextWatcher(etWardNo));
        etStreetName.addTextChangedListener(new MyTextWatcher(etStreetName));
        etConnectionType.addTextChangedListener(new MyTextWatcher(etConnectionType));





        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(Common.baseUrl).client(client).build().create(Service.class);


//        Add Connection
        connectiontype_items.add("Domestic");
        connectiontype_items.add("NonDomestic");
        connectiontype_items.add("Industries");


        spinnerDialogWardNo = new SpinnerDialog(getActivity(), wardno_items, "Select or Search WardNo", "Close");// With No Animation
        spinnerDialogStreetName = new SpinnerDialog(getActivity(), streetName_items, "Select or Search Street", "Close");// With No Animation
        spinnerDialogConnectionType = new SpinnerDialog(getActivity(), connectiontype_items, "Select or Search Connection Type", "Close");// With No Animation


        etConnectionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogConnectionType.showSpinerDialog();
            }
        });

        spinnerDialogConnectionType.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                etConnectionType.setText(s);
            }
        });


        tvChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePropertyTaxNo()) {
                    return;
                }
                if (!validateTaxName()) {
                    return;
                }

                if (!validateName()) {
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

                if (!validateConnectionType()) {
                    return;
                }

                uploadImage(file);

            }
        });


        preferences = getActivity().getSharedPreferences(prefername, Context.MODE_PRIVATE);
        editor = preferences.edit();

        districtName = preferences.getString(Common.param_distrct, "");
        panchayatName = preferences.getString(Common.param_panchayat, "");
        mobileNo = preferences.getString(Common.param_mobileNo, "");
        emailId = preferences.getString(Common.param_emailId, "");
        name = preferences.getString(Common.param_name, "");




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            file = new File(filePath);

            tvChooseFile.setText(String.valueOf(file.getName()));

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


    private void showFileChooser() {
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


    public void onClicks() {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();

            }
        });

    }

    private void submit() {

        if (!validatePropertyTaxNo()) {
            return;
        }
        if (!validateTaxName()) {
            return;
        }

        if (!validateName()) {
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

        if (!validateConnectionType()) {
            return;
        }

        addNewConnection(districtName, panchayatName, etPropertTaxNo.getText().toString(), etTaxName.getText().toString(), name, mobileNo, emailId, etDoorNo.getText().toString(), etWardNo.getText().toString(), StreetCode, etStreetName.getText().toString(), "");
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


                case R.id.input_propertytax_no:
                    validatePropertyTaxNo();
                    if (!editable.toString().isEmpty())
                        getAsessmentByNo(districtName, panchayatName, editable.toString());

                    break;
                case R.id.input_taxname:
                    validateTaxName();
                    break;
                case R.id.input_name:
                    validateName();
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
                case R.id.input_connectiontype:
                    validateConnectionType();
                    break;
            }
        }

    }


    private boolean validateDoorNo() {
        if (etDoorNo.getText().toString().trim().isEmpty()) {
            inputLayoutDoorNo.setError(getString(R.string.err_msg_doorno));
            requestFocus(etDoorNo);
            return false;
        } else {
            inputLayoutDoorNo.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePropertyTaxNo() {
        if (etPropertTaxNo.getText().toString().trim().isEmpty()) {
            inputLayoutPropertyTaxNo.setError(getString(R.string.err_msg_taxno));
            requestFocus(etPropertTaxNo);
            return false;
        } else {
            inputLayoutPropertyTaxNo.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateTaxName() {
        if (etTaxName.getText().toString().trim().isEmpty()) {
            inputLayoutTaxName.setError(getString(R.string.err_msg_taxname));
            requestFocus(etTaxName);
            return false;

        } else {
            inputLayoutTaxName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateName() {
        if (etName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(etName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateConnectionType() {
        if (etConnectionType.getText().toString().trim().isEmpty()) {
            inputLayoutConnectionType.setError(getString(R.string.err_msg_connectiontype));
            requestFocus(etConnectionType);
            return false;
        } else {
            inputLayoutConnectionType.setErrorEnabled(false);
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


    public void addNewConnection(String district, String panchayat, String propertyTaxNo, String PropertyName, String name, String mobileNo,
                                 String emailId, String doorNo, String wardno, String streetcode, String streetName, String connectionType) {

        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();
        String REQUEST_TAG = "taxBalancePayment";
        Random random = new Random();

        int number = random.nextInt(50) + 1;

        String url = null;
        try {
            url = Common.API_ADD_NEW_CONNECTION+"RequestNo=" + number + "&District=" + district + "" +
                    "&Panchayat=" + panchayat + "&PropertyTaxNo=" + propertyTaxNo + "&PropertyName=" + URLEncoder.encode(PropertyName, "utf-8") + "&Name=" + URLEncoder.encode(name, "utf-8") + "&MobileNo=" + mobileNo + "" +
                    "&EmailId=" + emailId + "&DoorNo=" + doorNo + "&WardNo=" + wardno + "&StreetCode=" + streetcode + "&StreetName=" + URLEncoder.encode(streetName, "utf-8") + "&" +
                    "ConnectionType=" + connectionType + "&EntryType=Android";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String message = response.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("Log", "" + response.toString());
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


        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest,"EtownRequest");


    }

    private void SnackShowTop(String message, View view) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View view_layout = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view_layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        view_layout.setLayoutParams(params);
        snack.show();
    }

    private void getAsessmentByNo(String district, String panchayat, String assno) {
        String url = Common.API_ASSBY_NO+"Type=PSearch&TaxNo=" + assno + "&FinYear=&District=" + district + "&Panchayat=" + panchayat + "";


        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();
        String REQUEST_TAG = "taxBalancePayment";

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = new JSONArray(response.getString("recordsets"));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);


                        if (jsonArray1.length() > 0) {
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject = (JSONObject) jsonArray1.get(j);
                                DoorNo = jsonObject.getString("DoorNo");
                                WardNo = jsonObject.getString("WardNo");
                                Name = jsonObject.getString("Name");
                                StreetName = jsonObject.getString("StreetName");
                                StreetCode = jsonObject.getString("StreetCode");

                            }


                            JSONArray jsonArray2 = (JSONArray) jsonArray.get(1);

                            if (jsonArray2.length() > 0) {
                                etDoorNo.setText("");
                                etWardNo.setText("");
                                etTaxName.setText("");
                                etStreetName.setText("");

                                Toast.makeText(getActivity(), "Sorry clear All Property Tax Due Amount", Toast.LENGTH_SHORT).show();

                            } else {
                                etDoorNo.setText(DoorNo);
                                etWardNo.setText(WardNo);
                                etTaxName.setText(Name);
                                etStreetName.setText(StreetName);
                            }

                        } else {

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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);


    }


}

package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Property;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Birth_Death.Districts;
import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Panchayats;
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

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_DISTRICT_DETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TOWNPANCHAYAT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.GET_ASSESSMENT_PROP;

public class NameTransfer extends AppCompatActivity {

    //Views
    TextInputEditText etDistrict,etPanchayat,etChoosenFileOne,etChoosenFileTwo,etChoosenFileThree;
    EditText edTaxNo,etMobileNo,etEmailId,etTransferName;
    Button submit,buttonSubmitNameTransfer;
    TextView tvName,tvWardNo,tvDoorNo,tvStreetName,tvUploadOne,tvUploadTwo,tvUploadThree;
    LinearLayout llAssessmentDetails,llNameTransfer;
    RelativeLayout rlRoot;

    //ArrayList
    ArrayList<String> distict_items=new ArrayList<>();
    ArrayList<String> panchayat_items=new ArrayList<>();

    ArrayList<chennaicitytrafficapplication.prematix.com.etownpublic.Model.Birth_Death.Districts> districts=new ArrayList<>();
    ArrayList<Panchayats> panchayats=new ArrayList<>();

    //Strings
    String districtName = "",panchayatName="",name="",doorNo="",wardNo="",streetName="",TAG = "NameTransfer--> ",
            fileString = "";

    //Integers
    int districtId,panchayatId;

    //Spinners
    SpinnerDialog spinnerDialogDistrict;
    SpinnerDialog spinnerDialogPanchayat;

    //ProgressDialog
    SpotsDialog progressDialog;

    private String filePath = null;

    private static final int PICK_IMAGE_REQUEST=1;

    //Interface
    Service service;

    //Toolbar
    Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_transfer);

        //Toolbar
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.property_name_transfer);

        //TextInputEditTexr
        etDistrict = (TextInputEditText)findViewById(R.id.et_district);
        etPanchayat = (TextInputEditText)findViewById(R.id.et_panchayat);
        etChoosenFileOne = (TextInputEditText)findViewById(R.id.ed_choosen_file_one);
        etChoosenFileTwo = (TextInputEditText)findViewById(R.id.ed_choosen_file_two);
        etChoosenFileThree = (TextInputEditText)findViewById(R.id.ed_choosen_file_three);

        //EditText
        edTaxNo = (EditText)findViewById(R.id.ed_taxno);
        etMobileNo = (EditText)findViewById(R.id.et_mobile_no);
        etEmailId = (EditText)findViewById(R.id.et_email_id);
        etTransferName = (EditText)findViewById(R.id.et_transfer_name);

        //Button
        submit = (Button)findViewById(R.id.button_submit_name_transfer);
        buttonSubmitNameTransfer = (Button)findViewById(R.id.submit_nametransfer);

        //TextView
        tvName = (TextView)findViewById(R.id.tv_name);
        tvWardNo = (TextView)findViewById(R.id.tv_wardno);
        tvDoorNo = (TextView)findViewById(R.id.tv_doorno);
        tvStreetName = (TextView)findViewById(R.id.tv_streetname);
        tvUploadOne = (TextView)findViewById(R.id.tv_upload_one);
        tvUploadTwo = (TextView)findViewById(R.id.tv_upload_two);
        tvUploadThree = (TextView)findViewById(R.id.tv_upload_three);

        //LinearLayout
        llAssessmentDetails = (LinearLayout)findViewById(R.id.ll_assesmentdetails);
        llNameTransfer = (LinearLayout)findViewById(R.id.ll_nametransfer);

        //RelativeLayout
        rlRoot = (RelativeLayout)findViewById(R.id.rl_root);

        districtList();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(Common.uploadUrl).client(client).build().create(Service.class);

        spinnerDialogPanchayat=new SpinnerDialog(NameTransfer.this,panchayat_items,"Select or Search Panchayat","Close");// With No Animation
        spinnerDialogPanchayat=new SpinnerDialog(NameTransfer.this,panchayat_items,"Select or Search Panchayat", R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation

        spinnerDialogDistrict=new SpinnerDialog(NameTransfer.this,distict_items,"Select or Search District","Close");// With No Animation
        spinnerDialogDistrict=new SpinnerDialog(NameTransfer.this,distict_items,"Select or Search District", R.style.DialogAnimations_SmileWindow,"Close");// With 	Animation

        spinnerDialogDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                etDistrict.setText(item);
                districtName = item;
                districtId = districts.get(position).getmDistrictId();

                etPanchayat.setText("");
                panchayatList(districtId);
            }
        });

        etDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogDistrict.showSpinerDialog();
                panchayat_items.clear();

            }
        });

        spinnerDialogPanchayat.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int position) {
                etPanchayat.setText(s);

                panchayatName = s;
                panchayatId = panchayats.get(position).getPanchayatId();
            }
        });

        etPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDistrict.getText().toString().isEmpty()){
                    spinnerDialogPanchayat.showSpinerDialog();

                }else{

                    Snackbar.make(rlRoot, "Select District First", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        edTaxNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDistrict.getText().toString().isEmpty() && etPanchayat.getText().toString().isEmpty()){

                    Snackbar.make(rlRoot, "Select District and Panchayat", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etDistrict.getText().toString().isEmpty() && !etPanchayat.getText().toString().isEmpty() &&
                        !edTaxNo.getText().toString().isEmpty()){

                    getAssessmentDetails(edTaxNo.getText().toString(),districtName,panchayatName);

                }else {
                    Snackbar.make(rlRoot, "Enter all Values", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        tvUploadOne.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                fileString = "registrationDocument";
                showFileChooser();
            }
        });

        tvUploadTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileString = "legalDocument";
                showFileChooser();
            }
        });

        tvUploadThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileString= "deathCertificate";
                showFileChooser();
            }
        });

        buttonSubmitNameTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etMobileNo.getText().toString().isEmpty() && !etEmailId.getText().toString().isEmpty() &&
                        !etTransferName.getText().toString().isEmpty() && !etChoosenFileOne.getText().toString().isEmpty()){

                    if (filePath != null) {

                        File sourceFile = new File(filePath);

                        if (Common.isNetworkAvailable(getApplicationContext())) {
                        uploadImage(sourceFile);
                        } else {

                            Snackbar.make(rlRoot, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Snackbar.make(rlRoot, "Enter Mobile Number, Email Id and Transfer Name", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void districtList(){

        progressDialog = new SpotsDialog(NameTransfer.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url = API_DISTRICT_DETAILS;

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String districtName = jsonObject.getString("DistrictName");
                                int districtId = jsonObject.getInt("DistrictId");

                                distict_items.add(districtName);

                                districts.add(new Districts(districtId,districtName));
                                progressDialog.dismiss();

                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            SnackShowTop("Time out", rlRoot);
                        } else if (error instanceof AuthFailureError) {
                            SnackShowTop("Connection Time out", rlRoot);
                        } else if (error instanceof ServerError) {
                            SnackShowTop("Could not connect server", rlRoot);
                        } else if (error instanceof NetworkError) {
                            SnackShowTop("Please check the internet connection", rlRoot);
                        } else if (error instanceof ParseError) {
                            SnackShowTop("Parse Error", rlRoot);
                        } else {
                            SnackShowTop(error.getMessage(), rlRoot);
                        }                    }
                }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
    }

    public void panchayatList(int districtId){

        progressDialog = new SpotsDialog(NameTransfer.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url =  API_TOWNPANCHAYAT+districtId;

        JsonArrayRequest request = new JsonArrayRequest(url,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    for(int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String panchayatName = jsonObject.getString("PanchayatName");
                            int panchayatId = jsonObject.getInt("PanchayatId");

                            panchayat_items.add(panchayatName);

                            panchayats.add(new Panchayats(panchayatName,panchayatId));
                            progressDialog.dismiss();

                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        SnackShowTop("Time out", rlRoot);
                    } else if (error instanceof AuthFailureError) {
                        SnackShowTop("Connection Time out", rlRoot);
                    } else if (error instanceof ServerError) {
                        SnackShowTop("Could not connect server", rlRoot);
                    } else if (error instanceof NetworkError) {
                        SnackShowTop("Please check the internet connection", rlRoot);
                    } else if (error instanceof ParseError) {
                        SnackShowTop("Parse Error", rlRoot);
                    } else {
                        SnackShowTop(error.getMessage(), rlRoot);
                    }                        }
            }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request, REQUEST_TAG);
    }

    public void getAssessmentDetails(String taxNo, String districtName, String panchayatName){

        progressDialog = new SpotsDialog(NameTransfer.this);
        progressDialog.show();

        String REQUEST_TAG = "townPanchayat";

        String url = GET_ASSESSMENT_PROP+taxNo+"&FinYear=&District="+districtName+"&Panchayat="+panchayatName;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject recordset = new JSONObject(response.toString());

                            JSONArray firstarray = new JSONArray(recordset.getString("recordsets"));
                            for (int i = 0; i < firstarray.length(); i++) {

                                Log.e("ooofirstarray","---"+firstarray.toString());

                                JSONArray secondArray = (JSONArray) firstarray.get(0);

                                if (secondArray.length() > 0) {
                                    for (int j = 0; j < secondArray.length(); j++) {

                                        JSONObject jsonObject = (JSONObject) secondArray.get(i);

                                        name = jsonObject.getString("Name");
                                        doorNo = jsonObject.getString("DoorNo");
                                        wardNo = jsonObject.getString("WardNo");
                                        streetName = jsonObject.getString("StreetName");

                                        tvName.setText(name);
                                        tvDoorNo.setText(doorNo);
                                        tvWardNo.setText(wardNo);
                                        tvStreetName.setText(streetName);

                                        llAssessmentDetails.setVisibility(View.VISIBLE);
                                        llNameTransfer.setVisibility(View.VISIBLE);

                                        progressDialog.dismiss();
                                        Log.e("ooosecond","---"+jsonObject.toString());

                                    }
                                } else {

                                    Snackbar.make(rlRoot, "No Data Found ", Snackbar.LENGTH_SHORT).show();

                                    llNameTransfer.setVisibility(View.GONE);
                                    progressDialog.dismiss();

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
                VolleyLog.e("Error: ", error.getMessage());

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    SnackShowTop("Time out", rlRoot);
                } else if (error instanceof AuthFailureError) {
                    SnackShowTop("Connection Time out", rlRoot);
                } else if (error instanceof ServerError) {
                    SnackShowTop("Could not connect server", rlRoot);
                } else if (error instanceof NetworkError) {
                    SnackShowTop("Please check the internet connection", rlRoot);
                } else if (error instanceof ParseError) {
                    SnackShowTop("Parse Error", rlRoot);
                } else {
                    SnackShowTop(error.getMessage(), rlRoot);
                }
            }
        }){
            @Override
            public Map<String,String> getHeaders()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);

                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req, REQUEST_TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();

            File file = new File(filePath);

            if (fileString.equalsIgnoreCase("registrationDocument")){
                etChoosenFileOne.setText(file.getName());

            }else if (fileString.equalsIgnoreCase("legalDocument")){

                etChoosenFileTwo.setText(file.getPath());

            }else if (fileString.equalsIgnoreCase("deathCertificate")){

                etChoosenFileThree.setText(file.getPath());
            }

        }
    }

    public void uploadImage(File file){

        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        final ProgressDialog dialog = new ProgressDialog(NameTransfer.this);
        dialog.setMessage("uploading");
        dialog.show();

        Call<ResponseBody> req = service.postImage(body, name);
        req.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.e("respomnse",""+response.message());

                Snackbar.make(rlRoot, ""+response.message(), Snackbar.LENGTH_SHORT).show();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("res",""+t.getMessage());

                Snackbar.make(rlRoot, ""+t.getMessage(), Snackbar.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });
    }

    private void showFileChooser() {
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
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
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), PICK_IMAGE_REQUEST);

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

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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Service;
import dmax.dialog.SpotsDialog;
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
 * Created by priyadharshini on 31/07/2018.
 */

public class NewAssessment_Third_Fragment extends Fragment {
    View v;
    Button btn_next;
    TextView tvChooseRegDoc, tvChooseParentDoc, tvRegChoosePattaDoc;
    TextView tvUploadRegDoc, tvUploadParentDoc, tvUploadPattaDoc;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout rootLayout;
    String prefername = "pager";

    int PICK_REGISTER_DOC_REQUEST = 100;
    int PICK_PARENT_DOC_REQUEST = 101;
    int PICK_PATTA_DOC_REQUEST = 102;
    Service service;
    File fileRegister;
    File fileParent;
    File filePatta;

    String REQUEST_TAG="Etowns";
    SharedPreferences sharedPreferences;


    String pDistrict, pPanchayat, pName, pMobileNo, pEmailId, pBLno, pBLDate,
            pBlockNo, pWardNo, pStreetName, pBuildingZone,
            pBuildingUsage, pBuildingType, pTotalArea,pStreetCode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_new_ass_third, container, false);

        btn_next = (Button) v.findViewById(R.id.btn_next);

        tvChooseRegDoc = (TextView) v.findViewById(R.id.tv_choose_regDoc);
        tvChooseParentDoc = (TextView) v.findViewById(R.id.tv_choose_parentDoc);
        tvRegChoosePattaDoc = (TextView) v.findViewById(R.id.tv_choose_pattaDoc);

        tvUploadRegDoc = (TextView) v.findViewById(R.id.tv_upload_regDoc);
        tvUploadParentDoc = (TextView) v.findViewById(R.id.tv_upload_parent_doc);
        tvUploadPattaDoc = (TextView) v.findViewById(R.id.tv_upload_pattaDoc);

        rootLayout = (LinearLayout)v.findViewById(R.id.rootLinear);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(Common.baseUrl).client(client).build().create(Service.class);


        onClicks();
        getValuesFromAllFragments();

        preferences = getActivity().getSharedPreferences(prefername, Context.MODE_PRIVATE);
        editor = preferences.edit();


        return v;
    }

    public void onClicks() {


        tvChooseRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_REGISTER_DOC_REQUEST);
            }
        });

        tvChooseParentDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_PARENT_DOC_REQUEST);
            }
        });


        tvRegChoosePattaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_PATTA_DOC_REQUEST);
            }
        });


        tvUploadRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(fileRegister);
            }
        });

        tvUploadParentDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(fileParent);
            }
        });

        tvUploadPattaDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(filePatta);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewPropertyAssessment(pDistrict,pPanchayat,pName,pMobileNo,pEmailId,pBLno,pBLDate,pBlockNo,pWardNo,pStreetCode,pStreetName,pBuildingZone,
                        pBuildingUsage, pBuildingType,pTotalArea);

                Toast.makeText(getActivity(), "Completed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_REGISTER_DOC_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            fileRegister = new File(filePath);

            tvChooseRegDoc.setText(String.valueOf(fileRegister.getName()));

        }

        if (requestCode == PICK_PARENT_DOC_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            fileParent = new File(filePath);

            tvChooseParentDoc.setText(String.valueOf(fileParent.getName()));

        }

        if (requestCode == PICK_PATTA_DOC_REQUEST && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            filePatta = new File(filePath);

            tvRegChoosePattaDoc.setText(String.valueOf(filePatta.getName()));

        }
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

    public void uploadImage(File file) {


        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("uploading");
        dialog.show();


        Call<ResponseBody> req = service.postImage(body, name);
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


    public void getValuesFromAllFragments()
    {
        Log.e("im innnn","kjkrgjktjkr");

        sharedPreferences =  getActivity().getSharedPreferences("StepperPreference", Context.MODE_PRIVATE);

        //--------------------------
        pDistrict = sharedPreferences.getString("pDistrict", "Empty");
        pPanchayat = sharedPreferences.getString("pPanchayat", "Empty");
        pName = sharedPreferences.getString("pName", "Empty");
        pMobileNo = sharedPreferences.getString("pMobileNo", "Empty");
        pEmailId = sharedPreferences.getString("pEmailId", "Empty");

        //-------------------------------------------------------------


        pBLno = sharedPreferences.getString("bLicenseNo", "Empty");
        pBLDate = sharedPreferences.getString("bLicenseDate", "Empty");
        pBlockNo = sharedPreferences.getString("BlockNo", "Empty");
        pWardNo = sharedPreferences.getString("wardNo", "Empty");
        pStreetCode = sharedPreferences.getString("streetCode","Empty");
        pStreetName = sharedPreferences.getString("streetName", "Empty");
        pBuildingZone = sharedPreferences.getString("bZone", "Empty");
        pBuildingUsage = sharedPreferences.getString("bUsage", "Empty");
        pBuildingType = sharedPreferences.getString("bType", "Empty");
        pTotalArea = sharedPreferences.getString("totalArea", "Empty");



        Log.e("getAll pDistrict",pDistrict);
        Log.e("getAll pPanchayat",pPanchayat);
        Log.e("getAll pName",pName);
        Log.e("getAll pMobileNo",pMobileNo);
        Log.e("getAll pEmailId",pEmailId);
        Log.e("getAll pBLno",pBLno);
        Log.e("getAll pBLDate",pBLDate);
        Log.e("getAll pBlockNo",pBlockNo);
        Log.e("getAll pWardNo",pWardNo);
        Log.e("getAll pStreetName",pStreetName);
        Log.e("getAll pBuildingZone",pBuildingZone);
        Log.e("getAll pBuildingUsage",pBuildingUsage);
        Log.e("getAll pBuildingType",pBuildingType);
        Log.e("getAll pTotalArea",pTotalArea);


    }
    public String parseDateToddMMyyyy(String inputdate) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputdate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void NewPropertyAssessment(String district, String panchayat, String name,
                                      String mobileNo, String emailId, String BLNo, String BLDate, String BlockNo,
                                      String wardNo, String streetCode, String streetName, String Bzone,
                                      String BUsage, String btype, String totalArea){


        final SpotsDialog spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();

        Random random = new Random();

        int number = random.nextInt(50)+1;

        String url= null;
        try {
            url = "http://www.predemos.com/Etown/EPSPropertyNewAssessment?RequestNo="+number+"&" +
                    "District="+ URLEncoder.encode(district,"utf-8")+"&Panchayat="+ URLEncoder.encode(panchayat,"utf-8")+"&Name="+ URLEncoder.encode(name,"utf-8")+"&MobileNo="+mobileNo+"&" +
                    "EmailId="+emailId+"&BLNo="+BLNo+"&BLDate="+parseDateToddMMyyyy(BLDate)+"&BlockNo="+BlockNo+"&WardNo="+wardNo+"&StreetCode="+streetCode+"&" +
                    "StreetName="+ URLEncoder.encode(streetName,"utf-8")+"&BZone="+Bzone+"&BUsage="+BUsage+"&BType="+btype+"&TotalArea="+totalArea+"&EntryType=Android";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String message = response.getString("message");
                    Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
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


                    SnackShowTop("Time out", rootLayout);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", rootLayout);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", rootLayout);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", rootLayout);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", rootLayout);

                } else {


                    SnackShowTop(error.getMessage(), rootLayout);

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

        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest, REQUEST_TAG);

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

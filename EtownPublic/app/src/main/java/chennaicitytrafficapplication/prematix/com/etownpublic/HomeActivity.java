package chennaicitytrafficapplication.prematix.com.etownpublic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import chennaicitytrafficapplication.prematix.com.etownpublic.VolleySingleton.AppSingleton;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.ContactUs;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.RootNavigation;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.HomeSliderAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;
import dmax.dialog.SpotsDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.ACCESS_TOKEN;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_DISTRICT_DETAILS;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.Common.API_TOWNPANCHAYAT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTDISTRICT;
import static chennaicitytrafficapplication.prematix.com.etownpublic.common.SharedPreferenceHelper.PREF_SELECTPANCHAYAT;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    android.app.AlertDialog waitingDialog;
    HomeSliderAdapter sliderAdapter;
    public static String TAG = HomeActivity.class.getSimpleName();
    DrawerLayout drawer;
    private String mDistrictId, mDistrictName;
    private String mPanchayatId, mPanchayatName;
    SpinnerDialog spinnerDialogDistrict;
    SpinnerDialog spinnerDialogPanchayat;
    SharedPreferences sharedPreference;
    String MyPREFERENCES = "User";
    SharedPreferences.Editor editor;
    private ArrayList<String> mDistrictList = new ArrayList<String>();
    private ArrayList<String> mPanchayatList = new ArrayList<String>();

    private HashMap<Integer, String> mDistrictHashmapitems = new LinkedHashMap<>();

    private HashMap<Integer, String> mPanchayatHashmapitems = new LinkedHashMap<>();
    ExtensiblePageIndicator indicator;
    TextView tv_selectPanchayat, tv_selectDistrict;
    LinearLayout linPropertyTax, liBirthDetails, mlinProfessional_tax, linear_watercharges, linear_nontax, linear_grievances, linear_deathdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        linPropertyTax = findViewById(R.id.lin_property);
        liBirthDetails = findViewById(R.id.li_birth);
        linear_watercharges = findViewById(R.id.linear_watercharges);
        mlinProfessional_tax = findViewById(R.id.lin_professional_tax);
        linear_nontax = findViewById(R.id.linear_nontax);
        linear_deathdetails = findViewById(R.id.linear_deathdetails);
        linear_grievances = findViewById(R.id.linear_grievances);

        drawer = findViewById(R.id.drawer_layout);

        linPropertyTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRootIntent("Property");
            }
        });


        tv_selectPanchayat = findViewById(R.id.tv_select_panchayat);
        tv_selectDistrict = findViewById(R.id.tv_select_district);


        if (!sharedPreference.getString(PREF_SELECTDISTRICT, "").isEmpty())
            tv_selectDistrict.setText(sharedPreference.getString(PREF_SELECTDISTRICT, ""));
        else tv_selectDistrict.setText(R.string.select_district);
        if (!sharedPreference.getString(PREF_SELECTPANCHAYAT, "").isEmpty())
            tv_selectPanchayat.setText(sharedPreference.getString(PREF_SELECTPANCHAYAT, ""));
        else tv_selectPanchayat.setText(R.string.select_panchayat);
        tv_selectPanchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_selectDistrict.getText().toString().equals("")) {
                    Snackbar.make(drawer, R.string.snack_district_search, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                editor.putString(PREF_SELECTPANCHAYAT, "");

                editor.apply();

                spinnerDialogPanchayat.showSpinerDialog();
            }
        });

        tv_selectDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanchayatList.clear();

                tv_selectPanchayat.setText("");
                tv_selectDistrict.setText("");

                editor = sharedPreference.edit();

                editor.putString(PREF_SELECTPANCHAYAT, "");

                editor.apply();

                spinnerDialogDistrict.showSpinerDialog();

            }
        });
        districtApiCall();

        spinnerDialogPanchayat = new SpinnerDialog(HomeActivity.this, mPanchayatList, "Select or Search Panchayat", "Close");// With No Animation

        spinnerDialogPanchayat.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int i) {


                tv_selectPanchayat.setText(item);


                for (Map.Entry<Integer, String> entry : mPanchayatHashmapitems.entrySet()) {

                    if (entry.getValue().equals(item)) {
                        Log.e(TAG, "Panchayat Id ===> : : " + entry.getKey() + " Count : " + entry.getValue());
                    }


                    editor = sharedPreference.edit();

                    editor.putString(PREF_SELECTPANCHAYAT, item);

                    editor.apply();


                }

            }
        });


        spinnerDialogDistrict = new SpinnerDialog(HomeActivity.this, mDistrictList, "Select or Search District", "Close");// With No Animation


        spinnerDialogDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
//                Toast.makeText(AssessmentSearch.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();


                tv_selectDistrict.setText(item);


                for (Map.Entry<Integer, String> entry : mDistrictHashmapitems.entrySet()) {

                    if (entry.getValue().equals(item)) {
                        Log.e(TAG, "District Id ===> : : " + entry.getKey() + " Count : " + entry.getValue());

                        townPanchayat(entry.getKey());

                    }
                    editor = sharedPreference.edit();

                    editor.putString(PREF_SELECTDISTRICT, item);

                    editor.apply();


                }


            }
        });


        linear_grievances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRootIntent("Grievances_Track");
            }
        });


        linear_deathdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRootIntent("Death");
            }
        });

        mlinProfessional_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRootIntent("Profession");
            }
        });
        liBirthDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRootIntent("Birth");
            }
        });
        linear_watercharges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRootIntent("Water");

            }
        });
        linear_nontax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRootIntent("NonTax");

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = findViewById(R.id.bannerSlider);
        sliderAdapter = new HomeSliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        Timer timer = new Timer();
        timer.schedule(new MyTime(), 1000, 2000);


        indicator = findViewById(R.id.indicator);
        indicator.initViewPager(viewPager);
    }

    private void moveRootIntent(String intent_type) {

        Intent i = new Intent(getApplicationContext(), RootNavigation.class);
        i.putExtra("Type", intent_type);
        startActivity(i);
        overridePendingTransition(R.anim.anim_slide_out_left,
                R.anim.leftanim);

    }

    public class MyTime extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            if (Common.isNetworkAvailable(getApplicationContext())) {


                try {
                    Intent actionSend = new Intent(Intent.ACTION_SEND);
                    actionSend.setType("text/plain");
                    actionSend.putExtra(Intent.EXTRA_SUBJECT, "ETown App");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                    actionSend.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(actionSend, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                    e.printStackTrace();
                }


            } else {
                Snackbar.make(drawer, "Not connected to Internet", Snackbar.LENGTH_SHORT).show();


            }


        } else if (id == R.id.nav_about) {

            Intent contact_intent = new Intent(HomeActivity.this, ContactUs.class);
            startActivity(contact_intent);


        } else if (id == R.id.nav_send) {

            if (Common.isNetworkAvailable(getApplicationContext())) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.prematix.cme&hl=en"));
                if (!MyStartActivity(intent)) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.prematix.cme&hl=en"));
                    if (!MyStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(HomeActivity.this, "Could not open Android market, please " +
                                "install the market app.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {

                Snackbar.make(drawer, "Internet Not Working", Snackbar.LENGTH_SHORT).show();
            }


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private void districtApiCall() {

        waitingDialog = new SpotsDialog(HomeActivity.this);
        waitingDialog.show();
        waitingDialog.setCancelable(false);
        String REQUEST_TAG = "apiDistrictDetails_Request";


        JsonArrayRequest apiDistrictDetails_Request = new JsonArrayRequest(Request.Method.GET, API_DISTRICT_DETAILS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.e(TAG, response.toString());


                    if (!response.isNull(0)) {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject district_jsonObject = response.getJSONObject(i);

                            mDistrictId = district_jsonObject.getString("DistrictId");
                            mDistrictName = district_jsonObject.getString("DistrictName");

                            mDistrictHashmapitems.put(Integer.parseInt(mDistrictId), mDistrictName);

                            mDistrictList.add(mDistrictName);
                        }


                    } else Snackbar.make(drawer, "Error", Snackbar.LENGTH_SHORT).show();


                    waitingDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Snackbar.make(drawer, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    waitingDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Log.e(TAG, error.toString());


                waitingDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", drawer);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", drawer);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", drawer);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", drawer);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", drawer);

                } else {


                    SnackShowTop(error.getMessage(), drawer);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);


                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(apiDistrictDetails_Request, REQUEST_TAG);


    }


    private void townPanchayat(Integer districtId) {

        waitingDialog = new SpotsDialog(HomeActivity.this);
        waitingDialog.show();
        String REQUEST_TAG = "townPanchayat";


        JsonArrayRequest api_townPanchayat_Request = new JsonArrayRequest(Request.Method.GET, API_TOWNPANCHAYAT + districtId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.e(TAG, response.toString());


                    if (!response.isNull(0)) {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject district_jsonObject = response.getJSONObject(i);

                            mPanchayatId = district_jsonObject.getString("PanchayatId");
                            mPanchayatName = district_jsonObject.getString("PanchayatName");

                            mPanchayatHashmapitems.put(Integer.parseInt(mPanchayatId), mPanchayatName);

                            mPanchayatList.add(mPanchayatName);
                        }


                    } else Snackbar.make(drawer, "Error", Snackbar.LENGTH_SHORT).show();


                    waitingDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Snackbar.make(drawer, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    waitingDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Log.e(TAG, error.toString());


                waitingDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {


                    SnackShowTop("Time out", drawer);


                } else if (error instanceof AuthFailureError) {


                    SnackShowTop("Connection Time out", drawer);

                } else if (error instanceof ServerError) {

                    SnackShowTop("Could not connect server", drawer);


                } else if (error instanceof NetworkError) {


                    SnackShowTop("Please check the internet connection", drawer);


                } else if (error instanceof ParseError) {


                    SnackShowTop("Parse Error", drawer);

                } else {


                    SnackShowTop(error.getMessage(), drawer);

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", ACCESS_TOKEN);


                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(api_townPanchayat_Request, REQUEST_TAG);


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

package chennaicitytrafficapplication.prematix.com.etownpublic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import chennaicitytrafficapplication.prematix.com.etownpublic.Network_Connection_BroadCast.InternetConnector_Receiver;


public class SplashScreen extends AppCompatActivity {


    public String TAG = SplashScreen.class.getName();
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 7000;
    boolean value;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = InternetConnector_Receiver.getConnectivityStatusString(context);
            setStatus(status);

        }
    };


    LinearLayout rootlayout;


    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        rootlayout = findViewById(R.id.rootlayout);
        checkRunTimePermission();


    }


    public void splash_Call() {


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent dashboardintent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(dashboardintent);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                // isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                            checkRunTimePermission();
                        }
                    }
                }
            }
        }
    }


    public void setStatus(String status) {

        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {


            splash_Call();

        } else {

            Snackbar.make(rootlayout, "Internet Not Connected", Snackbar.LENGTH_SHORT).show();

        }

    }


    public void registerInternetCheckReceiver() {
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public void dialog(boolean value) {
        // if(value)   {
        Intent dashboardintent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(dashboardintent);
        overridePendingTransition(R.anim.anim_slide_out_left,
                R.anim.leftanim);



    }


}



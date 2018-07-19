package com.none.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    ArrayList<Values> values = new ArrayList<>();

    Button save,call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = (Button)findViewById(R.id.save);
        call = (Button)findViewById(R.id.call);

        values.add(new Values(11,"Eleven",111111));
        values.add(new Values(12,"twelve",121212));
        values.add(new Values(13,"thirteen",131313));
        values.add(new Values(14,"fourteen",141414));
        values.add(new Values(15,"fifteen",151515));
        values.add(new Values(16,"sixteen",161616));
        values.add(new Values(17,"seventeen",171717));
        values.add(new Values(18,"Eighteen",181818));
        values.add(new Values(19,"nineteen",191919));
        values.add(new Values(20,"twenty",202020));
        values.add(new Values(21,"twenty one",212121));
        values.add(new Values(22,"twenty two",2222));
        values.add(new Values(23,"twenty three",232323));
        values.add(new Values(24,"twenty four",242424));
        values.add(new Values(25,"twenty five",252525));
        values.add(new Values(26,"twenty six",262626));
        values.add(new Values(27,"twenty seven",272727));
        values.add(new Values(28,"twenty eight",282828));
        values.add(new Values(29,"twenty nine",292929));
        values.add(new Values(30,"thirty",303030));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveAlert();
            }
        });


    }

    public void SaveAlert(){

        for (int i = 0;i<values.size();i++){

            Log.e("aaa","===");
            try {

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = "http://192.168.1.11:8085/addstudent";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("id", values.get(i).getId());
                jsonBody.put("name", values.get(i).getName());
                jsonBody.put("passportno", values.get(i).getpassno());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(1, URL, jsonBody, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

        }
    }
}

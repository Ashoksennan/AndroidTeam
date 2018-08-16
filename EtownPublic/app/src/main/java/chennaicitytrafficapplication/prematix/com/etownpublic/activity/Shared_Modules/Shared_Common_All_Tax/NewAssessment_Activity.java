package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Common_All_Tax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.badoualy.stepperindicator.StepperIndicator;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.NewAssessment_PagerAdapter;
import chennaicitytrafficapplication.prematix.com.etownpublic.common.Common;

/**
 * Created by priyadharshini on 31/07/2018.
 */

public class NewAssessment_Activity extends AppCompatActivity {


    public static ViewPager pager;
    StepperIndicator indicator;
    NewAssessment_PagerAdapter adapter;

    String step1,step2,step3;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor;
    String prefername = "pager";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ass_stepper);

        preferences = getSharedPreferences(prefername, Context.MODE_PRIVATE);
        editor = preferences.edit();

        step1 = preferences.getString("assFrag1","0");
        step2 = preferences.getString("assFrag2","0");
        step3 = preferences.getString("assFrag3","0");


        //ID's
        pager = (ViewPager) findViewById(R.id.new_ass_pager);
        indicator = (StepperIndicator) findViewById(R.id.new_ass_indicator);
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        Intent b = this.getIntent();
        String type =b.getStringExtra(Common.Type);

        assert pager != null;

        //adapter



        if(type.equalsIgnoreCase("PR")||type.equalsIgnoreCase("W")){
            adapter = new NewAssessment_PagerAdapter(getSupportFragmentManager(), 2,type);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
        }else {
            adapter = new NewAssessment_PagerAdapter(getSupportFragmentManager(), 3, type);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
        }

    }
}

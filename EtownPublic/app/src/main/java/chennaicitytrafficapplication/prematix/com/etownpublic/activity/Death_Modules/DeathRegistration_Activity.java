package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Death_Modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.badoualy.stepperindicator.StepperIndicator;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Death.DeathRegistration_PagerAdapter;


public class DeathRegistration_Activity extends AppCompatActivity {

    StepperIndicator birth_registration_indicator;
    public static ViewPager death_registration_pager;
    DeathRegistration_PagerAdapter br_pagerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deathregistration);
       toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.Death_apply_death_registration);

        //id's
        death_registration_pager = findViewById(R.id.death_registration_pager);
        birth_registration_indicator = findViewById(R.id.birth_registration_indicator);

        assert death_registration_pager != null;


        //setAdapter
        br_pagerAdapter = new DeathRegistration_PagerAdapter(getSupportFragmentManager(), 3);
        death_registration_pager.setAdapter(br_pagerAdapter);
        birth_registration_indicator.setViewPager(death_registration_pager);
        death_registration_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


    }
}

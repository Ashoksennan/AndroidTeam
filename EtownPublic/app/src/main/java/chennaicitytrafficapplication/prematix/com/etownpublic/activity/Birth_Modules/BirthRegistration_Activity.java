package chennaicitytrafficapplication.prematix.com.etownpublic.activity.Birth_Modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.badoualy.stepperindicator.StepperIndicator;

import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Birth.BirthRegistration_PagerAdapter;


public class BirthRegistration_Activity extends AppCompatActivity {

    StepperIndicator birth_registration_indicator;
    public static ViewPager birth_registration_pager;
    BirthRegistration_PagerAdapter br_pagerAdapter;
  Toolbar birth_registration_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_birthregistration);
        birth_registration_toolbar=(Toolbar)findViewById(R.id.birth_registration_toolbar);
        setSupportActionBar(birth_registration_toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.Birth_apply_registration);

        //id's
        birth_registration_pager = (ViewPager) findViewById(R.id.birth_registration_pager);
        birth_registration_indicator = (StepperIndicator) findViewById(R.id.birth_registration_indicator);

        assert birth_registration_pager != null;


        //setAdapter
        br_pagerAdapter = new BirthRegistration_PagerAdapter(getSupportFragmentManager(), 3);
        birth_registration_pager.setAdapter(br_pagerAdapter);
        birth_registration_indicator.setViewPager(birth_registration_pager);
        birth_registration_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


    }
}

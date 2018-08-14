package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Death;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Death_Fragments.DeathRegistration_First_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Death_Fragments.DeathRegistration_Second_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Death_Fragments.DeathRegistration_Third_Fragment;


public class DeathRegistration_PagerAdapter extends FragmentPagerAdapter {
    private int count;

    public DeathRegistration_PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new DeathRegistration_First_Fragment();

            case 1:
                return new DeathRegistration_Second_Fragment();

            case 2:
                return new DeathRegistration_Third_Fragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}

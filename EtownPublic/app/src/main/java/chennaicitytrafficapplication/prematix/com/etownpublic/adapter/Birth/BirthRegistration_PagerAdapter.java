package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Birth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Birth_Fragment.BirthRegistration_First_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Birth_Fragment.BirthRegistration_Second_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Birth_Fragment.BirthRegistration_Third_Fragment;

/**
 * Created by priyadharshini on 31/07/2018.
 */


public class BirthRegistration_PagerAdapter extends FragmentPagerAdapter {
    private int count;

    public BirthRegistration_PagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new BirthRegistration_First_Fragment();

            case 1:
                return new BirthRegistration_Second_Fragment();

            case 2:
                return new BirthRegistration_Third_Fragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}

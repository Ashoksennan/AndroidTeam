package chennaicitytrafficapplication.prematix.com.etownpublic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments.NewAssessment_First_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments.NewAssessment_Second_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments.NewAssessment_Third_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments.Prof_NewAssessment_Second_Fragment;
import chennaicitytrafficapplication.prematix.com.etownpublic.fragment.Assessment_Fragments.Water_NewAssessment_Second_Fragment;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class NewAssessment_PagerAdapter extends FragmentPagerAdapter {
   private int count;
   private String type;

    public NewAssessment_PagerAdapter(FragmentManager fm, int count, String type) {
        super(fm);
        this.count = count;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        if (type.equalsIgnoreCase("PR")) {
            switch (position) {


                case 0:

                    return new NewAssessment_First_Fragment();


                case 1:

                    return new Prof_NewAssessment_Second_Fragment();


                default:
                    return null;

            }
        }else if(type.equalsIgnoreCase("W")){

            switch (position) {


                case 0:

                    return new NewAssessment_First_Fragment();


                case 1:

                    return new Water_NewAssessment_Second_Fragment();


                default:
                    return null;

            }


        }else if(type.equalsIgnoreCase("P")){

            switch (position) {


                case 0:

                    return new NewAssessment_First_Fragment();


                case 1:

                    return new NewAssessment_Second_Fragment();


                case 2:
                    return new NewAssessment_Third_Fragment();

                default:
                    return null;

            }


        }else  {
            switch (position) {


                case 0:

                    return new NewAssessment_First_Fragment();


                case 1:

                    return new NewAssessment_Second_Fragment();


                case 2:
                    return new NewAssessment_Third_Fragment();

                default:
                    return null;

            }
        }
    }
    @Override
    public int getCount() {
        return count;
    }


}

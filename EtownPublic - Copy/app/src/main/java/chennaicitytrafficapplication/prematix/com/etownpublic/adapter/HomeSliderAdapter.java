package chennaicitytrafficapplication.prematix.com.etownpublic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class HomeSliderAdapter extends PagerAdapter {

    public static int images[] = {R.drawable.banner_one, R.drawable.banner_two, R.drawable.banner_three};
    public Context context;
    LayoutInflater layoutInflater;

    public HomeSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.home_slider_list, null);

        ImageView image = view.findViewById(R.id.banner);
        image.setImageResource(images[position]);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

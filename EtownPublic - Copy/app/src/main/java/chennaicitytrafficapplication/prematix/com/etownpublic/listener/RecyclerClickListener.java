package chennaicitytrafficapplication.prematix.com.etownpublic.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by admin on 12-06-2017.
 */

public class RecyclerClickListener implements RecyclerView.OnItemTouchListener {
    GestureDetector detector;
    private OnItemTouchListener mlistener;

    public RecyclerClickListener(Context context, OnItemTouchListener listener) {
        mlistener = listener;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mlistener != null && detector.onTouchEvent(e)) {
            mlistener.OnItemCLikc(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnItemTouchListener {
        void OnItemCLikc(View view, int position);
    }
}

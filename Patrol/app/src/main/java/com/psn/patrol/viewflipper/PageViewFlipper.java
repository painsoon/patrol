package com.psn.patrol.viewflipper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

/**
 * Created by Administrator on 2017/3/16.
 */

public class PageViewFlipper extends ViewFlipper {

    public PageViewFlipper(Context context) {
        super(context);
    }

    public PageViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        super.dispatchTouchEvent(ev);

        return false;
    }

}
package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

/**
 * Created by Administrator on 2017-08-11.
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float lastDownX = 0;
    private float lastDownY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Boolean onInterceptTouchEvent = false;

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                lastDownX = ev.getX();
                lastDownY = ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                if (Math.abs(ev.getY() - lastDownY) > Math.abs(ev.getX() - lastDownX)) {

                    LogHelper.i("偏向竖直方向滑动");
                    onInterceptTouchEvent = true;

                } else {


                    LogHelper.i("偏向水平方向滑动");
                    onInterceptTouchEvent = false;
                }


            case MotionEvent.ACTION_UP:

                break;


        }


        return onInterceptTouchEvent;
    }
}

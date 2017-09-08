package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

/**
 * Created by wangke on 2017-08-11.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    GestureDetector mGestureDetector = new GestureDetector(App.getContext(), new MyGestureListener());
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//
//
//        boolean isIntercept = false;
//
//        isIntercept = mGestureDetector.onTouchEvent(e);
//
//
//        LogHelper.i("isIntercept==>"+isIntercept);
//
//        return isIntercept;
//    }

}

class MyGestureListener implements GestureDetector.OnGestureListener {

    @Override
    public boolean onDown(MotionEvent motionEvent) {


        LogHelper.i("==>onDown");


        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {



        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {


        LogHelper.i("==>onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        LogHelper.i("==>onFling");
        return true;
    }

}

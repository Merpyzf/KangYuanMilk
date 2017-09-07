package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.merpyzf.kangyuanmilk.common.App;
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

    GestureDetector mGestureDetector = new GestureDetector(App.getContext(), new MyGestureListener());





    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {


        boolean isIntercept = false;


        switch (e.getAction()){

            case MotionEvent.ACTION_DOWN:

                LogHelper.i("tag","==> ACTION_DOWN");

                //不拦截
                isIntercept = false;

                break;

            case MotionEvent.ACTION_MOVE:

                LogHelper.i("tag","==> ACTION_MOVE");

                //拦截
                isIntercept = true;

                break;

            case MotionEvent.ACTION_UP:

                LogHelper.i("tag","==> ACTION_UP");

                //抬起的时候不进行拦截
                isIntercept = false;

                break;

        }

        LogHelper.i("tag","isIntercept==>"+isIntercept);


        return isIntercept;
    }




 /*   @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {


        boolean isIntercept = false;

        isIntercept = mGestureDetector.onTouchEvent(e);


        LogHelper.i("tag", "isIntercept==>" + isIntercept);

        return isIntercept;
    }*/


}

class MyGestureListener implements GestureDetector.OnGestureListener {

    private boolean isIntercept = false;

    @Override
    public boolean onDown(MotionEvent motionEvent) {


        //onDown事件拦截之后，后续的时间就传递到当前的ViewGroup，不会将事件传递到下面的子View中去
        return isIntercept;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {


    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        isIntercept = false;

        return isIntercept;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {


        LogHelper.i("tag","==>onScroll");
        isIntercept = true;


        return isIntercept;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {


        isIntercept = false;

        LogHelper.i("tag","onFling==>");

        return isIntercept;
    }


}

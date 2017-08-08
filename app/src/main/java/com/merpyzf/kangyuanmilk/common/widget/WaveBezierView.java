package com.merpyzf.kangyuanmilk.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wangke on 2017-08-08.
 */

public class WaveBezierView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Path mPath;

    //屏幕的宽高
    private int mHeight;
    private int mWidth;
    //一个波形的长度
    private int mWaveLength;
    //波形的数量
    private int mWaveCount;

    private int mCenterY;

    private ValueAnimator animator;
    private int mOffset;


    public WaveBezierView(Context context) {
        super(context);
        init();
    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setColor(Color.parseColor("#87CEFA"));
        mPath = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        mWaveLength = 800;
        mCenterY = h/2;
        mWaveCount = (int) Math.round(mWidth/mWaveLength+1.5);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength+mOffset,mCenterY);
        for (int i = 0; i < mWaveCount; i++) {

            mPath.quadTo(-mWaveLength*3/4+i*mWaveLength+mOffset,mCenterY-60,-mWaveLength/2+i*mWaveLength+mOffset,mCenterY);
            mPath.quadTo(-mWaveLength/4+i*mWaveLength+mOffset,mCenterY+60,i*mWaveLength+mOffset,mCenterY);
        }


        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

    }

    @Override
    public void onClick(View view) {


        animator = ValueAnimator.ofInt(0,mWaveLength);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(valueAnimator -> {

            mOffset = (int) valueAnimator.getAnimatedValue();
            invalidate();
        });


    }
}

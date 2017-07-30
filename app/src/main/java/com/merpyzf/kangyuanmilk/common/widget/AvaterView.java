package com.merpyzf.kangyuanmilk.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

import net.qiujuer.genius.ui.Ui;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jj
 * Created by Administrator on 2017-07-27.
 */

public class AvaterView extends CircleImageView {

    private int mWidth;
    private int mHeight;
    private int lineWidth = (int) Ui.dipToPx(getResources(),2);
    private Paint mPaint = null;
    private float animatedValue;

    public AvaterView(Context context) {
        super(context);
        initPaint();

    }

    public AvaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public AvaterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }


    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(Ui.dipToPx(getResources(), 2));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        LogHelper.i("mWidth: " + mWidth + "mHeight: " + mHeight);


    }


    public void start(){

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(valueAnimator1 -> {

            animatedValue = (float) valueAnimator1.getAnimatedValue();

            LogHelper.i("==>" + animatedValue);

            invalidate();



        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            canvas.drawArc(0, 0, mWidth+lineWidth, mHeight+lineWidth, 0f, 360f*animatedValue, true, mPaint);

        }


    }
}

package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.Ui;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wangke on 2017-07-27.
 */

public class AvaterView extends CircleImageView {

    private int mWidth;
    private int mHeight;
    private Paint mPaint = null;
    private float mProgress = 0;
    private boolean upLoadState = false;

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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Ui.dipToPx(getResources(), 3));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        LogHelper.i("mWidth: " + mWidth + "mHeight: " + mHeight);


    }

    public void setUpLoadProgress(float progress) {
        upLoadState = false;
        mProgress = progress;
        invalidate();

    }

    /**
     * 上传失败
     */
    public void upLoadFailed() {

        upLoadState = true;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //上传失败
        if (upLoadState) {

            if (mProgress == 0) {
                mProgress = 1;
            }
            //上传失败
            mPaint.setColor(Resource.Color.RED);
            canvas.drawArc(getPaddingTop(), getPaddingTop(), mWidth - getPaddingTop(), mHeight - getPaddingTop(), 0f, 360f * mProgress, false, mPaint);

            return;

        } else {

            //上传中
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            canvas.drawArc(getPaddingTop(), getPaddingTop(), mWidth - getPaddingTop(), mHeight - getPaddingTop(), 0f, 360f * mProgress, false, mPaint);

        }


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}

package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

import net.qiujuer.genius.ui.Ui;

/**
 * Created by 春水碧于天 on 2017/9/5.
 */

public class CalendarHeaderView extends View {

    private String mHeaders[] = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private int COL = mHeaders.length;
    private int mCellSpace;
    private Paint mPaint;

    public CalendarHeaderView(Context context) {
        this(context,null);
    }


    public CalendarHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();


    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LogHelper.i("==> onMeasure");
        //对View的高度进行重新测量
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),measureHeight(heightMeasureSpec));

    }

    /**
     * 对高度进行重新测量
     * @param heightMeasureSpec
     * @return 重新测量后的高度
     */
    private int measureHeight(int heightMeasureSpec) {

        int result = 0;

        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        if(specMode == MeasureSpec.AT_MOST){  // warp_content的情况
            result = (int) Ui.dipToPx(getResources(), 30);
        }else if (specMode == MeasureSpec.EXACTLY){ //精确赋值
            result = MeasureSpec.getSize(heightMeasureSpec);
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogHelper.i("==> onSizeChanged");
        mCellSpace = w / COL;
        mPaint.setTextSize(mCellSpace/3);
        LogHelper.i("列的宽度==> "+mCellSpace);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i=0;i<COL;i++){

            canvas.drawText(mHeaders[i], (float) ((i + 0.5) * mCellSpace - mPaint
                    .measureText(mHeaders[i]) / 2), mPaint.measureText(mHeaders[i],0,1),mPaint);

        }


    }
}

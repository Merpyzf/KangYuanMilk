package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.merpyzf.kangyuanmilk.utils.LogHelper;

/**
 * Created by wangke on 2017/9/5.
 */

public class CalenderView extends View {

    private int mFirstMonthDayIndex = -1; //每个月的第一天所在第一行的位置的下标值
    //记录这个值得目的是为了后面当用户手指点击CalenderView的时候能够正确的找到点击的那个cell
    private int mCellWidth;
    private int mCellHeight;

    private int ROW = 5; //行数
    private int COL = 7 ; //列数


    public CalenderView(Context context) {
        super(context);
    }

    public CalenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCellHeight = w/ROW; //计算一个cell的高度，外边框的高度
        mCellWidth = w/COL; //计算一个cell的宽度，外边框的宽度

        LogHelper.i("mCellHeight ==》"+mCellHeight+"mCellWidth==>"+mCellWidth);

    }

    class Cell{

        int row; //行
        int col; //列



    }


}

package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.merpyzf.kangyuanmilk.utils.CalendarUtils;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.TimeHelper;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.Ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangke on 2017/9/5.
 */

public class CalendarView extends View {

    private int mFirstMonthDayIndex = -1; //每个月的第一天所在第一行的位置的下标值
    //记录这个值得目的是为了后面当用户手指点击CalenderView的时候能够正确的找到点击的那个cell
    private int mCellWidth;
    private int mCellHeight;

    private int ROW = 5; //行数
    private int COL = 7; //列数
    //存放CalenderView中每一个日期cell的集合
    private List<Cell> mCellList = null;

    private Paint mPaintCell = null;
    private Paint mPaintText = null;
    private int FIRST_CELL_INDEX = -1;


    /**
     * 日期cell的状态
     */
    enum CellState {

        CURRENT,
        BEFORE,
        AFTER,
        REST

    }

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        init();
    }


    public void init() {

        mPaintCell = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCell.setColor(Resource.Color.BLUE);
        mPaintCell.setStrokeWidth(1);
        mPaintCell.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(Resource.Color.BLACK);
        mPaintText.setTextSize(Ui.dipToPx(getResources(), 16));
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCellHeight = w / ROW; //计算一个cell的高度，外边框的高度
        mCellWidth = w / COL; //计算一个cell的宽度，外边框的宽度

        LogHelper.i("mCellHeight ==》" + mCellHeight + "mCellWidth==>" + mCellWidth);

    }

    /**
     * 对高度进行测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //当宽度的模式设置为wrap_conent时
        if (heightModel == MeasureSpec.AT_MOST) {

            heightSize = widthSize;
            setMeasuredDimension(widthSize, heightSize);

        } else if (heightModel == MeasureSpec.EXACTLY) {

            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        }


    }

    //记录上一个选择的cell的下标值
    private int mLastCellPosition = -1;

    /**
     * 设置CalendarView的点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();

                //计算点击的cell所在的列
                int c = (int) (x / mCellWidth);
                //计算点击的cell所在的行
                int r = (int) (y / mCellHeight);

                //因为1号有时候不是现实在开头的，因此需要减去前面空出来的位置才能得到正确的点击位置的下标值
                int clickPosition = r * COL + c - FIRST_CELL_INDEX;

                mLastCellPosition = clickPosition;

                //当点击的位置在mCellList这个区域内时,相应用户的点击事件
                if (clickPosition >= 0 && clickPosition < mCellList.size()) {

                    // TODO: 2017/9/6 日期选择点击的回调,并绘制当前cell的状态

                    LogHelper.i("点击的cell的下标 ==>" + clickPosition);
                }


                break;


            case MotionEvent.ACTION_MOVE:


               /* int c1 = (int) (event.getX() / mCellWidth);
                int r1 = (int) (event.getY() / mCellHeight);


                int clickPosition1 = r1 * COL + c1;

                LogHelper.i("move");

                if (mLastCellPosition != clickPosition1) {

                    LogHelper.i("点击的cell的下标 move==>" + clickPosition1);
                    mLastCellPosition = clickPosition1;
                }
*/

                break;


        }


        return true;
    }

    /**
     * 外界设置当前CalenderView的月份
     *
     * @param month
     */
    public void setMonth(int month) {

        fillData(month);
    }

    /**
     * 填充数据
     *
     * @param month
     */
    private void fillData(int month) {

        //初始化集合
        if (mCellList == null) {
            mCellList = new ArrayList<>();
        } else {
            if (mCellList.size() > 0) {
                mCellList.clear();
            }
        }

        //根据设置的月份填充数据
        for (int i = 1; i <= CalendarUtils.getMonthDay(month); i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(CalendarUtils.getCurrentYear(), month - 1, i);
            String strDate = TimeHelper.getDate(calendar.getTime());
            int dayOfWeek = CalendarUtils.getDayOfWeek(strDate);
            if (i == 1) {

                FIRST_CELL_INDEX = dayOfWeek;
            }

            int weekOfMonth = CalendarUtils.getWeekOfMonth(strDate);
            LogHelper.i(strDate + "行: " + weekOfMonth + " 列: " + dayOfWeek);

            Cell cell = new Cell(calendar, weekOfMonth, dayOfWeek);


            mCellList.add(cell);

        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCellList.size(); i++) {

            mCellList.get(i).drawSelf(canvas);

        }


    }

    /**
     * 每个日期对应的单元格
     */
    class Cell {

        private Calendar mCalendar;
        private CellState mState;
        private int row; //行
        private int col; //列

        public Cell(Calendar calendar, int row, int col) {

            this.mCalendar = calendar;
            this.row = row;
            this.col = col;
            getCurrentState(calendar);

        }

        /**
         * 绘制自己的边框
         *
         * @param canvas
         */
        public void drawSelf(Canvas canvas) {

            canvas.drawRect(col * mCellWidth, row * mCellHeight, col * mCellWidth + mCellWidth, row * mCellHeight + mCellHeight, mPaintCell);

            canvas.drawText(String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)), (float) (col * mCellWidth + mCellWidth * 0.5), (float) (row * mCellHeight + mCellHeight * 0.5), mPaintText);


        }

        /**
         * 获取当前cell的日期的状态
         *
         * @param calendar
         * @return
         */
        public CellState getCurrentState(Calendar calendar) {

            Calendar currCalendar = Calendar.getInstance();
            currCalendar.setTime(CalendarUtils.getCurrentDate());


            if (calendar.get(Calendar.MONTH) > currCalendar.get(Calendar.MONTH)) {
                mState = CellState.AFTER;
                LogHelper.i("after");
                return mState;

            } else if (calendar.get(Calendar.MONTH) < currCalendar.get(Calendar.MONTH)) {

                LogHelper.i("before");

                mState = CellState.BEFORE;
                return mState;


            } else {
                //月份相同时判断day

                if (calendar.get(Calendar.DAY_OF_MONTH) < currCalendar.get(Calendar.DAY_OF_MONTH)) {

                    LogHelper.i("before");
                    mState = CellState.BEFORE;
                    return mState;

                } else if (calendar.get(Calendar.DAY_OF_MONTH) > currCalendar.get(Calendar.DAY_OF_MONTH)) {

                    LogHelper.i("after");
                    mState = CellState.AFTER;
                    return mState;

                } else {

                    LogHelper.i("current");

                    mState = CellState.CURRENT;
                    return mState;
                }

            }


        }


    }


}

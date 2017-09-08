package com.merpyzf.kangyuanmilk.common.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 春水碧于天 on 2017/9/7.
 * 管理CalendarView中的业务操作的变量
 */

public class CalendarManager {

    private static CalendarManager mManager;

    //如果cell中待绘制的格子在这个范围之内(全都转换成long进行if判断)，则以不同的颜色绘制
    private Calendar mStartDate = null;
    private Calendar mEndDate = null;
    public boolean isCompleted = false;
    //记录上一次选择的结束的日期
    public long lastEndDate = -1;


    private List<CalendarView> mCalendarViews = new ArrayList<>();


    private CalendarManager() {

    }

    public static CalendarManager getInstance() {

        if (mManager == null) {

            synchronized (Object.class) {

                if (mManager == null) {

                    mManager = new CalendarManager();

                }

            }
        }
        return mManager;
    }


    public Calendar getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Calendar mStartDate) {
        this.mStartDate = mStartDate;
    }

    public Calendar getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Calendar mEndDate) {
        this.mEndDate = mEndDate;
    }

    /**
     * 将在RecyclerView中显示的CalendarView添加到集合中去,进行统一管理
     *
     * @param calendarView
     */
    public void addCalendarView(CalendarView calendarView) {


        //如果list集合中没有要添加的对象才进行添加
        if (!mCalendarViews.contains(calendarView)) {

            mCalendarViews.add(calendarView);

        }

    }


    /**
     * 重新绘制所有添加到集合中的CalendarView中的内容
     */
    public void postInvalidate() {

        if (mCalendarViews.size() > 0) {

            for (CalendarView calendarView :
                    mCalendarViews) {


                calendarView.postInvalidate();


            }
        }


    }


    /**
     * 重置所有CalendarView中cell的当前选中状态
     */
    public void resetCellState() {



        for (CalendarView calendar : mCalendarViews) {


            for (CalendarView.Cell cell : calendar.getCellList()) {


                cell.setChoiceState(false, "");


            }


        }

    }


}




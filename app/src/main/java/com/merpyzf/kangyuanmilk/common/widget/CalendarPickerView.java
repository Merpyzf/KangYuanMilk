package com.merpyzf.kangyuanmilk.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.CalendarUtils;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangke on 2017/9/8.
 * <p>
 * 月份选择器
 */

public class CalendarPickerView extends RecyclerView {

    //显示的月份的个数，默认显示四个
    private int mShowMonthNum = 4;
    private List<Integer> mMonthsList = new ArrayList<>();
    private myAdapter mAdapter;
    private CalendarView.OnDatePickerListener mDatePickerListener = null;

    public CalendarPickerView(Context context) {
        this(context, null);
    }

    public CalendarPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarPickerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setLayoutManager(new LinearLayoutManager(getContext()));

        initData();

        mAdapter = new myAdapter(mMonthsList, getContext(), this);

        setAdapter(mAdapter);


    }

    /**
     * 设置要显示的月份的个数
     *
     * @param mShowMonthNum
     */
    public void setShowMonthNum(int mShowMonthNum) {
        this.mShowMonthNum = mShowMonthNum;

        //根据新设置的要显示的数据重新初始化数据
        initData();

        //重新设置数据
        if (mAdapter != null) {

            mAdapter.resetData(mMonthsList);

        }


    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mMonthsList.size() > 0) {
            mMonthsList.clear();
        }

        //当前系统所在的月份
        int currentMonth = CalendarUtils.getCurrentMonth();

        //填充要进行显示的月份
        for (int i = 0; i < mShowMonthNum; i++) {
            mMonthsList.add(currentMonth + i);
        }


    }


    class myAdapter extends RecyclerAdapter<Integer> {


        public myAdapter(List<Integer> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public com.merpyzf.kangyuanmilk.common.widget.ViewHolder createHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, parent, false);


            return new myHolder(view);
        }
    }


    class myHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Integer> {

        private TextView tv_header;
        private CalendarView mCalendarView;


        public myHolder(View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.tv_header);
            mCalendarView = itemView.findViewById(R.id.calendarView);

            LogHelper.i("MyHolder被调用==>");
            mCalendarView.setOnDatePickerListener(new CalendarView.OnDatePickerListener() {
                @Override
                public void startDate(String start) {


                    if (mDatePickerListener != null) {
                        mDatePickerListener.startDate(start);
                    }


                }

                @Override
                public void endDate(String end) {


                    if (mDatePickerListener != null) {

                        mDatePickerListener.endDate(end);
                    }

                }
            });

            //将CalendarView添加到CalendarManager中进行统一管理
            CalendarManager.getInstance().addCalendarView(mCalendarView);


        }

        @Override
        protected void onBindWidget(Integer integer) {

            tv_header.setText(integer + " 月");
            mCalendarView.setMonth(integer);


        }
    }


    public void setOnDatePickerListener(CalendarView.OnDatePickerListener mDatePickerListener) {
        this.mDatePickerListener = mDatePickerListener;
    }

}

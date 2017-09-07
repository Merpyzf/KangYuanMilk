package com.merpyzf.kangyuanmilk.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.CalendarManager;
import com.merpyzf.kangyuanmilk.common.widget.CalendarView;
import com.merpyzf.kangyuanmilk.common.widget.MyRecyclerView;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CalenderViewActivity extends AppCompatActivity {

    private MyRecyclerView mRecyclerView;
    private List<Integer> mMonth;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);

        mContext = this;

        mMonth = new ArrayList<>();
        mMonth.add(9);
        mMonth.add(10);
        mMonth.add(11);
        mMonth.add(12);

        mRecyclerView = (MyRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setAdapter(new myAdapter(mMonth, mContext, mRecyclerView));

    }


    class myAdapter extends RecyclerAdapter<Integer> {


        public myAdapter(List<Integer> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public ViewHolder createHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, parent, false);

            return new myHolder(view);
        }
    }


    class myHolder extends ViewHolder<Integer> {

        private TextView tv_header;
        private CalendarView mCalendarView;


        public myHolder(View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.tv_header);
            mCalendarView = itemView.findViewById(R.id.calendarView);

            //将CalendarView添加到CalendarManager中进行统一管理
            CalendarManager.getInstance().addCalendarView(mCalendarView);

        }

        @Override
        protected void onBindWidget(Integer integer) {

            tv_header.setText(integer + " 月");
            mCalendarView.setMonth(integer);


        }
    }


}

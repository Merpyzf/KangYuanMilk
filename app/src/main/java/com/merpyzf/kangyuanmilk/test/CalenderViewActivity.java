package com.merpyzf.kangyuanmilk.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.CalendarView;

import static com.merpyzf.kangyuanmilk.R.id.calenderView;

public class CalenderViewActivity extends AppCompatActivity {
    private CalendarView mCalenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);

        mCalenderView = (CalendarView) findViewById(calenderView);
        mCalenderView.setMonth(10);

    }
}

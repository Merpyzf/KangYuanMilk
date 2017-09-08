package com.merpyzf.kangyuanmilk.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.CalendarPickerView;
import com.merpyzf.kangyuanmilk.common.widget.CalendarView;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

public class CalenderViewActivity extends AppCompatActivity {

    private CalendarPickerView calendarPickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);


        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendarPickerView);

        calendarPickerView.setOnDatePickerListener(new CalendarView.OnDatePickerListener() {
            @Override
            public void startDate(String start) {

                LogHelper.i("cva","start :"+start);




            }

            @Override
            public void endDate(String end) {
                LogHelper.i("cva","end :"+end);
            }
        });


    }


}

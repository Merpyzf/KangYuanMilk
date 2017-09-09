package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 春水碧于天 on 2017/9/8.
 * 日期选择
 */


public class CalendarPickerFragment extends BottomSheetDialogFragment {


    private Unbinder mUnBinder;

    @BindView(R.id.calendarPickerView)
    CalendarPickerView mCalendarPickerView;
    @BindView(R.id.tv_show_date)
    TextView tv_show_date;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private String startDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_calender_view, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        btn_submit.setText("取消");


        mCalendarPickerView.setOnDatePickerListener(new CalendarView.OnDatePickerListener() {
            @Override
            public void startDate(String start) {
                startDate = start;
                tv_show_date.setText("请选择结束日期");
                btn_submit.setText("取消");


            }

            @Override
            public void endDate(String end) {


                tv_show_date.setText("从 "+startDate+" 到"+end+" 配送");
                btn_submit.setText("确定");

            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }
}

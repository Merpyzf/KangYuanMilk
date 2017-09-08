package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;

import java.util.Calendar;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_calender_view, container, false);
        mUnBinder = ButterKnife.bind(this, view);

        Calendar calendar_start = Calendar.getInstance();
        calendar_start.set(2017,9,1);


        Calendar calendar_end = Calendar.getInstance();
        calendar_end.set(2017,9,28);

        CalendarManager.getInstance().setStartDate(calendar_start);
        CalendarManager.getInstance().setEndDate(calendar_end);

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

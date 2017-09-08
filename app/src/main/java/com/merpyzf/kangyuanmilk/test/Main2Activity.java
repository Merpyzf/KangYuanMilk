package com.merpyzf.kangyuanmilk.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.CalendarPickerFragment;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void click(View v){

        CalendarPickerFragment pickerFragment = new CalendarPickerFragment();

        pickerFragment.show(getSupportFragmentManager(),"tag");


    }
}

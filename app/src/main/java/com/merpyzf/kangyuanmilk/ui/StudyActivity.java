package com.merpyzf.kangyuanmilk.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

public class StudyActivity extends AppCompatActivity {

    private LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        mRootView = (LinearLayout) findViewById(R.id.rootView);


//        View view = LayoutInflater.from(this)
//                .inflate(R.layout.item_search_clear, mRootView, false);

        //与上面的有同样的效果,两个参数的inflate方法，attachToRoot默认为true

        View view = LayoutInflater.from(this)
                .inflate(R.layout.item_search_clear, mRootView);


//        attachToRoot = false; 在这种情况下，inflate方法中的第一个参数所指定的View不会被添加到第二个参数所、
//        指定的View中

        //在任何我们不负责将View添加进ViewGroup的情况下都应该将attachToRoot设置为false



        if (view instanceof LinearLayout) {

            LogHelper.i("true");

        } else {

            LogHelper.i("false");
        }

    }
}

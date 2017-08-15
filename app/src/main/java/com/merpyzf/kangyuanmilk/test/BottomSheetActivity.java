package com.merpyzf.kangyuanmilk.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

public class BottomSheetActivity extends AppCompatActivity {

    private BottomSheetBehavior behavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        View view = findViewById(R.id.nested_view);

        View viewButton = findViewById(R.id.txt_main);

        viewButton.setOnClickListener(view1 -> {
            boolean operate = !view.isSelected();
            if (operate) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //从nested_view中获取behavior实例
        behavior = BottomSheetBehavior.from(view);
        //设置能够进行隐藏
        behavior.setHideable(true);

        //状态发生变化时的回调
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                String log = "";

                switch (newState) {
                    //拖动时的状态
                    case BottomSheetBehavior.STATE_DRAGGING:

                        log = "STATE_DRAGGING";

                        break;
                    //自动滑动
                    case BottomSheetBehavior.STATE_SETTLING:

                        log = "STATE_SETTLING";

                        break;
                    //展开
                    case BottomSheetBehavior.STATE_EXPANDED:

                        log = "STATE_EXPANDED";

                        break;

                    //收起
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        log = "STATE_COLLAPSED";

                        break;

                    //隐藏
                    case BottomSheetBehavior.STATE_HIDDEN:

                        log = "STATE_HIDDEN";

                        break;

                    default:

                        log = "defaule";
                        break;


                }

                LogHelper.i(log);

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }
}

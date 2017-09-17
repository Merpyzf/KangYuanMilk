package com.merpyzf.kangyuanmilk.ui.home.view;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;

import butterknife.BindView;

public class PayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initWidget() {

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("选择支付方式");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
}

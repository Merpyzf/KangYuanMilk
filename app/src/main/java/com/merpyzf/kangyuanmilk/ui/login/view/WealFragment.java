package com.merpyzf.kangyuanmilk.ui.login.view;

import android.os.Bundle;
import android.view.View;

import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.ui.login.presenter.WealPresenter;

/**
 * Created by wangke on 17-7-16.
 */

public class WealFragment extends BaseFragment implements IWealView {

    private WealPresenter presenter = new WealPresenter();

    @Override
    protected void initEvent() {

    }


    @Override
    protected void initArgs(Bundle arguments) {
        super.initArgs(arguments);
        presenter.attachView(this);
    }

    @Override
    protected void initWidget(View rootview) {


    }

    @Override
    protected void initData() {

    }




    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancleLoadingDialog() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void getWealList(int pageSize, int currentPage) {

    }

    @Override
    public void onRefreshData() {

    }

    @Override
    public void onLoadMoreData() {

    }


}

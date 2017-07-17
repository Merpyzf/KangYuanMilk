package com.merpyzf.kangyuanmilk.ui.login.view;

import com.merpyzf.kangyuanmilk.ui.login.base.IBaseView;

/**
 * Created by wangke on 17-7-16.
 */

public interface IWealView extends IBaseView {

    void getWealList(int pageSize, int currentPage);

    void onRefreshData();

    void onLoadMoreData();

}

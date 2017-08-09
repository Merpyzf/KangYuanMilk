package com.merpyzf.kangyuanmilk.common.widget;

import android.view.View;

/**
 * Created by wangke on 17-7-16.
 */

public interface ITipView {

    /**
     * 界面数据为空的提示
     * @param tip
     */
    void setEmptyTip(String tip);

    /**
     * 网络出错时的提示
     * @param tip
     */
    void setErrorTip(String tip);

    /**
     * 数据加载中
     */
    void setLoading();

    /**
     * 数据加载完成
     */
    void reset();

    /**
     * 绑定要进行数据加载的那个区域的View,在这个区域内进行相关提示
     * @param view
     */
    void bindView(View view);




}

package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

/**
 * Created by 春水碧于天 on 2017/9/11.
 * 活动内容展示页面
 */

public interface IContentContract {


    interface IContentView extends IBaseView {


        /**
         *
         * 显示AppBar部分的内容
         * @param title 标题
         * @param image 顶部的图片
         */
        void showAppbarContent(String title, String image);

        /**
         * 在webView中显示html代码
         * @param html
         */
        void showWebContent(String html);


    }

    interface IContentPresenter extends IBasePresenter<IContentView> {

        /**
         * 根据活动id获取活动的内容
         *
         * @param activityId
         */
        void getActivityContent(int activityId);



    }

}

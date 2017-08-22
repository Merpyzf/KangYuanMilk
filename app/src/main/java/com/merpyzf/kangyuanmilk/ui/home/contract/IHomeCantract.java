package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;

/**
 * Created by wangke on 2017-08-22.
 */

public interface IHomeCantract {

    interface IHomeView extends IBaseView{

        /**
         * 展示主页数据
         * @param responseBeanList
         */
        void showHomePage(HomeBean.ResponseBean responseBean);


    }


    interface IHomePresenter extends IBasePresenter<IHomeView>{

        /**
         * 获取主页数据
         */
        void getHomePageData(boolean isRefresh);


    }


}

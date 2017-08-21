package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.view.GoodsFragment;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;

/**
 * Created by wangke on 2017-08-14.
 */

public interface ICategoryContract {

    public interface ICategoryView extends IBaseView {

        void getMeiziData(Meizi meizi, boolean isRefresh);

    }


    public interface ICategoryPresenter extends IBasePresenter<ICategoryView> {


        void getMeiziData(GoodsFragment context, String page, boolean isRefresh);

    }

}

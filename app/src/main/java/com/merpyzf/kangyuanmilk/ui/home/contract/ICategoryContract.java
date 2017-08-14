package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.CategoryFragment;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;

/**
 * Created by Administrator on 2017-08-14.
 */

public interface ICategoryContract {

    public interface ICategoryView extends IBaseView {

        void getMeiziData(Meizi meizi);

    }


    public interface ICategoryPresenter extends IBasePresenter<ICategoryView> {


        void getMeiziData(CategoryFragment context, String page);

    }

}

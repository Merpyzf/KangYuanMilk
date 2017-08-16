package com.merpyzf.kangyuanmilk.ui.home.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.home.bean.Category;

import java.util.List;

/**
 * Created by wangke on 2017-08-16.
 */

public interface ICategoryPickerContract {


    interface ICategoryPickerView extends IBaseView {

        /**
         * 显示商品分类
         */
        void showGoodsCategory(List<Category> categoryList);

    }

    interface ICategoryPickPresenter extends IBasePresenter<ICategoryPickerView> {

        /**
         * 获取产品分类
         */
        void getGoodsCategory();

    }


}

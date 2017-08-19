package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Category;
import com.merpyzf.kangyuanmilk.ui.home.contract.ICategoryPickerContract;

import java.util.ArrayList;

/**
 * Created by wangke on 2017-08-16.
 */

public class CategoryPickerPresenter extends BasePresenter<ICategoryPickerContract.ICategoryPickerView> implements ICategoryPickerContract.ICategoryPickPresenter {

    @Override
    public void getGoodsCategory() {

        ArrayList<Category> categoryList = new ArrayList<>();


        for (int i = 0; i < 9; i++) {


            categoryList.add(new Category("分类" + i, i));


        }


        mMvpView.showGoodsCategory(categoryList);

    }
}

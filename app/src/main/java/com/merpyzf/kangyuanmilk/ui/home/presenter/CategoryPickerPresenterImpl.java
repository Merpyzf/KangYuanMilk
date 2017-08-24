package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.CategoryBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.ICategoryPickerContract;
import com.merpyzf.kangyuanmilk.ui.home.model.CategoryPickerModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.ICategoryPickerModel;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-16.
 */

public class CategoryPickerPresenterImpl extends BasePresenter<ICategoryPickerContract.ICategoryPickerView>
        implements ICategoryPickerContract.ICategoryPickPresenter {

    private ICategoryPickerModel mModel;

    public CategoryPickerPresenterImpl() {
        mModel = new CategoryPickerModelImpl();
    }

    @Override
    public void getGoodsCategory() {


        mModel.getGoodsCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryBean categoryBean) {


                        new ErrorHandle(this, categoryBean.getStatus()) {

                            @Override
                            protected void deal() {
                                //将分类中的第一个选项置为选中状态
                                categoryBean.getResponse().getMilkCategory().get(0).setChoice(true);
                                mMvpView.showGoodsCategory(categoryBean.getResponse().getMilkCategory());

                            }
                        };


                    }

                    @Override
                    public void onError(Throwable e) {

                        ErrorHandleHelper.handle(e, mMvpView);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}

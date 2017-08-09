package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.user.ModifyAddressActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IModifyAddressModel;
import com.merpyzf.kangyuanmilk.ui.user.model.ModifyAddressModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-08-07.
 */

public class ModifyAddressPresenterImpl extends BasePresenter<IModifyAddressContract.IModifyAddressView> implements IModifyAddressContract.IModefyAddressPresenter {

    private IModifyAddressModel mModel = null;

    public ModifyAddressPresenterImpl() {
        mModel = new ModifyAddressModelImpl();
    }


    @Override
    public void addAddress(ModifyAddressActivity context, Address address) {

        mMvpView.showLoadingDialog();

        mModel.addAddress(address)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageBean messageBean) {

                        new ErrorHandle(this, messageBean.getStatus()) {
                            @Override
                            protected void deal() {
                                //200
                                if (messageBean.getResponse().isResult()) {


                                    mMvpView.saveSuccess("添加成功");


                                } else {

                                    mMvpView.showErrorMsg("添加失败");
                                    mMvpView.cancelLoadingDialog();
                                }


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


    @Override
    public void updateAddress(ModifyAddressActivity context, Address address) {

        mModel.updateAddress(address)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageBean messageBean) {

                        new ErrorHandle(this, messageBean.getStatus()) {
                            @Override
                            protected void deal() {

                                if (messageBean.getResponse().isResult()) {

                                    mMvpView.saveSuccess("修改成功");



                                } else {

                                    mMvpView.showErrorMsg("修改失败");

                                }


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

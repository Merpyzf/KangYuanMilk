package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IModifyAddressModel;
import com.merpyzf.kangyuanmilk.ui.user.model.ModifyAddressModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;

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
    public void addAddress(Address address) {

        mMvpView.showLoadingDialog();

        mModel.addAddress(address)
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
}

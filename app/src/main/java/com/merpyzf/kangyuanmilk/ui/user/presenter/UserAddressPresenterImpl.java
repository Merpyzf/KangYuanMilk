package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IUserAddressModel;
import com.merpyzf.kangyuanmilk.ui.user.model.UserAddressModelImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017-08-08.
 */

public class UserAddressPresenterImpl extends BasePresenter<IUserAddressContract.IUserAddressView> implements IUserAddressContract.IUserAddressPresenter {

    private IUserAddressModel mModel = null;

    public UserAddressPresenterImpl() {

        mModel = new UserAddressModelImpl();

    }

    @Override
    public void getUserAddress() {

        LogHelper.i("执行了");

        mModel.getUserAddress(UserDao.getInstance().getUserInfo())
                .subscribe(new Observer<UserAddressBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAddressBean userAddressBean) {





                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

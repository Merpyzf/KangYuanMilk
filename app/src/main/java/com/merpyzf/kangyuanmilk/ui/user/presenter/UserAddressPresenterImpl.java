package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.user.UserAddressActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.bean.UserAddressBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IUserAddressModel;
import com.merpyzf.kangyuanmilk.ui.user.model.UserAddressModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

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

    /**
     * 获取用户的所有地址
     */
    @Override
    public void getUserAds(UserAddressActivity context) {

        LogHelper.i("执行了");

        mMvpView.showLoadingDialog();

        mModel.getUserAddress(UserDao.getInstance().getUserInfo())
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<UserAddressBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAddressBean userAddressBean) {


                        new ErrorHandle(this, userAddressBean.getStatus()) {


                            @Override
                            protected void deal() {

                                if (userAddressBean.getResponse().isResult()) {

                                    //获取当前用户的默认收货地址的id
                                    List<Address> addressList = userAddressBean.getResponse().getAddresses();
                                    if (addressList.size() != 0) {
                                        //预处理
                                        prepList(addressList, userAddressBean.getResponse().getDefaultId());

                                        //显示所有地址
                                        mMvpView.showUserAddress(addressList);

                                    } else {

                                        mMvpView.showEmptyTip("还没有收货地址呢,快来创建一个吧!");

                                    }
                                } else {

                                    mMvpView.showErrorMsg("sorry!服务器出错啦!");

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

    /**
     * 设置地址作为默认地址
     *
     * @param address
     */
    @Override
    public void setAdsAsDefault(UserAddressActivity context, Address address) {

        mModel.setAddressAsDefault(address)
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

                                    mMvpView.showSuccess("设置默认地址成功");


                                } else {


                                    mMvpView.showErrorMsg("设置默认地址失败");

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

    /**
     * 删除指定地址
     *
     * @param address
     */
    @Override
    public void deleteAds(UserAddressActivity context, Address address) {
        mModel.deleteAddress(address)
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

                                    mMvpView.showSuccess("删除成功");
                                    mMvpView.removeAdsSuccess(address);


                                } else {

                                    mMvpView.showErrorMsg("删除失败");
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


    /**
     * 对获取到的地址集合进行预处理,给每一个address对象添加一个isDefault属性
     *
     * @param addressList  地址集合
     * @param adsDafaultId 默认地址的id
     */
    private void prepList(List<Address> addressList, int adsDafaultId) {

        final Address[] tmpAddress = {null};

        addressList.forEach(address -> {

            if (address.getAddress_id() == adsDafaultId) {
                address.setDefault(true);
                tmpAddress[0] = address;
            }

        });

        if (tmpAddress[0] != null) {
            addressList.remove(tmpAddress[0]);
            addressList.add(0, tmpAddress[0]);
        }

    }

}

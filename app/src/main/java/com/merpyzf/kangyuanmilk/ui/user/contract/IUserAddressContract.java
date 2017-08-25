package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.user.view.UserAddressActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;

import java.util.List;

/**
 * Created by wangke on 2017-08-08.
 */

public interface IUserAddressContract {

    interface IUserAddressView extends IBaseView {

        /**
         * 显示用户的所有地址信息
         *
         * @param addressList 获取到的地址列表集合
         */
        void showUserAddress(List<Address> addressList);


        /**
         * 设置默认地址成功
         *
         * @param address
         */
        void setAdsDefaultSuccess(Address address);

        /**
         * 删除地址成功
         *
         * @param address
         */
        void removeAdsSuccess(Address address);


        /**
         * 显示网络错误时的提示
         */
        void showNetErrorTip(String msg);

        /**
         * 展示内容为空的提示
         */
        void showEmptyTip(String msg);

        /**
         * 操作成功时的提示
         *
         * @param msg 提示信息
         */
        void showSuccess(String msg);


    }


    interface IUserAddressPresenter extends IBasePresenter<IUserAddressView> {

        /**
         * 获取用户的所有地址信息
         */
        void getUserAds(UserAddressActivity context,boolean isRefresh);

        /**
         * 将地址设置成默认地址
         *
         * @param address
         */
        void setAdsAsDefault(UserAddressActivity context, Address address);

        /**
         * 删除地址
         *
         * @param address
         */
        void deleteAds(UserAddressActivity context, Address address);

    }

}

package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.bean.AddressBean;

/**
 * Created by wangke on 2017-08-07.
 */

public interface IModifyAddressContract {

    interface IModifyAddressView extends IBaseView {

        /**
         * 获取用户信息
         *
         * @return 地址
         */
        Address getAddressInfo();

        /**
         * 添加地址信息成功
         *
         * @param msg
         */
        void saveSuccess(String msg);

    }

    interface IModefyAddressPresenter extends IBasePresenter<IModifyAddressView> {

        /**
         * 保存收货地址
         *
         * @param address 地址信息
         */
        void addAddress(Address address);


    }


}

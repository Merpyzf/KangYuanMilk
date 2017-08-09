package com.merpyzf.kangyuanmilk.ui.adapter;

import com.merpyzf.kangyuanmilk.ui.user.bean.Address;

/**
 * Created by Administrator on 2017-08-09.
 */

public interface IUserAddressCallBack {


    public interface onItemWidgetClickListener {
        /**
         * 点击checkbox的回调，用来设置默认地址
         *
         * @param address 要设置成默认地址
         */
        void onCheckedChanged(Address address);

        /**
         * 编辑的按钮被点击后的回调
         *
         * @param address 待编辑的那条地址的信息
         */
        void onEditClick(Address address);

        /**
         * 删除按钮被点击后的回调
         *
         * @param address
         */
        void onRemoveClick(Address address);
    }



}

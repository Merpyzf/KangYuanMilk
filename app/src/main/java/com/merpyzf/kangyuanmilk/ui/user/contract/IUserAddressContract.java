package com.merpyzf.kangyuanmilk.ui.user.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

/**
 * Created by wangke on 2017-08-08.
 */

public interface IUserAddressContract {

    interface IUserAddressView extends IBaseView{

        void showUserAddress();

    }



    interface IUserAddressPresenter extends IBasePresenter<IUserAddressView>{

        void getUserAddress();

    }


}

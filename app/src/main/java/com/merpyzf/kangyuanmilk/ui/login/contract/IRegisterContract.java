package com.merpyzf.kangyuanmilk.ui.login.contract;

import com.merpyzf.kangyuanmilk.ui.base.IBasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.IBaseView;
import com.merpyzf.kangyuanmilk.ui.login.RegisterActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.User;

/**
 * Created by wangke on 2017-07-25.
 */

public interface IRegisterContract {

     interface IRegisterView extends IBaseView {

         /**
          * 注册成功
          * @param msg
          */
         void registerSuccess(String msg);

         /**
          * 用户名重复的时候回调
          * @param
          */
         void userRepeat( boolean isRepeat);
     }


     interface IRegisterPresenter extends IBasePresenter<IRegisterView>{

         /**
          * 注册
          * @param context
          * @param user
          */
         void register(RegisterActivity context, User user);

         /**
          * 检查用户名是否重复
          * @param context
          * @param user
          */
         void checkUserRepeat(RegisterActivity context,User user);


     }


}

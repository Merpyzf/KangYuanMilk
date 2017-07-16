package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.ui.login.model.User;
import com.merpyzf.kangyuanmilk.ui.login.view.ILogin;

/**
 * Created by wangke on 17-7-16.
 */

public class LoginPresenterImpl implements ILoginPresenter {

    private ILogin iLogin;

    public LoginPresenterImpl(ILogin iLogin) {
        this.iLogin = iLogin;

    }

    @Override
    public void clear() {

        iLogin.clearText();

    }

    @Override
    public void doLogin(User user) {

        if (user.getPwd().toString().equals("123") &&user.getUsername().toString().equals("123")){

            iLogin.onLoginResult(true,"yes");

        }else {

            iLogin.onLoginResult(false,"no");
        }

    }
}

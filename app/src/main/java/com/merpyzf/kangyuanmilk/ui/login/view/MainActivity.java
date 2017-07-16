package com.merpyzf.kangyuanmilk.ui.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.login.model.User;
import com.merpyzf.kangyuanmilk.ui.login.presenter.LoginPresenterImpl;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ILogin,View.OnClickListener {

    @BindView(R.id.edt_user)
    EditText edt_user;
    @BindView(R.id.edt_pwd)
    EditText edt_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_clear)
    Button btn_clear;

    private LoginPresenterImpl loginPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {

        loginPresenter = new LoginPresenterImpl(this);


    }
    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

        btn_login.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

    }



    @Override
    public void onLoginResult(boolean result, String code) {


        if(result){

            Toast.makeText(this,"登录成功： "+code,Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this,"登录失败： "+code,Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void clearText() {

        edt_user.setText("");
        edt_pwd.setText("");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_login:

                String userName = edt_user.getText().toString();
                String pwd = edt_pwd.getText().toString().trim();


                loginPresenter.doLogin(new User(userName, pwd));

                break;

            case R.id.btn_clear:

                loginPresenter.clear();

                break;

        }

    }
}

package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.HomeActivity;
import com.merpyzf.kangyuanmilk.ui.login.contract.ILoginContract;

import butterknife.BindView;

import static com.merpyzf.kangyuanmilk.R.id.fab_next;
import static com.merpyzf.kangyuanmilk.R.id.rl;
import static com.merpyzf.kangyuanmilk.R.id.rl_login_bg;
import static com.merpyzf.kangyuanmilk.R.id.view;
import static com.merpyzf.kangyuanmilk.R.layout.activity_login;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginContract.ILoginView {

    @BindView(R.id.cardView)
    CardView cardView;
    //短信页面页面跳转的fab
    @BindView(R.id.fab_next)
    FloatingActionButton fab_next;
    //登录
    @BindView(R.id.btn_login)
    Button btn_login;
    //用户名
    @BindView(R.id.edt_username)
    EditText edt_username;
    //密码
    @BindView(R.id.edt_pwd)
    EditText edt_pwd;
    //保存登录信息
    @BindView(R.id.cb_save)
    CheckBox cb_save;
    //找回密码
    @BindView(R.id.tv_find_pwd)
    TextView tv_find_pwd;

    @BindView(R.id.rl_login_bg)
    RelativeLayout rl_login_bg;


    private Boolean isExcute = true;

    @Override
    public void show(Context context, Bundle bundle) {
        super.show(context, bundle);


        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(context, LoginActivity.class);


        startActivity(intent);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //刚进入界面的时候开启圆形遮罩动画
        if (isExcute) {
            revealCircularAnim();
            isExcute = false;
        }
    }

    @Override
    public int getLayoutId() {
        return activity_login;
    }

    @Override
    public void initWidget() {

        setBackground();


    }



    @Override
    public void initEvent() {

        fab_next.setOnClickListener(this);
        btn_login.setOnClickListener(this);


    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            /**
             * 跳转到短信验证界面
             */
            case R.id.fab_next:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    //共享元素动画
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab_next, fab_next.getTransitionName());
                    fab_next.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(LoginActivity.this, SmsVerifyActivity.class), options.toBundle());

                } else {

                    fab_next.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(LoginActivity.this, SmsVerifyActivity.class));
                }

                break;

            //登录
            case R.id.btn_login:


                break;


            //找回密码
            case R.id.tv_find_pwd:


                break;

        }

    }


    /**
     * 圆形遮罩动画
     */
    private void revealCircularAnim() {

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, 0, 40, cardView.getWidth());
        mAnimator.setDuration(600);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

    }

    /**
     * 登录时请求网络的等待状态
     */
    @Override
    public void showLoadingDialog() {


    }

    /**
     * 关闭等待的dialog
     */
    @Override
    public void cancleLoadingDialog() {

    }

    /**
     * 遇到错误时候的提示信息
     *
     * @param errorMsg 错误信息
     */
    @Override
    public void showErrorMsg(String errorMsg) {

    }


    /**
     * 登录的逻辑
     *
     * @param username 用户名
     * @param pwd      密码
     */
    @Override
    public void login(String username, String pwd) {

    }

    /**
     * 登录失败
     *
     * @param errorText 登录失败时的文本提示
     */
    @Override
    public void loginError(String errorText) {


    }

    /**
     * 登录成功后返回主界面
     *
     * @param success
     */
    @Override
    public void loginSuccess(String success) {


    }

    /**
     * 给当前的界面设置背景
     */
    private void setBackground() {


        Glide.with(this)
                .load(R.drawable.ic_login_bg)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(rl_login_bg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        rl_login_bg.setBackground(resource.getCurrent());

                    }
                });

    }
}

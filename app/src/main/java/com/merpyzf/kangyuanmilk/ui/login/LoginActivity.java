package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.HomeActivity;
import com.merpyzf.kangyuanmilk.ui.login.contract.ILoginContract;
import com.merpyzf.kangyuanmilk.ui.login.presenter.LoginPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;

import butterknife.BindView;

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

    //标记遮罩动画是否已经执行
    private Boolean isExecute = true;
    private ILoginContract.ILoginPresenter mLoginPresenter = null;
    //用于提示当前状态的dialog
    private MaterialDialog mLoginDialog;

    /**
     * 当界面准备好的时候才开始执行遮罩动画
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //刚进入界面的时候开启圆形遮罩动画
        if (isExecute) {
            revealCircularAnim();
            isExecute = false;
        }
    }

    @Override
    public int getLayoutId() {
        return activity_login;
    }

    @Override
    public void initWidget() {
        //设置登录界面的背景
        setBackground();
        readLoginInfo();
    }

    @Override
    public void initEvent() {
        fab_next.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        cb_save.setOnCheckedChangeListener((compoundButton, b) -> {

            //如果勾选成为false则清除用户信息
            if (!b) {
                App.showToast("用户信息被清除了");
                SharedPreHelper.clearLoginInfo();

            }

        });


    }

    @Override
    protected void initData() {
        mLoginPresenter = new LoginPresenterImpl();
        //让presenter持有当前view的引用
        mLoginPresenter.attachView(this);


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


                String user_name = edt_username.getText().toString().trim();
                String pwd = edt_pwd.getText().toString().trim();

                if (!user_name.equals("") && !pwd.equals("")) {

                    login(user_name, pwd);


                } else {

                    App.showToast("请检查用户名或密码输入不能为空");

                }


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

        App.showToast("弹出dialog => 开始请求网络");
        mLoginDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_login)
                .content(R.string.please_wait_login)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();


    }

    /**
     * 关闭dialog
     */
    @Override
    public void cancelLoadingDialog() {
        App.showToast("关闭dialog => 请求网络完毕");
        mLoginDialog.dismiss();
    }

    /**
     * 遇到错误时候的提示信息
     *
     * @param errorMsg 错误信息
     */
    @Override
    public void showErrorMsg(String errorMsg) {

        //遇到错误的时候，给用户1秒钟的反应时间，优化用户体验

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                cancelLoadingDialog();
                App.showToast(errorMsg);
            }
        };

        Message message = Message.obtain();
        message.what = 0;
        handler.sendMessageDelayed(message, 1000);

    }


    /**
     * 登录的逻辑
     *
     * @param username 用户名
     * @param pwd      密码
     */
    @Override
    public void login(String username, String pwd) {

        mLoginPresenter.login(this, username, pwd);

    }

    /**
     * 登录失败,用户名或者密码错误
     *
     * @param errorText 登录失败时的文本提示
     */
    @Override
    public void loginError(String errorText) {

        cancelLoadingDialog();
        App.showToast(errorText);
        //如果用户登录失败也进行登录信息的清除
        SharedPreHelper.clearLoginInfo();

    }

    /**
     * 登录成功后返回主界面
     *
     * @param success
     */
    @Override
    public void loginSuccess(String success, String username, String pwd) {

        cancelLoadingDialog();
        App.showToast(success);

        //如果保存密码被勾选并且用户登录成功就记录用户的登录信息
        if (cb_save.isChecked()) {

            mLoginPresenter.saveLoginInfo(username, pwd);

        }

        startActivity(new Intent(this, HomeActivity.class));


    }

    /**
     * 读取用户的登录信息
     */
    @Override
    public void readLoginInfo() {

        SharedPreHelper.LoginInfo loginInfo = SharedPreHelper.getLoginInfo();

        //读取到了用户保存的登录信息
        if (loginInfo != null) {

            //设置选中状态
            cb_save.setChecked(true);

            edt_username.setText(loginInfo.getUserName());
            edt_pwd.setText(loginInfo.getPassword());


        } else {

            cb_save.setChecked(false);

        }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将view的引用置为null，避免发生内存泄露
        mLoginPresenter.detachView();

    }
}

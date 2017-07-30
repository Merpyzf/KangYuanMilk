package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.contract.IRegisterContract;
import com.merpyzf.kangyuanmilk.ui.login.presenter.RegisterPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.HashHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;

import static com.merpyzf.kangyuanmilk.R.layout.activity_register;

/**
 * 用户注册
 * @author wangke
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterContract.IRegisterView, View.OnFocusChangeListener {

    @BindView(R.id.cardview_input)
    CardView cardView;

    @BindView(R.id.fab_submit)
    FloatingActionButton fab_submit;

    @BindView(R.id.edt_username)
    EditText edt_username;

    @BindView(R.id.edt_pwd)
    EditText edt_pwd;

    @BindView(R.id.edt_repwd)
    EditText edt_repwd;

    @BindView(R.id.text_input_username)
    TextInputLayout text_input_username;

    @BindView(R.id.text_input_pwd)
    TextInputLayout text_input_pwd;


    private IRegisterContract.IRegisterPresenter mRegisterPresenter = null;
    private String phoneNum = null;
    private MaterialDialog mRegisterDialog;
    private boolean isRepeat = false;


    @Override
    protected boolean initArgs(Bundle bundle) {

        phoneNum = bundle.getString("phoneNum");

        LogHelper.i("phoneNum==> " + phoneNum);

        return true;
    }

    @Override
    public int getLayoutId() {

        return activity_register;
    }

    @Override
    public void initWidget() {
        showEnterAnimation();
        edt_username.setOnFocusChangeListener(this);


    }

    @Override
    public void initEvent() {

        fab_submit.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        mRegisterPresenter = new RegisterPresenterImpl();
        mRegisterPresenter.attachView(this);

    }


    @Override
    public void showLoadingDialog() {

        mRegisterDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_register)
                .content(R.string.please_wait_login)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();


    }

    @Override
    public void cancelLoadingDialog() {

        if (mRegisterDialog != null) {
            mRegisterDialog.dismiss();
        }

    }

    @Override
    public void showErrorMsg(String errorMsg) {

        cancelLoadingDialog();
        App.showToast(errorMsg);

    }

    @Override
    public void registerSuccess(String msg) {

        cancelLoadingDialog();

        //跳转到登录界面(singleTask)
        App.showToast(msg);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, fab_submit, fab_submit.getTransitionName());
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

            startActivity(intent, options.toBundle());

        } else {

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

        }


    }

    /**
     * 检测用户名是否重复
     *
     * @param isRepeat
     */
    @Override
    public void userRepeat(boolean isRepeat) {

        this.isRepeat = isRepeat;

        LogHelper.i("userRepeat===>" + isRepeat);

        if (isRepeat) {

            App.showToast("用户名重复");
            text_input_username.setError("用户名重复");
            text_input_username.setErrorEnabled(true);

        } else {

            text_input_username.setErrorEnabled(false);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_submit:


                //登录

                if (edt_username.equals("")) {

                    App.showToast("用户名不能为null");

                    return;
                }

                if (edt_pwd.equals("") || edt_pwd.equals("")) {
                    App.showToast("密码输入不能为null");


                } else {

                    if (!isRepeat) {


                        if (edt_pwd.getText().toString().equals(edt_repwd.getText().toString())) {

                            text_input_pwd.setErrorEnabled(false);

                            App.showToast("注册");
                            User user = new User();
                            user.setUser_name(edt_username.getText().toString().trim());
                            //MD5加密
                            user.setUser_pwd(HashHelper.getMD5String(edt_pwd.getText().toString().trim()));
                            user.setUser_tel(phoneNum);
                            mRegisterPresenter.register(this, user);

                        } else {

                            text_input_pwd.setErrorEnabled(true);
                            text_input_pwd.setError("两次密码输入请保持一致");

                        }

                    } else {

                        App.showToast("用户名重复");

                    }


                }


                break;

            default:

                break;

        }

    }


    /**
     * 入场时的动画监听
     */
    public void showEnterAnimation() {

        Transition transition = getWindow().getEnterTransition();

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

                cardView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onTransitionEnd(Transition transition) {

                transition.removeListener(this);
                animateRevealShow();

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

    }

    /**
     * 开始执行圆形遮罩动画
     */
    public void animateRevealShow() {

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth() / 2, cardView.getHeight(), fab_submit.getWidth() / 2, cardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // animateRevealClose();

    }

    @Override
    protected void onDestroy() {

        mRegisterPresenter.detachView();
        super.onDestroy();

    }

    /**
     * 用户名输入框的焦点监听事件
     * @param view
     * @param b
     */
    @Override
    public void onFocusChange(View view, boolean b) {

        EditText editText = (EditText) view;
        String value = editText.getText().toString().trim();

        User user = new User();
        user.setUser_name(value);
        if (!b && value.length() > 0) {
            mRegisterPresenter.checkUserRepeat(this, user);
        }
    }
}

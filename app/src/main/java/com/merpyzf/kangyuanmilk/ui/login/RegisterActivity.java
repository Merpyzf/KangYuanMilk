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
import android.text.TextUtils;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.ui.login.contract.IRegisterContract;
import com.merpyzf.kangyuanmilk.ui.login.presenter.RegisterPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.HashHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;

import static com.merpyzf.kangyuanmilk.R.layout.activity_register;

/**
 * 用户注册
 *
 * @author wangke
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterContract.IRegisterView, View.OnFocusChangeListener {

    @BindView(R.id.cardview_input)
    CardView mCardView;

    @BindView(R.id.fab_submit)
    FloatingActionButton mFabSubmit;

    @BindView(R.id.edt_username)
    EditText mEdtUsername;

    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;

    @BindView(R.id.edt_repwd)
    EditText mEdtRepwd;

    @BindView(R.id.text_input_username)
    TextInputLayout mTextInputUsername;

    @BindView(R.id.text_input_pwd)
    TextInputLayout mTextInputPwd;


    private IRegisterContract.IRegisterPresenter mRegisterPresenter = null;
    private String mPhoneNum = null;
    private MaterialDialog mRegisterDialog;
    private boolean mIsRepeat = false;


    @Override
    protected boolean initArgs(Bundle bundle) {

        mPhoneNum = bundle.getString("mPhoneNum");

        LogHelper.i("mPhoneNum==> " + mPhoneNum);

        return true;
    }

    @Override
    public int getLayoutId() {

        return activity_register;
    }

    @Override
    public void initWidget() {
        showEnterAnimation();
        mEdtUsername.setOnFocusChangeListener(this);


    }

    @Override
    public void initEvent() {

        mFabSubmit.setOnClickListener(this);

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

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, mFabSubmit, mFabSubmit.getTransitionName());
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

            startActivity(intent, options.toBundle());

        } else {

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

        }


    }

    /**
     * 检测用户名是否重复
     *
     * @param isRepeat 用户名是否重复
     *                 true : 重复
     *                 false : 不重复
     */
    @Override
    public void userRepeat(boolean isRepeat) {

        this.mIsRepeat = isRepeat;

        LogHelper.i("userRepeat===>" + isRepeat);

        if (isRepeat) {

            App.showToast("用户名重复");
            mTextInputUsername.setError("用户名重复");
            mTextInputUsername.setErrorEnabled(true);

        } else {

            mTextInputUsername.setErrorEnabled(false);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_submit:

                if (TextUtils.isEmpty(mEdtUsername.getText().toString().trim())) {

                    App.showToast("用户名不能为null");

                    return;
                }

                if (TextUtils.isEmpty(mEdtPwd.getText().toString().trim())) {
                    App.showToast("密码输入不能为null");

                } else {

                    //如果用户名不重复则进行注册
                    if (!mIsRepeat) {

                        //检查两次密码输入是否一致
                        if (mEdtPwd.getText().toString().equals(mEdtRepwd.getText().toString())) {

                            mTextInputPwd.setErrorEnabled(false);

                            App.showToast("注册");
                            User user = new User();
                            user.setUser_name(mEdtUsername.getText().toString().trim());
                            //MD5加密
                            user.setUser_pwd(HashHelper.getMD5String(mEdtPwd.getText().toString().trim()));
                            user.setUser_tel(mPhoneNum);
                            mRegisterPresenter.register(this, user);

                        } else {

                            mTextInputPwd.setErrorEnabled(true);
                            mTextInputPwd.setError("两次密码输入请保持一致");

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
     * 共享元素动画监听
     */
    public void showEnterAnimation() {

        Transition transition = getWindow().getEnterTransition();

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

                mCardView.setVisibility(View.INVISIBLE);

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
     * 圆形遮罩动画
     */
    public void animateRevealShow() {

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardView, mCardView.getWidth() / 2, mCardView.getHeight(), mFabSubmit.getWidth() / 2, mCardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }


    /**
     * 用户名输入框的焦点监听事件
     *
     * @param view 监听的view
     * @param b    焦点的状态
     *             true: 得到焦点
     *             false: 失去焦点
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

    @Override
    protected void onDestroy() {

        mRegisterPresenter.detachView();
        super.onDestroy();
    }
}

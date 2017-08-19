package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.receiver.SMSReceiver;
import com.merpyzf.kangyuanmilk.ui.login.contract.ISMSVerifyContract;
import com.merpyzf.kangyuanmilk.ui.login.presenter.SMSVerifyPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.RegexHelper;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 用户短信验证
 *
 * @author wangke
 */
public class SmsVerifyActivity extends BaseActivity implements View.OnClickListener, ISMSVerifyContract.ISMSVerifyView {

    @BindView(R.id.cardview_reg)
    CardView mCardview;
    //返回上一个界面
    @BindView(R.id.fab_back)
    FloatingActionButton mFabBack;
    //验证
    @BindView(R.id.btn_verify)
    Button mBtnVerify;
    //获取验证码
    @BindView(R.id.btn_get_code)
    Button mBtnGetCode;
    //手机号输入框
    @BindView(R.id.edt_phone_num)
    EditText mEdtPhoneNum;
    @BindView(R.id.edt_code)
    EditText mEdtCode;

    @BindView(R.id.text_input_phone)
    TextInputLayout mTextInputPhone;

    @BindView(R.id.text_input_code)
    TextInputLayout mTextInputCode;


    private ISMSVerifyContract.ISMSVerifyPresenter mPresenter = null;
    private int mTime;
    private Timer mTimer;
    private SMSReceiver mSmsReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms_verify;
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initEvent() {

        showEnterAnimation();

        //解析短信中的验证码
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        mSmsReceiver = new SMSReceiver(mEdtCode);
        registerReceiver(mSmsReceiver, filter);

        mFabBack.setOnClickListener(this);
        mBtnVerify.setOnClickListener(this);
        mBtnGetCode.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        mPresenter = new SMSVerifyPresenterImpl();
        mPresenter.attachView(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //返回上一个页面
            case R.id.fab_back:
                SmsVerifyActivity.super.onBackPressed();
                break;

            //获取验证码
            case R.id.btn_get_code:

                String phoneNum = mEdtPhoneNum.getText().toString().trim();
                //手机格式正确性的校验
                if (RegexHelper.regexPhoneNum(phoneNum)) {

                    mTextInputPhone.setErrorEnabled(false);
                    mPresenter.getVerificationCode("86", phoneNum);

                } else {

                    mTextInputPhone.setError("手机号格式错误");

                }
                break;

            //进行验证
            case R.id.btn_verify:

                String code = mEdtCode.getText().toString().trim();

                if (code.length() == 4) {

                    //提交验证码进行验证
                    mPresenter.submitVerify(code);

                    mTextInputCode.setErrorEnabled(false);

                } else {

                    mTextInputCode.setError("验证码为4位数");

                }
                break;

            default:

                break;
        }


    }

    /**
     * 验证成功后的回调(子线程)
     *
     * @param phoneNum 待验证的手机号码
     */
    @Override
    public void verifySuccess(String phoneNum) {
        LogHelper.i("验证码验证成功");
        //跳转到注册页面
        App.showToast(this, "跳转到注册页面");

        runOnUiThread(() -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SmsVerifyActivity.this, mFabBack, mFabBack.getTransitionName());
                Intent intent = new Intent(SmsVerifyActivity.this, RegisterActivity.class);
                intent.putExtra("phoneNum", phoneNum);
                startActivity(intent, options.toBundle());

            } else {

                startActivity(new Intent(SmsVerifyActivity.this, RegisterActivity.class));

            }


        });


    }

    /**
     * 验证码验证失败后的回调(子线程)
     */
    @Override
    public void verifyFailed() {
        LogHelper.i("验证码验证失败");
        App.showToast(this, "验证失败 === ");

    }

    /**
     * 获取验证码成功后的回调(子线程)
     */
    @Override
    public void getVerifyCodeSuccess() {

        LogHelper.i("获取验证码成功");

        mTime = 60;
        //开始倒计时
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(() -> {

                    mTime--;
                    String countDown = mTime + getString(R.string.sms_verify_activity_countdown);
                    mBtnGetCode.setText(countDown);
                    mBtnGetCode.setEnabled(false);
                    if (mTime == 0) {

                        mBtnGetCode.setEnabled(true);
                        mBtnGetCode.setText(R.string.sms_verify_activity_getcode);
                        mTimer.cancel();
                    }

                });
            }
        }, 0, 1000);

    }


    /**
     * 共享元素动画
     */
    public void showEnterAnimation() {

        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

                mCardview.setVisibility(View.INVISIBLE);

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
     * 进入时的圆形遮罩动画
     */
    public void animateRevealShow() {

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardview, mCardview.getWidth() / 2, 0, mFabBack.getWidth() / 2, mCardview.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCardview.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    /**
     * 退出后的遮罩动画
     */
    public void animateRevealClose() {


        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCardview, mCardview.getWidth() / 2, 0, mCardview.getHeight(), mFabBack.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardview.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFabBack.setImageResource(R.drawable.ic_add);
                SmsVerifyActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }
        });

        mAnimator.start();


    }


    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        LogHelper.i("view被移除");
        mPresenter.detachView();
        unregisterReceiver(mSmsReceiver);

        if (mTimer != null) {
            mTimer.cancel();
        }

        super.onDestroy();
    }
}

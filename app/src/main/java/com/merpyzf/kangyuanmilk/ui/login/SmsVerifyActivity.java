package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Context;
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
    CardView cardview;
    //返回上一个界面
    @BindView(R.id.fab_back)
    FloatingActionButton fab_back;
    //验证
    @BindView(R.id.btn_verify)
    Button btn_verify;
    //获取验证码
    @BindView(R.id.btn_get_code)
    Button btn_get_code;
    //手机号输入框
    @BindView(R.id.edt_phone_num)
    EditText edt_phone_num;
    @BindView(R.id.edt_code)
    EditText edt_code;

    @BindView(R.id.text_input_phone)
    TextInputLayout text_input_phone;

    @BindView(R.id.text_input_code)
    TextInputLayout text_input_code;



    private Context mContext;

    private ISMSVerifyContract.ISMSVerifyPresenter mSmsVerifyPresenter = null;
    private int mTime;
    private Timer timer;
    private SMSReceiver mSmsReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms_verify;
    }

    @Override
    public void initWidget() {

        mContext = this;

    }

    @Override
    public void initEvent() {

        showEnterAnimation();
        //注册一个广播接受者，进行解析短信中的验证码
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        mSmsReceiver = new SMSReceiver(edt_code);
        registerReceiver(mSmsReceiver, filter);

        fab_back.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        mSmsVerifyPresenter = new SMSVerifyPresenterImpl();

        LogHelper.i("view添加进去了");
        mSmsVerifyPresenter.attachView(this);
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

                String phoneNum = edt_phone_num.getText().toString().trim();
                //手机格式正确性的校验
                if (RegexHelper.regexPhoneNum(phoneNum)) {

                    text_input_phone.setErrorEnabled(false);
                    mSmsVerifyPresenter.getVerificationCode("86", phoneNum);

                } else {

                    text_input_phone.setError("手机号格式错误");

                }
                break;

            //进行验证
            case R.id.btn_verify:

                String code = edt_code.getText().toString().trim();

                if (code.length() == 4) {

                    //提交验证码进行验证
                    mSmsVerifyPresenter.submitVerify(code);

                    text_input_code.setErrorEnabled(false);

                } else {

                    text_input_code.setError("验证码为4位数");

                }
                break;

            default:

                break;
        }


    }

    /**
     * 验证成功后的回调(子线程)
     *
     * @param phoneNum
     */
    @Override
    public void verifySuccess(String phoneNum) {
        LogHelper.i("验证码验证成功");
        //跳转到注册页面
        App.showToast(this, "跳转到注册页面");

        runOnUiThread(()->{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SmsVerifyActivity.this, fab_back, fab_back.getTransitionName());
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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(() -> {

                    mTime--;
                    btn_get_code.setText(mTime + "s 后重新获取");
                    btn_get_code.setEnabled(false);
                    if (mTime == 0) {

                        btn_get_code.setEnabled(true);
                        btn_get_code.setText("获取验证码");
                        timer.cancel();
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

                cardview.setVisibility(View.INVISIBLE);

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

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardview, cardview.getWidth() / 2, 0, fab_back.getWidth() / 2, cardview.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cardview.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    /**
     * 退出后的遮罩动画
     */
    public void animateRevealClose() {


        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardview, cardview.getWidth() / 2, 0, cardview.getHeight(), fab_back.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardview.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab_back.setImageResource(R.drawable.ic_add);
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
        mSmsVerifyPresenter.detachView();
        unregisterReceiver(mSmsReceiver);

        if (timer != null) {
            timer.cancel();
        }

        super.onDestroy();
    }
}

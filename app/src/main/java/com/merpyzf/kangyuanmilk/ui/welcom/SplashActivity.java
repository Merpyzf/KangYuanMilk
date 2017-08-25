package com.merpyzf.kangyuanmilk.ui.welcom;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.widget.ApplyPermissionFragment;
import com.merpyzf.kangyuanmilk.ui.user.view.HomeActivity;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startCountDown(1222);





        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){


            //进入时首先进行权限判断
            ApplyPermissionFragment applyPermissionFragment = new ApplyPermissionFragment();

            //没有权限需要进行申请
            if(!applyPermissionFragment.checkHaveAllPermis(this)){
                applyPermissionFragment.haveAll(getSupportFragmentManager(), this);
                App.showToast("权限申请");
                //权限申请完成的监听
                applyPermissionFragment.setmOnApplyPermissionCompleted(() -> {


                    startIntent();


                });



            }else {

                //已经拥有权限

                startCountDown(3);



            }


        }else {


            startCountDown(3);

        }

    }

    /**
     * 倒计时
     */
    private void startCountDown(int second){



        CountDownTimer countDownTimer = new CountDownTimer(second*1000, 1000) {
            @Override
            public void onTick(long l) {


            }

            @Override
            public void onFinish() {


                startIntent();


            }
        };

        countDownTimer.start();


    }


    /**
     * 页面跳转
     */
    private void startIntent(){

        if(SharedPreHelper.isFirstUse()){
            GuideActivity.startAction(this);
        }else {
            HomeActivity.startAction(this);
        }
        finish();
    }

}

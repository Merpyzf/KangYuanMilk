package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.merpyzf.kangyuanmilk.R;

public class SmsVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardview;
    private FloatingActionButton fab;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verify);
        cardview = (CardView) findViewById(R.id.cardview_reg);
        fab = (FloatingActionButton) findViewById(R.id.fab_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        fab.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        showEnterAnimation();
    }

    /**
     * 共享元素动画
     */
    public void showEnterAnimation(){

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

    //页面开启时动画
    public void animateRevealShow(){

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardview, cardview.getWidth()/2,0, fab.getWidth() / 2, cardview.getHeight());
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

    //页面关闭时执行的动画
    public void animateRevealClose(){


        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardview,cardview.getWidth()/2,0, cardview.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardview.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_add);
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
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //返回上一个页面
            case R.id.fab_back:
                SmsVerifyActivity.super.onBackPressed();
                break;

            case R.id.btn_next:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SmsVerifyActivity.this, fab, fab.getTransitionName());

                    startActivity(new Intent(SmsVerifyActivity.this, RegisterActivity.class), options.toBundle());


                } else {

                    startActivity(new Intent(SmsVerifyActivity.this, RegisterActivity.class));

                }



                break;
            default:
                break;
        }



    }
}

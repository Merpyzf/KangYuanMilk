package com.merpyzf.kangyuanmilk.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.merpyzf.kangyuanmilk.R;


public class RegisterActivity extends AppCompatActivity {

    private CardView cardView;
    private FloatingActionButton fab_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cardView = (CardView) findViewById(R.id.cardview_input);
        fab_submit = (FloatingActionButton) findViewById(R.id.fab_submit);

        showEnterAnimation();

    }

    public void showEnterAnimation(){

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


    //页面开启时动画
    public void animateRevealShow(){

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth()/2,cardView.getHeight(), fab_submit.getWidth() / 2, cardView.getHeight());
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


    public void animateRevealClose(){

        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cardView,cardView.getWidth()/2,0,0,cardView.getHeight()-fab_submit.getHeight()/2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                RegisterActivity.super.onBackPressed();
                fab_submit.setImageResource(R.drawable.ic_close);
                super.onAnimationEnd(animation);
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
        super.onBackPressed();
       // animateRevealClose();

    }
}

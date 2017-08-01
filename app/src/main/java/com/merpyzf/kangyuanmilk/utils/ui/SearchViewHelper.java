package com.merpyzf.kangyuanmilk.utils.ui;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import net.qiujuer.genius.ui.Ui;

/**
 * Created by wangke on 2017-08-01.
 */

public class SearchViewHelper {

    public static void excuteAnimator(CardView cardView) {

        //执行隐藏动画
        if (cardView.getVisibility() == View.VISIBLE) {

            float radius = (float) Math.hypot(cardView.getWidth(), cardView.getHeight());

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth(), (int) Ui.dipToPx(App.getContext().getResources(), 20), radius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    changeSoftInPutStatus();
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    cardView.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


            circularReveal.setDuration(1000).start();

            //执行显示动画
        } else {

            float radius = (float) Math.hypot(cardView.getWidth(), cardView.getHeight());

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(cardView, cardView.getWidth(), (int) Ui.dipToPx(App.getContext().getResources(), 20), 0, radius);

            cardView.setVisibility(View.VISIBLE);

            circularReveal.setDuration(1000).start();

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    changeSoftInPutStatus();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


        }
    }


    /**
     * 改变软键盘的状态
     */
    public static void changeSoftInPutStatus() {

        //如果此时软键盘显示的话就将其隐藏
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}

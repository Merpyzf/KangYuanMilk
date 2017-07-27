package com.merpyzf.kangyuanmilk.ui.user;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserHomeContract;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户信息主页
 * @author wangke
 */
public class UserHomeActivity extends BaseActivity implements IUserHomeContract.IUserHomeView {

    @BindView(R.id.toolbar_user)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.civ_avater)
    CircleImageView civ_avater;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    public void initWidget() {
        //设置为ActionBar
        setSupportActionBar(toolbar);
        //显示toolbar上的返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //appbar默认为展开状态
        appbar.setExpanded(true);

    }

    @Override
    public void initEvent() {

        initAvaterAnim();
        //设置toolbar返回按钮的事件
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    /**
     * 设置头像随着AppbarLayout伸缩的动画
     */
    @Override
    public void initAvaterAnim() {

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //展开状态
            if (verticalOffset == 0) {
                civ_avater.setVisibility(View.VISIBLE);
                civ_avater.setScaleX(1);
                civ_avater.setScaleY(1);
                civ_avater.setAlpha(1);
            } else {

                final int totalHeight = appBarLayout.getTotalScrollRange();
                verticalOffset = Math.abs(verticalOffset);

                //关闭状态
                if (verticalOffset >= totalHeight) {
                    civ_avater.setVisibility(View.INVISIBLE);
                    civ_avater.setScaleX(0);
                    civ_avater.setScaleY(0);
                    civ_avater.setAlpha(0);

                } else {
                    //中间的过渡状态
                    float progress = 1 - (float) verticalOffset / totalHeight;

                    LogHelper.i("progress=>>" + progress);

                    civ_avater.setScaleX(progress);
                    civ_avater.setScaleY(progress);
                    civ_avater.setAlpha(progress);
                    //处于过渡状态时为可见状态
                    civ_avater.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    /**
     * 点击头像显示的提示框的事件
     */
    @Override
    public void showAvaterDialog() {





    }

    /**
     * 隐藏提示框
     */
    @Override
    public void dissmissAvaterDialog() {

    }

    /**
     * 当前图片上传的进度，用来设置头像上传的动画
     *
     * @param progress 图片上传的进度
     */
    @Override
    public void currentUpLoadImageProgress(float progress) {

    }

    /**
     * 展示用户信息
     *
     * @param user 存储在数据库中的用户信息
     */
    @Override
    public void showUserInfo(LoginBean.ResponseBean.UserBean user) {

    }
}

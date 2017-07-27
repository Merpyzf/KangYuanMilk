package com.merpyzf.kangyuanmilk.ui.user;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.GalleryFragment;
import com.merpyzf.kangyuanmilk.common.widget.AvaterView;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserHomeContract;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.qiniu.UpLoadHelper;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;

/**
 * 用户信息主页
 *
 * @author wangke
 */
public class UserHomeActivity extends BaseActivity implements IUserHomeContract.IUserHomeView, View.OnClickListener {

    @BindView(R.id.toolbar_user)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.civ_avater)
    AvaterView civ_avater;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    private MaterialDialog mMaterialDialog;
    private GalleryFragment mGalleryFragment;


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
        civ_avater.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    /**
     * menu菜单的监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            //更换头像
            case R.id.action_avater:

                showGallery();

                break;

            //编辑资料
            case R.id.action_editor:

                break;

            //退出登录
            case R.id.action_logout:

                break;

            default:

                break;
        }


        return true;
    }

    /**
     * 拍照和UCrop裁切图片完成后的回调事件
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                //GalleryView中拍照
                case GalleryView.GALLERYVIEW_TAKEPHOTO:
                    //调用Fragment中的onActivityResult方法将拍照的图片更新到GralleryView中
                    mGalleryFragment.onActivityResult(requestCode, resultCode, data);
                    break;

                //裁切图片的回调
                case UCrop.REQUEST_CROP:

                    final Uri resultUri = UCrop.getOutput(data);

                    LogHelper.i("要进行上传的图片: " + resultUri.getPath());
                    //裁切后的图片的文件名
                    String filename = new File(resultUri.getPath()).getName();





                    UpLoadHelper upLoadHelper = new UpLoadHelper();

                    //监听上传进度
                    upLoadHelper.upLoadAveter(resultUri.getPath(), (key, info, response) -> {
                        LogHelper.i("上传进度==>" + info.toString());
                        LogHelper.i(response.toString());
                    });

                    //隐藏图片选择器
                    mGalleryFragment.onDismiss();

                    break;

            }

        } else if (resultCode == UCrop.RESULT_ERROR) {

            Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();

        }


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

        mMaterialDialog = new MaterialDialog.Builder(this)
                .items(R.array.click_avater_items)
                .itemsCallback((dialog, view, which, text) -> {

                    showGallery();


                })
                .show();
    }

    /**
     * 隐藏提示框
     */
    @Override
    public void dissmissAvaterDialog() {

        mMaterialDialog.dismiss();

    }

    /**
     * 调用图片选择器
     */
    @Override
    public void showGallery() {

        mGalleryFragment = new GalleryFragment();
        mGalleryFragment.show(getSupportFragmentManager(), UserHomeActivity.class.getSimpleName());

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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.civ_avater:

                showAvaterDialog();

                break;

        }


    }
}

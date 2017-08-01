package com.merpyzf.kangyuanmilk.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.GalleryFragment;
import com.merpyzf.kangyuanmilk.common.observer.Observer;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.common.widget.AvaterView;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserHomeContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.UserHomePresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.yalantis.ucrop.UCrop;

import butterknife.BindView;

/**
 * 用户信息主页
 *
 * @author wangke
 */
public class UserHomeActivity extends BaseActivity implements IUserHomeContract.IUserHomeView, View.OnClickListener, Observer {

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
    private UserHomePresenterImpl mPresenter;


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
        //展示用户信息
        showUserInfo(UserDao.getInstance().getUserInfo());


    }

    @Override
    public void initEvent() {
        //根据手指的滑动设置头像的动画
        initAvaterAnim();
        //设置toolbar返回按钮的事件
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        civ_avater.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new UserHomePresenterImpl();
        mPresenter.attachView(this);

        //添加观察者对象
        UserInfoSubject.getInstance().attach(UserHomeActivity.class.getSimpleName(), this);

    }

    /**
     * 拍照和UCrop裁切图片完成后的回调事件
     *
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
                    String avaterPath = resultUri.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(avaterPath);
                    civ_avater.setImageBitmap(bitmap);
                    LogHelper.i("要进行上传的图片: " + resultUri.getPath());
                    //上传头像
                    mPresenter.upLoadAvater(this, resultUri.getPath());
                    //隐藏图片选择器
                    mGalleryFragment.onDismiss();
                    break;
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {

            Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();

        }

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
     * 调用图片选择器
     */
    @Override
    public void showGallery() {

        mGalleryFragment = new GalleryFragment();
        mGalleryFragment.show(getSupportFragmentManager(), UserHomeActivity.class.getSimpleName());

    }

    /**
     * 当前图片上传的进度，用来设置头像上传的动画
     * @param progress 图片上传的进度
     */
    @Override
    public void currentUpLoadImageProgress(float progress) {

        //设置当前上传图片的进度
        civ_avater.setUpLoadProgress(progress);

    }

    /**
     * 图片上传失败
     */
    @Override
    public void uploadFailed() {

        App.showToast("头像上传失败");
        //上传失败
        civ_avater.upLoadFailed();

    }

    /**
     * 上传成功
     */
    @Override
    public void upLoadSuccess(String key) {

        App.showToast("上传成功");
        //通知所有的观察者进行更新用户信息
        UserInfoSubject.getInstance().notifyChange();


    }

    /**
     * 展示用户信息
     * @param user 用户信息
     */
    @Override
    public void showUserInfo(User user) {

        showAvaterImg(user.getUser_head());
        //设置用户名
        collapsing_toolbar.setTitle(user.getUser_name());

    }


    /**
     * 当前页面的事件监听回调
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //点击头像的事件
            case R.id.civ_avater:

                showAvaterDialog();

                break;

        }


    }


    /**
     *  观察者中的接口，当用户信息发生修改时,立即刷新
     */
    @Override
    public void update() {
        User userInfo = UserDao.getInstance().getUserInfo();

        LogHelper.i("上传的更新的用户头像==>"+userInfo.getUser_head());

        //显示头像
        showAvaterImg(userInfo.getUser_head());
        //更新用户
        collapsing_toolbar.setTitle(userInfo.getUser_name());
    }

    /**
     * 显示用户头像
     *
     * @param avater 对应头像的url
     */
    private void showAvaterImg(String avater) {


        Glide.with(this)
                .load(avater)
                .asBitmap()
                .placeholder(R.drawable.ic_avater_default)
                .centerCrop()
                .dontAnimate()
                .into(new ViewTarget<View, Bitmap>(civ_avater) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        civ_avater.setImageBitmap(resource);

                    }
                });


    }

    /**
     * 展示错误信息
     *
     * @param errorMsg
     */
    @Override
    public void showErrorMsg(String errorMsg) {
        App.showToast(errorMsg);
    }


    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

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
     * 资源释放
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        //移除当前activity对应的观察者对象
        UserInfoSubject.getInstance().detach(UserHomeActivity.class.getSimpleName());
        super.onDestroy();
    }


}

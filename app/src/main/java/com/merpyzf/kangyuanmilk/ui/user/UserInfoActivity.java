package com.merpyzf.kangyuanmilk.ui.user;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.GalleryFragment;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.common.observer.Observer;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.common.widget.AvaterView;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserInfoContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.UserInfoPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.TimeHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.yalantis.ucrop.UCrop;

import butterknife.BindView;

/**
 * 用户资料
 *
 * @author wangke
 */
public class UserInfoActivity extends BaseActivity implements IUserInfoContract.IUserInfoView, View.OnClickListener, Observer {

    @BindView(R.id.toolbar_user)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.civ_avater)
    AvaterView civ_avater;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;

    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_tel)
    TextView tv_tel;
    @BindView(R.id.tv_defalut_address)
    TextView tv_default_address;
    @BindView(R.id.tv_identity)
    TextView tv_identity;
    @BindView(R.id.tv_reg_date)
    TextView tv_reg_date;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.cardView)
    CardView cardView;


    private MaterialDialog mMaterialDialog;
    private GalleryFragment mGalleryFragment;
    private UserInfoPresenterImpl mPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    public void initWidget() {
        //设置为ActionBar
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            //显示toolbar上的返回箭头
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        tv_edit.setOnClickListener(this);

        showEnterAnimation();


    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new UserInfoPresenterImpl();
        mPresenter.attachView(this);

        //添加观察者对象
        UserInfoSubject.getInstance().attach(UserInfoActivity.class.getSimpleName(), this);

    }

    /**
     * 拍照和UCrop裁切图片完成后的回调事件
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据
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

                    if (resultUri != null) {

                        String avaterPath = resultUri.getPath();

                        if (avaterPath != null) {

                            Bitmap bitmap = BitmapFactory.decodeFile(avaterPath);
                            civ_avater.setImageBitmap(bitmap);
                            LogHelper.i("要进行上传的图片: " + resultUri.getPath());
                            //上传头像
                            mPresenter.upLoadAvater(this, resultUri.getPath());
                            //隐藏图片选择器
                            mGalleryFragment.onDismiss();
                        }

                    }
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
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    /**
     * menu菜单的监听事件
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
        mGalleryFragment.show(getSupportFragmentManager(), UserInfoActivity.class.getSimpleName());

    }

    /**
     * 当前图片上传的进度，用来设置头像上传的动画
     *
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
     *
     * @param user 用户信息
     */
    @Override
    public void showUserInfo(User user) {

        LogHelper.i("update==>userTel: " + user.getUser_tel());

        showAvaterImg(user.getUser_head());
        //设置用户名
        collapsing_toolbar.setTitle(user.getUser_name());
        tv_gender.setText(user.isUser_sex() ? "男" : "女");
        tv_default_address.setText(user.getAddress_content());
        tv_identity.setText(user.getUser_idcard());
        tv_reg_date.setText(TimeHelper.getDateTime(Long.valueOf(user.getUser_registerdate())));
        tv_tel.setText(user.getUser_tel());

        LogHelper.i(user.toString());

    }


    /**
     * 当前页面的事件监听回调
     *
     * @param view 被点击的view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //点击头像的事件
            case R.id.civ_avater:

                showAvaterDialog();

                break;

            case R.id.tv_edit:

                ModifyUserInfoActivity.showAction(this, null);

                break;

        }


    }


    /**
     * 观察者中的接口，当用户信息发生修改时,立即刷新
     */
    @Override
    public void update() {

        User userInfo = UserDao.getInstance().getUserInfo();
        //显示头像
        showAvaterImg(userInfo.getUser_head());
        //更新用户i
        collapsing_toolbar.setTitle(userInfo.getUser_name());
        //更新用户信息
        showUserInfo(UserDao.getInstance().getUserInfo());

    }

    /**
     * 显示用户头像
     *
     * @param avater 对应头像的url
     */
    private void showAvaterImg(String avater) {


        Glide.with(this)
                .load(Common.OUTSIDE_CHAIN + avater)
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
     * @param errorMsg 出错时的消息提示
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
                .itemsCallback((dialog, view, which, text) -> showGallery())
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
        UserInfoSubject.getInstance().detach(UserInfoActivity.class.getSimpleName());
        super.onDestroy();
    }

    /**
     * 入场时的动画监听
     */
    public void showEnterAnimation() {

        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {


            }

            @Override
            public void onTransitionEnd(Transition transition) {

                Animator revealAnimator = ViewAnimationUtils.
                        createCircularReveal(cardView, (int) cardView.getX(), (int) cardView.getY(),
                                0f, (float) Math.hypot(cardView.getWidth(), cardView.getHeight()));

                revealAnimator.setDuration(800);

                cardView.setVisibility(View.VISIBLE);
                revealAnimator.start();


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

}

package com.merpyzf.kangyuanmilk.ui;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.ApplyPermissionFragment;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.observer.Observer;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.ui.home.HomeFragment;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主界面
 *
 * @author wangke
 */
public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener
        , Observer {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bottom_nav_view;
    //头像
    CircleImageView civ_avater;
    //用户名
    TextView tv_username;
    RelativeLayout rl_nav_header;
    private UserInfoSubject m;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initWidget() {

        ApplyPermissionFragment applyPermissionFragment = new ApplyPermissionFragment();

        applyPermissionFragment.haveAll(getSupportFragmentManager(), this);

        setSupportActionBar(toolbar);

        //从 navigationView中获取控件的引用时候,需要通过getHeaderView拿到HeaderView布局的引用，这样才能继续下面的工作
        View view = navigationView.getHeaderView(0);
        rl_nav_header = view.findViewById(R.id.rl_nav_header);
        civ_avater = view.findViewById(R.id.iv_avater);
        tv_username = view.findViewById(R.id.tv_username);

        setNavHeaderBg(rl_nav_header);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        updateUserCurrentStatus();


        getSupportFragmentManager().beginTransaction().add(R.id.coordLayout, new HomeFragment()).commit();


    }

    /**
     * 更新用户当前的状态
     *  刷新头像,和用户名
     */
    private void updateUserCurrentStatus() {

        UserDao userDao = new UserDao(this);
        LoginBean.ResponseBean.UserBean user = userDao.getUserInfo();

        if (user == null) {
            //未登录状态
            tv_username.setText("点击头像进行登录");
            ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();
            ImageLoaderOptions options = bulider.isCenterCrop(true)
                    .build();
            GlideImageLoader.showImage(civ_avater, R.drawable.ic_avater_default, options);

        } else {

            //已登录
            //显示用户名
            tv_username.setText(user.getUser_name());

            ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();
            ImageLoaderOptions options = bulider.isCenterCrop(true)
                    .build();
            GlideImageLoader.showImage(civ_avater, user.getUser_head(), options);
        }


        civ_avater.setOnClickListener(view1 -> {

            if (user == null) {
                //未登录状态
                startActivity(new Intent(this, LoginActivity.class));
            } else {

                //已登录
                App.showToast(this, "跳转到个人详情页面");
            }

        });


    }

    @Override
    public void initEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        bottom_nav_view.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void initData() {

        //注册一个观察者
        UserInfoSubject.getInstance().attach(HomeActivity.class.getSimpleName(), this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //导航抽屉菜单的点击事件
        switch (item.getItemId()) {

            case R.id.nav_indent:

                break;

            case R.id.nav_address:

                break;


            case R.id.nav_service:

                break;

            //注销登录
            case R.id.nav_logout:

                new MaterialDialog.Builder(this)
                        .title(R.string.dialog_logout)
                        .content(R.string.dialog_logout_content)
                        .positiveText(R.string.dialog_logout_positive)
                        .negativeText(R.string.dialog_logout_negative)
                        .onPositive((dialog, which) -> {
                            //清除用户的个人信息，但不包括保存的用户名和密码
                            UserDao userDao = new UserDao(App.getContext());
                            userDao.clearUser();

                            //通知所有绑定的观察者刷新
                            UserInfoSubject.getInstance()
                                    .notifyChange();
                        })
                        .onNegative((dialog, which) -> {

                            dialog.dismiss();

                        })
                        .show();


                break;
            case R.id.nav_setting:
                break;
            //
            case R.id.nav_share:

                break;

            case R.id.nav_advice:

                break;
            case R.id.nav_about:
                break;


            //底部导航菜单的事件监听
            case R.id.action_home:

                break;

            case R.id.action_goods:

                break;
            case R.id.action_shopping_cart:
                break;

            default:

                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    /**
     * 设置导航抽屉头部的背景图片
     *
     * @param rl_nav_header
     */
    private void setNavHeaderBg(RelativeLayout rl_nav_header) {

        Glide.with(this)
                .load(R.drawable.ic_nav_bar)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(rl_nav_header) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        rl_nav_header.setBackground(resource.getCurrent());
                    }
                });

    }



    @Override
    public void update() {
        LogHelper.i("更新头像了");
        //更新用户的当前状态,包含头像和用户名
        updateUserCurrentStatus();


    }

    /**
     * 点击back键后的事件
     */
    @Override
    public void onBackPressed() {
        //根据DrawerLayout的当前状态选择是开启还是关闭
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        //移除观察者
        UserInfoSubject.getInstance().detach(HomeActivity.class.getSimpleName());
        super.onDestroy();

    }
}

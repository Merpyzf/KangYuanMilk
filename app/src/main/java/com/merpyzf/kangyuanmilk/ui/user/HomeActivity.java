package com.merpyzf.kangyuanmilk.ui.user;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
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
import com.merpyzf.kangyuanmilk.common.widget.AvaterView;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.home.HomeFragment;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.user.contract.IHomeContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.HomePresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.AddressDao;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;

import butterknife.BindView;

/**
 * 主界面
 *
 * @author wangke
 */
public class HomeActivity extends BaseActivity
        implements IHomeContract.IHomeView, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener
        , Observer {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bottom_nav_view;
    AvaterView civ_avater;
    TextView tv_username;
    RelativeLayout rl_nav_header;
    private HomePresenterImpl mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initWidget() {

        //进入时首先进行权限判断
        ApplyPermissionFragment applyPermissionFragment = new ApplyPermissionFragment();
        applyPermissionFragment.haveAll(getSupportFragmentManager(), this);
        setSupportActionBar(toolbar);

        //从 navigationView中获取控件的引用时候,需要通过getHeaderView拿到HeaderView布局的引用，这样才能继续下面的工作
        View view = navigationView.getHeaderView(0);
        rl_nav_header = view.findViewById(R.id.rl_nav_header);
        civ_avater = view.findViewById(R.id.iv_avater);
        tv_username = view.findViewById(R.id.tv_username);
        //设置抽屉菜单头部背景
        setNavHeaderBg(rl_nav_header);
        //将抽屉菜单和ToolBar关联在一起
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //填充HomeFragment
        getSupportFragmentManager().beginTransaction().add(R.id.coordLayout, new HomeFragment()).commit();


    }

    @Override
    public void initEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        bottom_nav_view.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initData() {

        AddressDao.getInstance().getProvinceList();


        //注册一个观察者
        UserInfoSubject.getInstance().attach(HomeActivity.class.getSimpleName(), this);
        mPresenter = new HomePresenterImpl();
        mPresenter.attachView(this);
        //第一次创建的时候检查用户的当前状态
        mPresenter.checkUserCurrentStatus(this);
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

                if (UserDao.getInstance(App.getContext()).getUserInfo() != null) {

                    new MaterialDialog.Builder(this)
                            .title(R.string.dialog_logout)
                            .content(R.string.dialog_logout_content)
                            .positiveText(R.string.dialog_logout_positive)
                            .negativeText(R.string.dialog_logout_negative)
                            .onPositive((dialog, which) -> {
                                //清除用户的个人信息，但不包括保存的用户名和密码
                                UserDao userDao = UserDao.getInstance(App.getContext());
                                userDao.clearUser();

                                SharedPreHelper.clearLoginInfo();
                                //通知所有绑定的观察者刷新
                                currentStatus(false);

                            })
                            .onNegative((dialog, which) -> {

                                dialog.dismiss();

                            })
                            .show();
                }


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
    public void showErrorMsg(String errorMsg) {

        App.showToast(errorMsg);
    }


    @Override
    public void currentStatus(boolean isLogin) {

        //不同的登录状态对应头像不同的点击事件
        civ_avater.setOnClickListener(view -> {
            if (!isLogin) {
                //未登录状态
                startActivity(new Intent(this, LoginActivity.class));
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    //共享元素动画
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, civ_avater, civ_avater.getTransitionName());
                    startActivity(new Intent(HomeActivity.this, UserHomeActivity.class), options.toBundle());

                } else {

                    startActivity(new Intent(HomeActivity.this, UserHomeActivity.class));
                }

            }
        });
        LogHelper.i("更新UI");
        //从数据库中获取用户当前的信息(使用application的上下文对象而不是当前Activity对象避免内存泄露(单列模式需要注意的地方))
        UserDao userDao = UserDao.getInstance(App.getContext());
        User user = userDao.getUserInfo();
        if (user == null) {
            //未登录状态
            tv_username.setText("点击头像进行登录");
            civ_avater.setImageResource(R.drawable.ic_avater_default);

        } else {

            loadAvater(user.getUser_head());
            tv_username.setText(user.getUser_name());

        }

    }

    @Override
    public void authFailed() {

        App.showToast("认证失败,请重新登录");
        startActivity(new Intent(this, LoginActivity.class));

    }

    @Override
    public void updateUserInfo() {


    }

    /**
     * 观察者的update方法,当用户信息发生变化的时候调用
     */
    @Override
    public void update() {
        LogHelper.i("HomeActivity==> 更新头像了");
        //更新用户的头像和用户名
        UserDao userDao = UserDao.getInstance(App.getContext());
        User user = userDao.getUserInfo();
        loadAvater(user.getUser_head());
    }

    /**
     * 加载头像
     *
     * @param avaterUrl
     */
    private void loadAvater(String avaterUrl) {
        // TODO: 2017-07-28  使用了全局的上下文对象，不受Activity生命周期的影响，容易发生内存泄露
        Glide.with(App.getContext())
                .load(avaterUrl)
                .placeholder(R.drawable.ic_avater_default)
                .centerCrop()
                .dontAnimate()
                .into(new ViewTarget<View, GlideDrawable>(civ_avater) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        civ_avater.setImageDrawable(resource.getCurrent());

                    }
                });

    }

    @Override
    protected void onResume() {
        //重新回到这个页面的时候进行用户状态的检查
        mPresenter.checkUserCurrentStatus(this);
        super.onResume();
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
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    protected void onDestroy() {
        //移除观察者
        UserInfoSubject.getInstance().detach(HomeActivity.class.getSimpleName());
        super.onDestroy();

    }


}

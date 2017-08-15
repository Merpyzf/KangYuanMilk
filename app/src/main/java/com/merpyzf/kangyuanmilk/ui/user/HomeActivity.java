package com.merpyzf.kangyuanmilk.ui.user;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
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
import com.merpyzf.kangyuanmilk.common.widget.ApplyPermissionFragment;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.home.CategoryPickerFragment;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.common.observer.Observer;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.common.widget.AvaterView;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.home.CategoryFragment;
import com.merpyzf.kangyuanmilk.ui.home.HomeFragment;
import com.merpyzf.kangyuanmilk.ui.home.IndentFragment;
import com.merpyzf.kangyuanmilk.ui.home.SearchActivity;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.order.OrderActivity;
import com.merpyzf.kangyuanmilk.ui.user.contract.IHomeContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.HomePresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.ui.NavFragManager;

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
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.coordLayout)
    CoordinatorLayout coordinatorLayout;
    //默认为主页
    private CurrentPage mCurrentPage = CurrentPage.HOME;

    AvaterView civ_avater;
    TextView tv_username;
    RelativeLayout rl_nav_header;
    private HomePresenterImpl mPresenter;
    private NavFragManager mNavFragManager = null;
    private AppBarState mCurrentAppBarState = AppBarState.IDLE;


    private enum CurrentPage {
        HOME,
        GOODS,
        SHOPPING_CART

    }


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

        toolbar.post(() -> toolbar.setTitle("主页"));

        getMaxMemoryInfo();


    }

    private void getMaxMemoryInfo() {
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();

        LogHelper.i("MaxMemory: ==> " + Long.toString(maxMemory / (1024 * 1024)));
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        LogHelper.i("MemoryClass: ==> " + Long.toString(activityManager.getMemoryClass()));
        LogHelper.i("LargeMemoryClass:==> " + Long.toString(activityManager.getLargeMemoryClass()));
    }

    @Override
    public void initEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        bottom_nav_view.setOnNavigationItemSelectedListener(this);

        /**
         * 监听AppBarLayout的当前状态: 隐藏/展开/变化中
         */
        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

            int height = toolbar.getHeight();

            if (verticalOffset == 0) {
                mCurrentAppBarState = AppBarState.EXPAND;
            } else if (verticalOffset == -height) {
                mCurrentAppBarState = AppBarState.COLLAPSED;
            } else {
                mCurrentAppBarState = AppBarState.IDLE;
            }


        });


    }

    @Override
    protected void initData() {

        //注册一个观察者
        UserInfoSubject.getInstance().attach(HomeActivity.class.getSimpleName(), this);
        mPresenter = new HomePresenterImpl();
        mPresenter.attachView(this);
        //第一次创建的时候检查用户的当前状态
        mPresenter.checkUserCurrentStatus(this);
        mNavFragManager = new NavFragManager(this, R.id.coordLayout, getSupportFragmentManager());
        //初始化底部tab菜单所对应的Fragment
        mNavFragManager.add(R.id.action_home, new NavFragManager.Tab(HomeFragment.class, R.id.action_home))
                .add(R.id.action_goods, new NavFragManager.Tab(CategoryFragment.class, R.id.action_goods))
                .add(R.id.action_shopping_cart, new NavFragManager.Tab(IndentFragment.class, R.id.action_shopping_cart));
        //初始化首页
        mNavFragManager.initHome(R.id.action_home);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //导航抽屉菜单的点击事件
        switch (item.getItemId()) {

            //订单
            case R.id.nav_indent:

                OrderActivity.showAction(this);

                break;

            case R.id.nav_address:

                if (UserDao.getInstance().isLogin()) {

                    UserAddressActivity.showAction(HomeActivity.this);
                }

                break;

            case R.id.nav_service:

                break;

            //注销登录
            case R.id.nav_logout:

                if (UserDao.getInstance().isLogin()) {

                    new MaterialDialog.Builder(HomeActivity.this)
                            .title(R.string.dialog_logout)
                            .content(R.string.dialog_logout_content)
                            .positiveText(R.string.dialog_logout_positive)
                            .negativeText(R.string.dialog_logout_negative)
                            .onPositive((dialog, which) -> {
                                //清除用户的个人信息，但不包括保存的用户名和密码
                                UserDao userDao = UserDao.getInstance();
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

                toolbar.setTitle("主页");
                mNavFragManager.performClickNavMenu(item.getItemId());
                coordinatorLayout.setEnabled(true);

                mCurrentPage = CurrentPage.HOME;
                //刷新menu中的item
                invalidateOptionsMenu();
                break;

            case R.id.action_goods:

                toolbar.setTitle("商品");
                mNavFragManager.performClickNavMenu(item.getItemId());
                coordinatorLayout.setEnabled(false);

                mCurrentPage = CurrentPage.GOODS;
                invalidateOptionsMenu();
                break;
            case R.id.action_shopping_cart:

                toolbar.setTitle("购物车");
                mNavFragManager.performClickNavMenu(item.getItemId());
                coordinatorLayout.setEnabled(false);

                mCurrentPage = CurrentPage.SHOPPING_CART;
                invalidateOptionsMenu();
                break;

            default:

                break;
        }
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        //主页
        if (mCurrentPage == CurrentPage.HOME) {

            menu.findItem(R.id.action_home_category).setVisible(false);
            menu.findItem(R.id.action_home_remove).setVisible(false);
            //商品
        } else if (mCurrentPage == CurrentPage.GOODS) {


            menu.findItem(R.id.action_home_category).setVisible(true);
            menu.findItem(R.id.action_home_remove).setVisible(false);
        } else {

            menu.findItem(R.id.action_home_category).setVisible(false);
            menu.findItem(R.id.action_home_remove).setVisible(true);

        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            //商品搜索
            case R.id.action_home_search:

                startActivity(new Intent(this, SearchActivity.class));

                return true;

            //商品分类
            case R.id.action_home_category:

                CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();

                categoryPickerFragment.show(getSupportFragmentManager());


                return true;


            //清空购物车
            case R.id.action_home_remove:

                return true;


            //搜索
            case R.id.action_home_settings:


                return true;

        }


        return super.onOptionsItemSelected(item);
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
                    startActivity(new Intent(HomeActivity.this, UserInfoActivity.class), options.toBundle());

                } else {

                    startActivity(new Intent(HomeActivity.this, UserInfoActivity.class));

                }

            }
        });
        LogHelper.i("更新UI");
        //从数据库中获取用户当前的信息(使用application的上下文对象而不是当前Activity对象避免内存泄露(单列模式需要注意的地方))
        UserDao userDao = UserDao.getInstance();
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
        UserDao userDao = UserDao.getInstance();
        User user = userDao.getUserInfo();
        loadAvater(user.getUser_head());
        tv_username.setText(user.getUser_name());
    }

    /**
     * 加载头像
     *
     * @param avaterUrl
     */
    private void loadAvater(String avaterUrl) {
        // TODO: 2017-07-28  使用了全局的上下文对象，不受Activity生命周期的影响，容易发生内存泄露
        Glide.with(App.getContext())
                .load(Common.OUTSIDE_CHAIN + avaterUrl)
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);


        } else if (mCurrentAppBarState == AppBarState.COLLAPSED) {

            appbar.setExpanded(true, true);

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

    public enum AppBarState {
        EXPAND,
        COLLAPSED,
        IDLE
    }

    @Override
    protected void onDestroy() {
        //移除观察者
        UserInfoSubject.getInstance().detach(HomeActivity.class.getSimpleName());
        super.onDestroy();

    }


}

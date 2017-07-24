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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.ApplyPermissionFragment;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.home.HomeFragment;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主界面
 *
 * @author wangke
 */
public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

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

        getSupportFragmentManager().beginTransaction().add(R.id.coordLayout, new HomeFragment()).commit();


    }

//    private final Handler mHandler = new Handler();

    @Override
    public void initEvent() {
        navigationView.setNavigationItemSelectedListener(this);
        bottom_nav_view.setOnNavigationItemSelectedListener(this);


        //

        tv_username.setOnClickListener(view -> {


            HomeActivity.this.startActivity(new Intent(HomeActivity.this, LoginActivity.class));


        });




   /*     //模拟内存泄露
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3 * 60 * 1000);
        finish();*/

    }

    @Override
    protected void initData() {


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

            case R.id.nav_logout:

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

}

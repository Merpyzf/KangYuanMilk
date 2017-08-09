package com.merpyzf.kangyuanmilk.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 17-7-16.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();

        Bundle bundle = getIntent().getExtras();

        //如果获取到传递过来的参数才继续向下执行
        if (initArgs(bundle)) {

            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);

            initWidget();

            initEvent();
            initData();
        }

    }


    /**
     * 初始化窗体,setContentView之前调用
     */
    protected void initWindow() {


    }

    /**
     * 获取从Activity传递过来的布局参数
     *
     * @param bundle 封装要进行传递的值
     * @return true/false
     */
    protected boolean initArgs(Bundle bundle) {


        return true;
    }

    /**
     * 获取当前Activity的布局id
     *
     * @return 布局id
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件ui数据
     */
    public abstract void initWidget();

    /**
     * 创建控件的相关事件
     */
    public void initEvent() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 进行页面的跳转
     */
    public void show(Context context, Bundle bundle) {


    }

    @Override
    protected void onDestroy() {

        App.getRefWatcher().watch(this);

        //activity销毁的时候解除bind
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        /*获取当前Activity下放置的所有的fragment如果有任意的一个fragmet想要处理返回事件就直接return,使当前的activity
         *的back失效
         */
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {

            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment instanceof BaseFragment && ((BaseFragment) fragment).onBackPressed()) {
                    return;
                }
            }
        }


        super.onBackPressed();
    }
}

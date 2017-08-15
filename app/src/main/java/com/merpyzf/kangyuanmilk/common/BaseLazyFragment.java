package com.merpyzf.kangyuanmilk.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类(懒加载)
 * Created by wangke on 17-8-15.
 */

public abstract class BaseLazyFragment extends RxFragment {

    private String TAG;
    //标记当前Fragment的可见状态
    private boolean isFragmentVisible;
    private boolean isFirstVisible;
    //对Fragment中加载的View进行缓存
    private View mRootView;
    private Unbinder mUnbinder;


    public BaseLazyFragment(String TAG) {
        this.TAG = TAG;
    }


    /**
     * 当Fragment添加到Activity的时候最先调用此方法
     *
     * @param context 上下文对象
     */
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        //获取Fragment之间传递过来的参数
        initArgs(getArguments());


    }

    /**
     * 获取Fragment中传递过来的参数，选择重写
     *
     * @param arguments
     */
    protected void initArgs(Bundle arguments) {


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        LogHelper.i("wwk", TAG + " ===>onCreate");
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(getContentLayoutId(), container, false);

        mUnbinder = ButterKnife.bind(this, view);
        initWidget(mRootView);
        initEvent();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogHelper.i("wwk", TAG + " ===>onViewCreated");

        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    //处于可见状态并且Fragment是第一次开启
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }

                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }

        super.onViewCreated(mRootView, savedInstanceState);
    }

    /**
     * 初始化控件事件，选择重写
     */
    private void initEvent() {

    }

    /**
     * 初始化组件
     *
     * @param view
     */
    protected abstract void initWidget(View view);


    /**
     * 当Fragment第一次创建的时候调用,如果不可见则isVisible的参数值为false
     * 当Fragment对于用户可见时调用,此时的isVisibleToUser参数值为true
     * 当Fragment当前的状态由可见变为不可见时调用,此时的isVisibleToUser参数为false
     *
     * @param isVisibleToUser true : 可见
     *                        false : 不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogHelper.i("tag", TAG + " ==> setUserVisibleHint ");
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()的调用在Fragment的声明周期外调用,需要保证rootView不为空的时候调用 onFragmentVisibleChange方法
        if (mRootView == null) {
            return;
        }

        if (isFirstVisible && isVisibleToUser) {
            //当Fragment第一次可见的时候调用
            onFragmentFirstVisible();
            isFirstVisible = false;
        }

        if (isVisibleToUser) {
            //
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }

        if (isFragmentVisible) {
            //由 可见 -> 不可见
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }


    /**
     * 获取填充Fragment的View的id
     *
     * @return
     */
    protected abstract int getContentLayoutId();


    /**
     * 当Fragment的状态发生变化的时候调用,用于进行数据的刷新
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible);

    /**
     * 当Fragment第一次被开启的时候调用,用于请求数据
     */
    protected abstract void onFragmentFirstVisible();

    /**
     * 获取当前Fragment的可见状态
     *
     * @return
     */
    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 给变量赋初始值
     */
    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        mRootView = null;
    }

    @Override
    public void onDestroy() {
        initVariable();
        mUnbinder.unbind();
        super.onDestroy();
    }


}

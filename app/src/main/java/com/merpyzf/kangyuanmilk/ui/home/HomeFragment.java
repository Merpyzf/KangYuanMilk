package com.merpyzf.kangyuanmilk.ui.home;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * @author wangke
 */
public class HomeFragment extends BaseFragment {

    private List<String> mDatas = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View rootview) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mDatas.clear();
        mDatas.add("haha");
        recyclerView.setAdapter(new HomeAdapter(mDatas,getContext(),recyclerView));
    }

}

package com.merpyzf.kangyuanmilk.ui.home.view;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.adapter.CategoryPickerAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Category;
import com.merpyzf.kangyuanmilk.ui.home.contract.ICategoryPickerContract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.CategoryPickerPresenter;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品分类选择
 *
 * @author wangke
 */
public class CategoryPickerFragment extends BottomSheetDialogFragment implements
        ICategoryPickerContract.ICategoryPickerView, RecyclerAdapter.ItemClickListener<Category> {


    private Unbinder mUnbinder;
    private ICategoryPickerContract.ICategoryPickPresenter mPresenter;

    private OnCategoryCheckedCListener mListener = null;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private CategoryPickerAdapter mAdapter;

    public void show(FragmentManager fragmentManager) {

        show(fragmentManager, "tag");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPresenter = new CategoryPickerPresenter();
        mPresenter.attachView(this);
        View view = inflater.inflate(R.layout.fragment_category_picker, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);

        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getGoodsCategory();

        return view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getContext());
    }


    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void showGoodsCategory(List<Category> categoryList) {

        mAdapter = new CategoryPickerAdapter(categoryList, getContext(), mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        if (mAdapter != null) {

            mAdapter.setOnItemClickListener(this);

        }


    }

    //上一个被选中的分类的下标
    private int mOldCheckedPosition = 0;

    @Override
    public void onItemClick(ViewHolder viewHolder, Category category, int position) {

        if (mListener != null) {

            List<Category> datas = mAdapter.getDatas();
            for (int i = 0; i < datas.size(); i++) {
                //找出被选中的商品分类所在的下标记
                if (datas.get(i).isChoice()) {
                    mOldCheckedPosition = i;
                    datas.get(i).setChoice(false);
                }
            }
            //局部刷新RecyclerView
            mAdapter.notifyItemChanged(mOldCheckedPosition);
            datas.get(position).setChoice(true);
            mAdapter.notifyItemChanged(position);

            mListener.checkedCategoryId(category.getCategory_id());


        }

    }

    @Override
    public boolean onItemLongClick(ViewHolder viewHolder, Category category, int position) {
        return false;
    }

    public interface OnCategoryCheckedCListener {
        /**
         * 选中分类时的回调方法
         *
         * @param id 分类id
         */
        void checkedCategoryId(int id);

    }

    public void setmListener(OnCategoryCheckedCListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        mListener = null;
        mPresenter.detachView();
        super.onDestroy();

    }
}

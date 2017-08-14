package com.merpyzf.kangyuanmilk.ui.home;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品分类选择
 *
 * @author wangke
 */
public class CategoryPickerFragment extends BottomSheetDialogFragment {



    private Unbinder mUnbinder;


    public void show(FragmentManager fragmentManager) {

        show(fragmentManager, "tag");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_picker, container, false);
        mUnbinder = ButterKnife.bind(this, view);



        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();

    }


}

package com.merpyzf.kangyuanmilk.common.widget;


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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 地址选择
 *
 * @author wangke
 */
public class AddressPickerFragment extends BottomSheetDialogFragment {


    private Unbinder mUnbinder;
    private OnSelectorComplete mOnSelectorComplete;

    @BindView(R.id.addressSelectorView)
    AddressSelectorView addressSelectorView;

    public void show(FragmentManager fragmentManager) {

        show(fragmentManager, "tag");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address_picker, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        addressSelectorView.setOnAddressSelectListenter((String address, int id) -> {
            dismiss();
            if (mOnSelectorComplete != null) {
                mOnSelectorComplete.selectedComplete(address, id);
            }
        });
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

    /**
     * 地址选择结束后的回调
     */
    public interface OnSelectorComplete {
        void selectedComplete(String address, int id);
    }

    public void setOnSelectorComplete(OnSelectorComplete onSelectorComplete) {
        mOnSelectorComplete = onSelectorComplete;
    }
}

package com.merpyzf.kangyuanmilk.common;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.AddressSelectorView;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
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

        addressSelectorView.setOnAddressSelectListenter((address, id) -> {

            dismiss();
            LogHelper.i("地址==》" + address);

            if (mOnSelectorComplete != null) {

                mOnSelectorComplete.selectedComplete(address, id);

            }


        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();

    }


    public interface OnSelectorComplete {

        void selectedComplete(String address, int id);

    }

    public void setOnSelectorComplete(OnSelectorComplete onSelectorComplete) {
        mOnSelectorComplete = onSelectorComplete;

    }
}

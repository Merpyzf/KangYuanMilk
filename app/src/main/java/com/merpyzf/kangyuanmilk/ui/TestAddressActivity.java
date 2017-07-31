package com.merpyzf.kangyuanmilk.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.AddressSelectorView;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

public class TestAddressActivity extends AppCompatActivity {

    private AddressSelectorView addressSelectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_address);

        addressSelectorView = (AddressSelectorView) findViewById(R.id.addressSelectorView);


        addressSelectorView.setOnAddressSelectListenter((address, id) -> {

            LogHelper.i("id==>" + id + "address ==>" + address);

        });

    }
}

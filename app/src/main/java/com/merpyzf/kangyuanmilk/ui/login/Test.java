/*
package com.merpyzf.smssample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.merpyzf.kangyuanmilk.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Test extends AppCompatActivity {

    private EditText edt_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_code = (EditText) findViewById(R.id.edt_code);


        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(true);

        */
/**
         * 短信发送的监听，用于判断验证码是否发出，提交的验证码是否通过等信息，因为这个不是
         * 主线程中的，所以不能对UI线程进行操作，所以这里不能对UI进行操作,需要使用
         * 消息机制进行消息的传递
         *//*

        EventHandler eventHandler = new EventHandler() {

            @Override
            public void onRegister() {
                super.onRegister();
                Log.i("wk", "onRegister==>被调用");

            }

            @Override
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);

                Log.i("wk", "beforeEvent==>被调用");
            }


            public void afterEvent(int event, int result, Object data) {
                Log.i("wk", "event:" + event + "    result:" + result + "    data:" + data.toString());

                switch (event) {

                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:

                        if (result == SMSSDK.RESULT_COMPLETE) {

                            Log.i("wk", "短信验证成功");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(MainActivity.this, "短信验证成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Log.i("wk", "短信验证失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(MainActivity.this, "短信验证失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;

                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:

                        if (result == SMSSDK.RESULT_COMPLETE) {

                            Log.i("wk", "获取验证码成功");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(MainActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            //默认的智能验证是开启的,我已经在后台关闭
                        } else {

                            Log.i("wk", "获取验证码失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(MainActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;
                }
            }

            @Override
            public void onUnregister() {
                super.onUnregister();

                Log.i("wk", "onUnregister==>被调用");
            }

        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);


    }

    public void clickSMSCode(View view) {

        Toast.makeText(this, "短信验证码获取中……", Toast.LENGTH_SHORT).show();
        SMSSDK.getVerificationCode("86", "17714574929");

    }

    public void clickSubmit(View view) {
        Toast.makeText(this, "提交短信验证码……", Toast.LENGTH_SHORT).show();

        String code = edt_code.getText().toString().trim();
        //提交短信验证码，在监听中返回，str :手机号 str1:收到的验证码
        SMSSDK.submitVerificationCode("86", "17714574929", code);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当Activity销毁的时候取消注册
        SMSSDK.unregisterAllEventHandler();
    }
}
*/
/**/*/

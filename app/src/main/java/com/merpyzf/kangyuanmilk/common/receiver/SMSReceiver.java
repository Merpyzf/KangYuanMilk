package com.merpyzf.kangyuanmilk.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

    private EditText editText = null;

    public SMSReceiver(EditText editText) {

        this.editText = editText;

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action =  intent.getAction();

        if(action.equals("android.provider.Telephony.SMS_RECEIVED")) {

            //获取短信内容,有可能一次发来多条短信
            Object[] objects = (Object[]) intent.getExtras().get("pdus");

            for (Object obj : objects) {

                //获取短信对象
                SmsMessage msg = SmsMessage.createFromPdu((byte[]) obj);
                //获取短信内容
                String msgBody = msg.getDisplayMessageBody();
                //获取发信人的电话号码
                String originatingAddress = msg.getOriginatingAddress();
                Log.i("wk","短信内容==>"+msgBody+" 来自==>"+originatingAddress);

                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher matcher = pattern.matcher(msgBody);

                if (matcher.find()) {

                    editText.setText(matcher.group(1));

                }
            }

        }
    }
}

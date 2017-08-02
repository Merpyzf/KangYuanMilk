package com.merpyzf.kangyuanmilk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangke on 2017-07-25.
 * 正则校验
 */

public class RegexHelper {

    /**
     * 正则表达式：验证身份证,粗略验证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_PHONE_NUM = "^1(3|4|5|7|8)\\d{9}";

    /**
     * 手机号码的校验
     * @param phoneNum
     * @return true/false
     */
    public static boolean regexPhoneNum(String phoneNum) {

        Pattern compile = Pattern.compile(REGEX_PHONE_NUM);

        Matcher matcher = compile.matcher(phoneNum);

        return matcher.matches();
    }


    public static boolean regexIdCard(String idNum){

        Pattern compile = Pattern.compile(REGEX_ID_CARD);

        Matcher matcher = compile.matcher(idNum);

        return matcher.matches();

    }


}

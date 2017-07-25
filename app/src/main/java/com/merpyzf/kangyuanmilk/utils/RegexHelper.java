package com.merpyzf.kangyuanmilk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangke on 2017-07-25.
 * 正则校验
 */

public class RegexHelper {

    /**
     * 手机号码的校验
     * @param phoneNum
     * @return true/false
     */
    public static boolean regexPhoneNum(String phoneNum) {

        Pattern compile = Pattern.compile("^1(3|4|5|7|8)\\d{9}");

        Matcher matcher = compile.matcher(phoneNum);

        return matcher.matches();
    }

}

package com.merpyzf.kangyuanmilk;

import android.util.Log;

import com.merpyzf.kangyuanmilk.utils.qiniu.Auth;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void myTest(){

        Auth auth = Auth.create("KXLzuFCOxgNar5whqU3-0bmrH6rTHOqaidcohRes", "aiw1aAh-dwA9k6nkWAQLExy2Taz9cE1nFYA_01WY");
        String uploadToken = auth.uploadToken("kangyuanmilk", "/avater");
        System.out.println(uploadToken);


    }

}
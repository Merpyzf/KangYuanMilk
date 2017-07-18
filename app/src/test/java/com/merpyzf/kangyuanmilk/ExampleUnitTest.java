package com.merpyzf.kangyuanmilk;

import android.util.Log;

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

        ArrayList<Integer> mDatas = new ArrayList<>();

        mDatas.add(1);
        mDatas.add(2);
        mDatas.add(3);

        mDatas.remove(1);
        mDatas.add(1,9);

        for (int i = 0; i < mDatas.size(); i++) {

            System.out.println(mDatas.get(i));

        }



    }

}
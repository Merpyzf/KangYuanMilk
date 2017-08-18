package com.merpyzf.kangyuanmilk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void myTest() {


        String path = "http://otdmrup4y.bkt.clouddn.com/avatar/52f3187f518f7106a82b5578424ca6cf";

        String[] split = path.split("/");

        String avater = split[split.length - 2] + "/" + split[split.length - 1];


        List<String> mNewsDataList = new ArrayList<>();

        List<String> mLastDataList = new ArrayList<>();


        for (int i = 0; i < mNewsDataList.size(); i++) {

            mNewsDataList.add("new==>" + i);


        }


    }


}
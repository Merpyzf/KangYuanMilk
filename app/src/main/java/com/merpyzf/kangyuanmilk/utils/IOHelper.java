package com.merpyzf.kangyuanmilk.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangke on 2017-07-30.
 * IO工具类
 */

public final class IOHelper {

    private IOHelper(){
        throw new UnsupportedOperationException("u can't instantiante me……");
    }

    /**
     * 将流写入到文件
     *
     * @param is   输入流
     * @param file 要写入的文件
     */
    public static void stream2File(InputStream is, File file) {

        FileOutputStream os = null;
        byte[] buffer = new byte[1024];
        try {


            os = new FileOutputStream(file);


            while (is.read(buffer, 0, buffer.length) != -1) {

                os.write(buffer, 0, buffer.length);
                os.flush();
            }

            LogHelper.i("写入成功");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


}

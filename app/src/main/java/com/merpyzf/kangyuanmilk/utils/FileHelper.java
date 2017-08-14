package com.merpyzf.kangyuanmilk.utils;

import java.io.File;

/**
 * Created by wangke on 2017-07-20.
 * 与文件操作有关的工具类
 */

public final class FileHelper {

    private FileHelper(){
        throw new UnsupportedOperationException("u can't instantiante me……");
    }


    /**
     * 清理文件夹中不需要的文件
     * @param parent 父目录
     * @param max  清理文件临界值
     */
    public static void clearFiles(String parent,int max){

        File parentFile = new File(parent);

        if(!parentFile.exists()){
            Throwable throwable = new Throwable("要清理的文件夹不存在！");
            throwable.printStackTrace();
            return;
        }

        File[] files = parentFile.listFiles();
        if(files.length>max){

            for (File file : files) {

                boolean delete = file.delete();
                LogHelper.i("FileHelper->clearFiles==>文件清理结果:"+delete);
            }


        }


    }


}

package com.merpyzf.kangyuanmilk.utils.qiniu;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by wangke on 2017-07-20.
 * 单例获取UpLoadManager保证全局内唯一
 */

public class UpLoadManagerFactory {

    private static UploadManager mUpLoadManager = null;

    private UpLoadManagerFactory() {

    };

    public static UploadManager getInstance() {

        if (mUpLoadManager == null) {

            synchronized (UpLoadManagerFactory.class) {

                if (mUpLoadManager == null) {

                    //构造上传时的相关配置
                    Configuration config = new Configuration.Builder()
                            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                            .connectTimeout(10)           // 链接超时。默认10秒
                            .useHttps(true)               // 是否使用https上传域名
                            .responseTimeout(60)          // 服务器响应超时。默认60秒
//                            .recorder(null)           // recorder分片上传时，已上传片记录器。默认null
//                            .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                            .zone(FixedZone.zone1)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                            .build();

                    mUpLoadManager = new UploadManager(config);

                }


            }

        }


        return mUpLoadManager;

    }

}

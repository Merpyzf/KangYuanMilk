package com.merpyzf.kangyuanmilk.utils.qiniu;

import com.merpyzf.kangyuanmilk.utils.HashHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

/**
 * Created by wangke on 2017-07-20.
 * 上传文件到七牛云的工具类
 */

public  class UpLoadHelper {

    private static final String accessKey = "KXLzuFCOxgNar5whqU3-0bmrH6rTHOqaidcohRes";
    private static final String secretKey = "aiw1aAh-dwA9k6nkWAQLExy2Taz9cE1nFYA_01WY";
    private CompletionListener mCompletionListener = null;

    /**
     * 上传头像
     *
     * @param path
     */
    public void upLoadAveter(String path,CompletionListener completionListener) {

        this.mCompletionListener = completionListener;

        Auth auth = Auth.create(accessKey, secretKey);

        //上传凭证  TODO: 2017-07-20 应该从服务器端来进行上传凭证的获取
        String uploadToken = auth.uploadToken("kangyuanmilk");

        UploadManager uploadManager = UpLoadManagerFactory.getInstance();

        //根据path+当前系统时间 进行md5运算，避免发生文件名重复的文件导致无法上传
        uploadManager.put(path,"avatar/"+ HashHelper.getMD5String(path+System.currentTimeMillis()), uploadToken, new UpCompletionHandler() {

            //上传进度的回调
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

                if (mCompletionListener != null) {
                    mCompletionListener.onComplete(key, info, response);
                }

            }
        }, null);

    }

    public interface CompletionListener {

        public void onComplete(String key, ResponseInfo info, JSONObject response);
    }

}

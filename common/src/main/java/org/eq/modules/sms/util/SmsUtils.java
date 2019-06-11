package org.eq.modules.sms.util;

import okhttp3.*;
import org.slf4j.LoggerFactory;

public class SmsUtils {
    private static final String URL = "http://120.77.53.47:7862/sms";
    private static final String ACCOUNT = "900592";
    private static final String PASSWORD = "cWE5D4";
    private static final String EXTNO = "106905";

    /**
     * 发送短信
     * {"status":"0","balance":2871,"list":[{"mid":"6B05C6C386738A80","mobile":"18927401913","result":0}]}
     *
     * @param mobile
     * @param content
     * @return
     */
    public static String send(String mobile, String content) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("action", "send")
                    .add("account", ACCOUNT)
                    .add("password", PASSWORD)
                    .add("mobile", mobile)
                    .add("content", content)
                    .add("extno", EXTNO)
                    .add("rt", "json")
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            LoggerFactory.getLogger(SmsUtils.class).error(e.getMessage(), e);
            return null;
        }
    }

    public static String report() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("action", "report")
                    .add("account", ACCOUNT)
                    .add("password", PASSWORD)
                    .add("rt", "json")
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            LoggerFactory.getLogger(SmsUtils.class).error(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) {
       System.out.println(SmsUtils.send("18927404053","【易券】您的广告已发布成功"));
       //System.out.println(SmsUtils.report());
    }
}

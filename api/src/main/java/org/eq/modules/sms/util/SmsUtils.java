package org.eq.modules.sms.util;

import okhttp3.*;
import org.slf4j.LoggerFactory;

public class SmsUtils {
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
            String url = "http://120.77.53.47:7862/sms?action=send";
            String account = "900592";
            String password = "cWE5D4";
            String extno = "106905";
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("account", account)
                    .add("password", password)
                    .add("mobile", mobile)
                    .add("content", content)
                    .add("extno", extno)
                    .add("rt", "json")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
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
}

package org.eq.modules.utils;

import okhttp3.*;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WebClientUtil {

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Get 请求获取数据
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String synchronousGet(String url, Map<String,Object> params) throws Exception {
        StringBuilder builder = new StringBuilder(url);
        if(params!=null && params.size()>0){
            builder.append("?");
            Iterator<String> ite = params.keySet().iterator();
            while(ite.hasNext()){
                String key = ite.next();
                builder.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        url = builder.toString();
        if(url.equals("&")){
            url = url.substring(0,url.length()-1);
        }
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }
        return response.body().toString();
    }

    /**
     * 同步Post 请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String asynchronousPost(String url,Map<String,Object> params) throws Exception{

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            formBodyBuilder.add(key,String.valueOf(params.get(key)));
        }
        FormBody formBody = formBodyBuilder.build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            throw new IOException("Unexpected code " + response);
        }
        return response.body().toString();
    }





}

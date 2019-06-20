package org.eq.basic.common.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class WebClientUtil {


    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");


    /**
     * Get 请求获取数据
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String synchronousGet(String url, Map<String,Object> params) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
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
        Response response = okHttpClient.newCall(request).execute();
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
    public static String syncPostByForm(String url,Map<String,Object> params) throws Exception{
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if(params!=null && params.size()>0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                formBodyBuilder.add(key,String.valueOf(params.get(key)));
            }
        }
        FormBody formBody = formBodyBuilder.build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }


    /**
     * 同步Post 请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String synchPostForPayload(String url, JSONObject params) throws Exception{
        OkHttpClient okHttpClient = new OkHttpClient();
        String paramStr = "";
        if(params!=null){
            paramStr = params.toJSONString();
        }
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, paramStr);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        return null;
    }

    public static void main(String[] args) {
        try{
            JSONObject obj  = new JSONObject();
            obj.put("appId","FC21g7bs1R6N2FZ3");
            obj.put("appKey","n17kLF3EsghE9Vj+P+PZNqNgHPS1tNLIlNVoQNmW2FKS8zOu2W54lg==");
            String result =  synchPostForPayload("http://1.119.48.42:8858/auth/accessToken",obj);
            System.out.println(result);
            JSONObject obj2  = new JSONObject();
            obj2.put("pageSize",10);
            obj2.put("accessToken","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6IkZDMjFnN2JzMVI2TjJGWjMiLCJpc3MiOiJidW1vIiwiZXhwIjoxNTYxMDA5NDE1fQ.PjwJumGv1zXsfHBq7RMGdSY5J2b5a4jTL60SrpJU4lk");
            obj2.put("start",1);
            String result2 =  synchPostForPayload("http://1.119.48.42:8858/voucher/v1/list",obj2);
            System.out.println(result2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}

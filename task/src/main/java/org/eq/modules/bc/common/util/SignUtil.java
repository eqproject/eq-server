package org.eq.modules.bc.common.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {

    /**
     * 
     * @param params
     * @return
     */
    public static String getSignData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)||"sign_type".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            } else {
                content.append((i == 0 ? "" : "&") + key + "=");
            }

        }

        return content.toString();
    }

   /**
    * 
    * @param params
    * @return
    * @throws UnsupportedEncodingException
    */
    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 
     * @param url
     * @param name
     * @return
     */
    public static String getParameter(String url, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        name = name + "=";
        int start = url.indexOf(name);
        if (start < 0) {
            return null;
        }
        start += name.length();
        int end = url.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }
    /**
     * 
     * @param params
     * @param key
     * @return
     */
    public static String sign(Map<String, String> params, String key) {
    	String content = getSignData(params);
		String tosign = (content == null ? "" : content) +"&key="+key;
		String sign = "";
        try {
			sign =  MD5Utils.MD5(tosign);
			sign = sign.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sign;
	}

    /**
     * 去掉null 和""的排序加密
     * @param params
     * @param key
     * @return
     */
    public static String signRmempty(Map<String, String> params, String key) {
    	String content = getSignDataForEmpty(params);
		String tosign = (content == null ? "" : content) + "&key="+key;
		String sign = "";
        try {
			sign =  MD5Utils.MD5(tosign);
			sign = sign.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sign;
	}
    /**
     * 去掉map中value为null 和""字符串
     * @param params
     * @return
     */
    public static String getSignDataForEmpty(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)||"sign_type".equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null && !"".equals(value)) {
            	if(content.toString() != null && !"".equals(content.toString())){
            		content.append("&" + key + "=" + value);
            	}else{
            		content.append(key + "=" + value);
            	}              
            } 
        }
        return content.toString();
    }
    
}
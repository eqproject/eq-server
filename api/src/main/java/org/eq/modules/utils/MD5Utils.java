package org.eq.modules.utils;


import org.springframework.util.DigestUtils;

import java.util.Date;

public class MD5Utils {
    public static String digestAsHex(String content) throws Exception {
        //加密后的字符串
        String encodeStr = DigestUtils.md5DigestAsHex(content.getBytes());
        return encodeStr;
    }

    public static boolean verify(String content, String md5) throws Exception {
        //根据传入的密钥进行验证
        return digestAsHex(content).equalsIgnoreCase(md5);

    }

    public static void main(String[] args) throws Exception {
        System.out.println(MD5Utils.digestAsHex("com.eq.modules" + new Date()));
        String key = "96448f740822fd73517f96a3c3a2487f";
    }
}

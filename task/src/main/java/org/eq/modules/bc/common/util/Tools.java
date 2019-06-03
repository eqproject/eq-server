package org.eq.modules.bc.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.*;
import java.util.regex.Pattern;

public class Tools {
    public static boolean isNull(Object obj) {
        if (obj == null || StringUtils.isBlank(obj.toString())
                || "null".equals(obj)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getJSONFromRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean isNullByList(Collection<?> coll) {
        if (coll == null || coll.isEmpty() || coll.size() == 0) {
            return true;
        }
        return false;
    }

    public static String str2Base58(String srcStr) throws UnsupportedEncodingException {
        return Base58.encode(srcStr.getBytes("utf-8"));
    }

    public static String base582Str(String base58Str) throws UnsupportedEncodingException {
        return new String(Base58.decode(base58Str));
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public static String buildRequestMySign(Map<String, String> params, String key) {
        String mySign = Tools.createLinkString(Tools.paraFilter(params));
        String tempSignStr = mySign + "&key=" + key;
        String signStr = sign(tempSignStr, "utf-8");
        return signStr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value.toString();
            } else {
                prestr = prestr + key + "=" + value.toString() + "&";
            }
        }

        return prestr;
    }

    public static String[] randomStrArr(String[] src) {
        String t;
        int index;
        if (src.length < 2) {
            return src;
        }
        for (int i = 0; i < src.length; i++) {
            index = new Random().nextInt(src.length);
            System.out.println("index:" + index);
            t = src[i];
            src[i] = src[index];
            src[index] = t;
        }
        return src;
    }

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(Character.forDigit((b & 0xF0) >> 4, 16))
                    .append(Character.forDigit((b & 0x0F), 16));
        }
        return hex.toString();
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    private static String sign(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + "&key=" + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static List<List> averageAssignList(List source, int part) {
        List<List> result = new ArrayList<List>();
        int remainder = source.size() % part;
        int number = source.size() / part;
        int offset = 0;//偏移量
        for (int i = 0; i < part; i++) {
            List value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}

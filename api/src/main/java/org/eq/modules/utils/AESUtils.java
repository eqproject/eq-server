package org.eq.modules.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

@Slf4j
public class AESUtils {
    private static final String CHARTSET = "utf-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法


    /**
     * 加密
     *
     * @param src 加密字符串
     * @param key 密钥
     * @return 加密后的字符串
     */
    public static String encrypt(String src, String key) {
        // 判断密钥是否为空
        if (key == null) {
            log.info("密钥不能为空");
            return null;
        }
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE,key);
            byte[] encrypted = cipher.doFinal(src.getBytes(CHARTSET));

            return binary(encrypted, 16); //十六进制
        } catch (Exception err) {
            log.error("exception", err);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src 解密字符串
     * @param key 密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String src, String key) {
        try {
            // 判断Key是否正确
            if (key == null) {
                log.info("Key为空null");
                return null;
            }

            byte[] encrypted1 = toByteArray(src);//十六进制

            try {
                Cipher cipher = getCipher(Cipher.DECRYPT_MODE,key);
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, CHARTSET);
                return originalString;
            } catch (Exception e) {
                log.error("exception", e);
                return null;
            }
        } catch (Exception ex) {
            log.error("exception", ex);
            return null;
        }
    }

    private static Cipher getCipher(int mode,String key) throws Exception {
        // 密钥补位
        int plus = 16 - key.length();
        byte[] data = key.getBytes(CHARTSET);
        byte[] raw = new byte[16];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 16; i++) {
            if (data.length > i) {
                raw[i] = data[i];
            }else {
                raw[i] = plusbyte[0];
            }
        }

        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);    // 算法/模式/补码方式
        cipher.init(mode, skeySpec);
        return cipher;
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    private static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);   // 这里的1代表正数
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    private static byte[] toByteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

}
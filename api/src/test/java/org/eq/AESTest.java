package org.eq;

import org.eq.modules.utils.AESUtils;
import org.junit.Test;

/**
 * * @author gb
 *
 * @version 2019/6/20
 */
public class AESTest {
    @Test
    public void test(){
        //nvfBEyJEqCzvhRCgsICJqQ==
        String key = "96448f740822fd73517f96a3c3a2487f";
        for (int i = 0; i < 5; i++) {
            String s1 = AESUtils.encrypt("123456",key);
            System.out.println("第"+i+"次，加密："+s1);
            s1 = AESUtils.decrypt(s1,key);
            System.out.println("第"+i+"次，解密："+s1);
        }
    }

    @Test
    public void test1() {
        String encrypt = AESUtils.encrypt("123456", "96448f740822fd73517f96a3c3a2487f");
        System.out.println(encrypt);
        encrypt = AESUtils.encrypt("123456", "96448f740822fd73517f96a3c3a2487f");
        System.out.println(encrypt);
        encrypt = AESUtils.encrypt("123456", "96448f740822fd73517f96a3c3a2487f");
        System.out.println(encrypt);
        encrypt = AESUtils.encrypt("123456", "96448f740822fd73517f96a3c3a2487f");
        System.out.println(encrypt);
        encrypt = AESUtils.encrypt("123456", "96448f740822fd73517f96a3c3a2487f");
        System.out.println(encrypt);
    }
}

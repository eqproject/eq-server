package org.eq.modules.auth.util;

import com.alibaba.fastjson.JSON;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.crypto.keystore.KeyStore;


public class WalletUtil {
    public static String getWalletAddr(){
        Keypair keypair = Keypair.generator();
        String address = keypair.getAddress();
        String publicKey = keypair.getPublicKey();
        String privateKey = keypair.getPrivateKey();
        try {
            String keyStoreStr = JSON.toJSONString(KeyStore.generateKeyStore("1111111", privateKey, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(KeyStore.decipherKeyStore("1111", JSON.parseObject(msg, KeyStoreEty.class)));
        return address;
    }

    public static void main(String[] args) throws Exception {
        String wa = WalletUtil.getWalletAddr();
        System.out.println(wa);
    }
}

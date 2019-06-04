package org.eq.modules.wallet.util;

import com.alibaba.fastjson.JSON;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.crypto.keystore.KeyStore;
import io.bumo.encryption.crypto.keystore.entity.KeyStoreEty;
import org.eq.modules.bc.init.KeyStoreManager;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.wallet.entity.UserWallet;

import java.util.Date;


public class WalletUtil {

    public static UserWallet generate(long userId) {
        Keypair keypair = Keypair.generator();
        String address = keypair.getAddress();
        String publicKey = keypair.getPublicKey();
        String privateKey = keypair.getPrivateKey();
        String keyStore = null;
        try {
            keyStore = JSON.toJSONString(KeyStore.generateKeyStore(KeyStoreManager.getKeyStorePwd(), privateKey, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserWallet wallet = new UserWallet();
        wallet.setUserId(userId);
        wallet.setAddress(address);
        wallet.setPubilcKey(publicKey);
        wallet.setKeyStore(keyStore);
        wallet.setStatus(WalletStateEnum.NO_ACTIVE.getState());
        wallet.setCreateDate(new Date());
        wallet.setUpdateDate(new Date());
        return wallet;
    }

    public static String getPrivateKey(String keyStore) {
        try {
            KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(keyStore, KeyStoreEty.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1; i++) {
            Keypair keypair = Keypair.generator();
            String address = keypair.getAddress();
            String publicKey = keypair.getPublicKey();
            String privateKey = keypair.getPrivateKey();
            sb.append(" insert into initiator_acc (address,public_key,private_key,status,create_time,update_time)"
                    + " values (");
            sb.append("'" + address + "',");
            sb.append("'" + publicKey + "',");
            String keyStoreStr = JSON.toJSONString(KeyStore.generateKeyStore("1111111", privateKey, 2));
            sb.append("'" + keyStoreStr + "',");
            sb.append("0,");
            sb.append("now(),");
            sb.append("now());");
            sb.append("\n");
            System.out.println(keyStoreStr);
        }

        String msg = "{\"address\":\"buQsMB6VRipWngNm4eACQAZ5gUv6NiJ9gQEb\",\"aesctr_iv\":\"2C4962E04CD4AFFED138041549B1E5C9\",\"cypher_text\":\"C61FC753AE9402074B71CCF848F516DBE28C04E90C933C018C573278814738313392C07D7331F69FBC09A00A8A1991D0C00C12D0FE84D19B\",\"scrypt_params\":{\"n\":16384,\"p\":1,\"r\":8,\"salt\":\"B9BDDF2F39ACE7EA9DECF6D8EC14C4FCEAD89BE7A0C1C9305D52BFB318DDEF4C\"},\"version\":2}";
        System.out.println(KeyStore.decipherKeyStore("1111111", JSON.parseObject(msg, KeyStoreEty.class)));
    }
}

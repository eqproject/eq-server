package org.eq.modules.wallet.util;

import com.alibaba.fastjson.JSON;
import io.bumo.crypto.Keypair;
import io.bumo.encryption.crypto.keystore.KeyStore;
import io.bumo.encryption.crypto.keystore.entity.KeyStoreEty;
import io.bumo.encryption.key.PrivateKey;
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
        wallet.setPublicKey(publicKey);
        wallet.setKeyStore(keyStore);
        wallet.setStatus(WalletStateEnum.NO_ACTIVE.getState());
        wallet.setCreateDate(new Date());
        wallet.setUpdateDate(new Date());
        return wallet;
    }

    public static String getPrivateKey(String keyStore) {
        try {
            return KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(keyStore, KeyStoreEty.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        /*
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            Keypair keypair = Keypair.generator();
            String address = keypair.getAddress();
            String publicKey = keypair.getPublicKey();
            String privateKey = keypair.getPrivateKey();
            sb.append(" insert into initiator_acc (address,public_key,key_store,status,create_time,update_time)"
                    + " values (");
            sb.append("'" + address + "',");
            sb.append("'" + publicKey + "',");
            String keyStoreStr = JSON.toJSONString(KeyStore.generateKeyStore("123456", privateKey, 2));
            sb.append("'" + keyStoreStr + "',");
            sb.append("0,");
            sb.append("now(),");
            sb.append("now());");
            sb.append("\n");
            System.out.println(keyStoreStr);
        }
        System.out.println(sb.toString());
*/
        String msg = "{\"address\":\"buQjsEMDiBwoEuCg7zh46m3xkDhavE4fYAkH\",\"aesctr_iv\":\"D03F2BC6D2E06E0B417017700E5A5914\",\"cypher_text\":\"43063094C0B82D73FCD8CBE9D274B19C0F0EBA469B540E71E2A847DF0684D8E7B2552087AB980C8F25737F3F4E45C5A650714D8A0A275D24\",\"scrypt_params\":{\"n\":16384,\"p\":1,\"r\":8,\"salt\":\"F7418C2F31A5431A7374B494FCE1CC551B5F8F86E9BFE4EE039315E2D78534DC\"},\"version\":2}";
        String privateKey = WalletUtil.getPrivateKey(msg);
        PrivateKey privateObj = new PrivateKey(privateKey);
    }
}

package cn.bubi.basic.license.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import cn.bubi.basic.license.entity.LicenseEnum;
import cn.bubi.basic.license.entity.LicenseInfo;

/**
 * @Author: JoinHan
 * @Date: Created in 12:16 2018/2/12
 * @Modified By：
 */
public class StandaloneLicenseUtil {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(StandaloneLicenseUtil.class);

    private static String publicKey;

    private static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtil.genKeyPair();
            publicKey = RSAUtil.getPublicKey(keyMap);
            privateKey = RSAUtil.getPrivateKey(keyMap);
        } catch(Exception e) {
            logger.debug(e.getMessage());
        }
    }

    public static String generatorLicense(LicenseInfo properties) {

        try {
            Gson gson = new Gson();
            byte[] data = gson.toJson(properties).toString().getBytes();
            // 私钥加密 公钥解密
            byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
            // 签名
            String sign = RSAUtil.sign(encodedData, privateKey);
            // 输出认证内容
            logger.info("公钥：{}", publicKey);
            logger.info("签名：{}" , sign);
            logger.info("内容：{}" , Base64Utils.encodeToString(encodedData));
            return publicKey + "$$" + sign + "$$" + Base64Utils.encodeToString(encodedData);
        } catch(Exception e) {
            logger.debug(e.getMessage());
        }
        return null;
    }

    public static boolean licenseCheck(String equipmentInfo, String license) throws Exception {

        String[] params = license.split("\\$\\$");
        if (params.length < 3) {
            logger.error("License 有错误");
        } else {
            String publicKey = params[0];
            String sign = params[1];
            String encodeData = params[2];
            try {
                byte[] data = Base64Utils.decodeFromString(encodeData);
                // 验证签名
                if (RSAUtil.verify(data, publicKey, sign)) {
                    // 解密
                    byte[] decodedData = RSAUtil.decryptByPublicKey(data, publicKey);
                    String target = new String(decodedData); // 明文
                    Gson gson = new Gson();
                    LicenseInfo properties = new LicenseInfo();
                    properties = gson.fromJson(target, properties.getClass());
                    LicenseEnum type = properties.getType();
                    if (type == LicenseEnum.STANDALONE) {
                        String reqEquipment = properties.getEquipmentInfo();
                        if (reqEquipment != null && reqEquipment.equals(equipmentInfo)) {
                            // 验证时间的有效性
                            Date startDate = properties.getStartTime();
                            Date endDate = properties.getEndTime();
                            // 取出时间 TODO 这里没有防止通过修改系统时间使得系统有效
                            if (startDate.getTime() <= System.currentTimeMillis()
                                    && endDate.getTime() > System.currentTimeMillis()) {
                                logger.info("License 验证成功");
                                return true;
                            } else {
                                logger.error("License 已失效");
                            }
                        } else {
                            logger.error("License 设备要求不一致");
                        }
                    } else {
                        logger.error("License 类型不识别");
                    }
                } else {
                    logger.error("签名验证失败");
                }
            } catch(Exception e) {
                logger.debug(e.getMessage());
                throw e;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // 获取信息 生成license
        String license = null;
        new HashMap<>();
        // 获取设备信息
        String equipmentInfo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            equipmentInfo = EquipmentInfo.getEquipmentCode();
            LicenseInfo licenseInfo = new LicenseInfo();
            licenseInfo.setStartTime(sdf.parse("2015-01-01 00:00:00"));
            licenseInfo.setEndTime(sdf.parse("2019-01-01 00:00:00"));
            licenseInfo.setType(LicenseEnum.STANDALONE);
            licenseInfo.setEquipmentInfo(equipmentInfo);
            license = generatorLicense(licenseInfo);
            logger.info("License:{}", license);
        } catch(Exception e) {
            logger.debug(e.getMessage());
        }
        // 根据设备信息 验证license有效性
        if (license != null) {
            try {
                licenseCheck(equipmentInfo, license);
            } catch(Exception e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
            }
        }

    }
}

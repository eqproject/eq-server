package org.eq.basic.license.verify;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.eq.basic.common.util.FileLowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.eq.basic.license.util.EquipmentInfo;
import org.eq.basic.license.util.StandaloneLicenseUtil;

/**
 * liscence 验证类
 *
 * @Author: JoinHan
 * @Date: Created in 15:57 2018/2/12
 * @Modified By：
 */
@Component
public class LicenseVerify {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(LicenseVerify.class);

    @PostConstruct
    public boolean verify() {

        logger.info(">>>>> license <<<<<");
        // 获取设备信息
        boolean result = false;
        String equipmentInfo = "";
        try (FileInputStream fileInputStream = new FileInputStream(
                FileLowUtils.getClassPathFile("classpath:license.lic"))) {
            equipmentInfo = EquipmentInfo.getEquipmentCode();
            StringBuilder sb = new StringBuilder();
            byte[] buff = new byte[1024];
            int i = fileInputStream.read(buff);
            while (i != -1) {
                sb.append(new String(buff, 0, i));
                i = fileInputStream.read(buff);
            }
            String license = sb.toString();
            // license 验证失败 退出程序
            if (StandaloneLicenseUtil.licenseCheck(equipmentInfo, license)) {
                result = true;
            }
        } catch(Exception e) {
            // jar包处理
            try (InputStream inputStream = FileLowUtils.getClassPathInputStream("classpath:license.lic")) {
                equipmentInfo = EquipmentInfo.getEquipmentCode();
                StringBuilder sb = new StringBuilder();
                byte[] buff = new byte[1024];
                int i = inputStream.read(buff);
                while (i != -1) {
                    sb.append(new String(buff, 0, i));
                    i = inputStream.read(buff);
                }
                String license = sb.toString();
                // license 验证失败 退出程序
                if (StandaloneLicenseUtil.licenseCheck(equipmentInfo, license)) {
                    result = true;
                }
            } catch(Exception ex) {
                // es.printStackTrace();
            }
        }

        if (!result) {
            //logger.error("license 验证失败，程序退出");
            //System.exit(1);
        }
        return result;
    }

}

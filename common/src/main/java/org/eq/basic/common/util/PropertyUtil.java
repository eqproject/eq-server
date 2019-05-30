package org.eq.basic.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: JoinHan
 * @Date: Created in 22:44 2018/2/1
 * @Modified By：
 */
public class PropertyUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    public static Properties loadProps(String fileName) {

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(ResourceUtils.getFile(fileName));) {
            props.load(in);
            return props;
        } catch(Exception e) {
            try (InputStream in = FileLowUtils.getClassPathInputStream(fileName)) {
                props.load(in);
                return props;
            } catch(Exception ex) {
                logger.debug(ex.getMessage());
            }
        }
        return null;
    }
}

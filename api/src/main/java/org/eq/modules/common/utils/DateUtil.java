package org.eq.modules.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author  kaka
 * @date  2019-05-27
 */
public class DateUtil {

    private static final SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取当前时间 字符串
     *   格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public  static String getNowTimeStr(){
        return chinaFormat.format(new Date());
    }
}

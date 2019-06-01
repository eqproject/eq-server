package org.eq.basic.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author  kaka
 * @date  2019-05-27
 */
public class DateUtil {

    private static final SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 获取当前时间 字符串
     *   格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public  static String getNowTimeStr(){
        return chinaFormat.format(new Date());
    }


    /**
     * 获取当前时间
     * @return
     */
    public static Date getNowTime(){
        return new Date();
    }

    /**
     * 格式化时间
     * @param timeStr 时间字符串
     * @return
     */
    public static Date passDateOrNow(String timeStr){
        if(StringUtils.isEmpty(timeStr)){
            return getNowTime();
        }
        try{
            return simpleFormat.parse(timeStr);
        }catch (Exception e){}

        return getNowTime();
    }


}

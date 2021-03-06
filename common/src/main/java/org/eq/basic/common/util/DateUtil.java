package org.eq.basic.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author kaka
 * @date 2019-05-27
 */
public class DateUtil {

    private final static Logger logBase = LoggerFactory.getLogger(DateUtil.class);

    public static final String DATE_FORMAT_FULL = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_FULL_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    public static final String DATE_FORMAT_SHORT_01 = "yyyy-MM-dd";

    private static final SimpleDateFormat chinaFormat = new SimpleDateFormat(DATE_FORMAT_FULL_01);
    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_FORMAT_SHORT_01);


    /**
     * 获取当前时间 字符串
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTimeStr() {
        return chinaFormat.format(new Date());
    }

    /**
     * 获取当前时间
     * 格式为yyyy-MM-dd
     * @return
     */
    public static String getNowTimeShortStr() {
        return simpleFormat.format(new Date());
    }



    /**
     * 获取当前时间 字符串
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static Date paseTimeStr(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            return chinaFormat.parse(timeStr);
        } catch (Exception e) {
        }
        return  null;
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowTime() {
        return new Date();
    }

    /**
     * 格式化时间
     *
     * @param timeStr 时间字符串
     * @return
     */
    public static Date passDateOrNow(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return getNowTime();
        }
        try {
            return simpleFormat.parse(timeStr);
        } catch (Exception e) {
        }

        return getNowTime();
    }

    /**
     * 格式化时间
     *
     * @param timeStr 时间字符串
     * @return
     */
    public static Date passDate(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            return simpleFormat.parse(timeStr);
        } catch (Exception e) {
        }

        return getNowTime();
    }

    /**
     * 格式化时间
     *
     * @param timeStr 时间字符串
     * @return
     */
    public static Date passDateTime(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            return chinaFormat.parse(timeStr);
        } catch (Exception e) {
        }

        return getNowTime();
    }

    /**
     * 日期按照指定格式转换为日期字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToStr(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            logBase.error("日期转换为字符串异常", e);
        }
        return null;
    }

    /**
     * 返回时间戳
     *
     * @return
     */
    public static String getLockNowTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 返回多少小时之前的时间
     * @param date
     * @param hour
     * @return
     */
    public static Date beforeDateHour(Date date,int hour) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,-hour);
            return calendar.getTime();
        } catch (Exception e) {
            logBase.error("beforeDateHour异常", e);
        }
        return null;
    }


    /**
     * 返回多少小时之前的时间
     * @param date
     * @param hour
     * @return
     */
    public static Date afterDateHour(Date date,int hour) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,hour);
            return calendar.getTime();
        } catch (Exception e) {
            logBase.error("beforeDateHour异常", e);
        }
        return null;
    }

    /**
     * 返回30天之前的时间
     * @param date
     * @param day
     * @return
     */
    public static Date beforeDateDay(Date date,int day) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,-day);
            return calendar.getTime();
        } catch (Exception e) {
            logBase.error("beforeDateDay", e);
        }
        return null;
    }


    /**
     * 格式化固定时间
     * @param date
     * @return
     */
    public static String foramtChinaFormat(Date date){
        String result = "";
        if(date==null){
            return result;
        }
        try{
            return chinaFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取日期，明天的00:00
     * @return
     */
    public static Date getNextDayTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, +1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


}

package cn.bubi.basic.license.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类用于获取 系统，设备基本信息
 *
 * @Author: JoinHan
 * @Date: Created in 16:35 2018/2/11
 * @Modified By：
 */
public class EquipmentInfo {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(EquipmentInfo.class);


    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {

        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * @return 本机cpu 序列号
     */
    public static String getCPUSerial() {

        String os = getOSName();
        String serial = null;
        if (os.startsWith("windows")) {
            serial = WindowsEquipment.getCPUSerial();
        } else if (os.startsWith("linux")) {
            serial = LinuxEquipment.getCPUSerial();
        }
        return serial;
    }

    /**
     * 获取主板编号 不一定能读出来 泪奔
     *
     * @return
     */
    public static String getMainBordId() {

        String os = getOSName();
        String mainBordId = null;
        if (os.startsWith("windows")) {
            mainBordId = WindowsEquipment.getMainBordId();
        } else if (os.startsWith("linux")) {
            mainBordId = LinuxEquipment.getMainBordId();
        }
        return mainBordId;
    }

    public static void main(String[] args) {

        // 获取ip ips
        System.out.println("获取本机可用的一个ip：" + NetUtils.getLocalIP());
        System.out.println("获取本机所有ip地址：" + NetUtils.getLocalIPAll());

        // 获取ip ips
        System.out.println("获取本机可用的一个mac：" + NetUtils.getLocalMAC());
        System.out.println("获取本机所有mac地址：" + NetUtils.getLocalMACAll());
        // cpu
        System.out.println("获取本机cpu序列号：" + WindowsEquipment.getCPUSerial());
        // mainBord
        System.out.println("获取本机主板序列号：" + getMainBordId());
    }

    /**
     * 获取机器码 md5 加密
     *
     * @return
     */
    public static String getEquipmentCode() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 获取设备信息 目前只针对cup 序列号
        String equipmentInfo = getCPUSerial();
        if (equipmentInfo != null) {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5dig = md5.digest(equipmentInfo.getBytes("utf-8"));
            // 加密后的字符串
            String newstr = Base64.getEncoder().encodeToString(md5dig);
            logger.info("加密后字符串{}", newstr);
            return newstr;
        }
        return null;
    }
}

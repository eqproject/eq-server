package cn.bubi.basic.license.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author: JoinHan
 * @Date: Created in 11:34 2018/2/12
 * @Modified By：
 */
public class WindowsEquipment {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(EquipmentInfo.class);

    private static final String EXECSTR = "cscript //NoLogo ";

    /**
     * 获取主板序列号
     *
     * @return
     */
    public static String getMainBordId() {

        StringBuilder result = new StringBuilder();
        FileWriter fw = null;
        BufferedReader input = null;
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            fw = new FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(EXECSTR + file.getPath());
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
            fw.close();
        } catch (Exception e) {
            logger.error("获取主板信息错误", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (fw != null) {
                    fw.close();
                }

            } catch (IOException e) {
                logger.debug(e.getMessage());
            }

        }
        return result.toString();
    }

    /**
     * 获取硬盘序列号
     *
     * @param drive 盘符
     * @return
     */
    public static String getHardDiskSN(String drive) {

        StringBuilder result = new StringBuilder();
        FileWriter fw = null;
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            fw = new FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                + "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(EXECSTR + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
        } catch (Exception e) {
            logger.error("获取硬盘序列号错误", e);
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 获取CPU序列号
     *
     * @return
     */
    public static String getCPUSerial() {

        StringBuilder result = new StringBuilder();
        FileWriter fw = null;
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_Processor\") \n"
                + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.ProcessorId \n"
                + "    exit for  ' do the first cpu only! \n" + "Next \n";

            // + " exit for \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(EXECSTR + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
            if (!file.delete()) {
                logger.error("删除文件失败");
            }
        } catch (Exception e) {
            logger.error("获取cpu序列号错误", e);
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        if (result.length() == 0) {
            result.append("无CPU_ID被读取");
        }
        return result.toString();
    }

    public static void main(String[] args) {

        logger.info("CPU  SN:{}", WindowsEquipment.getCPUSerial());
        logger.info("主板  SN:{}", WindowsEquipment.getMainBordId());
        logger.info("C盘   SN:{}", WindowsEquipment.getHardDiskSN("c"));
    }
}

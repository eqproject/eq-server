/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bubi.basic.common.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 *
 * @author ThinkGem
 * @version 2015-3-16
 */
public class FileLowUtils extends FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileLowUtils.class);

    private static String exitsStr = "文件{}已存在";
    
    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     *
     * @param srcFileName
     *            待复制的文件名
     * @param descFileName
     *            目标文件名
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {

        return FileLowUtils.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName
     *            待复制的文件名
     * @param descFileName
     *            目标文件名
     * @param coverlay
     *            如果目标文件已存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {

        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            logger.debug("复制文件失败，源文件 {} 不存在!", srcFileName);
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            logger.debug("复制文件失败,{}不是一个文件!", srcFileName);
            return false;
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        int exists = processCopyCheckFileExists(descFile, descFileName, coverlay);
        if (exists < 0) {
            return false;
        }

        // 准备复制文件
        // 读取的位数
        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            logger.debug("复制单个文件 {} 到 {} 成功", srcFileName, descFileName);
            return true;
        } catch (Exception e) {
            logger.debug("复制文件失败：{}", e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            processClose(outs, ins);
        }
    }
    
    

    private static int processCopyCheckFileExists(File descFile, String descFileName, boolean coverlay) {

        int res = 0;
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                logger.debug("目标文件已存在，准备删除!");
                if (!FileLowUtils.delFile(descFileName)) {
                    logger.debug("删除目标文件 {} 失败!", descFileName);
                    res = -1;
                }
            } else {
                logger.debug(exitsStr, descFileName);
                res = -1;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                logger.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    logger.debug("创建目标文件所在的目录失败!");
                    res = -1;
                }
            }
        }
        return res;
    }
    
 // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
    private static void processClose(OutputStream outs, InputStream ins) {

        if (outs != null) {
            try {
                outs.close();
            } catch (IOException oute) {
                logger.debug(oute.getMessage());
            }
        }
        if (ins != null) {
            try {
                ins.close();
            } catch (IOException ine) {
                logger.debug(ine.getMessage());
            }
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     *
     * @param srcDirName
     *            源目录名
     * @param descDirName
     *            目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {

        return FileLowUtils.copyDirectoryCover(srcDirName, descDirName, false);
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName
     *            源目录名
     * @param descDirName
     *            目标目录名
     * @param coverlay
     *            如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {

        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            logger.debug("复制目录失败，源目录{}不存在!", srcDirName);
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            logger.debug("复制目录失败，{}不是一个目录!", srcDirName);
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        boolean exists = processCopyDirectoryCoverCheckExists(descDir, descDirNames, coverlay);
        if (!exists) {
            return false;
        }

        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        flag = processCopyDirectoryCover(files, descDirName);
        if (!flag) {
            logger.debug("复制单个文件 {} 到 {} 失败", srcDirName, descDirName);
            return false;
        }
        logger.debug("复制单个文件 {} 到 {} 成功", srcDirName, descDirName);
        return true;
    }

    
    private static boolean processCopyDirectoryCoverCheckExists(File descDir, String descDirNames, boolean coverlay) {

        boolean res = true;
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                logger.debug("目标目录已存在，准备删除!");
                if (!FileLowUtils.delFile(descDirNames)) {
                    logger.debug("删除目录 {}失败!", descDirNames);
                    res = false;
                }
            } else {
                logger.debug(exitsStr, descDirNames);
                res = false;
            }
        } else {
            // 创建目标目录
            logger.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                logger.debug("创建目标目录失败!");
                res = false;
            }
        }
        return res;
    }

    private static boolean processCopyDirectoryCover(File[] files, String descDirName) {

        boolean flag = true;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 如果是一个单个文件，则直接复制
                if (files[i].isFile()) {
                    flag = FileLowUtils.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName());
                    // 如果拷贝文件失败，则退出循环
                    if (!flag) {
                        break;
                    }
                }
                // 如果是子目录，则继续复制目录
                if (files[i].isDirectory()) {
                    flag = FileLowUtils.copyDirectory(files[i].getAbsolutePath(), descDirName + files[i].getName());
                    // 如果拷贝目录失败，则退出循环
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        return flag;
    }
    

    /**
     *
     * 删除文件，可以删除单个文件或文件夹
     *
     * @param fileName
     *            被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {

        File file = new File(fileName);
        if (!file.exists()) {
            logger.debug(" {}文件不存在!", fileName);
            return true;
        } else {
            if (file.isFile()) {
                return FileLowUtils.deleteFile(fileName);
            } else {
                return FileLowUtils.deleteDirectory(fileName);
            }
        }
    }

    /**
     *
     * 删除单个文件
     *
     * @param fileName
     *            被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.debug("删除文件 {} 成功!", fileName);
                return true;
            } else {
                logger.debug("删除文件{} 失败", fileName);
                return false;
            }
        } else {
            logger.debug(" {}文件不存在!", fileName);
            return true;
        }
    }

    /**
     *
     * 删除目录及目录下的文件
     *
     * @param dirName
     *            被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {

        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.debug(" {}目录不存在!", dirNames);
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    flag = FileLowUtils.deleteFile(files[i].getAbsolutePath());
                    // 如果删除文件失败，则退出循环
                    if (!flag) {
                        break;
                    }
                }
                // 删除子目录
                else if (files[i].isDirectory()) {
                    flag = FileLowUtils.deleteDirectory(files[i].getAbsolutePath());
                    // 如果删除子目录失败，则退出循环
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        if (!flag) {
            logger.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.debug("删除目录 {}成功!", dirName);
            return true;
        } else {
            logger.debug("删除目录 {} 失败!", dirName);
            return false;
        }

    }

    /**
     * 创建单个文件
     *
     * @param descFileName
     *            文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {

        File file = new File(descFileName);
        if (file.exists()) {
            logger.debug("文件{}已存在", descFileName);
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            logger.debug(" 为目录，不能创建目录!{}", descFileName);
            return false;
        }
        if (!file.getParentFile().exists() && !mkdirs(file)) {
            // 如果文件所在的目录不存在，则创建目录
            logger.debug("创建文件所在的目录失败!");
            return false;
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                logger.debug(" 文件创建成功!{}", descFileName);
                return true;
            } else {
                logger.debug(" 文件创建失败!{}", descFileName);
                return false;
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            logger.debug(" 文件创建失败!{}", descFileName);
            return false;
        }

    }
    /**
     * 创建父级文件
     *
     * @param file
     * @return
     */
    private static boolean mkdirs(File file) {

        return file.getParentFile().mkdirs();
    }

    /**
     * 创建目录
     *
     * @param descDirName
     *            目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {

        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug(exitsStr, descDirNames);
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug(" 目录创建成功!{}", descDirNames);
            return true;
        } else {
            logger.debug("目录创建失败{} ", descDirNames);
            return false;
        }

    }

    /**
     * 写入文件
     *
     * @param fileName
     *            要写入的文件
     */
    public static void writeToFile(String fileName, String content, boolean append) {

        try {
            FileLowUtils.write(new File(fileName), content, "utf-8", append);
            logger.debug("文件 {} 写入成功!", fileName);
        } catch (IOException e) {
            logger.debug("文件 {} 写入失败！{}!", fileName, e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName
     *            要写入的文件
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {

        try {
            FileLowUtils.write(new File(fileName), content, "utf-8", append);
            logger.debug("文件 {} 写入成功!", fileName);
        } catch (IOException e) {
            logger.debug("文件 {} 写入失败！{}!", fileName, e.getMessage());
        }
    }

    /**
     * 根据“文件名的后缀”获取文件内容类型（而非根据File.getContentType()读取的文件类型）
     *
     * @param returnFileName
     *            带验证的文件名
     * @return 返回文件类型
     */
    public static String getContentType(String returnFileName) {

        String contentType = "application/octet-stream";
        if (returnFileName.lastIndexOf(".") < 0) {
            return contentType;
        }
        returnFileName = returnFileName.toLowerCase();
        returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".") + 1);
        if (returnFileName.equals("html") || returnFileName.equals("htm") || returnFileName.equals("shtml")) {
            contentType = "text/html";
        } else if (returnFileName.equals("apk")) {
            contentType = "application/vnd.android.package-archive";
        } else if (returnFileName.equals("sis")) {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("sisx")) {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("msi")) {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("css")) {
            contentType = "text/css";
        } else if (returnFileName.equals("xml")) {
            contentType = "text/xml";
        } else if (returnFileName.equals("gif")) {
            contentType = "image/gif";
        } else if (returnFileName.equals("jpeg") || returnFileName.equals("jpg")) {
            contentType = "image/jpeg";
        } else if (returnFileName.equals("js")) {
            contentType = "application/x-javascript";
        } else if (returnFileName.equals("atom")) {
            contentType = "application/atom+xml";
        } else if (returnFileName.equals("rss")) {
            contentType = "application/rss+xml";
        } else if (returnFileName.equals("mml")) {
            contentType = "text/mathml";
        } else if (returnFileName.equals("txt")) {
            contentType = "text/plain";
        } else if (returnFileName.equals("jad")) {
            contentType = "text/vnd.sun.j2me.app-descriptor";
        } else if (returnFileName.equals("wml")) {
            contentType = "text/vnd.wap.wml";
        } else if (returnFileName.equals("htc")) {
            contentType = "text/x-component";
        } else if (returnFileName.equals("png")) {
            contentType = "image/png";
        } else if (returnFileName.equals("tif") || returnFileName.equals("tiff")) {
            contentType = "image/tiff";
        } else if (returnFileName.equals("wbmp")) {
            contentType = "image/vnd.wap.wbmp";
        } else if (returnFileName.equals("ico")) {
            contentType = "image/x-icon";
        } else if (returnFileName.equals("jng")) {
            contentType = "image/x-jng";
        } else if (returnFileName.equals("bmp")) {
            contentType = "image/x-ms-bmp";
        } else if (returnFileName.equals("svg")) {
            contentType = "image/svg+xml";
        } else if (returnFileName.equals("jar") || returnFileName.equals("var") || returnFileName.equals("ear")) {
            contentType = "application/java-archive";
        } else if (returnFileName.equals("doc")) {
            contentType = "application/msword";
        } else if (returnFileName.equals("pdf")) {
            contentType = "application/pdf";
        } else if (returnFileName.equals("rtf")) {
            contentType = "application/rtf";
        } else if (returnFileName.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (returnFileName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (returnFileName.equals("7z")) {
            contentType = "application/x-7z-compressed";
        } else if (returnFileName.equals("rar")) {
            contentType = "application/x-rar-compressed";
        } else if (returnFileName.equals("swf")) {
            contentType = "application/x-shockwave-flash";
        } else if (returnFileName.equals("rpm")) {
            contentType = "application/x-redhat-package-manager";
        } else if (returnFileName.equals("der") || returnFileName.equals("pem") || returnFileName.equals("crt")) {
            contentType = "application/x-x509-ca-cert";
        } else if (returnFileName.equals("xhtml")) {
            contentType = "application/xhtml+xml";
        } else if (returnFileName.equals("zip")) {
            contentType = "application/zip";
        } else if (returnFileName.equals("mid") || returnFileName.equals("midi") || returnFileName.equals("kar")) {
            contentType = "audio/midi";
        } else if (returnFileName.equals("mp3")) {
            contentType = "audio/mpeg";
        } else if (returnFileName.equals("ogg")) {
            contentType = "audio/ogg";
        } else if (returnFileName.equals("m4a")) {
            contentType = "audio/x-m4a";
        } else if (returnFileName.equals("ra")) {
            contentType = "audio/x-realaudio";
        } else if (returnFileName.equals("3gpp") || returnFileName.equals("3gp")) {
            contentType = "video/3gpp";
        } else if (returnFileName.equals("mp4")) {
            contentType = "video/mp4";
        } else if (returnFileName.equals("mpeg") || returnFileName.equals("mpg")) {
            contentType = "video/mpeg";
        } else if (returnFileName.equals("mov")) {
            contentType = "video/quicktime";
        } else if (returnFileName.equals("flv")) {
            contentType = "video/x-flv";
        } else if (returnFileName.equals("m4v")) {
            contentType = "video/x-m4v";
        } else if (returnFileName.equals("mng")) {
            contentType = "video/x-mng";
        } else if (returnFileName.equals("asx") || returnFileName.equals("asf")) {
            contentType = "video/x-ms-asf";
        } else if (returnFileName.equals("wmv")) {
            contentType = "video/x-ms-wmv";
        } else if (returnFileName.equals("avi")) {
            contentType = "video/x-msvideo";
        }
        return contentType;
    }

    /**
     * 修正路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param path
     *            待修正的路径
     * @return 修正后的路径
     */
    public static String path(String path) {

        String p = StringLowUtils.replace(path, "\\", "/");
        p = StringLowUtils.join(StringLowUtils.split(p, "/"), "/");
        if (!StringLowUtils.startsWithAny(p, "/") && StringLowUtils.startsWithAny(path, "\\", "/")) {
            p += "/";
        }
        if (!StringLowUtils.endsWithAny(p, "/") && StringLowUtils.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        if (path != null && path.startsWith("/")) {
            p = "/" + p; // linux下路径
        }
        return p;
    }

    /**
     * 获目录下的文件列表
     *
     * @param dir
     *            搜索目录
     * @param searchDirs
     *            是否是搜索目录
     * @return 文件列表
     */
    public static List<String> findChildrenList(File dir, boolean searchDirs) {

        List<String> files = new ArrayList();
        if (dir != null) {
            String[] dirList = dir.list();
            for (int i = 0; i < dirList.length; i++) {
                String subFiles = dirList[i];
                File file = new File(dir + "/" + subFiles);
                if (( ( searchDirs) && ( file.isDirectory())) || ( ( !searchDirs) && ( !file.isDirectory()))) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }

    /**
     * 获取文件扩展名(返回小写)
     *
     * @param fileName
     *            文件名
     * @return 例如：test.jpg 返回： jpg
     */
    public static String getFileExtension(String fileName) {

        if (( fileName == null) || ( fileName.lastIndexOf(".") == -1)
                || ( fileName.lastIndexOf(".") == fileName.length() - 1)) {
            return null;
        }
        return StringLowUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    /**
     * 获取文件名，不包含扩展名
     *
     * @param fileName
     *            文件名
     * @return 例如：d:\files\test.jpg 返回：d:\files\test
     */
    public static String getFileNameWithoutExtension(String fileName) {

        if (( fileName == null) || ( fileName.lastIndexOf(".") == -1)) {
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    // ---------------------------- 获取文件路径 读取程序内文件
    public static File getClassPathFile(String fileName) throws Exception {

        try {
            return ResourceUtils.getFile(fileName);
        } catch(Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    public static InputStream getClassPathInputStream(String fileName) throws Exception {

        Resource resource = new ClassPathResource(fileName);
        try {
            return resource.getInputStream();
        } catch(IOException e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * 获取工程路径
     *
     * @return
     */
    public static String getProjectPath() {

        String projectPath = "";
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch(IOException e) {
            logger.debug(e.getMessage());
        }
        return projectPath;
    }
}

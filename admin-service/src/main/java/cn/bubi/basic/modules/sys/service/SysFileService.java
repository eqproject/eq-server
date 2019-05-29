/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import cn.bubi.basic.common.base.BaseFile;
import cn.bubi.basic.common.base.ServiceExtend;
import org.springframework.web.multipart.MultipartFile;

import cn.bubi.basic.modules.sys.entity.SysFile;
import cn.bubi.basic.modules.sys.entity.SysFileExample;
import cn.bubi.basic.modules.sys.entity.SysUser;

/**
 * 附件表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysFileService extends ServiceExtend<SysFile, SysFileExample> {

    /**
     * 上传文件 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param file
     *            null 表示只写库 不上传
     * @param path
     *            null 表示使用默认路径
     * @return
     */
    BaseFile saveFile(SysFile sysFile, MultipartFile file, Path path);

    /**
     * 多文件上传文件 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param files
     *            null 表示只写库 不上传
     * @param path
     *            null 表示使用默认路径
     * @return
     */
    List<BaseFile> saveFile(SysFile sysFile, List<MultipartFile> files, Path path);

    /**
     * 上传文件 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param file
     *            null 表示只写库 不上传
     * @param path
     *            null 表示使用默认路径
     * @param pre
     *            null 表示使用默认文件夹
     * @return
     */
    BaseFile saveFile(SysFile sysFile, MultipartFile file, String path, String pre);

    /**
     * 多文件上传文件 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param files
     *            null 表示只写库 不上传
     * @param path
     *            null 表示使用默认路径
     * @param pre
     *            null 表示使用默认文件夹
     * @return
     */
    List<BaseFile> saveFile(SysFile sysFile, List<MultipartFile> files, String path, String pre);

    /**
     * 上传文件到默认路径 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param file
     *            null 表示只写库 不上传
     * @param pre
     *            null 表示使用默认文件夹
     * @return
     */
    BaseFile saveFile(SysFile sysFile, String pre, MultipartFile file);

    /**
     * 多文件上传文件到默认路径 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param files
     *            null 表示只写库 不上传
     * @param pre
     *            null 表示使用默认文件夹
     * @return
     */
    List<BaseFile> saveFile(SysFile sysFile, String pre, List<MultipartFile> files);

    /**
     * 根据请求路径 下载文件（文件为jar包外文件）
     *
     * @param path
     * @return
     */
    BaseFile downloadFile(String path);

    /**
     * 上传文件到默认路径 并写入数据库
     *
     * @param sysFile
     *            null 表示不入库
     * @param file
     *            null 表示只写库 不上传
     * @param pre
     *            null 表示使用默认文件夹
     * @return
     */
    BaseFile updateFile(SysFile sysFile, String pre, MultipartFile file);

    /**
     * 根据请求路径 下载文件（文件为jar包内文件）
     *
     * @param path
     * @return
     */
    InputStream downloadJarFile(String path);

    /**
     * 用户保存文件
     *
     * @param sysUser
     *            用户信息
     * @param pre
     *            null 表示使用默认文件夹
     * @param file
     * @return
     */
    BaseFile saveFile(SysUser sysUser, String pre, MultipartFile file);
}
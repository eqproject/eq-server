/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseFile;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.FileLowUtils;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.config.properties.SourceProperties;
import org.eq.basic.license.util.NetUtils;
import org.eq.basic.modules.sys.dao.SysFileMapper;
import org.eq.basic.modules.sys.entity.SysFile;
import org.eq.basic.modules.sys.entity.SysFileExample;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysFileService;

/**
 * 附件表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysFileServiceImpl extends ServiceImplExtend<SysFileMapper, SysFile, SysFileExample>
        implements SysFileService {

    @Autowired
    private SourceProperties sourceProperties;

    @Override
    public SysFileExample getExampleFromEntity(SysFile sysFile, Map<String, Object> params) {

        SysFileExample example = new SysFileExample();
        SysFileExample.Criteria ca = example.or();
        if (sysFile == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        String compare = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
            compare = (String) params.get("compare");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (sysFile.getId() != null) {
            ca.andIdEqualTo(sysFile.getId());
        }
        if (StringLowUtils.isNotBlank(sysFile.getFileName())) {
            ca.andFileNameLike("%" + sysFile.getFileName() + "%");
        }

        if (sysFile.getFileSize() != null) {
            if (StringLowUtils.isNotBlank(compare)) {
                switch (compare) {
                    case ">" :
                        ca.andFileSizeGreaterThan(sysFile.getFileSize());
                        break;
                    case ">=" :
                        ca.andFileSizeGreaterThanOrEqualTo(sysFile.getFileSize());
                        break;
                    case "=" :
                        ca.andFileSizeEqualTo(sysFile.getFileSize());
                        break;
                    case "<" :
                        ca.andFileSizeLessThan(sysFile.getFileSize());
                        break;
                    case "<=" :
                        ca.andFileSizeLessThanOrEqualTo(sysFile.getFileSize());
                        break;
                    default :
                        ca.andFileSizeEqualTo(sysFile.getFileSize());
                }
            }
        }
        if (StringLowUtils.isNotBlank(sysFile.getFileType())) {
            ca.andFileTypeLike("%" + sysFile.getFileType() + "%");
        }
        if (StringLowUtils.isNotBlank(sysFile.getLocation())) {
            ca.andLocationEqualTo(sysFile.getLocation());
        }
        if (StringLowUtils.isNotBlank(sysFile.getUrl())) {
            ca.andUrlEqualTo(sysFile.getUrl());
        }
        if (StringLowUtils.isNotBlank(sysFile.getStore())) {
            ca.andStoreEqualTo(sysFile.getStore());
        }
        if (StringLowUtils.isNotBlank(sysFile.getIp())) {
            ca.andIpEqualTo(sysFile.getIp());
        }
        if (sysFile.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysFile.getCreateBy());
        }
        if (sysFile.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysFile.getCreateDate());
        }
        if (sysFile.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysFile.getUpdateBy());
        }
        if (sysFile.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysFile.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysFile.getRemarks())) {
            ca.andRemarksEqualTo(sysFile.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysFile.getDelFlag())) {
            ca.andDelFlagEqualTo(sysFile.getDelFlag());
        }
        return example;
    }

    public BaseFile modifyFile(SysFile sysFile, MultipartFile file, Path path, boolean update) {

        // 获取保存文件位置
        String uploadPath = this.sourceProperties.getUploadPath();
        String uploadPre = this.sourceProperties.getUploadPre();

        if (path == null) {
            path = Paths.get(uploadPath + File.separator + uploadPre);
        }
        String store = path.toString();

        boolean uploadFlag = false;
        String fileName = null;
        Double fileSize = null;
        String ext = ""; // 文件后缀名
        String location = null;
        if (file != null) {
            // 文件名
            fileName = file.getOriginalFilename();
            if (StringLowUtils.isNoneEmpty(fileName)) {
                int dot = fileName.lastIndexOf('.');
                if (( dot > -1) && ( dot < ( fileName.length() - 1))) {
                    ext = fileName.substring(dot + 1);
                }
                // 存贮名称
                String storeName = this.renameFileName(fileName, ext);
                // 相对地址
                location = uploadPre + File.separator + storeName;
                fileSize = file.getSize() + 0.0;
                // 写文件
                try {
                    byte[] bytes = file.getBytes();
                    File dir = new File(store);
                    // 目录不存在创建目录
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    Files.write(Paths.get(uploadPath + File.separator + location), bytes);
                    uploadFlag = true;
                } catch(IOException e) {
                    logger.debug(e.getMessage());
                    return null;
                }
            }
        }
        if (sysFile != null) {
            // 写数据库
            if (uploadFlag) {
                sysFile.setFileName(fileName);
                sysFile.setFileSize(fileSize);
                sysFile.setFileType(ext);
                sysFile.setIp(NetUtils.getLocalIP());
                sysFile.setLocation(location);
                sysFile.setStore(uploadPath);
                sysFile.setUrl("/sys/sysFile/download/"
                        + StringLowUtils.replaceEach(location, new String[] {File.separator }, new String[] {"/" }));
            }
            if (update) {
                this.updateByPrimaryKeySelective(sysFile);
            } else {
                this.insertRecord(sysFile);
            }
        }

        // 返回数据
        BaseFile bf = new BaseFile();
        File ff = new File(uploadPath + File.separator + location);
        bf.setFile(ff);
        bf.setLocal(location);
        if (sysFile != null) {
            bf.setUrl(this.localUrl(sysFile.getUrl()));
        }
        bf.setSysFile(sysFile);
        return bf;
    }

    @Override
    public BaseFile saveFile(SysFile sysFile, MultipartFile file, Path path) {

        return this.modifyFile(sysFile, file, path, false);
    }

    @Override
    public List<BaseFile> saveFile(SysFile sysFile, List<MultipartFile> files, Path path) {

        List<BaseFile> baseFileList = null;
        for (MultipartFile file : files) {
            if (baseFileList == null) {
                baseFileList = new ArrayList<>();
            }
            baseFileList.add(this.saveFile(sysFile, file, path));
        }
        return baseFileList;
    }

    @Override
    public BaseFile saveFile(SysFile sysFile, MultipartFile file, String uploadPath, String pre) {

        if (pre == null) {
            pre = this.sourceProperties.getUploadPre();
        }
        if (uploadPath == null) {
            uploadPath = this.sourceProperties.getUploadPath();
        }
        Path path = Paths.get(uploadPath + File.separator + pre);
        return this.saveFile(sysFile, file, path);
    }

    @Override
    public List<BaseFile> saveFile(SysFile sysFile, List<MultipartFile> files, String uploadPath, String pre) {

        List<BaseFile> baseFileList = null;
        for (MultipartFile file : files) {
            if (baseFileList == null) {
                baseFileList = new ArrayList<>();
            }
            baseFileList.add(this.saveFile(sysFile, file, uploadPath, pre));
        }
        return baseFileList;
    }

    @Override
    public BaseFile saveFile(SysFile sysFile, String pre, MultipartFile file) {

        if (pre == null) {
            pre = this.sourceProperties.getUploadPre();
        }
        Path path = Paths.get(this.sourceProperties.getUploadPath() + File.separator + pre);
        return this.saveFile(sysFile, file, path);
    }

    @Override
    public List<BaseFile> saveFile(SysFile sysFile, String pre, List<MultipartFile> files) {

        List<BaseFile> baseFileList = null;
        for (MultipartFile file : files) {
            if (baseFileList == null) {
                baseFileList = new ArrayList<>();
            }
            baseFileList.add(this.saveFile(sysFile, pre, file));
        }
        return baseFileList;
    }

    @Override
    public BaseFile downloadFile(String path) {

        BaseFile baseFile = new BaseFile();
        String uploadPath = this.sourceProperties.getUploadPath();
        path = path.substring(path.indexOf("/sys/sysFile/download/") + 22, path.length());
        SysFileExample sysFileExample = new SysFileExample();
        sysFileExample.or().andUrlLike("%" + path).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
        List<SysFile> sysFileList = this.findListByExample(sysFileExample);
        if (sysFileList != null && !( sysFileList.isEmpty())) {
            baseFile.setSysFile(sysFileList.get(0));
            File file = null;
            try {
                file = ResourceUtils.getFile(uploadPath + File.separator + path);
                if (file.exists()) {
                    baseFile.setFile(file);
                }
            } catch(FileNotFoundException e) {
                logger.debug(e.getMessage());
                return null;
            }
        }
        return baseFile;
    }

    @Override
    public BaseFile updateFile(SysFile sysFile, String pre, MultipartFile file) {

        if (pre == null) {
            pre = this.sourceProperties.getUploadPre();
        }
        Path path = Paths.get(this.sourceProperties.getUploadPath() + File.separator + pre);
        return this.modifyFile(sysFile, file, path, true);
    }

    @Override
    public InputStream downloadJarFile(String path) {

        path = "classpath:fileTemplate/"
                + path.substring(path.indexOf("/sys/sysFile/downloadInside/") + 28, path.length());
        try {
            return new FileInputStream(FileLowUtils.getClassPathFile(path));
        } catch(Exception e) {
            try {
                return FileLowUtils.getClassPathInputStream(path);
            } catch(Exception ex) {
                logger.debug(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public BaseFile saveFile(SysUser sysUser, String pre, MultipartFile file) {

        return this.saveFile(this.initSysFile(new SysFile(), false, sysUser), pre, file);
    }

    private SysFile initSysFile(SysFile sysFile, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysFile.setCreateBy(user.getId());
            sysFile.setCreateDate(new Date());
            sysFile.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysFile.setUpdateBy(user.getId());
        sysFile.setUpdateDate(new Date());
        return sysFile;
    }

    private String localUrl(String url) {

        String port = this.sourceProperties.getEnv().getProperty("server.port");
        String context = this.sourceProperties.getEnv().getProperty("server.servlet.context-path");
        String site = null;
        if (this.sourceProperties.getWebSite() != null) {
            site = this.sourceProperties.getWebSite();
        } else {
            site = NetUtils.getLocalIP();
        }
        return site + ":" + port + "/" + context + url;
    }

    /**
     * 文件重命名
     */
    public String renameFileName(String fileName, String extension) {

        String formatDate = new SimpleDateFormat("yyMMddHHmmss").format(new Date()); // 当前时间字符串
        int random = new Random().nextInt(10000);
        if ("".equals(extension)) {
            return formatDate + random;
        }
        return formatDate + random + "." + extension;
    }

}
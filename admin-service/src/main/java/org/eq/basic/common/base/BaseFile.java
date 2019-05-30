package org.eq.basic.common.base;

import org.eq.basic.modules.sys.entity.SysFile;

import java.io.File;
import java.io.InputStream;

/**
 * 文件基础信息类
 *
 * @author JoinHan
 *         Created by JoinHan on 2017/6/7.
 */
public class BaseFile {

    // 文件
    private File file;

    // 文件流
    private InputStream inputStream;

    // 访问位置
    private String url;

    // 文件位置
    private String local;

    // 对应数据库
    private SysFile sysFile;

    public File getFile() {

        return this.file;
    }

    public void setFile(File file) {

        this.file = file;
    }

    public String getUrl() {

        return this.url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getLocal() {

        return this.local;
    }

    public void setLocal(String local) {

        this.local = local;
    }

    public SysFile getSysFile() {

        return this.sysFile;
    }

    public void setSysFile(SysFile sysFile) {

        this.sysFile = sysFile;
    }

    public InputStream getInputStream() {

        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {

        this.inputStream = inputStream;
    }
}

/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 附件表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String fileName; // 文件名

    private Double fileSize; // 文件大小

    private String fileType; // 文件类型

    private String location; // 文件相对地址

    private String url; // 文件相对访问地址

    private String store; // 文件存储位置

    private String ip; // 存贮ip

    public SysFile() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getFileName() {

        return this.fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public Double getFileSize() {

        return this.fileSize;
    }

    public void setFileSize(Double fileSize) {

        this.fileSize = fileSize;
    }

    public String getFileType() {

        return this.fileType;
    }

    public void setFileType(String fileType) {

        this.fileType = fileType;
    }

    public String getLocation() {

        return this.location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getUrl() {

        return this.url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getStore() {

        return this.store;
    }

    public void setStore(String store) {

        this.store = store;
    }

    public String getIp() {

        return this.ip;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

    @Override
    public Long getCreateBy() {

        return this.createBy;
    }

    @Override
    public void setCreateBy(Long createBy) {

        this.createBy = createBy;
    }

    @Override
    public Date getCreateDate() {

        return this.createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {

        this.createDate = createDate;
    }

    @Override
    public Long getUpdateBy() {

        return this.updateBy;
    }

    @Override
    public void setUpdateBy(Long updateBy) {

        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateDate() {

        return this.updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {

        this.updateDate = updateDate;
    }

    @Override
    public String getRemarks() {

        return this.remarks;
    }

    @Override
    public void setRemarks(String remarks) {

        this.remarks = remarks;
    }

    @Override
    public String getDelFlag() {

        return this.delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {

        this.delFlag = delFlag;
    }

}
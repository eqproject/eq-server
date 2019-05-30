/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 代码生成计划Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class GenPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String planName; // 计划名称

    private String basicPackage; // 包路径

    private String location; // 生成位置 为空为项目路径

    private String version; // 版本号

    public GenPlan() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getPlanName() {

        return this.planName;
    }

    public void setPlanName(String planName) {

        this.planName = planName;
    }

    public String getBasicPackage() {

        return this.basicPackage;
    }

    public void setBasicPackage(String basicPackage) {

        this.basicPackage = basicPackage;
    }

    public String getLocation() {

        return this.location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getVersion() {

        return this.version;
    }

    public void setVersion(String version) {

        this.version = version;
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
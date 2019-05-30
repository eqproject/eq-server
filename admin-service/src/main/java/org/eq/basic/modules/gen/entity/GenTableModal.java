/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 代码生成表配置Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class GenTableModal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String modalName; // 方案名称

    private Long planId; // 计划id

    private String functionName; // 功能名

    private String author; // 作者

    private String version; // 版本

    private String className; // 类名

    private String moduleName; // 模块名

    private String ifInheritBase; // 是否继承基础实体

    private String category; // 生成方式

    private Long dbId; // 数据库连接id

    private String dbMangerName;// 数据库连接名称

    private String tableName; // 表名

    private String pageSetting; // 页面设置

    public GenTableModal() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getModalName() {

        return this.modalName;
    }

    public void setModalName(String modalName) {

        this.modalName = modalName;
    }

    public Long getPlanId() {

        return this.planId;
    }

    public void setPlanId(Long planId) {

        this.planId = planId;
    }

    public String getFunctionName() {

        return this.functionName;
    }

    public void setFunctionName(String functionName) {

        this.functionName = functionName;
    }

    public String getAuthor() {

        return this.author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getVersion() {

        return this.version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public String getClassName() {

        return this.className;
    }

    public void setClassName(String className) {

        this.className = className;
    }

    public String getModuleName() {

        return this.moduleName;
    }

    public void setModuleName(String moduleName) {

        this.moduleName = moduleName;
    }

    public String getIfInheritBase() {

        return this.ifInheritBase;
    }

    public void setIfInheritBase(String ifInheritBase) {

        this.ifInheritBase = ifInheritBase;
    }

    public String getCategory() {

        return this.category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public Long getDbId() {

        return this.dbId;
    }

    public void setDbId(Long dbId) {

        this.dbId = dbId;
    }

    public String getTableName() {

        return this.tableName;
    }

    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

    public String getPageSetting() {

        return this.pageSetting;
    }

    public void setPageSetting(String pageSetting) {

        this.pageSetting = pageSetting;
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

    public String getDbMangerName() {

        return this.dbMangerName;
    }

    public void setDbMangerName(String dbMangerName) {

        this.dbMangerName = dbMangerName;
    }
}
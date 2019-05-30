/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import java.util.Date;

import org.eq.basic.common.annotation.ExcelResources;
import org.eq.basic.common.base.BaseEntity;

/**
 * 部门表Entity
 *
 * @author JoinHan
 * @version 2018-03-30
 */
public class SysDepart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; // 唯一标识

    private String name; // 部门名称

    private String code; // 部门代码

    private Long officeId; // 机构id

    private String type; // 部门类型

    private Integer sort; // 部门排序

    public SysDepart() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @ExcelResources(title = "部门名称", ifExport = true, ifImport = true, order = 1)
    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @ExcelResources(title = "部门代码", ifExport = true, ifImport = true, order = 2)
    public String getCode() {

        return this.code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    @ExcelResources(title = "所属机构", ifExport = true, ifImport = true, order = 3, type = "5")
    public Long getOfficeId() {

        return this.officeId;
    }

    public void setOfficeId(Long officeId) {

        this.officeId = officeId;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public Integer getSort() {

        return this.sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
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
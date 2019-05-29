/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.entity;

import cn.bubi.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 数据字典表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String value; // 字典值

    private String label; // 字典名称

    private String type; // 字典类型

    private String description; // 字典描述

    private Integer sort; // 排序

    public SysDict() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getLabel() {

        return this.label;
    }

    public void setLabel(String label) {

        this.label = label;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
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
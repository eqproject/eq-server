/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 地区表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private Long parentId; // 上级id

    private String parentIds; // 所有上级id字符串 逗号分隔

    private String name; // 地区名称

    private String code; // 地区编码

    private Integer type; // 地区类型

    private Integer sort; // 排序

    private Integer areaLevel; // 地区级别

    public SysArea() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getParentId() {

        return this.parentId;
    }

    public void setParentId(Long parentId) {

        this.parentId = parentId;
    }

    public String getParentIds() {

        return this.parentIds;
    }

    public void setParentIds(String parentIds) {

        this.parentIds = parentIds;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getCode() {

        return this.code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public Integer getType() {

        return this.type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public Integer getSort() {

        return this.sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
    }

    public Integer getAreaLevel() {

        return this.areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {

        this.areaLevel = areaLevel;
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
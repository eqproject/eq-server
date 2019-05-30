/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import org.eq.basic.common.annotation.ExcelResources;
import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 机构表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysOffice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private Long parentId; // 父机构id

    private String parentIds; // 上级机构

    private String name; // 机构名称

    private String code; // 机构代码

    private Integer sort; // 排序

    private String type; // 机构类型

    private Integer grade; // 机构等级

    private Long areaId; // 地区id

    private String address; // 地址

    private String phone; // 手机号

    private String email; // 邮箱

    public SysOffice() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @ExcelResources(title = "父级机构", ifExport = true, ifImport = true, order = 3, type = "3", sqlList = {
            @ExcelResources.List(sql = "select id id,name name from sys_office where del_flag="
                    + BaseEntity.DEL_FLAG_NORMAL, ifCash = false) })
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

    @ExcelResources(title = "机构名称", ifExport = true, ifImport = true, order = 1)
    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @ExcelResources(title = "机构代码", ifExport = true, ifImport = true, order = 2)
    public String getCode() {

        return this.code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public Integer getSort() {

        return this.sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
    }

    @ExcelResources(title = "类型", ifExport = true, ifImport = true, order = 4, dictionary = "officeType", type = "1")
    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public Integer getGrade() {

        return this.grade;
    }

    public void setGrade(Integer grade) {

        this.grade = grade;
    }

    @ExcelResources(title = "地区", ifExport = true, ifImport = true, order = 5, type = "4")
    public Long getAreaId() {

        return this.areaId;
    }

    public void setAreaId(Long areaId) {

        this.areaId = areaId;
    }

    @ExcelResources(title = "地址", ifExport = true, ifImport = true, order = 6)
    public String getAddress() {

        return this.address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getPhone() {

        return this.phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getEmail() {

        return this.email;
    }

    public void setEmail(String email) {

        this.email = email;
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
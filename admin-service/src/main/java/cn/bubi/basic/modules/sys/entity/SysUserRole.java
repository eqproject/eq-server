/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.entity;

import cn.bubi.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户角色表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private Long userId; // 用户id

    private Long roleId; // 角色id

    public SysUserRole() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getUserId() {

        return this.userId;
    }

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    public Long getRoleId() {

        return this.roleId;
    }

    public void setRoleId(Long roleId) {

        this.roleId = roleId;
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
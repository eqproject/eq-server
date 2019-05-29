/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.entity;

import cn.bubi.basic.common.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 角色表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String code; // 角色代码

    private String name; // 角色名称

    private String enname; // 角色英文名称

    private Integer type; // 角色类型

    private Integer dataScope; // 数据权限

    private Integer status; // 状态

    private List<SysRoleMenu> sysRoleMenuList;// 角色菜单

    public SysRole() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getCode() {

        return this.code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEnname() {

        return this.enname;
    }

    public void setEnname(String enname) {

        this.enname = enname;
    }

    public Integer getType() {

        return this.type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public Integer getDataScope() {

        return this.dataScope;
    }

    public void setDataScope(Integer dataScope) {

        this.dataScope = dataScope;
    }

    public Integer getStatus() {

        return this.status;
    }

    public void setStatus(Integer status) {

        this.status = status;
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

    public List<SysRoleMenu> getSysRoleMenuList() {

        return this.sysRoleMenuList;
    }

    public void setSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) {

        this.sysRoleMenuList = sysRoleMenuList;
    }
}
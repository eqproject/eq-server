/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 用户表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; // 主键

    private Long areaId; // 地区

    private Long officeId; // 机构

    private Long departId; // 部门

    private String lnm; // 登陆名

    private String pw; // 密码

    private String nm; // 昵称

    private String sex; // 性别

    private Integer status; // 状态 1--禁用

    private String tel; // 电话

    private Long noId; // 员工id

    private Integer type; // 用户类型

    private String photoHead; // 头像

    private String loginIp; // 最近登录ip

    private Date loginDate; // 最近登录时间

    private String job; // 职务

    private SysUserInfo sysUserInfo;// 用户详细信息

    private String officeidsStr;// 多个机构ids

    private String orderByClause;// 排序

    private List<SysUserOffice> sysUserOfficeList;// 用户机构

    private List<SysUserRole> sysUserRoleList;// 用户角色

    public SysUser() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getAreaId() {

        return this.areaId;
    }

    public void setAreaId(Long areaId) {

        this.areaId = areaId;
    }

    public Long getOfficeId() {

        return this.officeId;
    }

    public void setOfficeId(Long officeId) {

        this.officeId = officeId;
    }

    public Long getDepartId() {

        return this.departId;
    }

    public void setDepartId(Long departId) {

        this.departId = departId;
    }

    public String getLnm() {

        return this.lnm;
    }

    public void setLnm(String lnm) {

        this.lnm = lnm;
    }

    public String getPw() {

        return this.pw;
    }

    public void setPw(String pw) {

        this.pw = pw;
    }

    public String getNm() {

        return this.nm;
    }

    public void setNm(String nm) {

        this.nm = nm;
    }

    public String getSex() {

        return this.sex;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }

    public Integer getStatus() {

        return this.status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getTel() {

        return this.tel;
    }

    public void setTel(String tel) {

        this.tel = tel;
    }

    public Long getNoId() {

        return this.noId;
    }

    public void setNoId(Long noId) {

        this.noId = noId;
    }

    public Integer getType() {

        return this.type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public String getPhotoHead() {

        return this.photoHead;
    }

    public void setPhotoHead(String photoHead) {

        this.photoHead = photoHead;
    }

    public String getLoginIp() {

        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {

        this.loginIp = loginIp;
    }

    public Date getLoginDate() {

        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {

        this.loginDate = loginDate;
    }

    public String getJob() {

        return this.job;
    }

    public void setJob(String job) {

        this.job = job;
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

    public List<SysUserRole> getSysUserRoleList() {

        return this.sysUserRoleList;
    }

    public void setSysUserRoleList(List<SysUserRole> sysUserRoleList) {

        this.sysUserRoleList = sysUserRoleList;
    }

    public SysUserInfo getSysUserInfo() {

        return this.sysUserInfo;
    }

    public void setSysUserInfo(SysUserInfo sysUserInfo) {

        this.sysUserInfo = sysUserInfo;
    }

    public String getOfficeidsStr() {

        return this.officeidsStr;
    }

    public void setOfficeidsStr(String officeidsStr) {

        this.officeidsStr = officeidsStr;
    }

    public String getOrderByClause() {

        return this.orderByClause;
    }

    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    public List<SysUserOffice> getSysUserOfficeList() {

        return this.sysUserOfficeList;
    }

    public void setSysUserOfficeList(List<SysUserOffice> sysUserOfficeList) {

        this.sysUserOfficeList = sysUserOfficeList;
    }
}
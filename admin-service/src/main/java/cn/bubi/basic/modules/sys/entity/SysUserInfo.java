/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.entity;

import cn.bubi.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户信息表Entity
 *
 * @author JoinHan
 * @version 2018-04-11
 */
public class SysUserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; // 唯一标识

    private String name; // 姓名

    private String sex; // 性别

    private String tel; // 电话

    private String idCard; // 身份id

    private Integer status; // 状态

    private Integer type; // 用户类型

    private String photoHead; // 头像

    private String job; // 职务

    public SysUserInfo() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSex() {

        return this.sex;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }

    public String getTel() {

        return this.tel;
    }

    public void setTel(String tel) {

        this.tel = tel;
    }

    public String getIdCard() {

        return this.idCard;
    }

    public void setIdCard(String idCard) {

        this.idCard = idCard;
    }

    public Integer getStatus() {

        return this.status;
    }

    public void setStatus(Integer status) {

        this.status = status;
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

}
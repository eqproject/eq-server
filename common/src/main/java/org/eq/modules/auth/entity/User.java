package org.eq.modules.auth.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户管理Entity
 * @author hobe
 * @version 2019-05-30
 */
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Integer id;        // 唯一标识
    private String name;        // 用户名
    private String nickname;        // 昵称
    private String password;        // 登录密码md5(用户id+密码+盐 )
    private String txPassword;        // 交易密码md5(用户id+交易密码+盐 )
    private Integer sex;        // 性别(1:男;2:女)
    private String level;        // 用户等级
    private String mobile;        // 手机号
    private Integer authStatus;        // 实名验证状态(1:未认证;2:已认证;)
    private Date birthday;        // 生日
    private String photoHead;        // 头像
    private String intro;        // 简介

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTxPassword() {
        return txPassword;
    }

    public void setTxPassword(String txPassword) {
        this.txPassword = txPassword;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhotoHead() {
        return photoHead;
    }

    public void setPhotoHead(String photoHead) {
        this.photoHead = photoHead;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.entity;

import cn.bubi.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 代码生成数据库Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class GenDB extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private String name; // 连接名称

    private String dbname; // 数据库名

    private String url; // url

    private String username; // 用户名

    private String password; // 密码

    private String driverClassName; // 驱动

    private String type; // 数据库连接池（默认）

    public GenDB() {
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

    public String getDbname() {

        return this.dbname;
    }

    public void setDbname(String dbname) {

        this.dbname = dbname;
    }

    public String getUrl() {

        return this.url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return this.password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getDriverClassName() {

        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {

        this.driverClassName = driverClassName;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
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
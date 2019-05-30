package org.eq.modules.auth.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户管理Entity
 * @author hobe
 * @version 2019-05-30
 */
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long id;        // 唯一标识
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

}
/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实名认证记录Entity
 * @author bo.gao
 * @version 1.0.0
 */
@Data
public class UserIdentityAuth {

    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识
     */
    private Long id;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 身份证姓名
     */
    private String identityName;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 认证结果
     */
    private String authResult;
    private Long createBy;
    private LocalDateTime createDate;
}
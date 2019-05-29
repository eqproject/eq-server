package cn.bubi.c2c.auth.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class User  {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别(0,未知，1:男;2:女)
     * @see  cn.bubi.c2c.enums.UserSexEnum;
     */
    private Integer sex;
    /**
     *  手机号
     */
    private String mobile;
    /**
     * 身份id
     */
    private String idCard;
    /**
     * 状态 1:未认证;2:已认证
     * @see cn.bubi.c2c.enums.UserStateEnum
     */
    private Integer status;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String photoHead;
    /**
     * 简介
     */
    private String intro;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createDate;


    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     *  删除标志
     *  1 删除  0 通用
     */
    private Integer delFlag;
}

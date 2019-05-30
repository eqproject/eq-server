package org.eq.modules.auth.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 实名认证Entity
 * @author hobe
 * @version 2019-05-31
 */
public class UserIdentityAuth extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long id;        // 唯一标识
    private String identityCard;        // 身份证号,AES(user_id+身份证号+盐)
    private String identityName;        // 身份证姓名
    private Long userId;        // 用户id
    private Integer resultStatus;        // 认证结果(1:成功；2失败)
    private String resultMsg;        // 认证结果信息

    public UserIdentityAuth() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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

}
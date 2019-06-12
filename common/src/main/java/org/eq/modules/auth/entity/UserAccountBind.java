/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.entity;

import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 支付账号绑定Entity
 * @author hobe
 * @version 2019-06-13
 */
public class UserAccountBind extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private Integer type;		// 支付账号类型（1：支付宝；2：微信）
	private Long userId;		// 用户id
	private String payLoginId;		// 绑定支付账号
	private String identityNo;		// 绑定支付账号对应证件号
	private Integer status;		// 绑定状态：1：绑定；2：解绑
	private Integer defaultReceip;		// 默认收款：1：是；2：否

	public UserAccountBind() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPayLoginId() {
		return payLoginId;
	}

	public void setPayLoginId(String payLoginId) {
		this.payLoginId = payLoginId;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDefaultReceip() {
		return defaultReceip;
	}

	public void setDefaultReceip(Integer defaultReceip) {
		this.defaultReceip = defaultReceip;
	}

}
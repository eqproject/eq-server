/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.entity;

import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 用户白名单Entity
 * @author hobe
 * @version 2019-07-03
 */
public class UserClientWhitelist extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private String mobile;		// 手机号
	private Integer status;		// 是否关联注册用户(0:未关联;1:已关联;)

	public UserClientWhitelist() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
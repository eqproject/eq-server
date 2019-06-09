/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.sms.entity;

import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 短信发送记录Entity
 * @author hobe
 * @version 2019-06-06
 */
public class SmsLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private Long userId;		// 用户id
	private Long smsTemplateId;		// 短信模板id
	private String smsTemplateName;		// 短信模板名称
	private String mobile;		// 手机号
	private Integer status;		// 状态(1:发送中;2:发送成功;2:发送失败;)
	private String content;		// 短信内容

	public SmsLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(Long smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public String getSmsTemplateName() {
		return smsTemplateName;
	}

	public void setSmsTemplateName(String smsTemplateName) {
		this.smsTemplateName = smsTemplateName;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
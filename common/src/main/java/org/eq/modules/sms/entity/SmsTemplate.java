/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.sms.entity;

import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 短信Entity
 * @author hobe
 * @version 2019-06-08
 */
public class SmsTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private String code;		// 编码
	private String name;		// 模板名称
	private Integer limitDay;		// 24小时限制次数
	private Integer type;		// 业务类型(1:注册验证码;2:发布求购广告成功通知;3:发布出售广告成功通知;4:购买成功收货通知;5:转出成功通知;6:通知买家付款;)
	private String content;		// 短信内容

	public SmsTemplate() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Integer limitDay) {
		this.limitDay = limitDay;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
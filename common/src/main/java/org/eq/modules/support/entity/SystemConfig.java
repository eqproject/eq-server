/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class SystemConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 字段类型
	 * @see  org.eq.modules.enums.SystemConfigTypeEnum
	 */
	private Integer type;
	/**
	 * 显示的备注
	 */
	private String remark;

	/**
	 * 具体数值
	 */
	private String value;

	/**
	 * 状态(0:正常 1:删除)
	 */
	private Integer state;

}
/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告订单日志Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderAdLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 广告订单id
	 */
	private Long orderAdId;
	/**
	 * 状态
	 */
	private Integer oldStatus;
	/**
	 * 状态
	 */
	private Integer newStatus;
	/**
	 * 备注
	 */
	protected String remarks;

	/**
	 * 创建日期
	 */
	protected Date createDate;
}
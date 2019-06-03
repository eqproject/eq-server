/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 承兑管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderAcceptLog implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *  唯一标识
	 */
	private Long id;
	/**
	 * 承兑订单id
	 */
	private Long orderAcceptId;
	/**
	 * @see org.eq.modules.enums.OrderAcceptStateEnum
	 */
	private Integer oldStatus;
	/**
	 * @see org.eq.modules.enums.OrderAcceptStateEnum
	 */
	private Integer newStatus;
	/**
	 *  备注
	 */
	private String remarks;
	/**
	 *  创建日期
	 */
	private Date createDate;


}
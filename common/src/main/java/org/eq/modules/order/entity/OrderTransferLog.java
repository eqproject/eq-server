/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import lombok.Data;
import org.eq.modules.enums.OrderTransferStateEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 转让订单Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderTransferLog implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 唯一标识
	 */
	private Long id;
	/**
	 * 转让订单id
	 */
	private Long orderTransferId;
	/**
	 * 状态
	 * @see  OrderTransferStateEnum
	 */
	private Integer oldStatus;
	/**
	 * 状态
	 * @see  OrderTransferStateEnum
	 *
	 */
	private Integer newStatus;
	/**
	 * 创建日期
	 */
	private Date createDate;


	/**
	 * 备注
	 */
	private String remarks;


}
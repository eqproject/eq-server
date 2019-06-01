/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单支付交易日志Entity
 * @author yufei.sun
 * @version 0.0.1
 */
@Data
public class OrderPaymentTradeLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private Long orderPayTradeId;		// 支付交易id
	private Integer oldStatus;		// 旧状态:(1:支付成功;2:支付失败;3:通知放款中;4:通知放款成功;5:通知放款失败;)
	private Integer newStatus;		// 新状态:(1:支付成功;2:支付失败;3:通知放款中;4:通知放款成功;5:通知放款失败;)
	private String remarks; // 备注
	private Date createDate;// 创建日期

}
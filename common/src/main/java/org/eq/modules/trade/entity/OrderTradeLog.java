/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单交易日志Entity
 * @author yufei.sun
 * @version 0.0.1
 */
@Data
public class OrderTradeLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private Long orderTradeId;		// 交易订单id
	private Integer oldStatus;		// 状态:(1:创建;2:待支付;3:取消;4:下线取消;5:支付成功;6:支付失败;7:区块链处理中;8:区块链处理成功;8:区块链处理失败)
	private Integer newStatus;		// 状态:(1:创建;2:待支付;3:取消;4:下线取消;5:支付成功;6:支付失败;7:区块链处理中;8:区块链处理成功;8:区块链处理失败)
	private String remarks; // 备注
	private Date createDate;// 创建日期

}
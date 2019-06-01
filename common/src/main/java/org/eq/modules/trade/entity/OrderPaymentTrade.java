/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单支付交易Entity
 * @author yufei.sun
 * @version 1.0.0
 */
@Data
public class OrderPaymentTrade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private String tradeNo;		// 交易订单号
	private String payNo;		// 支付流水号
	private Integer payType;		// 支付类型(1:支付宝;2:微信)
	private String payerUser;		// 支付用户账号
	private String payeeUser;		// 收款用户账号
	private Long productId;		// 商品id
	private Integer orderNum;		// 订单数量
	private Integer status;		// 状态:(1:支付成功;2:支付失败;3:通知放款中;4:通知放款成功;5:通知放款失败)
	private Date payTime;		// 支付完成时间
	private Integer serviceFee;		// 服务费(分)
	private Integer amount;		// 金额(分)
	private String remarks; // 备注
	private Date createDate;// 创建日期
	private Date updateDate;// 更新日期

}
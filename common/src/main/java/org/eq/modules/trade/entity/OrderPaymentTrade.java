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
	private Long id;
	/**
	 * 交易订单号
	 */
	private String tradeNo;
	/**
	 * 支付流水号
	 */
	private String payNo;
	/**
	 * 支付类型(1:支付宝;2:微信)
	 */
	private Integer payType;
	/**
	 * 支付用户账号
	 */
	private String payerUser;
	/**
	 * 收款用户账号
	 */
	private String payeeUser;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	/**
	 * 状态
	 * @see org.eq.modules.enums.OrderPaymentTradeStateEnum
	 */
	private Integer status;
	/**
	 * 支付完成时间
	 */
	private Date payTime;
	/**
	 * 服务费
	 */
	private Integer serviceFee;
	/**
	 * 总金额
	 */
	private Integer amount;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

}
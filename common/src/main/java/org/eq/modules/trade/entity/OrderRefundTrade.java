/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单退款交易Entity
 * @author yufei.sun
 * @version 0.0.1
 */
@Data
public class OrderRefundTrade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private String tradeNo;		// 交易订单号
	private String refundTradeNo;		// 退款交易单号
	private String payNo;		// 退款流水号
	private Integer status;		// 状态:(1:退款中;2:退款成功;3退款失败)
	private Date refundTime;		// 退款完成时间
	private Integer amount;		// 金额(分)
	private String remarks; // 备注
	private Date createDate;// 创建日期
	private Date updateDate;// 更新日期

}
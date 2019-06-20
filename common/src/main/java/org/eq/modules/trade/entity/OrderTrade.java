/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单交易Entity
 * @author yufei.sun
 * @version 1.0
 */
@Data
public class OrderTrade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 售卖用户id
	 */
	private Long sellUserId;
	/**
	 * 购买用户id
	 */
	private Long buyUserId;
	/**
	 * 广告订单号
	 */
	private String adNo;
	/**
	 * 交易订单号
	 */
	private String tradeNo;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 订单数量
	 */
	private Integer orderNum;
	/**
	 * 	// 订单类型:(1:出售;2:求购)
	 */
	private Integer type;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 区块链状态
	 */
	private Integer blockchainStatus;
	/**
	 * 	售卖价格
	 */
	private Integer salePrice;
	/**
	 * 	商品面值
	 */
	private Integer unitPrice;
	/**
	 * 订单总金额
	 */
	private Integer amount;

	/**
	 * 服务费
	 */
	private Integer serviceFee;

	/**
	 * 是否已催
	 */
	private Integer remindPay;

	/**
	 * 终结世界
	 */
	private Date finishTime;

	/**
	 * 取消备注
	 */
	private String cancelDesc;
	/**
	 * 区块链交易ID
	 */
	private Long txId;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品图片
	 */
	private String productImg;
}
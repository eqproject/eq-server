/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告订单Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderAd implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 *  用户id
	 */
	private Long userId;
	/**
	 * 	广告订单号
	 */
	private String orderNo;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 广告标题
	 */
	private String title;
	/**
	 * 订单商品数量
	 */
	private Integer productNum;
	/**
	 * 交易中数量
	 */
	private Integer tradingNum;
	/**
	 * 已交易数量
	 */
	private Integer tradedNum;
	/**
	 * 订单类型:(1:出售;2:求购)
	 * @see org.eq.modules.enums.OrderAdTypeEnum
	 */
	private Integer type;
	/**
	 * 状态
	 * @see org.eq.modules.enums.OrderAdStateEnum
	 */
	private Integer status;
	/**
	 * 	销售单价
	 */
	private Integer price;
	/**
	 * 	金额
	 */
	private Integer amount;
	/**
	 * 	取消原因
	 */
	private String cancelDesc;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;


	/**
	 *  备注
	 */
	private String remarks;

}
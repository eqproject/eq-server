/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 承兑管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderAccept implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 唯一标识
	 */
	private Long id;
	/**
	 *  用户id
	 */
	private Long userId;
	/**
	 * 承兑订单号
	 */
	private String acceptNo;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 商品数量
	 */
	private Integer productNum;
	/**
	 * 状态
	 * @see  org.eq.modules.enums.OrderAcceptStateEnum
	 */
	private Integer status;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 *  收货人手机号
	 */
	private String consigneeMobile;
	/**
	 * 	收货人地址
	 */
	private String consigneeAddress;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 *  更新日期
	 */
	private Date updateDate;
	/**
	 * 区块链交易ID
	 */
	private Long txId;
	/**
	 *   备注
	 */
	private String remarks;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 用户昵称
	 */
	private String userNickName;

}
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
 * 商品转让Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderTransfer implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 唯一标识
	 */
	private Long id;
	/**
	 * 发起交易用户id
	 */
	private Long userId;
	/**
	 *  转让订单号
	 */
	private String transferNo;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 *  商品数量
	 */
	private Integer productNum;
	/**
	 *  状态:
	 * @see  OrderTransferStateEnum
	 */
	private Integer status;
	/**
	 *  目标钱包地址
	 */
	private String toAddress;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 区块链交易ID
	 */
	private Long txId;
	/**
	 * 备注
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
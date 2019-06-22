/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 投诉管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class OrderTradeAppeal extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 投诉单号
	 */
	private String appealNo;
	/**
	 * 交易单号
	 */
	private String tradeNo;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 	0:未 1:已解决
	 */
	private Integer status;
	private Date crateTime;
	private Date updateTime;
	private String remark;

	/**
	 * 昵称
	 */
	private String userNickName;

	/**
	 * 电话
	 */
	private String mobile;
}
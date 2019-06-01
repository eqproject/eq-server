/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户商品管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class UserProductStock implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 广告锁定数量
	 */
	private Integer lockedNum;

	/**
	 * 创建时间
	 */
	protected Date createDate;


	/**
	 * 更新日期
	 */
	protected Date updateDate;

	/**
	 * 备注
	 */
	protected String remarks;

	/**
	 * 商品状态
	 */
	protected int productStatus;

	/**
	 * 商品过期时间
	 */
	protected String productExpirationEnd;


}
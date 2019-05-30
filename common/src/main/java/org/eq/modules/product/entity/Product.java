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
 * 商品基本信息管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *  唯一标识
	 */
	private Long id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 产品图片url
	 */
	private String productImg;
	/**
	 * 承兑商Id
	 */
	private Long productAcceptId;
	/**
	 * 发行商id
	 */
	private Long productIssuerId;
	/**
	 * 单价
	 */
	private Integer unitPrice;
	/**
	 *  排序
	 */
	private Integer sort;
	/**
	 * 状态(1:正常,2:过期;3:下线)
	 * @see  org.eq.modules.enums.ProductStateEnum
	 */
	private Integer status;

	/**
	 * 创建日期
	 */
	protected Date createDate;

	/**
	 * 更新日期
	 */
	protected Date updateDate;
	/**
	 * 有效期开始时间
	 */
	private String expirationStart;
	/**
	 *  过期时间
	 */
	private String expirationEnd;
	/**
	 * 标签ID
	 */
	private String tagIds;
	/**
	 *  标签名称
	 */
	private String tagNames;
	/**
	 *  扩展信息
	 */
	private String extendInfo;

	/**
	 * 备注
	 */
	protected String remarks;


}
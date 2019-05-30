/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * 商品信息Entity
 * @author kaka
 * @version 2019.05.08
 */
@Data
public class Product {

	private static final long serialVersionUID = 1L;
	/**
	 * 商品主键
	 */
	private Long id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品编号
	 */
	private String code;
	/**
	 * 产品图片url
	 */
	private String productImg;
	/**
	 * 品牌商
	 */
	private String brand;
	/**
	 * 品牌商图片url
	 */
	private String brandImg;
	/**
	 *  品牌商备注信息
	 */
	private String brandDescription;
	/**
	 * 品牌商电话号码
	 */
	private String brandTele;
	/**
	 * 商品面值
	 */
	private Integer unitPrice;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 *  提货说明
	 */
	private String receive;

	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态(0：默认状态，1:上线状态，2:下线状态)
	 * @see  org.eq.enums.ProductStateEnum
	 *
	 */
	private Integer status;

	/**
	 * 创建者
	 */
	protected Long createBy;

	/**
	 * 创建日期
	 */
	protected Date createDate;

	/**
	 * 更新者
	 */
	protected Long updateBy;

	/**
	 *  更新日期
	 */
	protected Date updateDate;
	/**
	 * 有效期开始时间
	 */
	private String expirationStart;
	/**
	 * 过期时间
	 */
	private String expirationEnd;
	/**
	 * 标签ID
	 */
	private String tagIds;
	/**
	 * 标签名称
	 */
	private String tagNames;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 *  更新人 (扩展字段)
	 */
	private String  createUserName;

}
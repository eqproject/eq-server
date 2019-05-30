/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;

/**
 * 商品标签对应关系Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class ProductRelationTag  {

	private static final long serialVersionUID = 1L;

	/**
	 *  标签ID
	 */
	private Long tagId;
	/**
	 * 商品ID
	 */
	private Long productId;

}
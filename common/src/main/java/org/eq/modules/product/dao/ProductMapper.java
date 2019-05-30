/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.dao;

import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息Mapper接口
 * @author kaka
 * @version 2019.05.08
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product,ProductExample> {
	
}
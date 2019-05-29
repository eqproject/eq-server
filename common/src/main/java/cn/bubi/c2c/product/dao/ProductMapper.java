/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.product.entity.Product;
import cn.bubi.c2c.product.entity.ProductExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息Mapper接口
 * @author kaka
 * @version 2019.05.08
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product,ProductExample> {
	
}
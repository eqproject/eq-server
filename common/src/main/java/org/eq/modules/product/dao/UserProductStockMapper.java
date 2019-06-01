/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.dao;

import org.eq.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;

/**
 * 用户商品管理Mapper接口
 * @author kaka
 * @version 1.0.1
 */
@Mapper
public interface UserProductStockMapper extends BaseMapper<UserProductStock,UserProductStockExample> {
	
}
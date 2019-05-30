/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.dao;

import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.trade.entity.Order;
import org.eq.modules.trade.entity.OrderExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易订单管理Mapper接口
 * @author yufei.sun
 * @version 1.0.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order,OrderExample> {
	
}
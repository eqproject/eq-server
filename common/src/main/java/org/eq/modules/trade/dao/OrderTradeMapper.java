/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.dao;

import org.eq.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;

/**
 * 订单交易Mapper接口
 * @author yufei.sun
 * @version 1.0
 */
@Mapper
public interface OrderTradeMapper extends BaseMapper<OrderTrade,OrderTradeExample> {
	
}
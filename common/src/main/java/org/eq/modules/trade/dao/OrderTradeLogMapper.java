/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.dao;

import org.eq.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.eq.modules.trade.entity.OrderTradeLogExample;

/**
 * 订单交易日志Mapper接口
 * @author yufei.sun
 * @version 0.0.1
 */
@Mapper
public interface OrderTradeLogMapper extends BaseMapper<OrderTradeLog,OrderTradeLogExample> {
	
}
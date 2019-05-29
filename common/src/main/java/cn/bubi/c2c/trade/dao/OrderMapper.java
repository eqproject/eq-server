/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.trade.entity.Order;
import cn.bubi.c2c.trade.entity.OrderExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易订单管理Mapper接口
 * @author yufei.sun
 * @version 1.0.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order,OrderExample> {
	
}
/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.trade.entity.UserOrder;
import cn.bubi.c2c.trade.entity.UserOrderExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理用户个人订单Mapper接口
 * @author kaka
 * @version 1.0.0
 */
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder,UserOrderExample> {
	
}
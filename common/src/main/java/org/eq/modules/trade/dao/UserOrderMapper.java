/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.dao;

import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.trade.entity.UserOrder;
import org.eq.modules.trade.entity.UserOrderExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理用户个人订单Mapper接口
 * @author kaka
 * @version 1.0.0
 */
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder,UserOrderExample> {
	
}
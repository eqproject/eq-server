/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.service;

import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.c2c.trade.entity.UserOrder;
import cn.bubi.c2c.trade.entity.UserOrderExample;

/**
 * 管理用户个人订单Service
 * @author kaka
 * @version 1.0.0
 */
public interface UserOrderService extends ServiceExtend<UserOrder,UserOrderExample> {

    /**
     * 通过条件更新订单状态
     * @param userOrder
     * @param oldstate
     * @return
     */
    int updateUserOrderStateById(UserOrder userOrder, int oldstate);

}
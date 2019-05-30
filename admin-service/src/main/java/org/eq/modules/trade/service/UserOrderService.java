/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.trade.entity.UserOrder;
import org.eq.modules.trade.entity.UserOrderExample;

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
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.order.vo.*;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshoot;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshootExample;

/**
 * 订单Service
 * @author kaka
 * @version 1.0.1
 */
public interface OrderFinishSnapshootService extends ServiceExtend<OrderFinishSnapshoot, OrderFinishSnapshootExample> {

    /**
     * 获取完成订单
     * @param searchPageOrderFinishVO
     * @param user 用户实体
     * @return
     */
    PageResultData<OrderFinishSnapshootSimpleVO> pageFinishPlatOrder(SearchPageOrderFinishVO searchPageOrderFinishVO, User user);

}
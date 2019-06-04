/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.order.entity.OrderAccept;
import org.eq.modules.order.entity.OrderAcceptExample;
import org.eq.modules.order.vo.*;

/**
 * 承兑管理Service
 * @author kaka
 * @version 1.0.1
 */
public interface OrderAcceptService extends ServiceExtend<OrderAccept, OrderAcceptExample> {


    /**
     * 创建承兑
     * @param searchAcceptOrderVO
     * @param user 用户实体
     * @return
     */
    ServieReturn<OrderAcceptVO> createAcceptOrderVO(SearchAcceptOrderVO searchAcceptOrderVO, User user);


}
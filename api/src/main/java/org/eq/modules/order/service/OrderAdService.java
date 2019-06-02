/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.vo.ResOrderAdVO;
import org.eq.modules.order.vo.SearchAdOrderVO;
import org.eq.modules.order.vo.ServieReturn;

/**
 * 广告订单Service
 * @author kaka
 * @version 1.0.1
 */
public interface OrderAdService extends ServiceExtend<OrderAd, OrderAdExample> {


    /**
     * 创建广告订单
     * @param searchAdOrderVO
     * @param user 用户实体
     * @return
     */
    ServieReturn<ResOrderAdVO> createResOrderAdVO(SearchAdOrderVO searchAdOrderVO, User user);

}
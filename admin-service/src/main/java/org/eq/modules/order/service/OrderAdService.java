/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;

/**
 * 广告订单Service
 * @author kaka
 * @version 1.0.1
 */
public interface OrderAdService extends ServiceExtend<OrderAd, OrderAdExample> {


    /**
     * 根据商品ID 下架商品
     * @param productId
     * @return
     */
    int offOrderByProductId(long productId);


    /**
     * 取消订单
     * @param orderId
     * @return
     */
    boolean cacelOrderAd(long orderId);



}
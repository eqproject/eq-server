/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.vo.*;

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




    /**
     * 取消订单
     * @param searchAdOrderVO
     * @param user 用户实体
     * @return
     */
    ServieReturn<ResOrderAdVO> cacelResOrderAdVO(SearchAdOrderVO searchAdOrderVO, User user);



    /**
     * 获取广告订单
     * @param searchPageAdOrderVO
     * @param user 用户实体
     * @return
     */
    PageResultData<OrderAdSimpleVO> pagePlatOrderAd(SearchPageAdOrderVO searchPageAdOrderVO, User user);

    /**
     * 获取用户广告信息
     * @param searchPageAdOrderVO
     * @param user 用户实体
     * @return
     */
    PageResultData<OrderAdSimpleVO> pageUserOrderAd(SearchPageAdOrderVO searchPageAdOrderVO, User user);

}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.entity.OrderTransferExample;
import org.eq.modules.order.vo.*;

/**
 * 商品转让Service
 * @author kaka
 * @version 1.0.1
 */
public interface OrderTransferService extends ServiceExtend<OrderTransfer, OrderTransferExample> {

    /**
     * 创建广告订单
     * @param searchTransOrderVO
     * @param user 用户实体
     * @return
     */
    ServieReturn<OrderTransVO> createTransOrderVO(SearchTransOrderVO searchTransOrderVO, User user);


    /**
     * 承兑广告查询
     * @param searchPageTransVO
     * @param user 用户实体
     * @return
     */
    PageResultData<OrderTransVO> pageTransOrder(SearchPageTransVO searchPageTransVO, User user);



}
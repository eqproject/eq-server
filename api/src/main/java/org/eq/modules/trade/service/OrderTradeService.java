/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service;

import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.trade.entity.OrderPaymentTrade;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.trade.vo.*;

import java.util.List;

/**
 * 订单交易Service
 * @author yufei.sun
 * @version 1.0
 */
public interface OrderTradeService extends ServiceExtend<OrderTrade,OrderTradeExample> {

    /**
     * 创建交易订单
     * @param orderTradeCreateReqVO
     * @param user
     * @return
     */
    ServieReturn<OrderTrade> createTradeOrder(OrderTradeCreateReqVO orderTradeCreateReqVO, User user);

    void cancelTradeOrder(String tradeNo);

    OrderTradeDetailResVO tradeOrderDetail(String tradeNo);

    OrderTradePaymentResVO orderPaymentTradeNotify(OrderPaymentTrade orderPaymentTrade);


    PageResultData<OrderTradeListResVO> pageTradeOrderList(OrderTradeListReqVO orderTradeListReqVO,List<Integer> orderTradeStatus);



}
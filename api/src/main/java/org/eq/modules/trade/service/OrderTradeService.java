/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service;

import org.eq.modules.trade.entity.OrderPaymentTrade;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.trade.vo.OrderTradeDetailResVO;
import org.eq.modules.trade.vo.OrderTradePaymentResVO;

/**
 * 订单交易Service
 * @author yufei.sun
 * @version 1.0
 */
public interface OrderTradeService extends ServiceExtend<OrderTrade,OrderTradeExample> {

    OrderTrade createTradeOrder(OrderTrade orderTrade);

    void cancelTradeOrder(String tradeNo);

    OrderTradeDetailResVO tradeOrderDetail(String tradeNo);

    OrderTradePaymentResVO orderPaymentTradeNotify(OrderPaymentTrade orderPaymentTrade);



}
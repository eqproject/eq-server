/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service;

import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.trade.vo.*;

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


    /**
     * 支付前置接口
     * @param orderTradeSearchVO
     * @param user
     * @return
             */
    ServieReturn<OrderTrade> prePayTradeOrder(OrderTradeSearchVO orderTradeSearchVO, User user);


    /**
     * 取消交易订单
     * @param tradeNo 交易订单号
     */
    void cancelTradeOrder(String tradeNo);




    /**
     * 获取汇总信息
     * @param userId 用户ID
     */
    OrderTradePoolInfoVO poolInfolTradeOrder(long userId);

    /**
     * 获取详情
     * @param tradeNo
     * @return
     */
    OrderTradeDetailResVO tradeOrderDetail(String tradeNo);

    /**
     * 支付结果通知
     * @param orderTradePaymentReqVO
     * @return
     */
    OrderTradePaymentResVO orderPaymentTradeNotify(OrderTradePaymentReqVO orderTradePaymentReqVO);


    /**
     * 查询待付款数据
     * @param orderTradeListReqVO
     * @return
     */
    PageResultData<OrderTradeSimpleResVO> pageWaitPayList(OrderTradeListReqVO orderTradeListReqVO);


    /**
     * 交易中订单
     * @param orderTradeListReqVO
     * @return
     */
    PageResultData<OrderTradeTradingVO> pageTradingOrderList(OrderTradeListReqVO orderTradeListReqVO);


    /**
     *  催促订单号
     * @param tradNo
     * @return
     */
    boolean remindTrade(String tradNo);

    /**
     * 申诉
     * @param orderTradeSearchVO
     * @return
     */
    boolean appealTrade(OrderTradeSearchVO orderTradeSearchVO);


}
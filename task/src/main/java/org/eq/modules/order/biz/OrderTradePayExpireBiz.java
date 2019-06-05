package org.eq.modules.order.biz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.trade.dao.OrderPaymentTradeLogMapper;
import org.eq.modules.trade.dao.OrderPaymentTradeMapper;
import org.eq.modules.trade.dao.OrderTradeLogMapper;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @Title: OrderTradePayExpireBiz
 * @Copyright: Copyright (c) 2018
 * @Description: 关闭交易(支付超时)
 * @Company: 123.com
 * @Created on 2019/6/3下午11:43
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderTradePayExpireBiz {

    private final OrderTradeMapper orderTradeMapper;
    private final OrderTradeLogMapper orderTradeLogMapper;
    private final OrderPaymentTradeMapper orderPaymentTradeMapper;
    private final OrderPaymentTradeLogMapper orderPaymentTradeLogMapper;
    private final OrderAdMapper orderAdMapper;


    /**
     * 查询支付过期的交易信息
     * @return
     */
    public List<OrderTrade> searchPayExpireOrderTrade() {
        OrderTradeExample example = new OrderTradeExample();
        OrderTradeExample.Criteria ca = example.or();

        List<Integer> status = new ArrayList<>();
        status.add(OrderTradeStateEnum.WAIT_PAY.getState());
        ca.andStatusIn(status);
        ca.andCreateDateLessThan(DateUtil.beforeDateHour(DateUtil.getNowTime(),24));
        return orderTradeMapper.selectByExample(example);
    }

    /**
     * 更新支付过期的交易相关状态+减少广告订单交易中的数量
     * @param orderTrade
     */
    public void updateOrderTradeStatus(OrderTrade orderTrade) {
        Date nowDate = DateUtil.getNowTime();
        Integer oldStatus = orderTrade.getStatus();
        Integer newStatus = OrderTradeStateEnum.CANCEL_PAY_TIMEOUT.getState();

        orderTrade.setStatus(newStatus);
        orderTrade.setUpdateDate(nowDate);

        orderTradeMapper.updateByPrimaryKeySelective(orderTrade);

        OrderTradeLog orderTradeLog = new OrderTradeLog();
        orderTradeLog.setOldStatus(oldStatus);
        orderTradeLog.setNewStatus(newStatus);
        orderTradeLog.setCreateDate(nowDate);
        orderTradeLog.setOrderTradeId(orderTrade.getId());
        orderTradeLog.setRemarks("关闭交易(支付超时)");
        orderTradeLogMapper.insert(orderTradeLog);

        OrderPaymentTradeExample orderPaymentTradeExample = new OrderPaymentTradeExample();
        OrderPaymentTradeExample.Criteria criteria = orderPaymentTradeExample.or();
        criteria.andTradeNoEqualTo(orderTrade.getTradeNo());
        List<OrderPaymentTrade> orderPaymentTradeList = orderPaymentTradeMapper.selectByExample(orderPaymentTradeExample);
        if (CollectionUtils.isNotEmpty(orderPaymentTradeList)) {
            orderPaymentTradeList.forEach(b->{

                Integer paymentOldStatus = b.getStatus();
                OrderPaymentTradeLog orderPaymentTradeLog = new OrderPaymentTradeLog();
                orderPaymentTradeLog.setCreateDate(nowDate);
                orderPaymentTradeLog.setOldStatus(paymentOldStatus);
                orderPaymentTradeLog.setNewStatus(2);
                orderPaymentTradeLog.setOrderPayTradeId(b.getId());
                orderPaymentTradeLog.setRemarks("关闭交易(支付超时)支付订单状态改为支付失败");
                orderPaymentTradeLogMapper.insertSelective(orderPaymentTradeLog);

                b.setStatus(2);
                b.setUpdateDate(nowDate);
                orderPaymentTradeMapper.updateByPrimaryKeySelective(b);
            });
        }

        OrderAdExample orderAdExample = new OrderAdExample();
        OrderAdExample.Criteria criteria1 = orderAdExample.or();
        criteria1.andOrderNoEqualToForAll(orderTrade.getAdNo());
        List<OrderAd> orderAdList =  orderAdMapper.selectByExample(orderAdExample);
        if (CollectionUtils.isNotEmpty(orderAdList)) {
           orderAdList.forEach(b->{
               Integer num = b.getTradingNum()-orderTrade.getOrderNum();
               num=num<=0?0:num;
               b.setTradingNum(num);
               b.setUpdateDate(nowDate);
               orderAdMapper.updateByPrimaryKeySelective(b);
           });
        }
    }

}

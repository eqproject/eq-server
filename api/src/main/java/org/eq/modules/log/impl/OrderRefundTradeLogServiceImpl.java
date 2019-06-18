package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.trade.dao.OrderRefundTradeLogMapper;
import org.eq.modules.trade.entity.OrderRefundTradeLog;
import org.eq.modules.trade.entity.OrderRefundTradeLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单退款交易日志ServiceImpl
 *
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
public class OrderRefundTradeLogServiceImpl implements CommonLogService<OrderRefundTradeLog> {
    @Autowired
    private OrderRefundTradeLogMapper mapper;

    @Override
    public void save(OrderRefundTradeLog orderRefundTradeLog) {
        mapper.insert(orderRefundTradeLog);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRefundTradeLog> list(long orderId) {
        OrderRefundTradeLogExample example = new OrderRefundTradeLogExample();
        example.setOrderByClause("id asc");

        OrderRefundTradeLogExample.Criteria ca = example.or();
        ca.andOrderRefundTradeIdEqualTo(orderId);

        return mapper.selectByExample(example);
    }
}
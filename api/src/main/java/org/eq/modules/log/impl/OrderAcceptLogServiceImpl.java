/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.order.dao.OrderAcceptLogMapper;
import org.eq.modules.order.entity.OrderAcceptLog;
import org.eq.modules.order.entity.OrderAcceptLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 承兑管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAcceptLogServiceImpl implements CommonLogService<OrderAcceptLog> {
    private OrderAcceptLogMapper mapper;

    @Override
    public void save(OrderAcceptLog orderAcceptLog) {
        mapper.insert(orderAcceptLog);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderAcceptLog> list(long orderId) {
        OrderAcceptLogExample example = new OrderAcceptLogExample();
        example.setOrderByClause("id asc");

        OrderAcceptLogExample.Criteria ca = example.or();
        ca.andOrderAcceptIdEqualTo(orderId);

        return mapper.selectByExample(example);
    }
}
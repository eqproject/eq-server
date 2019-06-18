/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.trade.dao.OrderPaymentTradeLogMapper;
import org.eq.modules.trade.entity.OrderPaymentTradeLog;
import org.eq.modules.trade.entity.OrderPaymentTradeLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单支付交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderPaymentTradeLogServiceImpl implements CommonLogService<OrderPaymentTradeLog> {

	private OrderPaymentTradeLogMapper mapper;

	@Override
	public void save(OrderPaymentTradeLog orderPaymentTradeLog) {
		mapper.insert(orderPaymentTradeLog);
	}

	@Transactional(readOnly = true)
	@Override
	public List<OrderPaymentTradeLog> list(long orderId) {
		OrderPaymentTradeLogExample example = new OrderPaymentTradeLogExample();
		example.setOrderByClause("id asc");

		OrderPaymentTradeLogExample.Criteria ca = example.or();
		ca.andOrderPayTradeIdEqualTo(orderId);

		return mapper.selectByExample(example);
	}
}
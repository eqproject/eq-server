/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.trade.dao.OrderRefundTradeLogMapper;
import org.eq.modules.trade.entity.OrderRefundTradeLog;
import org.eq.modules.trade.entity.OrderRefundTradeLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单退款交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderRefundTradeLogServiceImpl implements CommonLogService<OrderRefundTradeLog> {
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
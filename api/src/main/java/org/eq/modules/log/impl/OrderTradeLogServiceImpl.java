package org.eq.modules.log.impl;

import org.eq.modules.log.CommonLogService;
import org.eq.modules.trade.dao.OrderTradeLogMapper;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.eq.modules.trade.entity.OrderTradeLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
public class OrderTradeLogServiceImpl implements CommonLogService<OrderTradeLog> {
	@Autowired
	private OrderTradeLogMapper mapper;

	@Override
	public void save(OrderTradeLog orderTradeLog) {
		mapper.insert(orderTradeLog);
	}

	@Transactional(readOnly = true)
	@Override
	public List<OrderTradeLog> list(long orderId) {
		OrderTradeLogExample example = new OrderTradeLogExample();
		example.setOrderByClause("id asc");

		OrderTradeLogExample.Criteria ca = example.or();
		ca.andOrderTradeIdEqualTo(orderId);

		return mapper.selectByExample(example);
	}
}
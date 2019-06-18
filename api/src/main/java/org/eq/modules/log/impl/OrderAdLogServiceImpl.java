/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.order.entity.OrderAdLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 广告订单日志ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAdLogServiceImpl  implements CommonLogService<OrderAdLog> {

	private OrderAdLogMapper mapper;

	@Override
	public void save(OrderAdLog orderAdLog) {
		mapper.insert(orderAdLog);
	}

	@Transactional(readOnly = true)
	@Override
	public List<OrderAdLog> list(long orderId) {
		OrderAdLogExample example = new OrderAdLogExample();
		example.setOrderByClause("id asc");

		OrderAdLogExample.Criteria ca = example.or();
		ca.andOrderAdIdEqualTo(orderId);

		return mapper.selectByExample(example);
	}
}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.log.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.log.CommonLogService;
import org.eq.modules.order.dao.OrderTransferLogMapper;
import org.eq.modules.order.entity.OrderTransferLog;
import org.eq.modules.order.entity.OrderTransferLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 转让订单ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderTransferLogServiceImpl implements CommonLogService<OrderTransferLog> {
	private OrderTransferLogMapper mapper;

	@Override
	public void save(OrderTransferLog orderTransferLog) {
		mapper.insert(orderTransferLog);
	}

	@Transactional(readOnly = true)
	@Override
	public List<OrderTransferLog> list(long orderId) {
		OrderTransferLogExample example = new OrderTransferLogExample();
		example.setOrderByClause("id asc");

		OrderTransferLogExample.Criteria ca = example.or();
		ca.andOrderTransferIdEqualTo(orderId);

		return mapper.selectByExample(example);
	}
}
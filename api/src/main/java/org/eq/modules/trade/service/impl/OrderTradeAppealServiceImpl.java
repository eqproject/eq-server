/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.trade.dao.OrderTradeAppealMapper;
import org.eq.modules.trade.entity.OrderTradeAppeal;
import org.eq.modules.trade.entity.OrderTradeAppealExample;
import org.eq.modules.trade.service.OrderTradeAppealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 投诉管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderTradeAppealServiceImpl extends ServiceImplExtend<OrderTradeAppealMapper, OrderTradeAppeal, OrderTradeAppealExample> implements OrderTradeAppealService {

	@Override
	public OrderTradeAppealExample getExampleFromEntity(OrderTradeAppeal orderTradeAppeal, Map<String, Object> params) {
		OrderTradeAppealExample example = new OrderTradeAppealExample();
		OrderTradeAppealExample.Criteria ca = example.or();
		if(orderTradeAppeal==null){
			return example;
		}
		String orderName = null;
		String orderDir = null;
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
		}
		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		return example;
	}

}
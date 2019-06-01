/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.eq.modules.trade.dao.OrderTradeLogMapper;
import org.eq.modules.trade.entity.OrderTradeLogExample;
import org.eq.modules.trade.service.OrderTradeLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 订单交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderTradeLogServiceImpl extends ServiceImplExtend<OrderTradeLogMapper, OrderTradeLog, OrderTradeLogExample> implements OrderTradeLogService {

	@Override
	public OrderTradeLogExample getExampleFromEntity(OrderTradeLog orderTradeLog, Map<String, Object> params) {
		OrderTradeLogExample example = new OrderTradeLogExample();
		OrderTradeLogExample.Criteria ca = example.or();
		if(orderTradeLog==null){
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
		if(orderTradeLog.getId()!=null){
			ca.andIdEqualTo(orderTradeLog.getId());
		}
		if(orderTradeLog.getOrderTradeId()!=null){
			ca.andOrderTradeIdEqualTo(orderTradeLog.getOrderTradeId());
		}
		if(orderTradeLog.getOldStatus()!=null){
			ca.andOldStatusEqualTo(orderTradeLog.getOldStatus());
		}
		if(orderTradeLog.getNewStatus()!=null){
			ca.andNewStatusEqualTo(orderTradeLog.getNewStatus());
		}
		if(orderTradeLog.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderTradeLog.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderTradeLog.getRemarks())){
			ca.andRemarksEqualTo(orderTradeLog.getRemarks());
		}
		return example;
	}

}
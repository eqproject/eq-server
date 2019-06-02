/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.modules.trade.entity.OrderPaymentTradeLog;
import org.eq.modules.trade.dao.OrderPaymentTradeLogMapper;
import org.eq.modules.trade.entity.OrderPaymentTradeLogExample;
import org.eq.modules.trade.service.OrderPaymentTradeLogService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 订单支付交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderPaymentTradeLogServiceImpl extends ServiceImplExtend<OrderPaymentTradeLogMapper, OrderPaymentTradeLog, OrderPaymentTradeLogExample> implements OrderPaymentTradeLogService {

	@Override
	public OrderPaymentTradeLogExample getExampleFromEntity(OrderPaymentTradeLog orderPaymentTradeLog, Map<String, Object> params) {
		OrderPaymentTradeLogExample example = new OrderPaymentTradeLogExample();
		OrderPaymentTradeLogExample.Criteria ca = example.or();
		if(orderPaymentTradeLog==null){
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
		if(orderPaymentTradeLog.getId()!=null){
			ca.andIdEqualTo(orderPaymentTradeLog.getId());
		}
		if(orderPaymentTradeLog.getOrderPayTradeId()!=null){
			ca.andOrderPayTradeIdEqualTo(orderPaymentTradeLog.getOrderPayTradeId());
		}
		if(orderPaymentTradeLog.getOldStatus()!=null){
			ca.andOldStatusEqualTo(orderPaymentTradeLog.getOldStatus());
		}
		if(orderPaymentTradeLog.getNewStatus()!=null){
			ca.andNewStatusEqualTo(orderPaymentTradeLog.getNewStatus());
		}
		if(orderPaymentTradeLog.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderPaymentTradeLog.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTradeLog.getRemarks())){
			ca.andRemarksEqualTo(orderPaymentTradeLog.getRemarks());
		}
		return example;
	}

}
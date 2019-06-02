/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.modules.trade.entity.OrderRefundTradeLog;
import org.eq.modules.trade.dao.OrderRefundTradeLogMapper;
import org.eq.modules.trade.entity.OrderRefundTradeLogExample;
import org.eq.modules.trade.service.OrderRefundTradeLogService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 订单退款交易日志ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderRefundTradeLogServiceImpl extends ServiceImplExtend<OrderRefundTradeLogMapper, OrderRefundTradeLog, OrderRefundTradeLogExample> implements OrderRefundTradeLogService {

	@Override
	public OrderRefundTradeLogExample getExampleFromEntity(OrderRefundTradeLog orderRefundTradeLog, Map<String, Object> params) {
		OrderRefundTradeLogExample example = new OrderRefundTradeLogExample();
		OrderRefundTradeLogExample.Criteria ca = example.or();
		if(orderRefundTradeLog==null){
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
		if(orderRefundTradeLog.getId()!=null){
			ca.andIdEqualTo(orderRefundTradeLog.getId());
		}
		if(orderRefundTradeLog.getOrderRefundTradeId()!=null){
			ca.andOrderRefundTradeIdEqualTo(orderRefundTradeLog.getOrderRefundTradeId());
		}
		if(orderRefundTradeLog.getOldStatus()!=null){
			ca.andOldStatusEqualTo(orderRefundTradeLog.getOldStatus());
		}
		if(orderRefundTradeLog.getNewStatus()!=null){
			ca.andNewStatusEqualTo(orderRefundTradeLog.getNewStatus());
		}
		if(orderRefundTradeLog.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderRefundTradeLog.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderRefundTradeLog.getRemarks())){
			ca.andRemarksEqualTo(orderRefundTradeLog.getRemarks());
		}
		return example;
	}

}
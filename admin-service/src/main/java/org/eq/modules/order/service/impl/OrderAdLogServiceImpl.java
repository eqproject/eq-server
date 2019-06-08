/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.order.entity.OrderAdLogExample;
import org.eq.modules.order.service.OrderAdLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 广告订单日志ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAdLogServiceImpl extends ServiceImplExtend<OrderAdLogMapper, OrderAdLog, OrderAdLogExample> implements OrderAdLogService {

	@Autowired
	public OrderAdLogServiceImpl(OrderAdLogMapper orderAdLogMapper){
		super.setMapper(orderAdLogMapper);
	}


	@Override
	public OrderAdLogExample getExampleFromEntity(OrderAdLog orderAdLog, Map<String, Object> params) {
		OrderAdLogExample example = new OrderAdLogExample();
		OrderAdLogExample.Criteria ca = example.or();
		if(orderAdLog==null){
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
		if(orderAdLog.getId()!=null){
			ca.andIdEqualTo(orderAdLog.getId());
		}
		if(orderAdLog.getOrderAdId()!=null){
			ca.andOrderAdIdEqualTo(orderAdLog.getOrderAdId());
		}
		if(orderAdLog.getOldStatus()!=null){
			ca.andOldStatusEqualTo(orderAdLog.getOldStatus());
		}
		if(orderAdLog.getNewStatus()!=null){
			ca.andNewStatusEqualTo(orderAdLog.getNewStatus());
		}
		if(orderAdLog.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderAdLog.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderAdLog.getRemarks())){
			ca.andRemarksEqualTo(orderAdLog.getRemarks());
		}
		return example;
	}

}
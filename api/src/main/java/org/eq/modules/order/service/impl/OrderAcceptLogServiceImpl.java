/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderAcceptLogMapper;
import org.eq.modules.order.entity.OrderAcceptLog;
import org.eq.modules.order.entity.OrderAcceptLogExample;
import org.eq.modules.order.service.OrderAcceptLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 承兑管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAcceptLogServiceImpl extends ServiceImplExtend<OrderAcceptLogMapper, OrderAcceptLog, OrderAcceptLogExample> implements OrderAcceptLogService {




	@Override
	public OrderAcceptLogExample getExampleFromEntity(OrderAcceptLog orderAcceptLog, Map<String, Object> params) {
		OrderAcceptLogExample example = new OrderAcceptLogExample();
		OrderAcceptLogExample.Criteria ca = example.or();
		if(orderAcceptLog==null){
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
		if(orderAcceptLog.getId()!=null){
			ca.andIdEqualTo(orderAcceptLog.getId());
		}
		if(orderAcceptLog.getOrderAcceptId()!=null){
			ca.andOrderAcceptIdEqualTo(orderAcceptLog.getOrderAcceptId());
		}
		if(orderAcceptLog.getOldStatus()!=null){
			ca.andOldStatusEqualTo(orderAcceptLog.getOldStatus());
		}
		if(orderAcceptLog.getNewStatus()!=null){
			ca.andNewStatusEqualTo(orderAcceptLog.getNewStatus());
		}
		if(orderAcceptLog.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderAcceptLog.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderAcceptLog.getRemarks())){
			ca.andRemarksEqualTo(orderAcceptLog.getRemarks());
		}
		return example;
	}


}
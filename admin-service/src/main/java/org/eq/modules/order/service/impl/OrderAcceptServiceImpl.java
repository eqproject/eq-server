/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderAcceptMapper;
import org.eq.modules.order.entity.OrderAccept;
import org.eq.modules.order.entity.OrderAcceptExample;
import org.eq.modules.order.service.OrderAcceptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 承兑ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAcceptServiceImpl extends ServiceImplExtend<OrderAcceptMapper, OrderAccept, OrderAcceptExample> implements OrderAcceptService {

	@Override
	public OrderAcceptExample getExampleFromEntity(OrderAccept orderAccept, Map<String, Object> params) {
		OrderAcceptExample example = new OrderAcceptExample();
		OrderAcceptExample.Criteria ca = example.or();
		if(orderAccept==null){
			return example;
		}
		String orderName = null;
		String orderDir = null;
		Date stateTime = null;
		Date endTime = null;
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
			stateTime = (Date) params.get("startTime");
			endTime = (Date) params.get("endTime");
		}

		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		if(orderAccept.getId()!=null){
			ca.andIdEqualTo(orderAccept.getId());
		}
		if(orderAccept.getUserId()!=null){
			ca.andUserIdEqualTo(orderAccept.getUserId());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getAcceptNo())){
			ca.andAcceptNoEqualTo(orderAccept.getAcceptNo());
		}
		if(orderAccept.getProductId()!=null){
			ca.andProductIdEqualTo(orderAccept.getProductId());
		}
		if(orderAccept.getProductNum()!=null){
			ca.andProductNumEqualTo(orderAccept.getProductNum());
		}
		if(orderAccept.getStatus()!=null){
			ca.andStatusEqualTo(orderAccept.getStatus());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getConsignee())){
			ca.andConsigneeEqualTo(orderAccept.getConsignee());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getConsigneeMobile())){
			ca.andConsigneeMobileEqualTo(orderAccept.getConsigneeMobile());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getConsigneeAddress())){
			ca.andConsigneeAddressEqualTo(orderAccept.getConsigneeAddress());
		}
		if(stateTime!=null){
			ca.andCreateDateGreaterThanOrEqualTo(stateTime);
		}
		if(endTime!=null){
			ca.andCreateDateLessThanOrEqualTo(endTime);
		}
		if(orderAccept.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(orderAccept.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getRemarks())){
			ca.andRemarksEqualTo(orderAccept.getRemarks());
		}
		if(orderAccept.getTxId()!=null){
			ca.andTxIdEqualTo(orderAccept.getTxId());
		}
		return example;
	}

}
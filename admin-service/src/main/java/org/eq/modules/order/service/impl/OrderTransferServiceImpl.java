/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderTransferMapper;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.entity.OrderTransferExample;
import org.eq.modules.order.service.OrderTransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 转让ServiceImpl
 * @author kaka
 * @version 1.0.01
 */
@SuppressWarnings("all")
@Service
@Transactional
@AutowiredService
public class OrderTransferServiceImpl extends ServiceImplExtend<OrderTransferMapper, OrderTransfer, OrderTransferExample> implements OrderTransferService {

	@Override
	public OrderTransferExample getExampleFromEntity(OrderTransfer orderTransfer, Map<String, Object> params) {
		OrderTransferExample example = new OrderTransferExample();
		OrderTransferExample.Criteria ca = example.or();
		if(orderTransfer==null){
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
		if(orderTransfer.getId()!=null){
			ca.andIdEqualTo(orderTransfer.getId());
		}
		if(orderTransfer.getUserId()!=null){
			ca.andUserIdEqualTo(orderTransfer.getUserId());
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getTransferNo())){
			ca.andTransferNoEqualTo(orderTransfer.getTransferNo());
		}
		if(orderTransfer.getProductId()!=null){
			ca.andProductIdEqualTo(orderTransfer.getProductId());
		}
		if(orderTransfer.getProductNum()!=null){
			ca.andProductNumEqualTo(orderTransfer.getProductNum());
		}
		if(orderTransfer.getStatus()!=null){
			ca.andStatusEqualTo(orderTransfer.getStatus());
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getToAddress())){
			ca.andToAddressEqualTo(orderTransfer.getToAddress());
		}
		if(stateTime!=null){
			ca.andCreateDateGreaterThanOrEqualTo(stateTime);
		}
		if(endTime!=null){
			ca.andCreateDateLessThanOrEqualTo(endTime);
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getRemarks())){
			ca.andRemarksEqualTo(orderTransfer.getRemarks());
		}
		if(orderTransfer.getTxId()!=null){
			ca.andTxIdEqualTo(orderTransfer.getTxId());
		}
		return example;
	}

}
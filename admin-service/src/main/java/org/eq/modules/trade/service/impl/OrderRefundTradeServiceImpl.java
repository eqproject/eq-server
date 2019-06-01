/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.trade.dao.OrderRefundTradeMapper;
import org.eq.modules.trade.entity.OrderRefundTrade;
import org.eq.modules.trade.entity.OrderRefundTradeExample;
import org.eq.modules.trade.service.OrderRefundTradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 订单退款交易ServiceImpl
 * @author yufei.sun
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderRefundTradeServiceImpl extends ServiceImplExtend<OrderRefundTradeMapper, OrderRefundTrade, OrderRefundTradeExample> implements OrderRefundTradeService {

	@Override
	public OrderRefundTradeExample getExampleFromEntity(OrderRefundTrade orderRefundTrade, Map<String, Object> params) {
		OrderRefundTradeExample example = new OrderRefundTradeExample();
		OrderRefundTradeExample.Criteria ca = example.or();
		if(orderRefundTrade==null){
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
		if(orderRefundTrade.getId()!=null){
			ca.andIdEqualTo(orderRefundTrade.getId());
		}
		if(StringLowUtils.isNotBlank(orderRefundTrade.getTradeNo())){
			ca.andTradeNoEqualTo(orderRefundTrade.getTradeNo());
		}
		if(StringLowUtils.isNotBlank(orderRefundTrade.getRefundTradeNo())){
			ca.andRefundTradeNoEqualTo(orderRefundTrade.getRefundTradeNo());
		}
		if(StringLowUtils.isNotBlank(orderRefundTrade.getPayNo())){
			ca.andPayNoEqualTo(orderRefundTrade.getPayNo());
		}
		if(orderRefundTrade.getStatus()!=null){
			ca.andStatusEqualTo(orderRefundTrade.getStatus());
		}
		if(orderRefundTrade.getRefundTime()!=null){
			ca.andRefundTimeEqualTo(orderRefundTrade.getRefundTime());
		}
		if(orderRefundTrade.getAmount()!=null){
			ca.andAmountEqualTo(orderRefundTrade.getAmount());
		}
		if(orderRefundTrade.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderRefundTrade.getCreateDate());
		}
		if(orderRefundTrade.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(orderRefundTrade.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(orderRefundTrade.getRemarks())){
			ca.andRemarksEqualTo(orderRefundTrade.getRemarks());
		}
		return example;
	}

}
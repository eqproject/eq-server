/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.modules.trade.entity.OrderPaymentTrade;
import org.eq.modules.trade.dao.OrderPaymentTradeMapper;
import org.eq.modules.trade.entity.OrderPaymentTradeExample;
import org.eq.modules.trade.service.OrderPaymentTradeService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 订单支付交易ServiceImpl
 * @author yufei.sun
 * @version 1.0.0
 */
@Service
@Transactional
@AutowiredService
public class OrderPaymentTradeServiceImpl extends ServiceImplExtend<OrderPaymentTradeMapper, OrderPaymentTrade, OrderPaymentTradeExample> implements OrderPaymentTradeService {

	@Override
	public OrderPaymentTradeExample getExampleFromEntity(OrderPaymentTrade orderPaymentTrade, Map<String, Object> params) {
		OrderPaymentTradeExample example = new OrderPaymentTradeExample();
		OrderPaymentTradeExample.Criteria ca = example.or();
		if(orderPaymentTrade==null){
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
		if(orderPaymentTrade.getId()!=null){
			ca.andIdEqualTo(orderPaymentTrade.getId());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTrade.getTradeNo())){
			ca.andTradeNoEqualTo(orderPaymentTrade.getTradeNo());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTrade.getPayNo())){
			ca.andPayNoEqualTo(orderPaymentTrade.getPayNo());
		}
		if(orderPaymentTrade.getPayType()!=null){
			ca.andPayTypeEqualTo(orderPaymentTrade.getPayType());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTrade.getPayerUser())){
			ca.andPayerUserEqualTo(orderPaymentTrade.getPayerUser());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTrade.getPayeeUser())){
			ca.andPayeeUserEqualTo(orderPaymentTrade.getPayeeUser());
		}
		if(orderPaymentTrade.getProductId()!=null){
			ca.andProductIdEqualTo(orderPaymentTrade.getProductId());
		}
		if(orderPaymentTrade.getOrderNum()!=null){
			ca.andOrderNumEqualTo(orderPaymentTrade.getOrderNum());
		}
		if(orderPaymentTrade.getStatus()!=null){
			ca.andStatusEqualTo(orderPaymentTrade.getStatus());
		}
		if(orderPaymentTrade.getPayTime()!=null){
			ca.andPayTimeEqualTo(orderPaymentTrade.getPayTime());
		}
		if(orderPaymentTrade.getServiceFee()!=null){
			ca.andServiceFeeEqualTo(orderPaymentTrade.getServiceFee());
		}
		if(orderPaymentTrade.getAmount()!=null){
			ca.andAmountEqualTo(orderPaymentTrade.getAmount());
		}
		if(orderPaymentTrade.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderPaymentTrade.getCreateDate());
		}
		if(orderPaymentTrade.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(orderPaymentTrade.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(orderPaymentTrade.getRemarks())){
			ca.andRemarksEqualTo(orderPaymentTrade.getRemarks());
		}
		return example;
	}

}
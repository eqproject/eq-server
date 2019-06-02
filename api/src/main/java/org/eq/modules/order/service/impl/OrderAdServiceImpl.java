/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.order.vo.ResOrderAdVO;
import org.eq.modules.order.vo.SearchAdOrderVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 广告订单ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAdServiceImpl extends ServiceImplExtend<OrderAdMapper, OrderAd, OrderAdExample> implements OrderAdService {

	@Override
	public OrderAdExample getExampleFromEntity(OrderAd orderAd, Map<String, Object> params) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		if(orderAd==null){
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
		if(orderAd.getId()!=null){
			ca.andIdEqualTo(orderAd.getId());
		}
		if(orderAd.getUserId()!=null){
			ca.andUserIdEqualTo(orderAd.getUserId());
		}
		if(StringLowUtils.isNotBlank(orderAd.getOrderNo())){
			ca.andOrderNoEqualTo(orderAd.getOrderNo());
		}
		if(orderAd.getProductId()!=null){
			ca.andProductIdEqualTo(orderAd.getProductId());
		}
		if(StringLowUtils.isNotBlank(orderAd.getTitle())){
			ca.andTitleEqualTo(orderAd.getTitle());
		}
		if(orderAd.getProductNum()!=null){
			ca.andProductNumEqualTo(orderAd.getProductNum());
		}
		if(orderAd.getTradingNum()!=null){
			ca.andTradingNumEqualTo(orderAd.getTradingNum());
		}
		if(orderAd.getTradedNum()!=null){
			ca.andTradedNumEqualTo(orderAd.getTradedNum());
		}
		if(orderAd.getType()!=null){
			ca.andTypeEqualTo(orderAd.getType());
		}
		if(orderAd.getStatus()!=null){
			ca.andStatusEqualTo(orderAd.getStatus());
		}
		if(orderAd.getPrice()!=null){
			ca.andPriceEqualTo(orderAd.getPrice());
		}
		if(orderAd.getAmount()!=null){
			ca.andAmountEqualTo(orderAd.getAmount());
		}
		if(StringLowUtils.isNotBlank(orderAd.getCancelDesc())){
			ca.andCancelDescEqualTo(orderAd.getCancelDesc());
		}
		if(orderAd.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderAd.getCreateDate());
		}
		if(orderAd.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(orderAd.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(orderAd.getRemarks())){
			ca.andRemarksEqualTo(orderAd.getRemarks());
		}
		return example;
	}

	@Override
	public ResOrderAdVO createResOrderAdVO(SearchAdOrderVO searchAdOrderVO) {
		return null;
	}
}
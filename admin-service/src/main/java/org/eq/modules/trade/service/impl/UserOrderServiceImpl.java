/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.trade.dao.UserOrderMapper;
import org.eq.modules.trade.entity.UserOrder;
import org.eq.modules.trade.entity.UserOrderExample;
import org.eq.modules.trade.service.UserOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 管理用户个人订单ServiceImpl
 * @author kaka
 * @version 1.0.0
 */
@Service
@Transactional
@AutowiredService
public class UserOrderServiceImpl extends ServiceImplExtend<UserOrderMapper, UserOrder, UserOrderExample> implements UserOrderService {



	@Override
	public UserOrderExample getExampleFromEntity(UserOrder userOrder, Map<String, Object> params) {
		UserOrderExample example = new UserOrderExample();
		UserOrderExample.Criteria ca = example.or();
		if(userOrder==null){
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
		if(userOrder.getId()!=null){
			ca.andIdEqualTo(userOrder.getId());
		}
		if(StringLowUtils.isNotBlank(userOrder.getOrderNo())){
			ca.andOrderNoEqualTo(userOrder.getOrderNo());
		}
		if(userOrder.getProductId()!=null){
			ca.andProductIdEqualTo(userOrder.getProductId());
		}
		if(userOrder.getProductNum()!=null){
			ca.andProductNumEqualTo(userOrder.getProductNum());
		}
		if(userOrder.getTradeNum()!=null){
			ca.andTradeNumEqualTo(userOrder.getTradeNum());
		}
		if(userOrder.getType()!=null){
			ca.andTypeEqualTo(userOrder.getType());
		}
		if(userOrder.getStatus()!=null){
			ca.andStatusEqualTo(userOrder.getStatus());
		}
		if(userOrder.getPrice()!=null){
			ca.andPriceEqualTo(userOrder.getPrice());
		}
		if(userOrder.getAmount()!=null){
			ca.andAmountEqualTo(userOrder.getAmount());
		}
		if(StringLowUtils.isNotBlank(userOrder.getDescription())){
			ca.andDescriptionEqualTo(userOrder.getDescription());
		}
		if(StringLowUtils.isNotBlank(userOrder.getCancelDesc())){
			ca.andCancelDescEqualTo(userOrder.getCancelDesc());
		}
		if(userOrder.getCreateBy()!=null){
			ca.andCreateByEqualTo(userOrder.getCreateBy());
		}
		if(userOrder.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userOrder.getCreateDate());
		}
		if(userOrder.getUpdateBy()!=null){
			ca.andUpdateByEqualTo(userOrder.getUpdateBy());
		}
		if(userOrder.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(userOrder.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(userOrder.getRemarks())){
			ca.andRemarksEqualTo(userOrder.getRemarks());
		}
        if(StringLowUtils.isNotBlank(userOrder.getProductName())){
            ca.andProductNameLike(userOrder.getProductName());
        }
        if(StringLowUtils.isNotBlank(userOrder.getProductCode())){
            ca.andProductCodeEqualTo(userOrder.getProductCode());
        }
        if(params.get("startTime")!=null){
            ca.andCreateDateGreaterThanOrEqualTo((Date) params.get("startTime"));
        }
        if(params.get("endTime")!=null){
            ca.andCreateDateLessThanOrEqualTo((Date) params.get("endTime"));
        }
		return example;
	}

	@Override
	public int updateUserOrderStateById(UserOrder userOrder,int oldState) {
		UserOrderExample example = new UserOrderExample();
		UserOrderExample.Criteria ca = example.or();
		if(userOrder==null){
			return 0;
		}
		ca.andIdEqualToForUpdate(userOrder.getId());
		ca.andStatusEqualToForUpdate(oldState);
		return this.updateByExampleSelective(userOrder,example);
	}
}
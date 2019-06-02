/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.exception.ProductNotExistsException;
import org.eq.basic.common.util.OrderNoGenerateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.enums.OrderNoPreFixEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.eq.modules.trade.service.OrderTradeLogService;
import org.eq.modules.trade.service.OrderTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 订单交易ServiceImpl
 * @author yufei.sun
 * @version 1.0
 */
@Service
@Transactional
@AutowiredService
public class OrderTradeServiceImpl extends ServiceImplExtend<OrderTradeMapper, OrderTrade, OrderTradeExample> implements OrderTradeService {

	@Autowired
	OrderTradeLogService  orderTradeLogService;
	@Autowired
	ProductService productService;

	@Override
	public OrderTradeExample getExampleFromEntity(OrderTrade orderTrade, Map<String, Object> params) {
		OrderTradeExample example = new OrderTradeExample();
		OrderTradeExample.Criteria ca = example.or();
		if(orderTrade==null){
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
		if(orderTrade.getId()!=null){
			ca.andIdEqualTo(orderTrade.getId());
		}
		if(orderTrade.getSellUserId()!=null){
			ca.andSellUserIdEqualTo(orderTrade.getSellUserId());
		}
		if(orderTrade.getBuyUserId()!=null){
			ca.andBuyUserIdEqualTo(orderTrade.getBuyUserId());
		}
		if(StringLowUtils.isNotBlank(orderTrade.getAdNo())){
			ca.andAdNoEqualTo(orderTrade.getAdNo());
		}
		if(StringLowUtils.isNotBlank(orderTrade.getTradeNo())){
			ca.andTradeNoEqualTo(orderTrade.getTradeNo());
		}
		if(orderTrade.getProductId()!=null){
			ca.andProductIdEqualTo(orderTrade.getProductId());
		}
		if(orderTrade.getOrderNum()!=null){
			ca.andOrderNumEqualTo(orderTrade.getOrderNum());
		}
		if(orderTrade.getType()!=null){
			ca.andTypeEqualTo(orderTrade.getType());
		}
		if(orderTrade.getStatus()!=null){
			ca.andStatusEqualTo(orderTrade.getStatus());
		}
		if(orderTrade.getBlockchainStatus()!=null){
			ca.andBlockchainStatusEqualTo(orderTrade.getBlockchainStatus());
		}
		if(orderTrade.getSalePrice()!=null){
			ca.andSalePriceEqualTo(orderTrade.getSalePrice());
		}
		if(orderTrade.getUnitPrice()!=null){
			ca.andUnitPriceEqualTo(orderTrade.getUnitPrice());
		}
		if(orderTrade.getAmount()!=null){
			ca.andAmountEqualTo(orderTrade.getAmount());
		}
		if(orderTrade.getRemindPay()!=null){
			ca.andRemindPayEqualTo(orderTrade.getRemindPay());
		}
		if(orderTrade.getFinishTime()!=null){
			ca.andFinishTimeEqualTo(orderTrade.getFinishTime());
		}
		if(StringLowUtils.isNotBlank(orderTrade.getRemarks())){
			ca.andRemarksEqualTo(orderTrade.getRemarks());
		}
		if(StringLowUtils.isNotBlank(orderTrade.getCancelDesc())){
			ca.andCancelDescEqualTo(orderTrade.getCancelDesc());
		}
		if(orderTrade.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderTrade.getCreateDate());
		}
		if(orderTrade.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(orderTrade.getUpdateDate());
		}
		if(orderTrade.getTxId()!=null){
			ca.andTxIdEqualTo(orderTrade.getTxId());
		}
		return example;
	}

	@Override
	public OrderTrade createTradeOrder(OrderTrade orderTrade) {

		Product product = productService.selectByPrimaryKey(orderTrade.getProductId());
		if (product == null) {
			logger.error("createTradeOrder 商品ID[{}]记录不存在",orderTrade.getProductId());
			throw new ProductNotExistsException("商品ID"+orderTrade.getProductId()+"记录不存在");
		}
		orderTrade.setUnitPrice(product.getUnitPrice());
		orderTrade.setAmount(orderTrade.getOrderNum()*orderTrade.getSalePrice());

		String tradeNo = OrderNoGenerateUtil.generateNo(OrderNoPreFixEnum.TRADE_NO);
		orderTrade.setTradeNo(tradeNo);
		Long orderTradeId = getMapper().insertSelectiveAndReturnId(orderTrade);

		// 插入交易日志
		OrderTradeLog orderTradeLog = new OrderTradeLog();
		orderTradeLog.setOldStatus(OrderTradeStateEnum.WAIT_PAY.getState());
		orderTradeLog.setNewStatus(OrderTradeStateEnum.WAIT_PAY.getState());
		orderTradeLog.setOrderTradeId(orderTradeId);
		orderTradeLog.setCreateDate(orderTrade.getCreateDate());
		orderTradeLog.setRemarks(tradeNo);
		orderTradeLogService.insertSelective(orderTradeLog);

		return orderTrade;
	}
}
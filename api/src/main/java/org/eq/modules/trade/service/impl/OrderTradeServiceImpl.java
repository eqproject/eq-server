/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.OrderNoGenerateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.exception.UserNotExistsException;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.enums.OrderNoPreFixEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.exception.OrderAdNotExistsException;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.exception.ProductNotExistsException;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.*;
import org.eq.modules.trade.exception.PaymentTradeOrderNotExistsException;
import org.eq.modules.trade.exception.TradeOrderNotExistsException;
import org.eq.modules.trade.service.OrderPaymentTradeLogService;
import org.eq.modules.trade.service.OrderPaymentTradeService;
import org.eq.modules.trade.service.OrderTradeLogService;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
	public OrderTradeServiceImpl(OrderTradeMapper orderTradeMapper) {
		super.setMapper(orderTradeMapper);
	}

	@Autowired
	OrderTradeLogService  orderTradeLogService;
	@Autowired
	ProductService productService;

	@Autowired
	OrderAdService orderAdService;
	@Autowired
	UserService userService;

	@Autowired
	OrderPaymentTradeService orderPaymentTradeService;

	@Autowired
	OrderPaymentTradeLogService orderPaymentTradeLogService;

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
			throw new ProductNotExistsException("商品ID记录不存在");
		}
		OrderAd orderAd = new OrderAd();
		orderAd.setOrderNo(orderTrade.getAdNo());
		orderAd = orderAdService.selectByRecord(orderAd);
		if (orderAd == null) {
			logger.error("createTradeOrder 广告订单[{}]记录不存在",orderTrade.getAdNo());
			throw new OrderAdNotExistsException("广告订单记录不存在");
		}

		User buserUser = userService.selectByPrimaryKey(orderTrade.getBuyUserId());
		if (buserUser == null) {
			logger.error("createTradeOrder 买家[{}]用户记录不存在",orderTrade.getBuyUserId());
			throw new UserNotExistsException("买家用户记录不存在");
		}

		User sellUser = userService.selectByPrimaryKey(orderAd.getUserId());
		if (sellUser == null) {
			logger.error("createTradeOrder 卖家[{}]用户记录不存在",orderAd.getUserId());
			throw new UserNotExistsException("卖家用户记录不存在");
		}
		Date nowDate = DateUtil.getNowTime();

		orderTrade.setCreateDate(nowDate);
		orderTrade.setUpdateDate(nowDate);
		orderTrade.setUnitPrice(product.getUnitPrice());
		orderTrade.setAmount(orderTrade.getOrderNum()*orderTrade.getSalePrice());
		orderTrade.setSellUserId(orderAd.getUserId());

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

		// 插入支付记录
		OrderPaymentTrade orderPaymentTrade = new OrderPaymentTrade();
		orderPaymentTrade.setTradeNo(tradeNo);
		orderPaymentTrade.setCreateDate(nowDate);
		orderPaymentTrade.setUpdateDate(nowDate);
		orderPaymentTrade.setProductId(orderTrade.getProductId());
		orderPaymentTrade.setOrderNum(orderTrade.getOrderNum());
		orderPaymentTrade.setServiceFee(orderTrade.getServiceFee());
		Long orderPaymentTradeId = orderPaymentTradeService.insertOrderPaymentTradeReturnId(orderPaymentTrade);

		// 插入支付日志记录
		OrderPaymentTradeLog orderPaymentTradeLog = new OrderPaymentTradeLog();
		orderPaymentTradeLog.setOrderPayTradeId(orderPaymentTradeId);
		orderPaymentTradeLog.setCreateDate(nowDate);
		orderPaymentTradeLog.setOldStatus(orderPaymentTrade.getStatus());
		orderPaymentTradeLog.setNewStatus(orderPaymentTrade.getStatus());
		orderPaymentTradeLogService.insertSelective(orderPaymentTradeLog);

		return orderTrade;
	}

	@Override
	public void cancelTradeOrder(String tradeNo) {
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(tradeNo);
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null) {
			logger.error("cancelTradeOrder 失败，交易单号[{}]记录不存在",tradeNo);
			throw new TradeOrderNotExistsException("交易单号记录不存在");
		}
		orderTrade.setStatus(OrderTradeStateEnum.CANCEL.getState());
		orderTrade.setUpdateDate(DateUtil.getNowTime());
		updateByPrimaryKeySelective(orderTrade);
	}

	@Override
	public OrderTradeDetailResVO tradeOrderDetail(String tradeNo) {
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(tradeNo);
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null) {
			logger.error("tradeOrderDetail 交易单号[{}]记录不存在",tradeNo);
			throw new TradeOrderNotExistsException("交易单号记录不存在");
		}

		Product product = productService.selectByPrimaryKey(orderTrade.getProductId());
		if (product == null) {
			logger.error("tradeOrderDetail 商品ID[{}]记录不存在",orderTrade.getProductId());
			throw new ProductNotExistsException("商品ID记录不存在");
		}

		User buserUser = userService.selectByPrimaryKey(orderTrade.getBuyUserId());
		if (buserUser == null) {
			logger.error("tradeOrderDetail 买家[{}]用户记录不存在",orderTrade.getBuyUserId());
			throw new UserNotExistsException("买家用户记录不存在");
		}

		User sellUser = userService.selectByPrimaryKey(orderTrade.getSellUserId());
		if (sellUser == null) {
			logger.error("tradeOrderDetail 卖家[{}]用户记录不存在",orderTrade.getSellUserId());
			throw new UserNotExistsException("卖家用户记录不存在");
		}

		OrderPaymentTrade orderPaymentTrade = new OrderPaymentTrade();
		orderPaymentTrade.setTradeNo(orderTrade.getTradeNo());
		orderPaymentTrade = orderPaymentTradeService.selectByRecord(orderPaymentTrade);


		OrderTradeDetailProduct orderTradeDetailProduct = new OrderTradeDetailProduct();
		orderTradeDetailProduct.setUnitPrice(product.getUnitPrice());
		orderTradeDetailProduct.setName(product.getName());
		orderTradeDetailProduct.setProductImg(product.getProductImg());

		OrderTradeDetailTrade trade = new OrderTradeDetailTrade();
		trade.setAmount(orderTrade.getAmount());
		trade.setCreateTime(DateUtil.dateToStr(orderTrade.getCreateDate(),DateUtil.DATE_FORMAT_FULL_01));
		trade.setOrderNum(orderTrade.getOrderNum());
		trade.setRemindPay(orderTrade.getRemindPay());
		trade.setSalePrice(orderTrade.getSalePrice());
		if (orderPaymentTrade != null) { // 未支付之前，数值为空
			trade.setPayNo(orderPaymentTrade.getPayNo());
			trade.setServiceFee(orderPaymentTrade.getServiceFee());
			trade.setPayTime(DateUtil.dateToStr(orderPaymentTrade.getPayTime(),DateUtil.DATE_FORMAT_FULL_01));
		}

		OrderTradeDetailUser orderTradeDetailUser = new OrderTradeDetailUser();
		orderTradeDetailUser.setBuyUserId(orderTrade.getBuyUserId());
		orderTradeDetailUser.setSellUserId(orderTrade.getSellUserId());
		orderTradeDetailUser.setBuyUserNickName(buserUser.getNickname());
		orderTradeDetailUser.setSellUserName(sellUser.getName());
		orderTradeDetailUser.setSellUserNickName(sellUser.getNickname());
		orderTradeDetailUser.setSellUserAccount("");// todo 从用户绑定账户表中获取卖家支付账户


		OrderTradeDetailResVO orderTradeDetailResVO = new OrderTradeDetailResVO();
		orderTradeDetailResVO.setTrade(trade);
		orderTradeDetailResVO.setProduct(orderTradeDetailProduct);
		orderTradeDetailResVO.setUser(orderTradeDetailUser);
		return orderTradeDetailResVO;
	}

	@Override
	public OrderTradePaymentResVO orderPaymentTradeNotify(OrderPaymentTrade orderPaymentTrade) {

		OrderPaymentTrade orderPaymentTradeOld = orderPaymentTradeService.findOrderPaymentTradeByTradeNo(orderPaymentTrade.getTradeNo());
		if (orderPaymentTradeOld == null) {
			logger.error("orderPaymentTradeNotify 交易单号[{}]支付记录不存在",orderPaymentTrade.getTradeNo());
			throw new PaymentTradeOrderNotExistsException("交易单号支付记录不存在");
		}

		Integer oldStatus = orderPaymentTradeOld.getStatus();
		Date nowDate = DateUtil.getNowTime();

		orderPaymentTradeOld.setPayTime(nowDate);
		orderPaymentTradeOld.setStatus(orderPaymentTrade.getStatus());
		orderPaymentTradeOld.setUpdateDate(nowDate);
		orderPaymentTradeOld.setPayNo(orderPaymentTrade.getPayNo());
		orderPaymentTradeOld.setPayType(orderPaymentTrade.getPayType());
		orderPaymentTradeOld.setAmount(orderPaymentTrade.getAmount());
		orderPaymentTradeService.updateByPrimaryKeySelective(orderPaymentTradeOld);

		OrderPaymentTradeLog orderPaymentTradeLog = new OrderPaymentTradeLog();
		orderPaymentTradeLog.setOrderPayTradeId(orderPaymentTradeOld.getId());
		orderPaymentTradeLog.setCreateDate(nowDate);
		orderPaymentTradeLog.setOldStatus(oldStatus);
		orderPaymentTradeLog.setNewStatus(orderPaymentTrade.getStatus());
		orderPaymentTradeLogService.insertSelective(orderPaymentTradeLog);

		OrderTradePaymentResVO orderTradePaymentResVO = new OrderTradePaymentResVO();
		orderTradePaymentResVO.setTradeNo(orderPaymentTrade.getTradeNo());
		return orderTradePaymentResVO;
	}
}
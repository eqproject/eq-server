/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.order.service.OrderAdLogService;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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


	@Autowired
	private ProductService productService;

	@Autowired
	private UserProductStockService userProductStockService;

	@Autowired
	private OrderAdLogService orderAdLogService;


	@Override
	public OrderAdExample getExampleFromEntity(OrderAd orderAd, Map<String, Object> params) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		if(orderAd==null){
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
			example.setOrderByClause("OA.update_date desc");
		}
		if(!StringUtils.isEmpty(orderAd.getOrderNo())){
			ca.andOrderNoEqualToForAll(orderAd.getOrderNo());
		}
		if(!StringUtils.isEmpty(orderAd.getProductName())){
			ca.andProductNameLikeForAll(orderAd.getProductName());
		}
		if(orderAd.getStatus()!=null){
			ca.andStatusEqualToForALL(orderAd.getStatus());
		}
		if(orderAd.getType()!=null){
			ca.andTypeEqualToForAll(orderAd.getType());
		}
		if(stateTime!=null){
			ca.andCreateDateGreaterThanOrEqualToForAll(stateTime);
		}
		if(endTime!=null){
			ca.andCreateDateLessThanOrEqualToForAll(endTime);
		}
		return example;
	}



	private  OrderAdExample getSimpleExampleFromEntity(OrderAd orderAd) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		if(orderAd==null){
			return example;
		}
		if(orderAd.getProductId()!=null){
			ca.andProductIdEqualToForUpdate(orderAd.getProductId());
		}
		return example;
	}


	@Override
	public int offOrderByProductId(long productId){
		if(productId<=0){
			return 0;
		}
		Product product = productService.selectByPrimaryKey(productId);
		if(product==null){
			return 0;
		}
		OrderAd updateWhere = new OrderAd();
		updateWhere.setProductId(product.getId());
		OrderAdExample orderAdExample = getSimpleExampleFromEntity(updateWhere);
		List<OrderAd> list = findListByExample(orderAdExample);
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		int result = 0;
		for(OrderAd orderAd : list){
			if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState() || orderAd.getStatus()==OrderAdStateEnum.ORDER_FINISH.getState()){
				continue;
			}
			boolean upresult = cacelOrderAd(orderAd.getId());
			if(!upresult){
				logger.error("取消订单失败,订单编号为:{}",orderAd.getOrderNo());
			}
			result= result+1;
		}
		return result;

	}


	@Override
	public boolean cacelOrderAd(long orderId) {
		if(orderId<=0){
			return false;
		}
		OrderAd orderAd = selectByPrimaryKey(orderId);
		if(orderAd==null){
			return false;
		}
		if(orderAd.getStatus() == OrderAdStateEnum.ORDER_FINISH.getState() || orderAd.getStatus() == OrderAdStateEnum.ORDER_REJECT.getState()){
			return false;
		}
		if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()){
			return true;
		}
		OrderAd updateOrder = new OrderAd();
		updateOrder.setStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
		updateOrder.setUpdateDate(new Date());
		updateOrder.setCancelDesc("管理员取消");
		int updateResult = updateByExampleSelective(updateOrder,getExampleFromEntity(orderAd.getStatus(),orderAd.getId()));
		if(updateResult<=0){
			return false;
		}
		StringBuffer remarks = new StringBuffer();
		if(orderAd.getType()== OrderAdTypeEnum.ORDER_SALE.getType()){
			int number  = orderAd.getProductNum() -orderAd.getTradedNum()  - orderAd.getTradingNum();
			boolean stockResult  = userProductStockService.updateStock(orderAd.getProductId(),orderAd.getUserId(),-number);
			if(stockResult){
				remarks.append("取消成功,").append("退回库存成功,应退:").append(number).append(",执行快照为:").append(orderAd.toString());
			}else{
				remarks.append("取消成功,").append("退回库存失败,应退回:").append(number).append(",执行快照为:").append(orderAd.toString());
			}
		}else{
			remarks.append("取消成功,").append("执行快照为:").append(orderAd.toString());
		}
		OrderAdLog orderAdLog = new OrderAdLog();
		orderAdLog.setCreateDate(new Date());
		orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
		orderAdLog.setOldStatus(orderAd.getStatus());
		orderAdLog.setOrderAdId(orderAd.getId());
		orderAdLog.setRemarks(remarks.toString());
		try{
			orderAdLogService.insertRecord(orderAdLog);
		}catch (Exception e){
			e.printStackTrace();
			logger.info("插入订单操作日志记录数据出错 {}",orderAdLog.toString());
		}
		return true ;
	}

	@Override
	public boolean auditOrder(long orderId,boolean ispass) {
		if(orderId<=0){
			return false;
		}
		OrderAd orderAd = selectByPrimaryKey(orderId);
		if(orderAd==null){
			return false;
		}
		if(orderAd.getStatus() != OrderAdStateEnum.ORDER_DEFAULT.getState()){
			return false;
		}
		int newState = OrderAdStateEnum.ORDER_REJECT.getState();
		if(ispass){
			newState = OrderAdStateEnum.ORDER_TRADEING.getState();
		}
		OrderAd updateOrder = new OrderAd();
		updateOrder.setStatus(newState);
		updateOrder.setUpdateDate(new Date());
		int updateResult = updateByExampleSelective(updateOrder,getExampleFromEntity(orderAd.getStatus(),orderAd.getId()));
		if(updateResult<=0){
			return false;
		}
		StringBuffer remarks = new StringBuffer();
		if(orderAd.getType()== OrderAdTypeEnum.ORDER_SALE.getType()){
			if(!ispass){
				int number  = orderAd.getProductNum() -orderAd.getTradedNum()  - orderAd.getTradingNum();
				boolean stockResult  = userProductStockService.updateStock(orderAd.getProductId(),orderAd.getUserId(),-number);
				if(stockResult){
					remarks.append("审核成功,").append("退回库存成功,应退:").append(number).append(",执行快照为:").append(orderAd.toString());
				}else{
					remarks.append("审核成功,").append("退回库存失败,应退回:").append(number).append(",执行快照为:").append(orderAd.toString());
				}
			}
		}else{
			remarks.append("审核成功,").append("执行快照为:").append(orderAd.toString());
		}
		OrderAdLog orderAdLog = new OrderAdLog();
		orderAdLog.setCreateDate(new Date());
		orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
		orderAdLog.setOldStatus(orderAd.getStatus());
		orderAdLog.setOrderAdId(orderAd.getId());
		orderAdLog.setRemarks(remarks.toString());
		try{
			orderAdLogService.insertRecord(orderAdLog);
		}catch (Exception e){
			e.printStackTrace();
			logger.info("插入订单操作日志记录数据出错 {}",orderAdLog.toString());
		}
		return true ;
	}





	/**
	 * 获取简单查询实体
	 * @param statue
	 * @param id
	 * @return
	 */
	@SuppressWarnings("all")
	private OrderAdExample getExampleFromEntity(int statue,long id) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		ca.andIdEqualToForUpdate(id);
		ca.andStatusEqualToForUpdate(statue);
		return example;
	}



}
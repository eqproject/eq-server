/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.OrderUtil;
import org.eq.modules.common.utils.ProductUtil;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.order.service.OrderAdLogService;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	private UserProductStockService userProductStockService;

	@Autowired
	private ProductService productService;

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
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
		}
		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		return example;
	}


	/**
	 * 获取简单查询实体
	 * @param statue
	 * @param id
	 * @return
	 */
	public OrderAdExample getExampleFromEntity(int statue,long id) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
	    ca.andIdEqualToForUpdate(id);
	    ca.andStatusEqualToForUpdate(statue);

		return example;
	}


	/**
	 * 获取有效订单数据
	 * @param orderType
	 * @return
	 */
	public OrderAdExample getExampleFromEntityAll(int orderType,List<Long> productList) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		ca.andTypeEqualToForAll(orderType);
		if(!CollectionUtils.isEmpty(productList)){
			ca.andProductIdInForAll(productList);
		}
		List<Integer> states = new ArrayList<>();
		states.add(OrderAdStateEnum.ORDER_TRADEING.getState());
		ca.andStatusInForAll(states);
		example.setOrderByClause("sort desc");
		return example;
	}


	/**
	 * 获取用户有效订单数据
	 * @param user
	 * @return
	 */
	private  OrderAdExample getExampleFromEntityAllForUser(User user) {
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		List<Integer> states = new ArrayList<>();
		states.add(OrderAdStateEnum.ORDER_TRADEING.getState());
		ca.andStatusInForAll(states);
		ca.andUserIdEqualToForAll(user.getId());
		example.setOrderByClause("sort desc");
		return example;
	}

	@Override
	public ServieReturn<ResOrderAdVO> createResOrderAdVO(SearchAdOrderVO searchAdOrderVO, User user) {
		String volidResult = VolidOrderInfo.volidSearchOrderAd(searchAdOrderVO);
		ServieReturn<ResOrderAdVO> result  = new ServieReturn<>();
		if(!StringUtils.isEmpty(volidResult)){
			result.setErrMsg(volidResult);
			return result;
		}
		if(user==null){
			result.setErrMsg("用户信息为空");
			return result;
		}
		if(OrderAdTypeEnum.ORDER_BUY.getType()==searchAdOrderVO.getOrderType()){

			try{
				ResOrderAdVO resOrderAdVO = OrderUtil.transObj(createBuyOrderAd(searchAdOrderVO,user));
				if(resOrderAdVO==null){
					result.setErrMsg("创建订单失败");
				}else{
					result.setData(resOrderAdVO);
				}
			}catch (Exception e){
				result.setErrMsg("创建订单失败");
				e.printStackTrace();
			}
			return result;
		}

		UserProductStock userProductStock =  userProductStockService.getUserProductStock(searchAdOrderVO.getProductId(),user);
		if(userProductStock==null){
			result.setErrMsg("此商品无效");
			return result;
		}
		int balance = userProductStock.getStockNum() - userProductStock.getLockedNum();
		if((balance-searchAdOrderVO.getNumber())<=0){
			result.setErrMsg("可售卖量不足");
			return result;
		}
		ResOrderAdVO resOrderAdVO = OrderUtil.transObj(createSaleOrderAd(searchAdOrderVO,user));
		if(resOrderAdVO!=null){
			result.setData(resOrderAdVO);
			return result;
		}
		result.setErrMsg("创建订单失败");
		return result;
	}

	@Override
	public ServieReturn<ResOrderAdVO> cacelResOrderAdVO(SearchAdOrderVO searchAdOrderVO, User user) {
		ServieReturn<ResOrderAdVO> result  = new ServieReturn<>();
		if(searchAdOrderVO == null  || StringUtils.isEmpty(searchAdOrderVO.getOrderCode())){
			result.setErrMsg("订单号为空，无法取消");
			return result;
		}
		if(user==null){
			result.setErrMsg("用户信息为空");
			return result;
		}
		OrderAd orderAd = new OrderAd();
		orderAd.setUserId(user.getId());
		orderAd.setOrderNo(searchAdOrderVO.getOrderCode());
		orderAd = selectByRecord(orderAd);
		if(orderAd==null){
			result.setErrMsg("订单为空，无法取消");
			return result;
		}
		if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()){
			result.setErrMsg("订单已为取消状态，无法进行二次取消");
			return result;
		}
		OrderAd updateOrder = new OrderAd();
		updateOrder.setStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
		updateOrder.setUpdateDate(new Date());
		updateOrder.setCancelDesc("外部接口调用取消");
		int updateResult = updateByExampleSelective(updateOrder,getExampleFromEntity(orderAd.getStatus(),orderAd.getId()));
		if(updateResult<=0){
			result.setErrMsg("订单取消失败");
			return result;
		}
		StringBuffer remarks = new StringBuffer();
		if(orderAd.getType()==OrderAdTypeEnum.ORDER_SALE.getType()){
			int number  = orderAd.getProductNum() -orderAd.getTradedNum()  - orderAd.getTradingNum();
			if(number<=0){
				result.setErrMsg("订单库存异常");
				return result;
			}
			boolean stockResult  = userProductStockService.updateStock(orderAd.getProductId(),user.getId(),-number);
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
		result.setData(OrderUtil.transObj(orderAd));
		return  result ;
	}

	@Override
	public PageResultData<OrderAdSimpleVO> pagePlatOrderAd(SearchPageAdOrderVO searchPageAdOrderVO,User user) {
		PageResultData<OrderAdSimpleVO> result = new PageResultData<>();
		if(searchPageAdOrderVO ==null){
			searchPageAdOrderVO = new SearchPageAdOrderVO();
		}
		if(searchPageAdOrderVO.getPageSize()<=0 || searchPageAdOrderVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageAdOrderVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageAdOrderVO.getPageNum()<=0){
			searchPageAdOrderVO.setPageNum(1);
		}
		if(searchPageAdOrderVO.getOrderType()!=1 && searchPageAdOrderVO.getOrderType()!=2){
			return result;
		}
		if(user==null){
			return result;
		}
		OrderAdExample orderAdExample = null;
		//我要卖 难
		if(searchPageAdOrderVO.getOrderType()==1){
			List<Long> userProductId = new ArrayList<>();
			userProductId.add(-1L);
			userProductId.addAll(userProductStockService.listUserProdutId(user));
			orderAdExample  = getExampleFromEntityAll(OrderAdTypeEnum.ORDER_BUY.getType(),userProductId);
		}else{//我要买
			orderAdExample  = getExampleFromEntityAll(OrderAdTypeEnum.ORDER_SALE.getType(),null);
		}
		BaseTableData baseTableData = findDataTableByExampleForPage(orderAdExample, searchPageAdOrderVO.getPageNum(), searchPageAdOrderVO.getPageSize());
		if(baseTableData==null){
			return result;
		}
		List<OrderAdSimpleVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderAd> pList = baseTableData.getData();
		for(OrderAd p : pList){
			dataList.add(OrderUtil.transObjForSimple(p));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}


	@Override
	public PageResultData<OrderAdSimpleVO> pageUserOrderAd(SearchPageAdOrderVO searchPageAdOrderVO,User user) {
		PageResultData<OrderAdSimpleVO> result = new PageResultData<>();
		if(searchPageAdOrderVO ==null){
			searchPageAdOrderVO = new SearchPageAdOrderVO();
		}
		if(searchPageAdOrderVO.getPageSize()<=0 || searchPageAdOrderVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageAdOrderVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageAdOrderVO.getPageNum()<=0){
			searchPageAdOrderVO.setPageNum(1);
		}

		BaseTableData baseTableData = findDataTableByExampleForPage(getExampleFromEntityAllForUser(user), searchPageAdOrderVO.getPageNum(), searchPageAdOrderVO.getPageSize());
		if(baseTableData==null){
			return result;
		}
		List<OrderAdSimpleVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderAd> pList = baseTableData.getData();
		for(OrderAd p : pList){
			dataList.add(OrderUtil.transObjForSimple(p));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}


	/**
	 * 创建求购订单
	 * @param searchAdOrderVO
	 * @param user
	 * @return
	 */
	private  OrderAd  createBuyOrderAd(SearchAdOrderVO searchAdOrderVO, User user){
		if(searchAdOrderVO==null || searchAdOrderVO.getProductId() <=0){
			return null;
		}
		Product product = productService.selectByPrimaryKey(searchAdOrderVO.getProductId());
		if(!ProductUtil.isEffect(product)){
			return null;
		}
		OrderAd orderAd = new OrderAd();
		orderAd.setUserId(user.getId());
		orderAd.setOrderNo(getOrderNo());
		orderAd.setProductId(searchAdOrderVO.getProductId());
		orderAd.setTitle(searchAdOrderVO.getAdTitle());
		orderAd.setProductNum(searchAdOrderVO.getNumber());
		orderAd.setTradingNum(0);
		orderAd.setTradedNum(0);
		orderAd.setType(OrderAdTypeEnum.ORDER_BUY.getType());
		orderAd.setStatus(OrderAdStateEnum.ORDER_DEFAULT.getState());
		orderAd.setPrice(searchAdOrderVO.getPrice());
		orderAd.setAmount(orderAd.getProductNum()*orderAd.getPrice());
		orderAd.setCreateDate(new Date());
		orderAd.setUpdateDate(orderAd.getCreateDate());
		int result = insertSelective(orderAd);
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(orderAd);
			retryNum--;
		}
		return result>0?orderAd:null;
	}


	/**
	 * 创建求购订单
	 * @param searchAdOrderVO
	 * @param user
	 * @return
	 */
	private  OrderAd  createSaleOrderAd(SearchAdOrderVO searchAdOrderVO, User user){
		if(searchAdOrderVO==null || searchAdOrderVO.getProductId() <=0){
			return null;
		}
		Product product = productService.selectByPrimaryKey(searchAdOrderVO.getProductId());
		if(!ProductUtil.isEffect(product)){
			return null;
		}
		boolean updateStockResult = false;
		try {
			updateStockResult = userProductStockService.updateStock(product.getId(),user.getId(),searchAdOrderVO.getNumber());
		}catch (Exception e){
			e.printStackTrace();
		}
		if(!updateStockResult){
			return null;
		}
		OrderAd orderAd = new OrderAd();
		orderAd.setUserId(user.getId());
		orderAd.setOrderNo(getOrderNo());
		orderAd.setProductId(searchAdOrderVO.getProductId());
		orderAd.setTitle(searchAdOrderVO.getAdTitle());
		orderAd.setProductNum(searchAdOrderVO.getNumber());
		orderAd.setTradingNum(0);
		orderAd.setTradedNum(0);
		orderAd.setType(OrderAdTypeEnum.ORDER_SALE.getType());
		orderAd.setStatus(OrderAdStateEnum.ORDER_DEFAULT.getState());
		orderAd.setPrice(searchAdOrderVO.getPrice());
		orderAd.setAmount(orderAd.getProductNum()*orderAd.getPrice());
		orderAd.setCreateDate(new Date());
		orderAd.setUpdateDate(orderAd.getCreateDate());
		int result = insertSelective(orderAd);
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(orderAd);
			retryNum--;
		}
		if(result<=0){
			updateStockResult =true;
			try {
				updateStockResult = userProductStockService.updateStock(product.getId(),user.getId(),-searchAdOrderVO.getNumber());
			}catch (Exception e){
				e.printStackTrace();
			}
			if(!updateStockResult){
				logger.error("创建订单失败，但是锁定库存成功，释放库存失败，用户id :{} 商品ID:{} 应释放量:{}",searchAdOrderVO.getProductId(),user.getId(),searchAdOrderVO.getNumber());
			}

		}
		return result>0?orderAd:null;
	}

	private String getOrderNo(){
		StringBuffer buffer = new StringBuffer("AD");
		String number = String.valueOf((Math.random()*9+1)*100000);
		if(number.contains(".")){
			number=number.substring(0,number.indexOf("."));
		}
		buffer.append(DateUtil.getLockNowTime()).append(number);
		return buffer.toString();
	}

}
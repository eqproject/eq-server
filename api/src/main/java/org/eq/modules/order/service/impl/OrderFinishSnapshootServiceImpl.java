/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.OrderTradeFinishVO;
import org.eq.modules.trade.vo.OrderTradeListReqVO;
import org.eq.modules.trade.vo.OrderTradeTradingVO;
import org.eq.modules.utils.OrderUtil;
import org.eq.modules.order.service.OrderFinishSnapshootService;
import org.eq.modules.order.vo.*;
import org.eq.modules.orderfinish.dao.OrderFinishSnapshootMapper;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshoot;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshootExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class OrderFinishSnapshootServiceImpl extends ServiceImplExtend<OrderFinishSnapshootMapper, OrderFinishSnapshoot, OrderFinishSnapshootExample> implements OrderFinishSnapshootService {

	@Autowired
	private OrderAdService orderAdService;


	@Autowired
	private OrderTradeService orderTradeService;

	@Override
	public OrderFinishSnapshootExample getExampleFromEntity(OrderFinishSnapshoot orderFinishSnapshoot, Map<String, Object> params) {
		OrderFinishSnapshootExample example = new OrderFinishSnapshootExample();
		OrderFinishSnapshootExample.Criteria ca = example.or();
		if(orderFinishSnapshoot==null){
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
	 * @param userId
	 * @return
	 */
	public OrderFinishSnapshootExample getExampleFromEntity(long userId) {
		OrderFinishSnapshootExample example = new OrderFinishSnapshootExample();
		OrderFinishSnapshootExample.Criteria ca = example.or();
	    ca.andAllUserIdEqualToForAll(userId);
		example.setOrderByClause("OFS.create_date desc");
		return example;
	}



	@Override
	public PageResultData<OrderFinishSnapshootSimpleVO> pageFinishPlatOrder(SearchPageOrderFinishVO searchPageOrderFinishVO,User user) {
		PageResultData<OrderFinishSnapshootSimpleVO> result = new PageResultData<>();
		if(user==null){
			return result;
		}
		if(searchPageOrderFinishVO ==null){
			searchPageOrderFinishVO = new SearchPageOrderFinishVO();
		}
		List<OrderFinishSnapshootSimpleVO> dataList = new ArrayList<>();
		//订单
		if(searchPageOrderFinishVO.getOrderType()==1){
			SearchPageAdOrderVO searchPageAdOrderVO = new SearchPageAdOrderVO();
			searchPageAdOrderVO.setUserId(user.getId());
			searchPageAdOrderVO.setPageNum(searchPageOrderFinishVO.getPageNum());
			searchPageAdOrderVO.setPageSize(searchPageOrderFinishVO.getPageSize());
			PageResultData<OrderAdSimpleVO>  orderPage =orderAdService.pagePlatOrderAd(searchPageAdOrderVO,user);
			if(orderPage==null || CollectionUtils.isEmpty(orderPage.getList())){
				return result;
			}
			List<OrderAdSimpleVO> pList = orderPage.getList();
			for(OrderAdSimpleVO p : pList){
				dataList.add(OrderUtil.transFinishedForOrderAd(p,user));
			}
			result.setList(dataList);
			result.setTotal(orderPage.getTotal());
			return result;

		}else{
			OrderTradeListReqVO orderTradeListReqVO = new OrderTradeListReqVO();
			orderTradeListReqVO.setUserId(user.getId());
			orderTradeListReqVO.setPageNum(searchPageOrderFinishVO.getPageNum());
			orderTradeListReqVO.setPageSize(searchPageOrderFinishVO.getPageSize());
			PageResultData<OrderTradeFinishVO> tradePage = orderTradeService.pageFinishedOrderList(orderTradeListReqVO);
			if(tradePage==null || CollectionUtils.isEmpty(tradePage.getList())){
				return result;
			}
			List<OrderTradeFinishVO> pList = tradePage.getList();
			for(OrderTradeFinishVO p : pList){
				dataList.add(OrderUtil.transFinishOrderForTrade(p,user));
			}
			result.setList(dataList);
			result.setTotal(tradePage.getTotal());
			return result;
		}
	}




}
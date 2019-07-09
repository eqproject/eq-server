/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.service.OrderAdService;
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

		//订单
		if(searchPageOrderFinishVO.getOrderType()==1){
			SearchPageAdOrderVO searchPageAdOrderVO = new SearchPageAdOrderVO();
			searchPageAdOrderVO.setUserId(user.getId());
			searchPageAdOrderVO.setPageNum(searchPageOrderFinishVO.getPageNum());
			searchPageAdOrderVO.setPageSize(searchPageOrderFinishVO.getPageSize());
			PageResultData<OrderAdSimpleVO> orderList =orderAdService.pagePlatOrderAd(searchPageAdOrderVO,user);
		}


		if(searchPageOrderFinishVO.getPageSize()<=0 || searchPageOrderFinishVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageOrderFinishVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageOrderFinishVO.getPageNum()<=0){
			searchPageOrderFinishVO.setPageNum(1);
		}

		OrderFinishSnapshootExample orderFinishSnapshootExample =getExampleFromEntity(user.getId());

		BaseTableData baseTableData = findDataListByExampleForPage(orderFinishSnapshootExample, searchPageOrderFinishVO.getPageNum(), searchPageOrderFinishVO.getPageSize());
		if(baseTableData==null){
			return result;
		}
		List<OrderFinishSnapshootSimpleVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderFinishSnapshoot> pList = baseTableData.getData();
		for(OrderFinishSnapshoot p : pList){
			dataList.add(OrderUtil.transObjForSimple(p,user));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}




}
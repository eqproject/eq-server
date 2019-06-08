/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 用户商品管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Transactional
@AutowiredService
@Service
@SuppressWarnings("all")
public class UserProductStockServiceImpl extends ServiceImplExtend<UserProductStockMapper, UserProductStock, UserProductStockExample> implements UserProductStockService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private ProductService productService;

	@Override
	public UserProductStockExample getExampleFromEntity(UserProductStock userProductStock, Map<String, Object> params) {
		UserProductStockExample example = new UserProductStockExample();
		UserProductStockExample.Criteria ca = example.or();
		if(userProductStock==null){
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
		if(userProductStock.getId()!=null){
			ca.andIdEqualTo(userProductStock.getId());
		}
		if(userProductStock.getUserId()!=null){
			ca.andUserIdEqualTo(userProductStock.getUserId());
		}
		if(userProductStock.getProductId()!=null){
			ca.andProductIdEqualTo(userProductStock.getProductId());
		}
		return example;
	}


	@Override
	public boolean updateStock(long productId, long userId, int number) {
		UserProductStock userProductStock = new UserProductStock();
		userProductStock.setUserId(userId);
		userProductStock.setProductId(productId);
		userProductStock = selectByRecord(userProductStock);
		if(userProductStock==null){
			return false;
		}
		int oldNum = userProductStock.getLockedNum();

		UserProductStock update = new UserProductStock();
		update.setLockedNum(userProductStock.getLockedNum()+number);
		update.setUpdateDate(new Date());
		int updateResult = updateByExampleSelective(update,getStockNumExample(userProductStock.getId(),oldNum));
		if(updateResult>0){
			return true;
		}
		return false;
	}


	private  UserProductStockExample  getStockNumExample(long id,long stock){
		UserProductStockExample example = new UserProductStockExample();
		UserProductStockExample.Criteria ca = example.or();
		if(id<=0){
			return example;
		}
		ca.andIdEqualToForSimple(id);
		ca.andLockNumForSimple(stock);
		return example;
	}
}
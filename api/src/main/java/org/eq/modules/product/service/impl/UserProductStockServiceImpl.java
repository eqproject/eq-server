/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.ProductUtil;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.product.service.ProductBlockchainService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.eq.modules.product.vo.TicketProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户商品管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class UserProductStockServiceImpl extends ServiceImplExtend<UserProductStockMapper, UserProductStock, UserProductStockExample> implements UserProductStockService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	public UserProductStockServiceImpl(UserProductStockMapper mapper){
		super.setMapper(mapper);
	}

	@Autowired
	ProductBlockchainService productBlockchainService;

	@Autowired
	private ProductCache productCache;

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
	public PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO, User user) {
		PageResultData<ProductBaseVO> result = new PageResultData<>();
		if(user ==null || StringUtils.isEmpty(user.getTxPassword())){
			return result;
		}
		Map<String,TicketProductVO> tickMap = ProductUtil.getTicketUserProduct(user.getTxPassword());
		if(tickMap ==null || tickMap.size()<=0){
			return result;
		}
		if(searchPageProductVO ==null){
			searchPageProductVO = new SearchPageProductVO();
		}
		if(searchPageProductVO.getPageSize()<=0 || searchPageProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageProductVO.getPageNum()<=0){
			searchPageProductVO.setPageNum(1);
		}
		//组装可返回实体列表
		List<ProductBaseVO> allReult = new ArrayList<>();
		Iterator<String> ite = tickMap.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			TicketProductVO ticketProductVO = tickMap.get(key);
			String productId = productCache.getProductIdByTicketKey(key);
			if(StringUtils.isEmpty(productId)){
				continue;
			}
			Product product = productService.selectByPrimaryKey(Long.valueOf(productId));
			if(!ProductUtil.isEffect(product)){
				continue;
			}
			int number = Integer.valueOf(ticketProductVO.getBalance()) - getLockedStockNum(product.getId(),user.getId()); ;
			if(number<=0){
				continue;
			}

			ProductBaseVO productBaseVO = ProductUtil.transObj(product);
			productBaseVO.setNumber(number);
			allReult.add(productBaseVO);
		}
		Collections.sort(allReult,new ProductBaseVO());
		List<ProductBaseVO> dataList = pageBySubList(allReult,searchPageProductVO.getPageSize(),searchPageProductVO.getPageNum());
		result.setList(dataList);
		result.setTotal(allReult.size());
		return result;
	}

	/**
	 * 根据商品信息和用户信息获取用户库存数据
	 * @param productId
	 * @param userId
	 * @return
	 */
	private int getLockedStockNum(long productId, long userId){
		if(productId<=0 || userId<=0 ){
			return 0;
		}
		UserProductStock userProductStock = new UserProductStock();
		userProductStock.setUserId(userId);
		userProductStock.setProductId(productId);
		userProductStock = selectByRecord(userProductStock);
		if(userProductStock!=null){
			return userProductStock.getLockedNum();
		}
		userProductStock = new UserProductStock();
		userProductStock.setProductId(productId);
		userProductStock.setUserId(userId);
		userProductStock.setCreateDate(new Date());
		userProductStock.setLockedNum(0);
		userProductStock.setUpdateDate(userProductStock.getCreateDate());
		int result = insertSelective(userProductStock);
		System.out.println(result+"====");
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(userProductStock);
			retryNum--;
		}
		return 0;
	}

	private static List<ProductBaseVO> pageBySubList(List<ProductBaseVO> list, int pagesize, int currentPage) {
		int totalcount = list.size();
		int pagecount = 0;
		List<ProductBaseVO> subList = new ArrayList<>();
		int m = totalcount % pagesize;
		if (m > 0) {
			pagecount = totalcount / pagesize + 1;
		} else {
			pagecount = totalcount / pagesize;
		}
		int start = (currentPage - 1) * pagesize;
		if(start>totalcount){
			return subList;
		}
		int end = pagesize * (currentPage);
		if(m!=0 && currentPage == pagecount){
			end = totalcount;
		}
		if(end>totalcount){
			end = totalcount;
		}
		return  list.subList(start,end );
	}
}
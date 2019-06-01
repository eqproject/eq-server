/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.ProductUtil;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductBlockchain;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.product.service.ProductBlockchainService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.eq.modules.product.vo.TicketProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public PageResultData<ProductVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO,User user) {
		PageResultData<ProductVO> result = new PageResultData<>();
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
		BaseTableData baseTableData = findDataTableByExampleForPage(ProductUtil.getUserBaseEffectExample(), searchPageProductVO.getPageNum(), searchPageProductVO.getPageSize());
		if(baseTableData==null || CollectionUtils.isEmpty(baseTableData.getData())){
			return result;
		}
		List<ProductVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<UserProductStock> pList = baseTableData.getData();

		for(UserProductStock p : pList){
			//ProductAll productAll = productCache.getProduct(String.valueOf(p.getProductId()));
			Product product = productService.selectByPrimaryKey(p.getProductId());
			if(!ProductUtil.isEffect(product)){
				continue;
			}
			ProductBlockchain productBlockchain = productBlockchainService.getBuyProductId(p.getId());
			if(productBlockchain==null){
				continue;
			}
			StringBuilder ticketKey = new StringBuilder().append(productBlockchain.getTicketid()).append("_").append(productBlockchain.getTrancheid());
			TicketProductVO ticketProductVO = tickMap.get(ticketKey.toString());
			if(ticketProductVO==null){
				continue;
			}
			int number = Integer.valueOf(ticketProductVO.getBalance());
			if((number-p.getLockedNum())<=0){
				continue;
			}
			dataList.add(ProductUtil.transObj(product));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}
}
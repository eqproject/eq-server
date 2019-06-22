/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.product.dao.ProductBlockchainMapper;
import org.eq.modules.product.entity.ProductBlockchain;
import org.eq.modules.product.entity.ProductBlockchainExample;
import org.eq.modules.product.service.ProductBlockchainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * productBlockServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class ProductBlockchainServiceImpl extends ServiceImplExtend<ProductBlockchainMapper, ProductBlockchain, ProductBlockchainExample> implements ProductBlockchainService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ProductBlockchainExample getExampleFromEntity(ProductBlockchain productBlockchain, Map<String, Object> params) {
		ProductBlockchainExample example = new ProductBlockchainExample();
		ProductBlockchainExample.Criteria ca = example.or();
		if(productBlockchain==null){
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
			example.setOrderByClause("product_id asc");
		}
		if(productBlockchain.getProductId()!=null){
			ca.andProductIdEqualTo(productBlockchain.getProductId());
		}
		return example;
	}

	@Override
	public ProductBlockchain getBuyProductId(long productId) {
		ProductBlockchain productBlockchain = new ProductBlockchain();
		productBlockchain.setProductId(productId);
		ProductBlockchain result =  selectByRecord(productBlockchain);
		return result;
	}
}
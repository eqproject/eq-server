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
			example.setOrderByClause("id asc");
		}
		if(productBlockchain.getProductId()!=null){
			ca.andProductIdEqualTo(productBlockchain.getProductId());
		}
		if(StringLowUtils.isNotBlank(productBlockchain.getAssetCode())){
			ca.andAssetCodeEqualTo(productBlockchain.getAssetCode());
		}
		if(StringLowUtils.isNotBlank(productBlockchain.getAssetIssuer())){
			ca.andAssetIssuerEqualTo(productBlockchain.getAssetIssuer());
		}
		if(StringLowUtils.isNotBlank(productBlockchain.getContractAddress())){
			ca.andContractAddressEqualTo(productBlockchain.getContractAddress());
		}
		if(StringLowUtils.isNotBlank(productBlockchain.getTicketid())){
			ca.andTicketidEqualTo(productBlockchain.getTicketid());
		}
		if(StringLowUtils.isNotBlank(productBlockchain.getTrancheid())){
			ca.andTrancheidEqualTo(productBlockchain.getTrancheid());
		}
		if(productBlockchain.getCreateDate()!=null){
			ca.andCreateDateEqualTo(productBlockchain.getCreateDate());
		}
		if(productBlockchain.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(productBlockchain.getUpdateDate());
		}
		return example;
	}

}
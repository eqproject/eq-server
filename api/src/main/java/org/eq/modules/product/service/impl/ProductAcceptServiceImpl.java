/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.product.dao.ProductAcceptMapper;
import org.eq.modules.product.dao.ProductBlockchainMapper;
import org.eq.modules.product.entity.ProductAccept;
import org.eq.modules.product.entity.ProductAcceptExample;
import org.eq.modules.product.service.ProductAcceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 商品承兑ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class ProductAcceptServiceImpl extends ServiceImplExtend<ProductAcceptMapper, ProductAccept, ProductAcceptExample> implements ProductAcceptService {


	@Autowired
	public ProductAcceptServiceImpl(ProductAcceptMapper productAcceptMapper){
		super.setMapper(productAcceptMapper);
	}


	@Override
	public ProductAcceptExample getExampleFromEntity(ProductAccept productAccept, Map<String, Object> params) {
		ProductAcceptExample example = new ProductAcceptExample();
		ProductAcceptExample.Criteria ca = example.or();
		if(productAccept==null){
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
		if(productAccept.getId()!=null){
			ca.andIdEqualTo(productAccept.getId());
		}
		if(StringLowUtils.isNotBlank(productAccept.getName())){
			ca.andNameEqualTo(productAccept.getName());
		}
		if(StringLowUtils.isNotBlank(productAccept.getIcon())){
			ca.andIconEqualTo(productAccept.getIcon());
		}
		if(StringLowUtils.isNotBlank(productAccept.getAddress())){
			ca.andAddressEqualTo(productAccept.getAddress());
		}
		if(StringLowUtils.isNotBlank(productAccept.getIntro())){
			ca.andIntroEqualTo(productAccept.getIntro());
		}
		if(productAccept.getCreateDate()!=null){
			ca.andCreateDateEqualTo(productAccept.getCreateDate());
		}
		if(productAccept.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(productAccept.getUpdateDate());
		}
		return example;
	}

}
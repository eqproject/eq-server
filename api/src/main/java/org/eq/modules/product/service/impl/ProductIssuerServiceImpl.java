/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;


import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.product.dao.ProductIssuerMapper;
import org.eq.modules.product.entity.ProductIssuer;
import org.eq.modules.product.entity.ProductIssuerExample;
import org.eq.modules.product.service.ProductIssuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 品牌管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class ProductIssuerServiceImpl extends ServiceImplExtend<ProductIssuerMapper, ProductIssuer, ProductIssuerExample> implements ProductIssuerService {

	@Autowired
	public ProductIssuerServiceImpl(ProductIssuerMapper productIssuerMapper){
		super.setMapper(productIssuerMapper);
	}



	@Override
	public ProductIssuerExample getExampleFromEntity(ProductIssuer productIssuer, Map<String, Object> params) {
		ProductIssuerExample example = new ProductIssuerExample();
		ProductIssuerExample.Criteria ca = example.or();
		if(productIssuer==null){
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
		if(productIssuer.getId()!=null){
			ca.andIdEqualTo(productIssuer.getId());
		}
		if(StringLowUtils.isNotBlank(productIssuer.getName())){
			ca.andNameEqualTo(productIssuer.getName());
		}
		if(StringLowUtils.isNotBlank(productIssuer.getIcon())){
			ca.andIconEqualTo(productIssuer.getIcon());
		}
		if(StringLowUtils.isNotBlank(productIssuer.getAddress())){
			ca.andAddressEqualTo(productIssuer.getAddress());
		}
		if(StringLowUtils.isNotBlank(productIssuer.getIntro())){
			ca.andIntroEqualTo(productIssuer.getIntro());
		}
		if(productIssuer.getCreateDate()!=null){
			ca.andCreateDateEqualTo(productIssuer.getCreateDate());
		}
		if(productIssuer.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(productIssuer.getUpdateDate());
		}
		return example;
	}

}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.service.impl;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.c2c.product.dao.ProductRelationTagMapper;
import cn.bubi.c2c.product.entity.ProductRelationTag;
import cn.bubi.c2c.product.entity.ProductRelationTagExample;
import cn.bubi.c2c.product.service.ProductRelationTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 商品标签对应关系ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class ProductRelationTagServiceImpl extends ServiceImplExtend<ProductRelationTagMapper, ProductRelationTag, ProductRelationTagExample> implements ProductRelationTagService {

	@Override
	public ProductRelationTagExample getExampleFromEntity(ProductRelationTag productRelationTag, Map<String, Object> params) {
		ProductRelationTagExample example = new ProductRelationTagExample();
		ProductRelationTagExample.Criteria ca = example.or();
		if(productRelationTag==null){
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
		if(productRelationTag.getTagId()!=null){
			ca.andTagIdEqualTo(productRelationTag.getTagId());
		}
		if(productRelationTag.getProductId()!=null){
			ca.andProductIdEqualTo(productRelationTag.getProductId());
		}
		return example;
	}

}
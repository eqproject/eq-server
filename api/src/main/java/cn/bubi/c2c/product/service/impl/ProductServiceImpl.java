/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.service.impl;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.c2c.product.dao.ProductMapper;
import cn.bubi.c2c.product.entity.Product;
import cn.bubi.c2c.product.entity.ProductExample;
import cn.bubi.c2c.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品信息ServiceImpl
 * @author kaka
 * @version 2019.05.08
 */
@Service
@Transactional
@AutowiredService
public class ProductServiceImpl extends ServiceImplExtend<ProductMapper, Product, ProductExample> implements ProductService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public ProductExample getExampleFromEntity(Product product, Map<String, Object> params) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria ca = example.or();
		if(product==null){
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
		/**
		 * 商品名称
		 */
		if(StringUtils.isNotBlank(product.getName())){
			ca.andNameLike("%"+product.getName()+"%");
		}
		/**
		 * 商品编号
		 */
		if(StringUtils.isNotBlank(product.getCode())){
			ca.andCodeEqualTo(product.getCode());
		}
		/**
		 * 商品状态
		 */
		if(product.getStatus()!=null){
			ca.andStatusEqualTo(product.getStatus());
		}

		return example;
	}

	@Override
	public Product getProductBetweenScore(int score, boolean isup) {

		ProductExample example = new ProductExample();

		ProductExample.Criteria ca = example.or();

		if(isup){
			example.setOrderByClause("sort asc ");
			ca.andSortGreaterThan(score);
		}else{
			example.setOrderByClause("sort desc ");
			ca.andSortLessThan(score);
		}
		List<Product> list = this.findListByExample(example);
		if (list != null && !( list.isEmpty())) {
			return list.get(0);
		}
		return null;
	}
}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.utils.WebClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品信息ServiceImpl
 * @author kaka
 * @version 2019.05.08
 */
@SuppressWarnings("all")
@Service
@Transactional
@AutowiredService
public class ProductServiceImpl extends ServiceImplExtend<ProductMapper, Product, ProductExample> implements ProductService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private ProductMapper productMapper;


	@Autowired
	private OrderAdService orderAdService;


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
		 * 商品ID
		 */
		if(product.getId()!=null && product.getId()>0 ){
			ca.andIdEqualTo(product.getId());
		}

		/**
		 * 商品名称
		 */
		if(StringUtils.isNotBlank(product.getName())){
			ca.andNameLike("%"+product.getName()+"%");
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

	@Override
	public ProductAll getProductAll(long productId) {
		ProductExample example = new ProductExample();
		ProductExample.Criteria ca = example.or();
		example.setOrderByClause(" sort desc ");
		ca.andIdAllEqualTo(productId);
		List<ProductAll> productList = productMapper.selectProductAllByExample(example);
		if(CollectionUtils.isEmpty(productList)){
			return null;
		}
		return productList.get(0);
	}


	@Override
	public int updateProductState(int newState,int oldState,long productId){
		if(productId<=0){
			return 0;
		}
		Product product = selectByPrimaryKey(productId);
		if(product ==null){
			return 0;
		}
		if(product.getStatus() == newState){
			return 1;
		}
		if(ProductStateEnum.isOverState(oldState)){
			return 0;
		}
		Product whereProduct = new Product();
		whereProduct.setStatus(oldState);
		whereProduct.setId(product.getId());
		ProductExample example = getExampleFromEntity(whereProduct,null);

		Product updateProduct = new Product();
		updateProduct.setStatus(newState);
		updateProduct.setUpdateDate(new Date());

		int upresult = updateByExampleSelective(updateProduct,example);
		if(upresult<=0){
			return 0;
		}
		if(oldState != ProductStateEnum.ONLINE.getState()){
			return 1;
		}
		if(ProductStateEnum.isOverState(newState)){
			try{
				orderAdService.offOrderByProductId(product.getId());
			}catch (Exception e){
				e.printStackTrace();
				logger.error("取消商品下的订单异常");
			}
		}
		return 1;

	}

	/**
	 * 同步商品信息
	 * TODO 区块链对接
	 * @return
	 */
	@Override
	public int loadProduct(){
		WebClientUtil webClientUtil = new WebClientUtil();
		try{
			webClientUtil.asynchronousPost("",null);
		}catch (Exception e){
			e.printStackTrace();
		}

		return 0;
	}

}
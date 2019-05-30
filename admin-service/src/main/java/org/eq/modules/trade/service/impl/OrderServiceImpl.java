/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.trade.dao.OrderMapper;
import org.eq.modules.trade.entity.Order;
import org.eq.modules.trade.entity.OrderExample;
import org.eq.modules.trade.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 交易订单管理ServiceImpl
 * @author yufei.sun
 * @version 1.0.0
 */
@Service
@Transactional
@AutowiredService
public class OrderServiceImpl extends ServiceImplExtend<OrderMapper, Order, OrderExample> implements OrderService {

	@Autowired
	private ProductService productService;

	@Override
	public OrderExample getExampleFromEntity(Order order, Map<String, Object> params) {
		OrderExample example = new OrderExample();
		OrderExample.Criteria ca = example.or();
		if(order==null){
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
		if(order.getAmount()!=null){
			ca.andAmountEqualTo(order.getAmount());
		}
		if(StringLowUtils.isNotBlank(order.getCode())){
			ca.andCodeEqualTo(order.getCode());
		}
		if(order.getCreateBy()!=null){
			ca.andCreateByEqualTo(order.getCreateBy());
		}

//		if(order.getCreateDate()!=null){
//			ca.andCreateDateEqualTo(order.getCreateDate());
//		}

		setProductQueryContion(ca,order);

		if(StringLowUtils.isNotBlank(order.getDescription())){
			ca.andDescriptionEqualTo(order.getDescription());
		}
		if(order.getId()!=null){
			ca.andIdEqualTo(order.getId());
		}
		if(order.getPayType()!=null){
			ca.andPayTypeEqualTo(order.getPayType());
		}
		if(order.getProductId()!=null){
			ca.andProductIdEqualTo(order.getProductId());
		}
		if(order.getProductNum()!=null){
			ca.andProductNumEqualTo(order.getProductNum());
		}
		if(StringLowUtils.isNotBlank(order.getRemarks())){
			ca.andRemarksEqualTo(order.getRemarks());
		}
		if(order.getStatus()!=null){
			ca.andStatusEqualTo(order.getStatus());
		}
		if(order.getType()!=null){
			ca.andTypeEqualTo(order.getType());
		}
		if(order.getUpdateBy()!=null){
			ca.andUpdateByEqualTo(order.getUpdateBy());
		}
		if(order.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(order.getUpdateDate());
		}
		return example;
	}

	/**
	 * 设置按商品名称或商品编码的查询条件
	 * @param ca
	 * @param order
	 */
	private void setProductQueryContion(OrderExample.Criteria ca,Order order) {
		List<Long> productIdList =new LinkedList<>();
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteriaProduct = productExample.or();
		if (StringUtils.isNotBlank(order.getProductName()) || StringUtils.isNotBlank(order.getProductCode())) {
			productIdList.add(Long.valueOf(-1));
			if (StringUtils.isNotBlank(order.getProductName())) {
				criteriaProduct.andProductImgNotLike(order.getProductName());
			}
			if (StringUtils.isNotBlank(order.getProductCode())) {
				criteriaProduct.andCodeEqualTo(order.getProductCode());
			}
			List<Product> productList = productService.findListByExample(productExample);
			if (!CollectionUtils.isEmpty(productList)) {
				for (Product product : productList) {
					productIdList.add(product.getId());
				}
			}
			if (productIdList.size()>1) {
				ca.andProductIdIn(productIdList);
			} else {
				ca.andProductIdEqualTo(productIdList.get(0));
			}
		}
	}

}
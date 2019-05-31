/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.common.entitys.PageResultBase;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.DateUtil;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	public ProductServiceImpl(ProductMapper mapper){
		super.setMapper(mapper);
	}

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
		 * 商品状态
		 */
		if(product.getStatus()!=null){
			ca.andStatusEqualTo(product.getStatus());
		}
		return example;
	}

	@Override
	public PageResultBase<ProductVO> pageSimpeProduct(SearchProductVO searchProductVO) {
		PageResultBase<ProductVO> result = new PageResultBase<>();
		if(searchProductVO ==null){
			searchProductVO = new SearchProductVO();
		}
		if(searchProductVO.getPageSize()<0 || searchProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchProductVO.getPageNum()<=0){
			searchProductVO.setPageNum(1);
		}
		BaseTableData baseTableData = findDataTableByExampleForPage(getExampleFromEntity(null,null),1,100);
		/*PageInfo pageInfo = this.findListByExampleForPage(getBaseEffectExample(),searchProductVO.getPageNum(),searchProductVO.getPageSize());
		result.setData(pageInfo.getList());
		result.setRecordsFiltered(pageInfo.getTotal());
		result.setRecordsTotal(pageInfo.getTotal());*/
		return null;
	}


	/**
	 * 获取基本有效查询条件
	 * @return
	 */
	private ProductExample getBaseEffectExample() {
		ProductExample example = new ProductExample();
		ProductExample.Criteria ca = example.or();
		example.setOrderByClause(" sort desc ");
		ca.andStatusEqualTo(ProductStateEnum.ONLINE.getState());
		ca.andExpirationEndGreaterThan(DateUtil.getNowTimeStr());
		return example;
	}
}
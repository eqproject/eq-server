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
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.DateUtil;
import org.eq.modules.common.utils.ProductUtil;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.eq.modules.product.vo.SearchProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

	@Autowired
	public ProductServiceImpl(ProductMapper mapper){
		super.setMapper(mapper);
	}

	@Autowired
	private ProductMapper productMapper;

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
	public PageResultData<ProductVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO) {
		PageResultData<ProductVO> result = new PageResultData<>();
		if(searchPageProductVO ==null){
			searchPageProductVO = new SearchPageProductVO();
		}
		if(searchPageProductVO.getPageSize()<=0 || searchPageProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageProductVO.getPageNum()<=0){
			searchPageProductVO.setPageNum(1);
		}
		BaseTableData baseTableData = findDataTableByExampleForPage(ProductUtil.getBaseEffectExample(), searchPageProductVO.getPageNum(), searchPageProductVO.getPageSize());
		if(baseTableData==null || CollectionUtils.isEmpty(baseTableData.getData())){
			return result;
		}
		List<ProductVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<Product> pList = baseTableData.getData();
		for(Product p : pList){
			dataList.add(ProductUtil.transObj(p));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}

	@Override
	public List<ProductAll> listProductAll(SearchProductVO searchProductVO) {
		if(searchProductVO ==null){
			searchProductVO = new SearchProductVO();
		}
		ProductExample productExample = ProductUtil.createPlatformSearchExample(searchProductVO);
		List<ProductAll> result = productMapper.selectProductAllByExample(productExample);
		if(CollectionUtils.isEmpty(result)){
			return new ArrayList<ProductAll>();
		}
		return  result;
	}






}
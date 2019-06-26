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
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.product.service.ProductLoadService;
import org.eq.modules.utils.ProductUtil;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
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
	private ProductCache productCache;

	@Autowired
	private UserProductStockService userProductStockService;

	@Autowired
	private ProductLoadService productLoadService;


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
	public PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO) {
		PageResultData<ProductBaseVO> result = new PageResultData<>();
		if(searchPageProductVO ==null){
			searchPageProductVO = new SearchPageProductVO();
		}
		if(searchPageProductVO.getPageSize()<=0 || searchPageProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageProductVO.getPageNum()<=0){
			searchPageProductVO.setPageNum(1);
		}
		BaseTableData baseTableData = findDataListByExampleForPage(ProductUtil.getBaseEffectExample(), searchPageProductVO.getPageNum(), searchPageProductVO.getPageSize());
		if(baseTableData==null || CollectionUtils.isEmpty(baseTableData.getData())){
			return result;
		}
		List<ProductBaseVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<Product> pList = baseTableData.getData();
		for(Product p : pList){
			dataList.add(ProductUtil.transObj(p));
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}

	@Override
	public List<ProductAll> listProductAll(BSearchProduct searchBSearchProduct) {
		if(searchBSearchProduct ==null){
			searchBSearchProduct = new BSearchProduct();
		}
		ProductExample productExample = ProductUtil.createPlatformSearchExample(searchBSearchProduct,true);
		List<ProductAll> result = productMapper.selectProductAllByExample(productExample);
		if(CollectionUtils.isEmpty(result)){
			return new ArrayList<ProductAll>();
		}
		return  result;
	}

    @Override
    public ProductDetailVO getProductAll(BSearchProduct bsearchProduct) {
		if(bsearchProduct ==null){
			bsearchProduct = new BSearchProduct();
		}
		ProductExample productExample = ProductUtil.createPlatformSearchExample(bsearchProduct,true);
		List<ProductAll> productList = productMapper.selectProductAllByExample(productExample);
		if(CollectionUtils.isEmpty(productList)){
			return null;
		}
		return ProductUtil.transObjTOProductDetail(productList.get(0));
    }

	@Override
	public ServieReturn<UserProductDetailVO> getUserProductAll(BSearchProduct bsearchProduct, User user) {
		ServieReturn<UserProductDetailVO> reult = new ServieReturn<>();
		if(user==null){
			reult.setErrMsg("用户为空");
			return reult;
		}
		if(bsearchProduct ==null ){
			bsearchProduct = new BSearchProduct();
		}
		ProductExample productExample = ProductUtil.createPlatformSearchExample(bsearchProduct,true);
		List<ProductAll> productList = productMapper.selectProductAllByExample(productExample);
		if(CollectionUtils.isEmpty(productList)){
			reult.setErrMsg("商品不存在或者已下架");
			return reult;
		}
		ProductAll  productAll = productList.get(0);
		UserProductStock userProductStock = userProductStockService.getUserProductStock(productAll.getId(),user);
		if(userProductStock==null){
			reult.setErrMsg("用户无此商品");
			return reult;
		}
		productAll.setNumber(userProductStock.getStockNum()+userProductStock.getLockedNum());
		productAll.setLockNumber(userProductStock.getLockedNum());
		UserProductDetailVO userProductDetailVO = ProductUtil.transObjTOUserProductDetail(productList.get(0));
		reult.setData(userProductDetailVO);
		return reult ;
	}


	@Override
	public ServieReturn<UserProductDetailVO> getUserProductNoHold(BSearchProduct bsearchProduct, User user) {
		ServieReturn<UserProductDetailVO> result = new ServieReturn();
		if(user==null){
			result.setErrMsg("用户为空");
			return result;
		}
		if(bsearchProduct.getProductId()<=0){
			result.setErrMsg("商品ID为空");
			return result;
		}
		if(bsearchProduct ==null ){
			bsearchProduct = new BSearchProduct();
		}
		ProductExample productExample = ProductUtil.createNoEffectformSearchExample(bsearchProduct,true);
		List<ProductAll> productList = productMapper.selectProductAllByExample(productExample);
		if(CollectionUtils.isEmpty(productList)){
			result.setErrMsg("商品不存在");
			return result;
		}
		ProductAll  productAll = productList.get(0);
		boolean ishave = false;
		Map<String, TicketProductVO> ticketMap = productLoadService.getTicketUserProduct(user.getTxPassword());
		if(ticketMap!=null && ticketMap.size()>0) {
			Iterator<String> ite = ticketMap.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				TicketProductVO ticketProductVO = ticketMap.get(key);
				String productId = productCache.getProductIdByTicketKey(key);
				if(productId.equals(String.valueOf(bsearchProduct.getProductId()))){
					continue;
				}
				ishave = true;
			}
		}
		if(!ishave){
			result.setErrMsg("该用户无此券");
			return result;
		}
		if(ProductUtil.isEffect(productAll)){
			result.setErrMsg("此商品有效");
			return result;
		}
		productAll.setNumber(0);
		productAll.setLockNumber(0);
		UserProductDetailVO userProductDetailVO = ProductUtil.transObjTOUserProductDetail(productAll);
		result.setData(userProductDetailVO);
		return result;


	}


}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.product.service.ProductLoadService;
import org.eq.modules.utils.PageUtils;
import org.eq.modules.utils.ProductUtil;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.product.service.ProductBlockchainService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.eq.modules.product.vo.TicketProductVO;
import org.eq.modules.product.vo.VoucherProductBaseVO;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 用户商品管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Transactional
@AutowiredService
@Service
public class UserProductStockServiceImpl extends ServiceImplExtend<UserProductStockMapper, UserProductStock, UserProductStockExample> implements UserProductStockService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductBlockchainService productBlockchainService;

	@Autowired
	private ProductCache productCache;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductLoadService productLoadService;

	/**
	 * 用户钱包
	 */
	@Autowired
	private UserWalletService userWalletService;

	@Override
	public UserProductStockExample getExampleFromEntity(UserProductStock userProductStock, Map<String, Object> params) {
		UserProductStockExample example = new UserProductStockExample();
		UserProductStockExample.Criteria ca = example.or();
		if(userProductStock==null){
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
		if(userProductStock.getId()!=null){
			ca.andIdEqualTo(userProductStock.getId());
		}
		if(userProductStock.getUserId()!=null){
			ca.andUserIdEqualTo(userProductStock.getUserId());
		}
		if(userProductStock.getProductId()!=null){
			ca.andProductIdEqualTo(userProductStock.getProductId());
		}
		return example;
	}

	@Override
	public PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO, User user) {
		PageResultData<ProductBaseVO> result = new PageResultData<>();
		if(user ==null){
			return result;
		}
		UserWallet userWallet = userWalletService.selectByPrimaryKey(user.getId());
		if(userWallet ==null || userWallet.getStatus()!= WalletStateEnum.ACTIVE.getState() || StringUtils.isEmpty(userWallet.getAddress())){
			return result;
		}
		Map<String,TicketProductVO> tickMap = productLoadService.getTicketUserProduct(userWallet.getAddress());
		if(tickMap ==null || tickMap.size()<=0){
			return result;
		}
		if(searchPageProductVO ==null){
			searchPageProductVO = new SearchPageProductVO();
		}
		if(searchPageProductVO.getPageSize()<=0 || searchPageProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageProductVO.getPageNum()<=0){
			searchPageProductVO.setPageNum(1);
		}
		//组装可返回实体列表
		List<ProductBaseVO> allReult = new ArrayList<>();
		Iterator<String> ite = tickMap.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			TicketProductVO ticketProductVO = tickMap.get(key);
			String productId = productCache.getProductIdByTicketKey(key);
			if(StringUtils.isEmpty(productId)){
				continue;
			}
			Product product = productService.selectByPrimaryKey(Long.valueOf(productId));
			if(!ProductUtil.isEffect(product)){
				continue;
			}
			int number = Integer.valueOf(ticketProductVO.getBalance()) - getLockedStockNum(product.getId(),user.getId()); ;
			if(number<=0){
				continue;
			}

			ProductBaseVO productBaseVO = ProductUtil.transObj(product);
			productBaseVO.setNumber(number);
			allReult.add(productBaseVO);
		}
		Collections.sort(allReult,new ProductBaseVO());
		List<ProductBaseVO> dataList = PageUtils.pageBySubList(allReult,searchPageProductVO.getPageSize(),searchPageProductVO.getPageNum());
		result.setList(dataList);
		result.setTotal(allReult.size());
		return result;
	}

	@Override
	public UserProductStock getUserProductStock(long productId, User user) {
		if(productId<=0 || user==null || user.getId()<=0){
			return null;
		}
		Product product = productService.selectByPrimaryKey(productId);
		if(!ProductUtil.isEffect(product)){
			return null;
		}
		String ticketKey = productCache.getTicketKeyByProductId(productId);
		if(StringUtils.isEmpty(ticketKey)){
			return null;
		}
		UserWallet userWallet = userWalletService.selectByPrimaryKey(user.getId());
		if(userWallet ==null || userWallet.getStatus()!= WalletStateEnum.ACTIVE.getState() || StringUtils.isEmpty(userWallet.getAddress())){
			return null;
		}
		Map<String,TicketProductVO> ticketMap = productLoadService.getTicketUserProduct(userWallet.getAddress());
		if(ticketMap ==null || ticketMap.size()<=0 ){
			return null;
		}
		TicketProductVO ticketProductVO = ticketMap.get(ticketKey);
		if(ticketProductVO==null){
			return null;
		}
		UserProductStock userProductStock = getUserStockLocked(product.getId(),user.getId());
		if(userProductStock==null){
			return null;
		}
		userProductStock.setStockNum(Integer.valueOf(ticketProductVO.getBalance()) - userProductStock.getLockedNum());
		return userProductStock;
	}

	@Override
	public boolean updateStock(long productId, long userId, int number) {
		UserProductStock userProductStock = new UserProductStock();
		userProductStock.setUserId(userId);
		userProductStock.setProductId(productId);
		userProductStock = selectByRecord(userProductStock);
		if(userProductStock==null){
			return false;
		}
		int oldNum = userProductStock.getLockedNum();

		UserProductStock update = new UserProductStock();
		update.setLockedNum(userProductStock.getLockedNum()+number);
		update.setUpdateDate(new Date());
		int updateResult = updateByExampleSelective(update,getStockNumExample(userProductStock.getId(),oldNum));
		if(updateResult>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Long> listUserProdutId(User user) {
		List<Long> result = new ArrayList<>();
		UserWallet userWallet = userWalletService.selectByPrimaryKey(user.getId());
		if(userWallet ==null || userWallet.getStatus()!= WalletStateEnum.ACTIVE.getState() || StringUtils.isEmpty(userWallet.getAddress())){
			return result;
		}
		Map<String,TicketProductVO> tickMap = productLoadService.getTicketUserProduct(userWallet.getAddress());
		if(tickMap ==null || tickMap.size()<=0){
			return result;
		}
		Iterator<String> ite = tickMap.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			TicketProductVO ticketProductVO = tickMap.get(key);
			String productId = productCache.getProductIdByTicketKey(key);
			if(StringUtils.isEmpty(productId)){
				continue;
			}
			Product product = productService.selectByPrimaryKey(Long.valueOf(productId));
			if(!ProductUtil.isEffect(product)){
				continue;
			}
			int number = Integer.valueOf(ticketProductVO.getBalance()) - getLockedStockNum(product.getId(),user.getId()); ;
			if(number<=0){
				continue;
			}
			result.add(product.getId());
		}
		return result;
	}

	@Override
	public PageResultData<VoucherProductBaseVO> pageVoucherProduct(SearchPageProductVO searchPageProductVO, User user) {
		PageResultData<VoucherProductBaseVO> result = new PageResultData<>();
		if(user ==null || user.getId()<=0){
			return result;
		}
		UserWallet userWallet = userWalletService.selectByPrimaryKey(user.getId());
		if(userWallet ==null || userWallet.getStatus()!= WalletStateEnum.ACTIVE.getState() || StringUtils.isEmpty(userWallet.getAddress())){
			return result;
		}
		Map<String,TicketProductVO> tickMap = productLoadService.getTicketUserProduct(userWallet.getAddress());
		if(tickMap ==null || tickMap.size()<=0){
			return result;
		}
		if(searchPageProductVO ==null){
			searchPageProductVO = new SearchPageProductVO();
		}
		if(searchPageProductVO.getPageSize()<=0 || searchPageProductVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageProductVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageProductVO.getPageNum()<=0){
			searchPageProductVO.setPageNum(1);
		}
		//组装可返回实体列表
		List<VoucherProductBaseVO> allReult = new ArrayList<>();
		Iterator<String> ite = tickMap.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			TicketProductVO ticketProductVO = tickMap.get(key);
			String productId = productCache.getProductIdByTicketKey(key);
			if(StringUtils.isEmpty(productId)){
				continue;
			}
			Product product = productService.selectByPrimaryKey(Long.valueOf(productId));
			if(!ProductUtil.isEffect(product)){
				continue;
			}
			int tickNum = Integer.valueOf(ticketProductVO.getBalance());
			int lockNum =  getLockedStockNum(product.getId(),user.getId());
			if((tickNum - lockNum)<=0){
				continue;
			}
			VoucherProductBaseVO voucherProductBaseVO = ProductUtil.transObjForVoucher(product);
			voucherProductBaseVO.setNumber(tickNum);
			voucherProductBaseVO.setLockNumber(lockNum);
			voucherProductBaseVO.setEffectNumber(tickNum-lockNum);
			allReult.add(voucherProductBaseVO);
		}
		Collections.sort(allReult,new ProductBaseVO());
		List<VoucherProductBaseVO> dataList = PageUtils.pageBySubList(allReult,searchPageProductVO.getPageSize(),searchPageProductVO.getPageNum());
		result.setList(dataList);
		result.setTotal(allReult.size());
		return result;
	}



	/**
	 * 根据商品信息和用户信息获取用户库存数据
	 * @param productId
	 * @param userId
	 * @return
	 */
	private int getLockedStockNum(long productId, long userId){
		UserProductStock userProductStock = getUserStockLocked(productId,userId);
		if(userProductStock==null){
			return 0;
		}
		return userProductStock.getLockedNum();
	}


	/**
	 * 根据商品信息和用户信息获取用户库存数据
	 * @param productId
	 * @param userId
	 * @return
	 */
	private UserProductStock getUserStockLocked(long productId, long userId){
		if(productId<=0 || userId<=0 ){
			return null;
		}
		UserProductStock userProductStock = new UserProductStock();
		userProductStock.setUserId(userId);
		userProductStock.setProductId(productId);
		userProductStock = selectByRecord(userProductStock);
		if(userProductStock!=null){
			return userProductStock;
		}
		userProductStock = new UserProductStock();
		userProductStock.setProductId(productId);
		userProductStock.setUserId(userId);
		userProductStock.setCreateDate(new Date());
		userProductStock.setLockedNum(0);
		userProductStock.setUpdateDate(userProductStock.getCreateDate());
		int result = insertSelective(userProductStock);
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(userProductStock);
			retryNum--;
		}
		return result>0?userProductStock:null;
	}

	/*private static List<ConfigVO> pageBySubList(List<ConfigVO> list, int pagesize, int currentPage) {
		int totalcount = list.size();
		int pagecount = 0;
		List<ConfigVO> subList = new ArrayList<>();
		int m = totalcount % pagesize;
		if (m > 0) {
			pagecount = totalcount / pagesize + 1;
		} else {
			pagecount = totalcount / pagesize;
		}
		int start = (currentPage - 1) * pagesize;
		if(start>totalcount){
			return subList;
		}
		int end = pagesize * (currentPage);
		if(m!=0 && currentPage == pagecount){
			end = totalcount;
		}
		if(end>totalcount){
			end = totalcount;
		}
		return  list.subList(start,end );
	}

*/
	/**
	 * 分页 券包
	 * @param list
	 * @param pagesize
	 * @param currentPage
	 * @return
	 */
	private static List<VoucherProductBaseVO> pageVoucherBySubList(List<VoucherProductBaseVO> list, int pagesize, int currentPage) {
		int totalcount = list.size();
		int pagecount = 0;
		List<VoucherProductBaseVO> subList = new ArrayList<>();
		int m = totalcount % pagesize;
		if (m > 0) {
			pagecount = totalcount / pagesize + 1;
		} else {
			pagecount = totalcount / pagesize;
		}
		int start = (currentPage - 1) * pagesize;
		if(start>totalcount){
			return subList;
		}
		int end = pagesize * (currentPage);
		if(m!=0 && currentPage == pagecount){
			end = totalcount;
		}
		if(end>totalcount){
			end = totalcount;
		}
		return  list.subList(start,end );
	}


	private  UserProductStockExample  getStockNumExample(long id,long stock){
		UserProductStockExample example = new UserProductStockExample();
		UserProductStockExample.Criteria ca = example.or();
		if(id<=0){
			return example;
		}
		ca.andIdEqualToForSimple(id);
		ca.andLockNumForSimple(stock);
		return example;
	}
}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.User;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.utils.OrderUtil;
import org.eq.modules.common.utils.PageUtils;
import org.eq.modules.common.utils.ProductUtil;
import org.eq.modules.enums.OrderAcceptStateEnum;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.order.dao.OrderAcceptMapper;
import org.eq.modules.order.entity.*;
import org.eq.modules.order.service.OrderAcceptService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAccept;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.service.ProductAcceptService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.TicketProductVO;
import org.eq.modules.product.vo.VoucherProductBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 承兑管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderAcceptServiceImpl extends ServiceImplExtend<OrderAcceptMapper, OrderAccept, OrderAcceptExample> implements OrderAcceptService {

	@Autowired
	private UserProductStockService userProductStockService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BcTxRecordMapper bcTxRecordMapper;

	@Autowired
	private ProductCache productCache;

	@Autowired
	private ProductAcceptService productAcceptService;

	@Override
	public OrderAcceptExample getExampleFromEntity(OrderAccept orderAccept, Map<String, Object> params) {
		OrderAcceptExample example = new OrderAcceptExample();
		OrderAcceptExample.Criteria ca = example.or();
		if(orderAccept==null){
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
		if(orderAccept.getId()!=null){
			ca.andIdEqualTo(orderAccept.getId());
		}
		if(orderAccept.getUserId()!=null){
			ca.andUserIdEqualTo(orderAccept.getUserId());
		}
		if(StringLowUtils.isNotBlank(orderAccept.getAcceptNo())){
			ca.andAcceptNoEqualTo(orderAccept.getAcceptNo());
		}
		if(orderAccept.getProductId()!=null){
			ca.andProductIdEqualTo(orderAccept.getProductId());
		}
		if(orderAccept.getStatus()!=null){
			ca.andStatusEqualTo(orderAccept.getStatus());
		}
		if(orderAccept.getTxId()!=null){
			ca.andTxIdEqualTo(orderAccept.getTxId());
		}
		return example;
	}




	@Override
	public ServieReturn<OrderAcceptVO> createAcceptOrderVO(SearchAcceptOrderVO searchAcceptOrderVO, User user){
		String volidResult = VolidOrderInfo.volidCreateAcceptOrder(searchAcceptOrderVO);
		ServieReturn<OrderAcceptVO> result  = new ServieReturn<>();
		if(!StringUtils.isEmpty(volidResult)){
			result.setErrMsg(volidResult);
			return result;
		}
		if(user==null || StringUtils.isEmpty(user.getAddress())){
			result.setErrMsg("用户未激活钱包地址，不能进行承兑");
			return result;
		}
		UserProductStock userProductStock =  userProductStockService.getUserProductStock(searchAcceptOrderVO.getProductId(),user);
		if(userProductStock==null){
			result.setErrMsg("此商品无效");
			return result;
		}
		int balance = userProductStock.getStockNum() - userProductStock.getLockedNum();
		if((balance-searchAcceptOrderVO.getNumber())<=0){
			result.setErrMsg("可转让库存不足");
			return result;
		}
		ProductAll productAll = productCache.getProduct(String.valueOf(searchAcceptOrderVO.getProductId()));
		if(productAll==null){
			result.setErrMsg("商品无效");
			return result;
		}
		OrderAccept orderAccept = createAcceptOrder(searchAcceptOrderVO,user);
		OrderAcceptVO orderAcceptVO = OrderUtil.transObjForOrderTrans(orderAccept);
		if(orderAcceptVO==null){
			result.setErrMsg("创建订单失败");
			return result;
		}
		orderAcceptVO.setProductName(productAll.getName());
		orderAcceptVO.setImg(productAll.getProductImg());
		BcTxRecord bcTxRecord = new BcTxRecord();
		bcTxRecord.setFromAddress(user.getAddress());
		bcTxRecord.setToAddress(productAll.getAcceptAddress());
		bcTxRecord.setTransferAmount(String.valueOf(searchAcceptOrderVO.getNumber()));
		bcTxRecord.setTicketid(productAll.getTicketid());
		bcTxRecord.setTrancheid(productAll.getTrancheid());
		bcTxRecord.setAssetCode(productAll.getAssetCode());
		bcTxRecord.setAssetIssuer(productAll.getAssetIssuer());
		bcTxRecord.setContractAddress(productAll.getContractAddress());
		bcTxRecord.setAssetType(1);
		bcTxRecord.setTxStatus(0);
		bcTxRecord.setBizType(5);
		bcTxRecord.setCreateTime(new Date());
		bcTxRecord.setUpdateTime(new Date());
		bcTxRecord.setOptMetadata("用户ID:"+user.getId()+"承兑券");
		long inserNum  = bcTxRecordMapper.insertSelective(bcTxRecord);
		if(inserNum<=0){
			logger.error("插入区块链转出数据失败，数据为:{}",bcTxRecord.toString());
		}
		long transId = orderAccept.getId();
		orderAccept = new OrderAccept();
		orderAccept.setId(transId);
		orderAccept.setTxId(bcTxRecord.getId());
		updateByPrimaryKeySelective(orderAccept);
		result.setData(orderAcceptVO);
		return result;
	}



	@Override
	public PageResultData<OrderAcceptVO> pageAcceptOrder(SearchPageAcceptVO searchsPageAcceptVO, User user){
		PageResultData<OrderAcceptVO> result = new PageResultData<>();
		if(searchsPageAcceptVO ==null){
			searchsPageAcceptVO = new SearchPageAcceptVO();
		}
		if(searchsPageAcceptVO.getPageSize()<=0 || searchsPageAcceptVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchsPageAcceptVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchsPageAcceptVO.getPageNum()<=0){
			searchsPageAcceptVO.setPageNum(1);
		}
		if(user==null){
			return result;
		}
		OrderAccept orderAccept = new OrderAccept();
		orderAccept.setUserId(user.getId());
		orderAccept.setStatus(OrderAcceptStateEnum.ACCEPT_PADDING.getState());
		OrderAcceptExample orderAcceptExample = getExampleFromEntity(orderAccept,null);

		BaseTableData baseTableData = findDataTableByExampleForPage(orderAcceptExample, searchsPageAcceptVO.getPageNum(), searchsPageAcceptVO.getPageSize());
		if(baseTableData==null){
			return result;
		}
		List<OrderAcceptVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderAccept> pList = baseTableData.getData();
		for(OrderAccept p : pList){
			Product product = productService.selectByPrimaryKey(p.getProductId());
			if(product==null){
				logger.error("查询承兑订单，获取商品详细信息异常");
				continue;
			}
			OrderAcceptVO orderAcceptVO =  OrderUtil.transObjForOrderTrans(p);
			orderAcceptVO.setImg(product.getProductImg());
			orderAcceptVO.setProductName(product.getName());
			orderAcceptVO.setUnitPrice(product.getUnitPrice());
			dataList.add(orderAcceptVO);
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}



	@Override
	public PageResultData<OverdueVO> pageOverdueOrder(SearchPageAcceptVO searchsPageAcceptVO, User user){
		PageResultData<OverdueVO> result = new PageResultData<>();
		if(searchsPageAcceptVO ==null){
			searchsPageAcceptVO = new SearchPageAcceptVO();
		}
		if(searchsPageAcceptVO.getPageSize()<=0 || searchsPageAcceptVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchsPageAcceptVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchsPageAcceptVO.getPageNum()<=0){
			searchsPageAcceptVO.setPageNum(1);
		}
		if(user==null){
			return result;
		}

		OrderAccept orderAccept = new OrderAccept();
		orderAccept.setUserId(user.getId());
		orderAccept.setStatus(OrderAcceptStateEnum.ACCEPT_FINISH.getState());
		OrderAcceptExample orderAcceptExample = getExampleFromEntity(orderAccept,null);

		List<OverdueVO> dataList = new ArrayList<>();

		BaseTableData baseTableData = findDataTableByExampleForPage(orderAcceptExample, searchsPageAcceptVO.getPageNum(), searchsPageAcceptVO.getPageSize());
		if(baseTableData!=null && !CollectionUtils.isEmpty(baseTableData.getData())){
			List<OrderAccept> pList = baseTableData.getData();
			for(OrderAccept p : pList){
				Product product = productService.selectByPrimaryKey(p.getProductId());
				if(product==null){
					logger.error("查询承兑订单，获取商品详细信息异常");
					continue;
				}
				OverdueVO overdueVO =  OrderUtil.transObjForOverdueVO(p);
				overdueVO.setImg(product.getProductImg());
				overdueVO.setProductName(product.getName());
				overdueVO.setUnitPrice(product.getUnitPrice());
				overdueVO.setSort(product.getSort());
				dataList.add(overdueVO);
			}
		}
		Map<String, TicketProductVO> ticketMap = ProductUtil.getTicketUserProduct(user.getTxPassword());
		if(ticketMap!=null && ticketMap.size()>0){
			Iterator<String> ite = ticketMap.keySet().iterator();
			while(ite.hasNext()){
				String key = ite.next();
				TicketProductVO ticketProductVO = ticketMap.get(key);
				String productId  = productCache.getProductIdByTicketKey(key);

				if(StringUtils.isEmpty(productId)){
					continue;
				}
				Product product = productService.selectByPrimaryKey(Long.valueOf(productId));
				if(product==null || ProductUtil.isEffect(product)){
					continue;
				}
				OverdueVO overdueVO = new OverdueVO();
				overdueVO.setUserId(user.getId());
				overdueVO.setProductId(product.getId());
				int number = ParseUtil.getInt(ticketProductVO.getBalance());
				if(number<=0){
					continue;
				}
				overdueVO.setNumber(number);
				overdueVO.setOverdueReason("已失效");
				overdueVO.setImg(product.getProductImg());
				overdueVO.setProductName(product.getName());
				overdueVO.setUnitPrice(product.getUnitPrice());
				overdueVO.setSort(product.getSort());
				dataList.add(overdueVO);
			}
		}
		Collections.sort(dataList,new OverdueVO());
		List<OverdueVO> pageList = PageUtils.pageBySubList(dataList,searchsPageAcceptVO.getPageSize(),searchsPageAcceptVO.getPageNum());
		result.setList(dataList);
		result.setTotal(pageList.size());
		return result;
	}



	@Override
	public ServieReturn<OrderAcceptVO> searchOrderAccept(SearchAcceptOrderVO searchAcceptOrderVO, User user){
		String volidResult = VolidOrderInfo.volidSearchAcceptOrder(searchAcceptOrderVO);
		ServieReturn<OrderAcceptVO> result  = new ServieReturn<>();
		if(!StringUtils.isEmpty(volidResult)){
			result.setErrMsg(volidResult);
			return result;
		}
		if(user==null){
			result.setErrMsg("用户为空");
			return result;
		}
		OrderAccept orderAccept = new OrderAccept();
		orderAccept.setUserId(user.getId());
		orderAccept.setAcceptNo(searchAcceptOrderVO.getAcceptCode());

		try {
			orderAccept =selectByRecord(orderAccept);
		}catch (Exception e){
			logger.error("查询承兑订单为空");
		}
		if(orderAccept==null){
			result.setErrMsg("订单不存在");
			return result;
		}
		OrderAcceptVO orderAcceptVO = OrderUtil.transObjForOrderTrans(orderAccept);
		if(orderAcceptVO==null){
			result.setErrMsg("订单转化异常");
			return result;
		}
		Product product = productService.selectByPrimaryKey(orderAcceptVO.getProductId());
		if(product==null){
			result.setErrMsg("订单查询异常");
			return result;
		}
		orderAcceptVO.setProductName(product.getName());
		orderAcceptVO.setImg(product.getProductImg());
		orderAcceptVO.setUnitPrice(product.getUnitPrice());
		String modile = "";
		try{
			ProductAccept accept = productAcceptService.selectByPrimaryKey(product.getProductAcceptId());
			if(accept!=null){
				modile = accept.getMobile();
			}
		}catch (Exception e){
			logger.error("查询承兑商信息异常",e);
		}
		orderAcceptVO.setAcceptModile(modile);
		result.setData(orderAcceptVO);
		return result;
	}



	/**
	 * 创建承兑订单
	 * @param searchAcceptOrderVO
	 * @param user
	 * @return
	 */
	private OrderAccept createAcceptOrder(SearchAcceptOrderVO searchAcceptOrderVO, User user){
		if(searchAcceptOrderVO==null || searchAcceptOrderVO.getProductId() <=0){
			return null;
		}
		Product product = productService.selectByPrimaryKey(searchAcceptOrderVO.getProductId());
		if(!ProductUtil.isEffect(product)){
			return null;
		}
		boolean updateStock= false;
		try {
			updateStock = userProductStockService.updateStock(product.getId(),user.getId(),searchAcceptOrderVO.getNumber());
		}catch (Exception e){
			e.printStackTrace();
		}
		if(!updateStock){
			return null;
		}
		OrderAccept orderAccept = new OrderAccept();
		orderAccept.setUserId(user.getId());
		orderAccept.setAcceptNo(getOrderNo());
		orderAccept.setProductId(searchAcceptOrderVO.getProductId());
		orderAccept.setProductNum(searchAcceptOrderVO.getNumber());
		orderAccept.setStatus(OrderAcceptStateEnum.ACCEPT_WAIT.getState());
		orderAccept.setConsignee(searchAcceptOrderVO.getConsignee());
		orderAccept.setConsigneeAddress(searchAcceptOrderVO.getConsigneeAddress());
		orderAccept.setConsigneeMobile(searchAcceptOrderVO.getConsigneePhone());
		orderAccept.setCreateDate(new Date());
		orderAccept.setUpdateDate(new Date());
		orderAccept.setRemarks("");
		long result = insertSelective(orderAccept);
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(orderAccept);
			retryNum--;
		}
		if(result<=0){
			updateStock =true;
			try {
				updateStock = userProductStockService.updateStock(product.getId(),user.getId(),-searchAcceptOrderVO.getNumber());
			}catch (Exception e){
				e.printStackTrace();
			}
			if(!updateStock){
				logger.error("创建承兑订单失败，但是锁定库存成功，释放库存失败，用户id :{} 商品ID:{} 应释放量:{}",searchAcceptOrderVO.getProductId(),user.getId(),searchAcceptOrderVO.getNumber());
			}

		}
		return result>0?orderAccept:null;
	}



	private String getOrderNo(){
		StringBuffer buffer = new StringBuffer("AS");
		String number = String.valueOf((Math.random()*9+1)*100000);
		if(number.contains(".")){
			number=number.substring(0,number.indexOf("."));
		}
		buffer.append(DateUtil.getLockNowTime()).append(number);
		return buffer.toString();
	}





}
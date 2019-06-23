/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.User;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.enums.LogTypeEnum;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.log.OrderLogService;
import org.eq.modules.utils.IdWork;
import org.eq.modules.utils.OrderUtil;
import org.eq.modules.utils.ProductUtil;
import org.eq.modules.enums.OrderTransferStateEnum;
import org.eq.modules.order.dao.OrderTransferMapper;
import org.eq.modules.order.entity.*;
import org.eq.modules.order.service.OrderTransferService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品转让ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class OrderTransferServiceImpl extends ServiceImplExtend<OrderTransferMapper, OrderTransfer, OrderTransferExample> implements OrderTransferService {

	@Autowired
	private UserProductStockService userProductStockService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BcTxRecordMapper bcTxRecordMapper;

	@Autowired
	private ProductCache productCache;

	@Autowired
	private UserWalletService userWalletService;

	@Autowired
	private OrderLogService orderLogService ;

	@Override
	public OrderTransferExample getExampleFromEntity(OrderTransfer orderTransfer, Map<String, Object> params) {
		OrderTransferExample example = new OrderTransferExample();
		OrderTransferExample.Criteria ca = example.or();
		if(orderTransfer==null){
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
		if(orderTransfer.getId()!=null){
			ca.andIdEqualTo(orderTransfer.getId());
		}
		if(orderTransfer.getUserId()!=null){
			ca.andUserIdEqualTo(orderTransfer.getUserId());
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getTransferNo())){
			ca.andTransferNoEqualTo(orderTransfer.getTransferNo());
		}
		if(orderTransfer.getProductId()!=null){
			ca.andProductIdEqualTo(orderTransfer.getProductId());
		}
		if(orderTransfer.getProductNum()!=null){
			ca.andProductNumEqualTo(orderTransfer.getProductNum());
		}
		if(orderTransfer.getStatus()!=null){
			ca.andStatusEqualTo(orderTransfer.getStatus());
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getToAddress())){
			ca.andToAddressEqualTo(orderTransfer.getToAddress());
		}
		if(orderTransfer.getCreateDate()!=null){
			ca.andCreateDateEqualTo(orderTransfer.getCreateDate());
		}
		if(StringLowUtils.isNotBlank(orderTransfer.getRemarks())){
			ca.andRemarksEqualTo(orderTransfer.getRemarks());
		}
		if(orderTransfer.getTxId()!=null){
			ca.andTxIdEqualTo(orderTransfer.getTxId());
		}
		return example;
	}

	@Override
	public ServieReturn<OrderTransVO> createTransOrderVO(SearchTransOrderVO searchTransOrderVO, User user){
		String volidResult = VolidOrderInfo.volidSearchTransOrderAd(searchTransOrderVO);
		ServieReturn<OrderTransVO> result  = new ServieReturn<>();
		if(!StringUtils.isEmpty(volidResult)){
			result.setErrMsg(volidResult);
			return result;
		}
		UserWallet userWallet = userWalletService.selectByPrimaryKey(user.getId());
		if(user ==null || userWallet ==null || userWallet.getStatus()!= WalletStateEnum.ACTIVE.getState() || StringUtils.isEmpty(userWallet.getAddress())){
			result.setErrMsg("用户钱包地址未激活");
			return result;
		}

		ProductAll productAll = productCache.getProduct(String.valueOf(searchTransOrderVO.getProductId()));
		if(productAll==null){
			result.setErrMsg("商品无效");
			return result;
		}
		UserProductStock userProductStock =  userProductStockService.getUserProductStock(searchTransOrderVO.getProductId(),user);
		if(userProductStock==null){
			result.setErrMsg("此商品无效");
			return result;
		}
		int balance = userProductStock.getStockNum() - userProductStock.getLockedNum();
		if((balance-searchTransOrderVO.getNumber())<0){
			result.setErrMsg("可转让库存不足");
			return result;
		}

		OrderTransfer orderTransfer = createTransOrder(searchTransOrderVO,user);
		OrderTransVO orderTransVO = OrderUtil.transObjForOrderTrans(orderTransfer);
		if(orderTransVO==null){
			result.setErrMsg("创建订单失败");
			return result;
		}

		BcTxRecord bcTxRecord = new BcTxRecord();
		bcTxRecord.setFromAddress(userWallet.getAddress());
        bcTxRecord.setToAddress(searchTransOrderVO.getAddress());
        bcTxRecord.setTransferAmount(String.valueOf(searchTransOrderVO.getNumber()));
        bcTxRecord.setTicketid(productAll.getTicketid());
        bcTxRecord.setTrancheid(productAll.getTrancheid());
        bcTxRecord.setAssetCode(productAll.getAssetCode());
        bcTxRecord.setAssetIssuer(productAll.getAcceptAddress());
        bcTxRecord.setContractAddress(productAll.getContractAddress());
        bcTxRecord.setAssetType(1);
        bcTxRecord.setTxStatus(0);
        bcTxRecord.setBizType(4);
        bcTxRecord.setCreateTime(new Date());
        bcTxRecord.setUpdateTime(new Date());
        bcTxRecord.setOptMetadata("用户ID:"+user.getId()+"转让券");
		long inserNum  = bcTxRecordMapper.insertSelective(bcTxRecord);
		if(inserNum<=0){
		    logger.error("插入区块链转出数据失败，数据为:{}",bcTxRecord.toString());
        }
		long transId = orderTransfer.getId();
		orderTransfer = new OrderTransfer();
		orderTransfer.setId(transId);
		orderTransfer.setTxId(bcTxRecord.getId());
		updateByPrimaryKeySelective(orderTransfer);
        result.setData(orderTransVO);
		return result;
	}



	@Override
	public PageResultData<OrderTransVO> pageTransOrder(SearchPageTransVO searchPageTransVO, User user){
		PageResultData<OrderTransVO> result = new PageResultData<>();
		if(searchPageTransVO ==null){
			searchPageTransVO = new SearchPageTransVO();
		}
		if(searchPageTransVO.getPageSize()<=0 || searchPageTransVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			searchPageTransVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(searchPageTransVO.getPageNum()<=0){
			searchPageTransVO.setPageNum(1);
		}
		if(user==null){
			return result;
		}
		OrderTransfer orderTransfer = new OrderTransfer();
		orderTransfer.setUserId(user.getId());
		OrderTransferExample orderTransferExample = getExampleFromEntity(orderTransfer,null);

		BaseTableData baseTableData = findDataTableByExampleForPage(orderTransferExample, searchPageTransVO.getPageNum(), searchPageTransVO.getPageSize());
		if(baseTableData==null){
			return result;
		}
		List<OrderTransVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderTransfer> pList = baseTableData.getData();
		for(OrderTransfer p : pList){
			Product product = productService.selectByPrimaryKey(p.getProductId());
			if(product==null){
				logger.error("查询转让订单，获取商品详细信息异常");
				continue;
			}
			OrderTransVO orderTransVO =  OrderUtil.transObjForOrderTrans(p);
			orderTransVO.setImg(product.getProductImg());
			orderTransVO.setProductName(product.getName());
			orderTransVO.setUnitPrice(product.getUnitPrice());
			dataList.add(orderTransVO);
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}






	/**
	 * 创建求购订单
	 * @param searchTransOrderVO
	 * @param user
	 * @return
	 */
	private OrderTransfer createTransOrder(SearchTransOrderVO searchTransOrderVO, User user){
		if(searchTransOrderVO==null || searchTransOrderVO.getProductId() <=0){
			return null;
		}
		Product product = productService.selectByPrimaryKey(searchTransOrderVO.getProductId());
		if(!ProductUtil.isEffect(product)){
			return null;
		}
		boolean updateStock= false;
		try {
			updateStock = userProductStockService.updateStock(product.getId(),user.getId(),searchTransOrderVO.getNumber());
		}catch (Exception e){
			e.printStackTrace();
		}
		if(!updateStock){
			return null;
		}
		OrderTransfer orderTransfer = new OrderTransfer();
		orderTransfer.setUserId(user.getId());
		orderTransfer.setTransferNo(IdWork.getOrderCode("TS"));
		orderTransfer.setProductId(searchTransOrderVO.getProductId());
		orderTransfer.setProductNum(searchTransOrderVO.getNumber());
		orderTransfer.setStatus(OrderTransferStateEnum.TRANSFER_PADDING.getState());
		orderTransfer.setToAddress(searchTransOrderVO.getAddress());
		orderTransfer.setCreateDate(new Date());
		long result = insertSelective(orderTransfer);
		int retryNum = 3;
		while(result<=0 && retryNum>0){
			result = insertSelective(orderTransfer);
			retryNum--;
		}
		if(result<=0){
			updateStock =true;
			try {
				updateStock = userProductStockService.updateStock(product.getId(),user.getId(),-searchTransOrderVO.getNumber());
			}catch (Exception e){
				e.printStackTrace();
			}
			if(!updateStock){
				logger.error("创建转让订单失败，但是锁定库存成功，释放库存失败，用户id :{} 商品ID:{} 应释放量:{}",searchTransOrderVO.getProductId(),user.getId(),searchTransOrderVO.getNumber());
			}
		}else{
			saveLog(orderTransfer.getId(),-1,OrderTransferStateEnum.TRANSFER_PADDING.getState(),"新建转让订单");
		}
		return result>0?orderTransfer:null;
	}

	/**
	 * 插如广告日志
	 * @param transId
	 * @param oldState
	 * @param newState
	 * @param remarks
	 */
	private void saveLog(long transId,int oldState,int newState,String remarks){
		OrderTransferLog orderTransferLog = new OrderTransferLog();
		orderTransferLog.setCreateDate(new Date());
		orderTransferLog.setNewStatus(newState);
		orderTransferLog.setOldStatus(oldState);
		orderTransferLog.setOrderTransferId(transId);
		orderTransferLog.setRemarks(remarks);
		try{
			orderLogService.save(LogTypeEnum.TRANSFER,orderTransferLog);
		}catch (Exception e){
			e.printStackTrace();
			logger.info("插入转让订单日志记录数据出错 {}",orderTransferLog.toString());
		}
	}


}
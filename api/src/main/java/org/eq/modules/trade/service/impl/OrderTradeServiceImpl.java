/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.IdworkUtil;
import org.eq.basic.common.util.OrderNoGenerateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.exception.UserNotExistsException;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.enums.LogTypeEnum;
import org.eq.modules.common.enums.ResponseStateEnum;
import org.eq.modules.enums.*;
import org.eq.modules.log.OrderLogService;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.exception.ProductNotExistsException;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.BSearchProduct;
import org.eq.modules.product.vo.ProductDetailVO;
import org.eq.modules.sms.service.SmsService;
import org.eq.modules.support.dao.SystemConfigMapper;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.entity.SystemConfigExample;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.*;
import org.eq.modules.trade.exception.TradeOrderException;
import org.eq.modules.trade.service.OrderPaymentTradeService;
import org.eq.modules.trade.service.OrderTradeAppealService;
import org.eq.modules.trade.service.OrderTradeMapService;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.*;
import org.eq.modules.utils.IdWork;
import org.eq.modules.utils.ProductUtil;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单交易ServiceImpl
 * @author yufei.sun
 * @version 1.0
 */
@SuppressWarnings("all")
@Service
@Transactional
@AutowiredService
public class OrderTradeServiceImpl extends ServiceImplExtend<OrderTradeMapper, OrderTrade, OrderTradeExample> implements OrderTradeService {

    /**
     * 订单日志服务
     */
	@Autowired
	OrderLogService orderLogService;


	@Autowired
	private SmsService smsService;

    /**
     * 商品服务
     */
	@Autowired
	ProductService productService;

	@Autowired
	OrderAdService orderAdService;

	@Autowired
	UserService userService;

	@Autowired
	OrderPaymentTradeService orderPaymentTradeService;


	@Autowired
	private BcTxRecordMapper bcTxRecordMapper;

	/**
	 * 系统配置表
	 */
	@Autowired
	private SystemConfigMapper systemConfigMapper;

	@Autowired
	private UserProductStockService userProductStockService;

	@Autowired
	private OrderTradeMapper orderTradeMapper;

	@Autowired
	private ProductCache productCache;

    /**
     * 用户钱包服务
     */
	@Autowired
    private UserWalletService userWalletService;

    /**
     * 交易投诉管理
     */
	@Autowired
    private OrderTradeAppealService orderTradeAppealService;




	@Override
	public OrderTradeExample getExampleFromEntity(OrderTrade orderTrade, Map<String, Object> params) {
		OrderTradeExample example = new OrderTradeExample();
		OrderTradeExample.Criteria ca = example.or();
		if(orderTrade==null){
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
		if(!StringUtils.isEmpty(orderTrade.getTradeNo())){
			ca.andTradeNoEqualTo(orderTrade.getTradeNo());
		}


		return example;
	}

	/**
	 * 获取code以及用户查询实体
	 * @param orderCode
	 * @param userId
	 * @return
	 */
	private OrderAdExample getExampleFromUserAndCode(String orderCode,long userId){
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		if(!StringUtils.isEmpty(orderCode)){
			ca.andOrderNoEqualToForAll(orderCode);
		}
		if(userId>0){
			ca.andUserIdEqualToForAll(userId);
		}
		return example;
	}

	@Override
	public ServieReturn<OrderTrade> createTradeOrder(OrderTradeCreateReqVO orderTradeCreateReqVO,User user) {
		ServieReturn<OrderTrade> result  = new ServieReturn<>();
		String errMsg = VolidOrderTradeInfo.volidCreate(orderTradeCreateReqVO);
		if(!StringUtils.isEmpty(errMsg)){
			result.setErrMsg(errMsg);
			return  result;
		}
		OrderAd orderAd = getOrderAdForCode(orderTradeCreateReqVO.getAdNo());
		if (orderAd == null || (orderAd.getStatus()!= OrderAdStateEnum.ORDER_TRADEING.getState())) {
			result.setErrMsg("广告订单不存在或已下架");
			return  result;
		}
		if(String.valueOf(orderAd.getUserId()).equals(String.valueOf(user.getId()))){
            result.setErrMsg("买卖双方同一人");
            return  result;
        }
        UserWallet fromUser = userWalletService.selectByPrimaryKey(orderAd.getUserId());
        UserWallet toUser = userWalletService.selectByPrimaryKey(user.getId());
        String fromAddress = fromUser==null? "" :(fromUser.getStatus()==0? "" : fromUser.getAddress());
        String toAddress = toUser==null? "" :(toUser.getStatus()==0? "" : toUser.getAddress());
        if(StringUtils.isEmpty(fromAddress) || StringUtils.isEmpty(toAddress)){
            result.setErrMsg("用户钱包地址未激活");
            return  result;
        }

		Product product = productService.selectByPrimaryKey(orderAd.getProductId());
		if (!ProductUtil.isEffect(product)) {
			result.setErrMsg("商品不存在或已下架");
			return  result;
		}
		User orderUser = userService.selectByPrimaryKey(orderAd.getUserId());
		if(orderUser==null){
			result.setErrMsg("用户信息有误");
			return result;
		}

		long sellUserId = orderAd.getUserId();
		long buyUserId = user.getId();
		int tradeType = OrderTradeTypeEnum.ORDER_BUY.getType();
		//求购订单 发生交易需要锁定用户库存
		if(orderAd.getType() == OrderAdTypeEnum.ORDER_BUY.getType()){
			UserProductStock userProductStock =  userProductStockService.getUserProductStock(product.getId(),user);
			if(userProductStock==null){
				result.setErrMsg("用户无此商品库存信息");
				return result;
			}
			int balance = userProductStock.getStockNum() - userProductStock.getLockedNum();
			if((balance-orderTradeCreateReqVO.getOrderNum())<=0){
				result.setErrMsg("可售卖量不足");
				return result;
			}
			boolean lockResult = lockUserStock(product.getId(),user.getId(),orderTradeCreateReqVO.getOrderNum());
			if(!lockResult){
				result.setErrMsg("库存不足");
				return result;
			}
			sellUserId = user.getId();
			buyUserId = orderAd.getUserId();
			tradeType = OrderTradeTypeEnum.ORDER_SALE.getType();
		}
		int retryNum = 3;
		boolean retryResult = false;
		retryResult = lockOrderAd(orderAd,orderTradeCreateReqVO.getOrderNum());
		while(retryNum>0 && !retryResult){
			retryResult = lockOrderAd(getOrderAdForCode(orderTradeCreateReqVO.getAdNo()),orderTradeCreateReqVO.getOrderNum());
			retryNum--;
		}
		if(!retryResult){
			result.setErrMsg("交易量超出订单量");
			return  result;
		}
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setSellUserId(sellUserId);
		orderTrade.setBuyUserId(buyUserId);
		orderTrade.setTradeNo(OrderNoGenerateUtil.generateNo(OrderNoPreFixEnum.TRADE_NO));
		orderTrade.setAdNo(orderAd.getOrderNo());
		orderTrade.setProductId(product.getId());
		orderTrade.setOrderNum(orderTradeCreateReqVO.getOrderNum());
		orderTrade.setType(tradeType);
		orderTrade.setStatus(OrderTradeStateEnum.WAIT_PAY.getState());
		orderTrade.setSalePrice(orderTradeCreateReqVO.getSalePrice());
		orderTrade.setUnitPrice(product.getUnitPrice());
		orderTrade.setAmount(orderTrade.getOrderNum() * orderTrade.getSalePrice());
		BigDecimal bigDecimal = new BigDecimal(Double.valueOf(getServiceFee())).multiply(new BigDecimal(orderTrade.getAmount()));
		int servicefee = bigDecimal.setScale(0, BigDecimal.ROUND_UP).intValue();
		orderTrade.setServiceFee(servicefee);
		orderTrade.setRemindPay(0);
		orderTrade.setCreateDate(new Date());
		orderTrade.setFinishTime(DateUtil.afterDateHour(orderTrade.getCreateDate(),getOverHour()));
		orderTrade.setRemarks("创建订单");
		orderTrade.setUpdateDate(new Date());

		long insertId=orderTradeMapper.insertSelectiveAndReturnId(orderTrade);
		if(insertId<=0){
			result.setErrMsg("创建交易订单失败");
			return  result;
		}
		// 插入交易日志
		insertOrderTradeLog(orderTrade.getId(),-1,orderTrade.getStatus(),"初始化创建");
		result.setData(orderTrade);
		return result;
	}


	/**
	 * 支付回调前接口
	 * @param orderTradeSearchVO
	 * @param user
	 * @return
	 */
    @Override
    public ServieReturn<OrderTrade> prePayTradeOrder(OrderTradeSearchVO orderTradeSearchVO,User user) {
        ServieReturn<OrderTrade> result  = new ServieReturn<>();
        String errMsg = VolidOrderTradeInfo.volidSearch(orderTradeSearchVO);
        if(!StringUtils.isEmpty(errMsg)){
            result.setErrMsg(errMsg);
            return  result;
        }
        OrderTrade orderTrade = new OrderTrade();
        orderTrade.setTradeNo(orderTradeSearchVO.getTradeNo());
        orderTrade = selectByRecord(orderTrade);
        if (orderTrade == null || !OrderTradeStateEnum.isRunPay(orderTrade.getStatus())) {
            result.setErrMsg("交易订单不能进行交易");
            return  result;
        }
        Date date = DateUtil.afterDateHour(orderTrade.getCreateDate(),getOverHour());
        Date nowTime = new Date();
        if(nowTime.getTime()>date.getTime()){
			result.setErrMsg("交易订单已过期");
			return  result;
		}

        OrderAd orderAd = getOrderAdForCode(orderTrade.getAdNo());
        if (orderAd == null || !OrderAdStateEnum.isEffectState(orderAd.getStatus())) {
            result.setErrMsg("广告无效，不能发起交易");
            return  result;
        }
        Product product = productService.selectByPrimaryKey(orderAd.getProductId());
        if (!ProductUtil.isEffect(product)) {
            result.setErrMsg("商品不存在或已下架");
            return  result;
        }

		Integer oldStatus = orderTrade.getStatus();

		OrderTradeExample updateOrderTrade = new OrderTradeExample();
		OrderTradeExample.Criteria updateOrderCa = updateOrderTrade.or();
		updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());
		updateOrderCa.andStatusEqualToForUpdate(oldStatus);

		OrderTrade updateObj = new OrderTrade();
		updateObj.setId(orderTrade.getId());
		updateObj.setStatus(OrderTradeStateEnum.PAY_ING.getState());
		updateObj.setUpdateDate(new Date());

		int number = updateByExampleSelective(updateObj,updateOrderTrade);
		if(number<=0){
			result.setErrMsg("前置支付设置出错");
			return  result;
		}
		// 插入交易日志
		insertOrderTradeLog(orderTrade.getId(),orderTrade.getStatus(),OrderTradeStateEnum.PAY_ING.getState(),"支付前置回调");
        result.setData(orderTrade);
        return result;
    }


	/**
	 * 取消订单
	 * @param tradeNo 交易订单号
	 */
	@Override
	public void cancelTradeOrder(String tradeNo) {
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(tradeNo);
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null || !OrderTradeStateEnum.isAllowCancel(orderTrade.getStatus())) {
			throw new TradeOrderException("交易单号记录不存在或者不能进行取消");
		}
		OrderAd orderAd = getOrderAdForCode(orderTrade.getAdNo());
		if(orderAd==null){
			throw new TradeOrderException("广告订单不存在");
		}
		Integer oldStatus = orderTrade.getStatus();

		OrderTradeExample updateOrderTrade = new OrderTradeExample();
		OrderTradeExample.Criteria updateOrderCa = updateOrderTrade.or();
		updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());
		updateOrderCa.andStatusEqualToForUpdate(oldStatus);

		OrderTrade updateObj = new OrderTrade();
		updateObj.setId(orderTrade.getId());

		updateObj.setStatus(OrderTradeStateEnum.CANCEL.getState());
		updateObj.setUpdateDate(new Date());
		updateObj.setFinishTime(new Date());
		int number = updateByExampleSelective(updateObj,updateOrderTrade);
		if(number<=0){
			throw new TradeOrderException("取消失败");
		}
		StringBuilder errMsgBuilder = new StringBuilder("[");
		//需要释放库存
		if(orderTrade.getType() == OrderTradeTypeEnum.ORDER_SALE.getType()){
			boolean lockResult = lockUserStock(orderTrade.getProductId(),orderTrade.getSellUserId(),orderTrade.getOrderNum());
			if(!lockResult){
				errMsgBuilder.append("释放用户库存失败");
			}
		}

		int retryNum = 3;
		boolean retryResult = false;
		retryResult = lockOrderAd(orderAd,-orderTrade.getOrderNum());
		while(retryNum>0 && !retryResult){
			retryResult = lockOrderAd(getOrderAdForCode(orderAd.getOrderNo()),-orderTrade.getOrderNum());
			retryNum--;
		}
		if(!retryResult){
			errMsgBuilder.append(",释放订单在途交易量:").append(orderTrade.getOrderNum()).append("失败");
		}
		insertOrderTradeLog(orderTrade.getId(),oldStatus,OrderTradeStateEnum.CANCEL.getState(),"API调用方式取消交易，库存相关备注:"+errMsgBuilder.toString());
	}


	/**
	 * 获取交易数
	 * @param userId 用户ID
	 * @return
	 */
	@Override
	public OrderTradePoolInfoVO poolInfolTradeOrder(long userId) {
		OrderTradePoolInfoVO result = new OrderTradePoolInfoVO();

		OrderTradeExample orderTradeExample = new OrderTradeExample();
		OrderTradeExample.Criteria ca = orderTradeExample.or();
		ca.andStatusInForAll(OrderTradeStateEnum.getRunningStates());
		ca.andAllUserIdEqualTo(userId);

		List<OrderTrade> list = findListByExample(orderTradeExample);
		if(CollectionUtils.isEmpty(list)){
			return result;
		}

		int waitPay = 0;
		int progress = 0;
		for(OrderTrade tem : list){
			if(OrderTradeStateEnum.isRunPay(tem.getStatus()) && tem.getBuyUserId()==userId){
				waitPay ++;
			}else{
				progress++;
			}
		}
		result.setProgress(progress);
		result.setWaitPay(waitPay);
		return result;
	}


	/**
	 * 交易订单详情
	 * @param tradeNo
	 * @return
	 */
	@Override
	public OrderTradeDetailResVO tradeOrderDetail(String tradeNo) {
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(tradeNo);
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null) {
			logger.error("tradeOrderDetail 交易单号[{}]记录不存在",tradeNo);
			throw new TradeOrderException("交易单号记录不存在");
		}
		OrderTradeDetailResVO orderTradeDetailResVO = new OrderTradeDetailResVO();
		BSearchProduct bsearchProduct =  new BSearchProduct();
		bsearchProduct.setProductId(orderTrade.getProductId());
		ProductDetailVO productDetailVO = productService.getProductAll(bsearchProduct);
		if (productDetailVO == null) {
			logger.error("tradeOrderDetail 商品ID[{}]记录不存在",orderTrade.getProductId());
			throw new ProductNotExistsException("商品ID记录不存在");
		}
		orderTradeDetailResVO.setProduct(productDetailVO);

		User buserUser = userService.selectByPrimaryKey(orderTrade.getBuyUserId());
		User sellUser = userService.selectByPrimaryKey(orderTrade.getSellUserId());
		if (buserUser == null || sellUser == null ) {
			logger.error("tradeOrderDetail 买家不存在 {} 或者 卖家不存在 {}",orderTrade.getBuyUserId(),orderTrade.getSellUserId());
			throw new UserNotExistsException("买卖用户记录不存在");
		}
		orderTradeDetailResVO.setTrade(initTradDetail(orderTrade));

		OrderTradeUser orderTradeDetailUser = new OrderTradeUser();
		orderTradeDetailUser.setBuyUserId(orderTrade.getBuyUserId());
		orderTradeDetailUser.setSellUserId(orderTrade.getSellUserId());
		orderTradeDetailUser.setBuyUserNickName(buserUser.getNickname());
		orderTradeDetailUser.setSellUserNickName(sellUser.getNickname());

		orderTradeDetailResVO.setUser(orderTradeDetailUser);
		return orderTradeDetailResVO;
	}

	/**
	 * 支付结果通知
	 * @param orderTradePaymentReqVO
	 * @return
	 */
	@Override
	public OrderTradePaymentResVO orderPaymentTradeNotify(OrderTradePaymentReqVO orderTradePaymentReqVO) {

		OrderTradePaymentResVO result = new OrderTradePaymentResVO();
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(orderTradePaymentReqVO.getTradeNo());
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null) {
			throw new TradeOrderException("交易单号记录不存在");
		}
		if(!OrderTradeStateEnum.isPayBack(orderTrade.getStatus())){
            throw new TradeOrderException("非支付回调状态");
        }
		boolean isSuccess= "1".equals(String.valueOf(orderTradePaymentReqVO.getPayStatus()))? true : false;

		OrderPaymentTrade orderPaymentTrade = insertOrderPayment(orderTrade,isSuccess,orderTradePaymentReqVO.getPayNo(),orderTradePaymentReqVO.getPayType());
		if(orderPaymentTrade ==null){
			logger.error(" 插入支付流水失败");
			throw new TradeOrderException("插入支付流水记录失败");
		}
		result.setTradeNo(orderTrade.getTradeNo());

		Integer oldStatus = orderTrade.getStatus();
		OrderTradeExample updateOrderTrade = new OrderTradeExample();
		OrderTradeExample.Criteria updateOrderCa = updateOrderTrade.or();
		updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());
		updateOrderCa.andStatusEqualToForUpdate(oldStatus);

		OrderTrade updateObj = new OrderTrade();
		updateObj.setId(orderTrade.getId());
		if(isSuccess){
			updateObj.setStatus(OrderTradeStateEnum.PAY_SUCCESS.getState());
		}else{
			updateObj.setStatus(OrderTradeStateEnum.PAY_FAIL.getState());
		}
		updateObj.setUpdateDate(new Date());
		int number = updateByExampleSelective(updateObj,updateOrderTrade);
		if(number<=0){
			throw new TradeOrderException("更改交易订单失败");
		}
		insertOrderTradeLog(orderTrade.getId(),oldStatus,updateObj.getStatus(),"通知成功，完成支付流水入库");
		if(!isSuccess){
            return result;
        }
        UserWallet fromUser = userWalletService.selectByPrimaryKey(orderTrade.getSellUserId());
        UserWallet toUser = userWalletService.selectByPrimaryKey(orderTrade.getBuyUserId());
        String fromAddress = fromUser==null? "" :(fromUser.getStatus()==0? "" : fromUser.getAddress());
        String toAddress = toUser==null? "" :(toUser.getStatus()==0? "" : toUser.getAddress());
		int bizType=2;
        if(orderTrade.getType() == OrderTradeTypeEnum.ORDER_BUY.getType()){
            //当前用户发起购买
            bizType = 3;
        }

        ProductAll productAll =  productCache.getProduct(String.valueOf(orderTrade.getProductId()));
        BcTxRecord bcTxRecord  = insertBx(productAll,fromAddress,toAddress,orderTrade.getOrderNum(),bizType);
        if(bcTxRecord==null){
            logger.info("交易订单支付成功，但插入区块链表失败,交易ID 为 : {}",orderTrade.getId());
        }

        updateOrderTrade = new OrderTradeExample();
        updateOrderCa = updateOrderTrade.or();
        updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());
        updateOrderCa.andStatusEqualToForUpdate(OrderTradeStateEnum.PAY_SUCCESS.getState());

        updateObj = new OrderTrade();
        updateObj.setId(orderTrade.getId());
        updateObj.setStatus(OrderTradeStateEnum.VOUCHER_ING.getState());
        updateObj.setUpdateDate(new Date());
        updateObj.setTxId(bcTxRecord==null?-1:bcTxRecord.getId());
        number = updateByExampleSelective(updateObj,updateOrderTrade);
        if(number<=0){
            logger.error("订单支付成功，区块量");
            throw new TradeOrderException("更改交易订单失败");
        }
        insertOrderTradeLog(orderTrade.getId(),OrderTradeStateEnum.PAY_SUCCESS.getState(),updateObj.getStatus(),"支付成功后，状态变更为放券中");
		return result;
	}

	/**
	 * 获取待付款数据
	 * @param orderTradeListReqVO
	 * @return
	 */
	@Override
	public PageResultData<OrderTradeSimpleResVO> pageWaitPayList(OrderTradeListReqVO orderTradeListReqVO){
		PageResultData<OrderTradeSimpleResVO> result = new PageResultData<>();

		if(orderTradeListReqVO ==null  || orderTradeListReqVO.getUserId()<=0){
			result.setList(new ArrayList<>());
			return result;
		}
		if(orderTradeListReqVO.getPageSize()<=0 || orderTradeListReqVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			orderTradeListReqVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(orderTradeListReqVO.getPageNum()<=0){
			orderTradeListReqVO.setPageNum(1);
		}
		OrderTradeExample orderTradeExample = new OrderTradeExample();
		OrderTradeExample.Criteria ca = orderTradeExample.or();
		orderTradeExample.setOrderByClause(" create_date desc");

		ca.andStatusInForAll(OrderTradeStateEnum.getRunPay());
		ca.andBuyUserIdEqualTo(orderTradeListReqVO.getUserId());

		BaseTableData baseTableData = findDataTableByExampleForPage(orderTradeExample,orderTradeListReqVO.getPageNum(), orderTradeListReqVO.getPageSize());
		if(baseTableData==null || CollectionUtils.isEmpty(baseTableData.getData())){
			return result;
		}
		List<OrderTradeSimpleResVO> dataList = new ArrayList<>(baseTableData.getData().size());
		List<OrderTrade> pList = baseTableData.getData();
		for(OrderTrade orderTrade : pList){
			Product product = productService.selectByPrimaryKey(orderTrade.getProductId());
			if (product == null) {
				logger.error("tradeOrderDetail 商品ID[{}]记录不存在",orderTrade.getProductId());
				continue;
			}
			User sellUser = userService.selectByPrimaryKey(orderTrade.getSellUserId());
			if (sellUser == null) {
				logger.error("pageTradeOrderList 交易订单号[{}] 卖家[{}]用户记录不存在",orderTrade.getTradeNo(),orderTrade.getSellUserId());
				continue;
			}
			OrderTradeSimpleResVO temp = new OrderTradeSimpleResVO();
			OrderTradeDetailTrade trade = initTradDetailForWaitPay(orderTrade);
			trade.setProductImg(product.getProductImg());
			trade.setProductName(product.getName());
			temp.setTrade(trade);

			OrderTradeUser orderTradeUser = new OrderTradeUser();
			orderTradeUser.setSellUserId(sellUser.getId());
			orderTradeUser.setSellUserNickName(sellUser.getNickname());
			temp.setOrderTradeUser(orderTradeUser);

			dataList.add(temp);
		}
		result.setList(dataList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}

	@Override
	public PageResultData<OrderTradeTradingVO> pageTradingOrderList(OrderTradeListReqVO orderTradeListReqVO) {
		PageResultData<OrderTradeTradingVO> result = new PageResultData<>();
		result.setList(new ArrayList<>());
		if(orderTradeListReqVO ==null  || orderTradeListReqVO.getUserId()<=0){
			return result;
		}
		User user = userService.selectByPrimaryKey(orderTradeListReqVO.getUserId());
		if (user == null) {
			return result;
		}
		if(orderTradeListReqVO.getPageSize()<=0 || orderTradeListReqVO.getPageSize()> StaticEntity.MAX_PAGE_SIZE){
			orderTradeListReqVO.setPageSize(StaticEntity.MAX_PAGE_SIZE);
		}
		if(orderTradeListReqVO.getPageNum()<=0){
			orderTradeListReqVO.setPageNum(1);
		}
		OrderTradeExample orderTradeExample = new OrderTradeExample();
		OrderTradeExample.Criteria ca = orderTradeExample.or();
		orderTradeExample.setOrderByClause(" create_date desc");

		ca.andStatusInForAll(OrderTradeStateEnum.getRunningStates());
		ca.andBuyUserIdEqualTo(orderTradeListReqVO.getUserId());
		ca.andNoWaitBuyUserIdForAll(orderTradeListReqVO.getUserId(),OrderTradeStateEnum.getRunPay());

		BaseTableData baseTableData = findDataTableByExampleForPage(orderTradeExample,orderTradeListReqVO.getPageNum(), orderTradeListReqVO.getPageSize());
		if(baseTableData==null || CollectionUtils.isEmpty(baseTableData.getData())){
			return result;
		}
		List<OrderTrade> dataList = baseTableData.getData();

		List<OrderTradeTradingVO> tradingList = new ArrayList<>(dataList.size());

		for(OrderTrade orderTrade : dataList){
			User otherUser = null;
			if(user.getId().equals(orderTrade.getBuyUserId())){
				otherUser = userService.selectByPrimaryKey(orderTrade.getSellUserId());
			}else{
				otherUser = userService.selectByPrimaryKey(orderTrade.getBuyUserId());
			}
			if(otherUser==null){
				continue;
			}
			OrderTradeTradingVO orderTradeTradingVO = OrderTradeMapService.initTradingVO(orderTrade,user);
			orderTradeTradingVO.setUserNickName(otherUser.getNickname());
			orderTradeTradingVO.setPhotoHead(otherUser.getPhotoHead());
			tradingList.add(orderTradeTradingVO);
		}
		result.setList(tradingList);
		result.setTotal(baseTableData.getRecordsTotal());
		return result;
	}

	@Override
	public boolean remindTrade(String tradNo) {
		OrderTrade orderTrade = new OrderTrade();
		orderTrade.setTradeNo(tradNo);
		orderTrade = selectByRecord(orderTrade);
		if (orderTrade == null) {
			return false;
		}
		if(orderTrade.getRemindPay()!=null && orderTrade.getRemindPay().equals(1)){
			return false;
		}
		User buyUser = userService.selectByPrimaryKey(orderTrade.getBuyUserId());
		if(buyUser==null || StringUtils.isEmpty(buyUser.getMobile())){
			return false;
		}
		if(!OrderTradeStateEnum.isRunPay(orderTrade.getStatus())){
			return false;
		}
		ResponseData result = smsService.send(buyUser.getMobile(),6);
		if(!ResponseStateEnum.SUCCESS.getStatus().equals(result.getStatus())){
			return false;
		}
		OrderTradeExample updateOrderTrade = new OrderTradeExample();
		OrderTradeExample.Criteria updateOrderCa = updateOrderTrade.or();
		updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());

		OrderTrade updateObj = new OrderTrade();
		updateObj.setRemindPay(1);
		int number  = updateByExampleSelective(updateObj,updateOrderTrade);
		return number>0?true:false;
	}

    @Override
    public boolean appealTrade(OrderTradeSearchVO orderTradeSearchVO) {
        OrderTrade orderTrade = new OrderTrade();
        orderTrade.setTradeNo(orderTradeSearchVO.getTradeNo());
        orderTrade = selectByRecord(orderTrade);
        if (orderTrade == null) {
            return false;
        }
        OrderTradeAppealExample example = new OrderTradeAppealExample();
        OrderTradeAppealExample.Criteria ca = example.or();
        ca.andTradeNoEqualToForAll(orderTradeSearchVO.getTradeNo());

        ca.andStatusEqualToForUpdate(0);
        List<OrderTradeAppeal> applealList = orderTradeAppealService.findListByExample(example);
        if(!CollectionUtils.isEmpty(applealList)){
           return  false;
        }
        OrderTradeAppeal orderTradeAppeal = new OrderTradeAppeal();
        orderTradeAppeal.setAppealNo(IdWork.getOrderCode("AP"));
        orderTradeAppeal.setTradeNo(orderTradeSearchVO.getTradeNo());
        orderTradeAppeal.setUserId(orderTradeSearchVO.getUserId());
        orderTradeAppeal.setStatus(0);
        orderTradeAppeal.setCrateTime(new Date());
        orderTradeAppeal.setUpdateTime(new Date());
        orderTradeAppeal.setRemark("用户点击申诉");
        int result = orderTradeAppealService.insertRecord(orderTradeAppeal);
        return result>0?true:false;
    }
	/**
	 * 格式化交易订单数据
	 * @param orderTrade
	 * @return
	 */
	private OrderTradeDetailTrade initTradDetailForWaitPay(OrderTrade orderTrade){
		if(orderTrade ==null){
			return null;
		}
		OrderTradeDetailTrade result = new OrderTradeDetailTrade();
		result.setTradeNo(orderTrade.getTradeNo());
		result.setAmount(orderTrade.getAmount());
		result.setCreateTime(DateUtil.foramtChinaFormat(orderTrade.getCreateDate()));
		result.setUpdateTime(DateUtil.foramtChinaFormat(orderTrade.getUpdateDate()));
		result.setOrderNum(orderTrade.getOrderNum());
		result.setRemindPay(orderTrade.getRemindPay());
		result.setSalePrice(orderTrade.getSalePrice());
		result.setStatus(orderTrade.getStatus());
		result.setPayTimeOut(getOverHour());
		result.setServiceFee(orderTrade.getServiceFee()==null?0:orderTrade.getServiceFee());
		result.setUnitPrice(orderTrade.getUnitPrice());
		return result;
	}

	/**
	 * 格式化正在交易数据
	 * @param orderTrade
	 * @return
	 */
	private OrderTradeDetailTrade initTradDetail(OrderTrade orderTrade){
		if(orderTrade ==null){
			return null;
		}
		OrderTradeDetailTrade result = initTradDetailForWaitPay(orderTrade);
		OrderPaymentTrade orderPaymentTrade = new OrderPaymentTrade();
		try{
			orderPaymentTrade.setTradeNo(orderTrade.getTradeNo());
			orderPaymentTrade = orderPaymentTradeService.selectByRecord(orderPaymentTrade);
		}catch (Exception e){
			e.printStackTrace();
		}

		if (orderPaymentTrade != null) {
			result.setPayNo(orderPaymentTrade.getPayNo());
			result.setPayTime(DateUtil.foramtChinaFormat(orderPaymentTrade.getPayTime()));
		}
		return result;
	}

	@SuppressWarnings("all")
	private int getOverHour(){
		int result = 0;
		SystemConfigExample example = new SystemConfigExample();
		SystemConfigExample.Criteria ca = example.or();
		ca.andTypeEqualTo(SystemConfigTypeEnum.TRADE_HOUR.getType());
		ca.andStateEqualTo(SystemConfigStateEnum.DEFAULT.getState());

		try{
			List<SystemConfig> list = systemConfigMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				logger.info("系统未配置订单过期数据");
				return result;
			}
			SystemConfig systemConfig = list.get(0);
			result = Integer.valueOf(String.valueOf(systemConfig.getValue()).trim());

		}catch (Exception e){
			e.printStackTrace();
			logger.error("获取交易订单关闭时长配置异常");
		}
		return result;
	}


	/**
	 * 获取服务费
	 * @return
	 */
	private String getServiceFee(){
		String result = "0" ;
		SystemConfigExample example = new SystemConfigExample();
		SystemConfigExample.Criteria ca = example.or();
		ca.andTypeEqualTo(SystemConfigTypeEnum.FEE_SERVICE.getType());
		ca.andStateEqualTo(SystemConfigStateEnum.DEFAULT.getState());

		try{
			List<SystemConfig> list = systemConfigMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				logger.info("系统未配置服务费");
				return result;
			}
			SystemConfig systemConfig = list.get(0);
			result =systemConfig.getValue();
		}catch (Exception e){
			e.printStackTrace();
			logger.error("获取服务费配置异常");
		}
		return result;
	}

	/**
	 * 锁定交易量
	 * @param orderAd
	 * @param addLockNum
	 * @return
	 */
	private boolean lockOrderAd(OrderAd orderAd,int addLockNum){
		if(orderAd==null || orderAd.getProductNum() ==null){
			return false;
		}
		if(orderAd.getId()<=0){
			return false;
		}
		int oldTradingNm  = orderAd.getTradingNum()==null?0:orderAd.getTradingNum();
		OrderAd updateOrderAd = new OrderAd();
		updateOrderAd.setUpdateDate(new Date());

		if(addLockNum>0){
			updateOrderAd.setTradingNum(oldTradingNm+addLockNum);
			int residueNum = orderAd.getProductNum()-orderAd.getTradedNum()-updateOrderAd.getTradingNum();
			if(residueNum<0){
				return false;
			}
			if(residueNum<addLockNum){
				return false;
			}
		}else{
			updateOrderAd.setTradingNum(oldTradingNm+addLockNum);
			if(updateOrderAd.getTradingNum()<0){
				return false;
			}
		}
		OrderAdExample example = new OrderAdExample();
		OrderAdExample.Criteria ca = example.or();
		ca.andIdEqualToForUpdate(orderAd.getId());
		ca.andTradingNumEqualToForUpdate(oldTradingNm);
		if(orderAdService.updateByExampleSelective(updateOrderAd,example)<=0){
			return  false;
		}
		return true;

	}

    /**
     * 插入交易券记录
     * @param productAll 商品信息
     * @param fromAddress 收方地址
     * @param toAddress
     * @param number
     * @param bizType
     * @return
     */
	private BcTxRecord insertBx(ProductAll productAll,String fromAddress,String toAddress,int number,int bizType){
		BcTxRecord bcTxRecord = new BcTxRecord();
		bcTxRecord.setFromAddress(fromAddress);
		bcTxRecord.setToAddress(toAddress);
		bcTxRecord.setTransferAmount(String.valueOf(number));
		bcTxRecord.setTicketid(productAll.getTicketid());
		bcTxRecord.setTrancheid(productAll.getTrancheid());
		bcTxRecord.setAssetCode(productAll.getAssetCode());
		bcTxRecord.setAssetIssuer(productAll.getAssetIssuer());
		bcTxRecord.setContractAddress(productAll.getContractAddress());
		bcTxRecord.setAssetType(1);
		bcTxRecord.setTxStatus(0);
		bcTxRecord.setBizType(bizType);
		bcTxRecord.setCreateTime(new Date());
		bcTxRecord.setUpdateTime(new Date());
		bcTxRecord.setOptMetadata("用户地址:"+fromAddress+" 转往"+toAddress);
		bcTxRecordMapper.insertSelective(bcTxRecord);
		return bcTxRecord;
	}

	/**
	 * 插入交易流水表数据
	 * @param orderTrade
	 */
	private OrderPaymentTrade insertOrderPayment(OrderTrade orderTrade,boolean ispass,String payNo,int payType){

		// 插入支付记录
		OrderPaymentTrade orderPaymentTrade = new OrderPaymentTrade();
		orderPaymentTrade.setTradeNo(orderTrade.getTradeNo());
		orderPaymentTrade.setPayNo(payNo);
		orderPaymentTrade.setPayType(payType);
		orderPaymentTrade.setPayeeUser(String.valueOf(orderTrade.getBuyUserId()));
		orderPaymentTrade.setPayeeUser(String.valueOf(orderTrade.getSellUserId()));
		orderPaymentTrade.setProductId(orderTrade.getProductId());
		orderPaymentTrade.setOrderNum(orderTrade.getOrderNum());
		orderPaymentTrade.setServiceFee(orderTrade.getServiceFee());
		orderPaymentTrade.setAmount(orderTrade.getAmount());
		orderPaymentTrade.setRemarks("回调");
		orderPaymentTrade.setCreateDate(new Date());
		orderPaymentTrade.setUpdateDate(new Date());
		int state = ispass?OrderPaymentTradeStateEnum.PAY_SUCCESS.getState() : OrderPaymentTradeStateEnum.PAY_FAIL.getState();
		orderPaymentTrade.setStatus(state);

		Long orderPaymentTradeId = orderPaymentTradeService.insertOrderPaymentTradeReturnId(orderPaymentTrade);
		if(orderPaymentTradeId<=0){
		    logger.error("插入交易支付记录失败 {}",orderPaymentTrade.toString());
		    return null;
        }
        insertOrderTradePayMentLog(orderPaymentTrade.getId(),-1,state,"支付回调日志插入");
		return orderPaymentTrade;
	}


	/**
	 * 通过订单号获取订单数据
	 * @param code
	 * @return
	 */
	private OrderAd getOrderAdForCode(String code){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		OrderAd result = null;
		List<OrderAd> list =  orderAdService.findListByExample(getExampleFromUserAndCode(code,0));
		if(!CollectionUtils.isEmpty(list)){
			result = list.get(0);
		}
		return result;
	}



	/**
	 * 创建求购订单
	 * @param searchAdOrderVO
	 * @param user
	 * @return
	 */
	private  boolean  lockUserStock(long productId,long userId,int number){
		if(productId<=0){
			return false;
		}
		Product product = productService.selectByPrimaryKey(productId);
		if(!ProductUtil.isEffect(product)){
			return false;
		}
		boolean updateStockResult = false;
		try {
			updateStockResult = userProductStockService.updateStock(product.getId(),userId,number);
		}catch (Exception e){
			e.printStackTrace();
		}
		return updateStockResult;
	}


	/**
	 * 插入交易日志
	 * @param trandId
	 * @param oldState
	 * @param newState
	 * @param remark
	 */
	private void  insertOrderTradeLog(long trandId,int oldState,int newState,String remark){
		OrderTradeLog orderTradeLog = new OrderTradeLog();
		orderTradeLog.setOldStatus(oldState);
		orderTradeLog.setNewStatus(newState);
		orderTradeLog.setOrderTradeId(trandId);
		orderTradeLog.setCreateDate(new Date());
		orderTradeLog.setRemarks(remark);
		orderLogService.save(LogTypeEnum.TRADE,orderTradeLog);
	}


    /**
     * 插入交易流水日志
     * @param trandId
     * @param oldState
     * @param newState
     * @param remark
     */
    private void  insertOrderTradePayMentLog(long trandId,int oldState,int newState,String remark){
        // 插入支付日志记录
        OrderPaymentTradeLog orderPaymentTradeLog = new OrderPaymentTradeLog();
        orderPaymentTradeLog.setOrderPayTradeId(trandId);
        orderPaymentTradeLog.setCreateDate(new Date());
        orderPaymentTradeLog.setOldStatus(oldState);
        orderPaymentTradeLog.setNewStatus(newState);
        orderPaymentTradeLog.setRemarks(remark);
        orderLogService.save(LogTypeEnum.TRADE_PAYMENT,orderPaymentTradeLog);
    }




}
package org.eq.modules.trade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.auth.service.UserWalletService;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BcTxRecordExample;
import org.eq.modules.enums.OrderTradeBlockChainStateEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.enums.OrderTradeTypeEnum;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductBlockchain;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductBlockchainService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.OrderPaymentTrade;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.eq.modules.trade.exception.TradeOrderException;
import org.eq.modules.trade.service.OrderTradeLogService;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.wallet.entity.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 订单交易ServiceImpl
 *
 * @author yufei.sun
 * @version 1.0
 */
@Service
@Transactional
@AutowiredService
public class OrderTradeServiceImpl extends ServiceImplExtend<OrderTradeMapper, OrderTrade, OrderTradeExample> implements OrderTradeService {


    @Autowired
    private ProductService productService;

    @Autowired
    private OrderTradeLogService orderTradeLogService;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private BcTxRecordMapper bcTxRecordMapper;

    @Autowired
    private ProductBlockchainService productBlockchainService;

    @Override
    public OrderTradeExample getExampleFromEntity(OrderTrade orderTrade, Map<String, Object> params) {
        OrderTradeExample example = new OrderTradeExample();
        OrderTradeExample.Criteria ca = example.or();
        if (orderTrade == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        Date beginCreateDate = null;
        Date endCreateDate = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
            beginCreateDate = (Date) params.get("beginCreateDate");
            endCreateDate = (Date) params.get("endCreateDate");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (orderTrade.getSellUserId() != null) {
            ca.andSellUserIdEqualTo(orderTrade.getSellUserId());
        }
        if (orderTrade.getBuyUserId() != null) {
            ca.andBuyUserIdEqualTo(orderTrade.getBuyUserId());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getAdNo())) {
            ca.andAdNoEqualTo(orderTrade.getAdNo());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getTradeNo())) {
            ca.andTradeNoEqualTo(orderTrade.getTradeNo());
        }
        if (orderTrade.getProductId() != null) {
            ca.andProductIdEqualTo(orderTrade.getProductId());
        }
        if (orderTrade.getOrderNum() != null) {
            ca.andOrderNumEqualTo(orderTrade.getOrderNum());
        }
        if (orderTrade.getType() != null) {
            ca.andTypeEqualTo(orderTrade.getType());
        }
        if (orderTrade.getBlockchainStatus() != null) {
            ca.andBlockchainStatusEqualTo(orderTrade.getBlockchainStatus());
        }
        if (orderTrade.getSalePrice() != null) {
            ca.andSalePriceEqualTo(orderTrade.getSalePrice());
        }
        if (orderTrade.getUnitPrice() != null) {
            ca.andUnitPriceEqualTo(orderTrade.getUnitPrice());
        }
        if (orderTrade.getAmount() != null) {
            ca.andAmountEqualTo(orderTrade.getAmount());
        }
        if (orderTrade.getRemindPay() != null) {
            ca.andRemindPayEqualTo(orderTrade.getRemindPay());
        }
        if (orderTrade.getFinishTime() != null) {
            ca.andFinishTimeEqualTo(orderTrade.getFinishTime());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getRemarks())) {
            ca.andRemarksEqualTo(orderTrade.getRemarks());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getCancelDesc())) {
            ca.andCancelDescEqualTo(orderTrade.getCancelDesc());
        }
        if (orderTrade.getCreateDate() != null) {
            ca.andCreateDateEqualTo(orderTrade.getCreateDate());
        }
        if (orderTrade.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(orderTrade.getUpdateDate());
        }
        if (orderTrade.getTxId() != null) {
            ca.andTxIdEqualTo(orderTrade.getTxId());
        }
        if (beginCreateDate != null) {
            ca.andCreateDateGreaterThanOrEqualTo(beginCreateDate);
        }
        if (endCreateDate != null) {
            ca.andCreateDateLessThan(endCreateDate);
        }
        if (orderTrade.getProductName() != null) {
            ca.andProductNameLikeForAll(orderTrade.getProductName());
        }

        // 设置商品的查询条件
        //setProductQueryContion(ca, orderTrade);

        return example;
    }


    @Override
    public boolean voucherTrade(long tradeId, SysUser sysUser) {
        if(tradeId<=0 || sysUser ==null){
            return false;
        }
        OrderTrade orderTrade = selectByPrimaryKey(tradeId);
        if(orderTrade==null ){
            return false;
        }
        if(orderTrade.getStatus()!=OrderTradeStateEnum.VOUCHER_ING.getState() && orderTrade.getBlockchainStatus()!=OrderTradeBlockChainStateEnum.FAIL.getState()){
            return false;
        }
        if(orderTrade.getTxId()<=0){
            return false;
        }
        BcTxRecord bcTxRecord = bcTxRecordMapper.selectByPrimaryKey(orderTrade.getTxId());
        if(bcTxRecord==null){
            return false;
        }
        BcTxRecord update = new BcTxRecord();
        update.setId(bcTxRecord.getId());
        update.setTxStatus(0);
        update.setOptMetadata("管理员重置");

        BcTxRecordExample example = new BcTxRecordExample();
        BcTxRecordExample.Criteria ca = example.or();
        ca.andIdEqualTo(bcTxRecord.getId());
        ca.andTxStatusEqualTo(bcTxRecord.getTxStatus());

        int updateResut = bcTxRecordMapper.updateByExampleSelective(update,example);
        if(updateResut<0){
            return  false;
        }

        Integer oldStatus = orderTrade.getStatus();
        OrderTradeExample updateOrderTrade = new OrderTradeExample();
        OrderTradeExample.Criteria updateOrderCa = updateOrderTrade.or();
        updateOrderCa.andIdEqualToForUpdate(orderTrade.getId());
        updateOrderCa.andStatusEqualToForUpdate(oldStatus);

        OrderTrade updateObj = new OrderTrade();
        updateObj.setStatus(OrderTradeStateEnum.VOUCHER_ING.getState());
        updateObj.setBlockchainStatus(OrderTradeBlockChainStateEnum.PROCESSING.getState());
        updateObj.setUpdateDate(new Date());
        int number = updateByExampleSelective(updateObj,updateOrderTrade);
        if(number<=0){
           return  false;
        }
        StringBuilder remark = new StringBuilder().append("管理员:").append(sysUser.getLnm()).append("充置放券");
        insertOrderTradeLog(orderTrade.getId(),oldStatus,updateObj.getStatus(),remark.toString());
        return number>0?true:false;
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
        orderTradeLogService.insertSelective(orderTradeLog);
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
    @SuppressWarnings("all")
    private BcTxRecord insertBx(ProductBlockchain productBlockchain, String fromAddress, String toAddress, int number, int bizType){
        BcTxRecord bcTxRecord = new BcTxRecord();
        bcTxRecord.setFromAddress(fromAddress);
        bcTxRecord.setToAddress(toAddress);
        bcTxRecord.setTransferAmount(String.valueOf(number));
        bcTxRecord.setTicketid(productBlockchain.getTicketid());
        bcTxRecord.setTrancheid(productBlockchain.getTrancheid());
        bcTxRecord.setAssetCode(productBlockchain.getAssetCode());
        bcTxRecord.setAssetIssuer(productBlockchain.getAssetIssuer());
        bcTxRecord.setContractAddress(productBlockchain.getContractAddress());
        bcTxRecord.setAssetType(1);
        bcTxRecord.setTxStatus(0);
        bcTxRecord.setBizType(bizType);
        bcTxRecord.setCreateTime(new Date());
        bcTxRecord.setUpdateTime(new Date());
        bcTxRecord.setOptMetadata("管理员重置:"+fromAddress+" 转往"+toAddress);
        bcTxRecordMapper.insertSelective(bcTxRecord);
        return bcTxRecord;
    }



}
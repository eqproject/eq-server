package org.eq.modules.order.biz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.*;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.support.dao.SystemConfigMapper;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.entity.SystemConfigExample;
import org.eq.modules.trade.dao.OrderPaymentTradeLogMapper;
import org.eq.modules.trade.dao.OrderPaymentTradeMapper;
import org.eq.modules.trade.dao.OrderTradeLogMapper;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @Title: OrderTradePayExpireBiz
 * @Copyright: Copyright (c) 2018
 * @Description: 关闭交易(支付超时)
 * @Company: 123.com
 * @Created on 2019/6/3下午11:43
 */
@SuppressWarnings("all")
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderTradePayExpireBiz {

    protected final Logger logger = LoggerFactory.getLogger(OrderTradePayExpireBiz.class);


    /**
     * 系统配置表
     */
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    /**
     * 订单交易
     */
    private final OrderTradeMapper orderTradeMapper;

    /**
     * 交易日志
     */
    private final OrderTradeLogMapper orderTradeLogMapper;
    private final OrderPaymentTradeMapper orderPaymentTradeMapper;
    private final OrderPaymentTradeLogMapper orderPaymentTradeLogMapper;
    private final OrderAdMapper orderAdMapper;


    /**
     * 用户库存
     */
    @Autowired
    private UserProductStockMapper userProductStockMapper;


    /**
     * 查询交易订单
     * @return
     */
    public List<OrderTrade> searchPayExpireOrderTrade() {
        int hour = getOverHour();
        if(hour<=0){
            logger.error(" 获取过期交易订单配置时长异常，请持续关注");
            return null;
        }
        List<OrderTrade> result = new ArrayList<>();
        OrderTradeExample example = new OrderTradeExample();
        OrderTradeExample.Criteria ca = example.or();
        List<Integer> status = new ArrayList<>();
        status.add(OrderTradeStateEnum.WAIT_PAY.getState());
        ca.andStatusInForAll(status);
        ca.andCreateDateLessThanForALL(DateUtil.beforeDateHour(DateUtil.getNowTime(),hour));
        result.addAll(orderTradeMapper.selectByExample(example));

        example = new OrderTradeExample();
        ca = example.or();
        status = new ArrayList<>();
        status.add(OrderTradeStateEnum.PAY_ING.getState());
        status.add(OrderTradeStateEnum.PAY_FAIL.getState());
        ca.andStatusInForAll(status);
        ca.andUpdateDateLessThanForAll(DateUtil.beforeDateHour(DateUtil.getNowTime(),hour));
        result.addAll(orderTradeMapper.selectByExample(example));
        return result;
    }

    /**
     * 更新支付过期的交易相关状态+减少广告订单交易中的数量
     * @param orderTrade
     */
    public void updateOrderTradeStatus(OrderTrade orderTrade) {
        Date nowDate = DateUtil.getNowTime();
        Integer oldStatus = orderTrade.getStatus();
        Integer newStatus = OrderTradeStateEnum.CANCEL_PAY_TIMEOUT.getState();

        OrderTrade updateOrderTrade  = new OrderTrade();
        updateOrderTrade.setStatus(newStatus);
        updateOrderTrade.setUpdateDate(nowDate);
        updateOrderTrade.setFinishTime(new Date());
        OrderTradeExample whereExample = new OrderTradeExample();
        OrderTradeExample.Criteria ca = whereExample.or();
        ca.andStatusEqualToForUpdate(oldStatus);
        ca.andIdEqualToForUpdate(orderTrade.getId());


        int result = orderTradeMapper.updateByExampleSelective(updateOrderTrade,whereExample);
        if(result<=0){
            logger.error("更新交易订单状态失败");
            return ;
        }

        OrderTradeLog orderTradeLog = new OrderTradeLog();
        orderTradeLog.setOldStatus(oldStatus);
        orderTradeLog.setNewStatus(newStatus);
        orderTradeLog.setCreateDate(nowDate);
        orderTradeLog.setOrderTradeId(orderTrade.getId());
        orderTradeLog.setRemarks("定时任务关闭交易(支付超时/订单超时)");
        result = orderTradeLogMapper.insert(orderTradeLog);
        if(result<=0){
            logger.error("插入交易订单状态日志记录失败");
        }
        //取消交易订单库存
        cancelTradeStock(orderTrade);

       /* OrderPaymentTradeExample orderPaymentTradeExample = new OrderPaymentTradeExample();
        OrderPaymentTradeExample.Criteria criteria = orderPaymentTradeExample.or();
        criteria.andTradeNoEqualTo(orderTrade.getTradeNo());
        List<OrderPaymentTrade> orderPaymentTradeList = orderPaymentTradeMapper.selectByExample(orderPaymentTradeExample);
        if (CollectionUtils.isNotEmpty(orderPaymentTradeList)) {
            orderPaymentTradeList.forEach(orderPaymentTrade->{

                Integer paymentOldStatus = orderPaymentTrade.getStatus();

                OrderPaymentTradeExample orderTradeWhereExample = new OrderPaymentTradeExample();
                OrderPaymentTradeExample.Criteria orderTradeCa = orderTradeWhereExample.or();
                orderTradeCa.andStatusEqualTo(paymentOldStatus);
                orderTradeCa.andIdEqualTo(orderPaymentTrade.getId());

                OrderPaymentTrade updateObj = new OrderPaymentTrade();
                updateObj.setStatus(OrderPaymentTradeStateEnum.PAY_CANCEL.getState());
                updateObj.setUpdateDate(nowDate);
                int udpateResult = orderPaymentTradeMapper.updateByExampleSelective(updateObj,orderTradeWhereExample);

                StringBuffer remark = new StringBuffer("定时任务关闭交易订单，交易流水开始关闭");
                if(udpateResult>0){
                    remark.append(",关闭成功");
                }else{
                    remark.append(",关闭失败");
                }
                OrderPaymentTradeLog orderPaymentTradeLog = new OrderPaymentTradeLog();
                orderPaymentTradeLog.setCreateDate(nowDate);
                orderPaymentTradeLog.setOldStatus(paymentOldStatus);
                orderPaymentTradeLog.setNewStatus(OrderPaymentTradeStateEnum.PAY_CANCEL.getState());
                orderPaymentTradeLog.setOrderPayTradeId(orderTrade.getId());
                orderPaymentTradeLog.setRemarks(remark.toString());
                orderPaymentTradeLogMapper.insertSelective(orderPaymentTradeLog);
            });
        }*/



    }


    private int getOverHour(){
        int result = -1;
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
     * 只有在交易订单关闭的情况下方可进行此操作
     * @param orderTrade
     */
    public void cancelTradeStock(OrderTrade orderTrade){
        OrderAdExample orderAdExample = new OrderAdExample();
        OrderAdExample.Criteria criteria1 = orderAdExample.or();
        criteria1.andOrderNoEqualToForAll(orderTrade.getAdNo());
        List<OrderAd> orderAdList =  orderAdMapper.selectByExample(orderAdExample);
        OrderAd orderAd = null;
        if(!CollectionUtils.isEmpty(orderAdList)){
            orderAd = orderAdList.get(0);
        }
        if(orderAd==null ){
            logger.error("释放交易订单{} 在途库存，查询失败",orderTrade.getTradeNo());
            return;
        }

        OrderAdExample orderAdWhereExample = new OrderAdExample();
        OrderAdExample.Criteria orderAdCa = orderAdWhereExample.or();
        orderAdCa.andIdEqualToForUpdate(orderAd.getId());
        orderAdCa.andTradingNumEqualToForUpdate(orderAd.getTradingNum());

        Integer num = orderAd.getTradingNum() - orderTrade.getOrderNum();
        OrderAd updateObj = new OrderAd();
        updateObj.setTradingNum(num);
        updateObj.setUpdateDate(new Date());
        int update = 0;
        try{
            update = orderAdMapper.updateByExampleSelective(updateObj,orderAdWhereExample);
        }catch (Exception e){
            logger.error("更新交易订单锁定量失败",e);
        }
        if(update<=0){
            logger.error("更新交易订单锁定量失败,{} 执行条件为:正在交易量为 :{}",updateObj.toString(),orderAd.getTradingNum());
        }
        boolean release = true;
        long userId = 0;
        if(orderAd.getType() == OrderAdTypeEnum.ORDER_SALE.getType()){
            // 出售订单，库存广告订单锁定。广告订单是取消状态。说明广告取消时广告库存未释放
            if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()){
                release = updateStock(orderAd.getUserId(),orderAd.getProductId(),-orderTrade.getOrderNum());
                userId = orderAd.getUserId();
            }
        }else{//求购广告 广告不锁定库存，但是交易订单锁定
            release = updateStock(orderTrade.getSellUserId(),orderTrade.getProductId(),-orderTrade.getOrderNum());
            userId = orderTrade.getSellUserId();
        }
        if(!release){
            logger.error("用户:{} 释放商品ID:{},释放量:{}",userId,orderTrade.getProductId(),orderTrade.getOrderNum());
        }

    }


    /**
     * 更改用户库存信息
     * @param userId
     * @param productId
     * @param number
     * @return
     */
    private boolean updateStock(long userId,long productId,int number){

        List<UserProductStock> userProductStockList = userProductStockMapper.selectByExample(getUserProductStockExapple(userId,productId));
        if(CollectionUtils.isEmpty(userProductStockList)){
            return false;
        }
        UserProductStock userProductStock = userProductStockList.get(0);
        int lockNum = userProductStock.getLockedNum();

        UserProductStock update = new UserProductStock();
        update.setLockedNum(userProductStock.getLockedNum()+number);
        update.setUpdateDate(new Date());

        UserProductStockExample example = new UserProductStockExample();
        UserProductStockExample.Criteria ca = example.or();
        ca.andIdEqualToForSimple(userProductStock.getId());
        ca.andLockNumForUpdate(lockNum);
        int updateResult = userProductStockMapper.updateByExampleSelective(update,example);
        if(updateResult>0){
            return true;
        }
        return false;
    }

    private UserProductStockExample getUserProductStockExapple(Long userId,Long productId) {
        UserProductStockExample example = new UserProductStockExample();
        UserProductStockExample.Criteria ca = example.or();
        if(userId!=null){
            ca.andUserIdEqualTo(userId);
        }
        if(productId!=null){
            ca.andProductIdEqualTo(productId);
        }
        return example;
    }





}

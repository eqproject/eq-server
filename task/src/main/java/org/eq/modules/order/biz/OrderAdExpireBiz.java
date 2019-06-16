package org.eq.modules.order.biz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.*;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.product.biz.ProductBiz;
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
 * 广告订单服务
 * @author  kaka
 * @date  20190614
 */
@SuppressWarnings("all")
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderAdExpireBiz {

    protected final Logger logger = LoggerFactory.getLogger(OrderAdExpireBiz.class);

    /**
     * 广告
     */
    @Autowired
    private OrderAdMapper orderAdMapper;

    /**
     * 广告日志
     */
    @Autowired
    private OrderAdLogMapper orderAdLogMapper;


    /**
     * 用户库存
     */
    @Autowired
    private UserProductStockMapper userProductStockMapper;

    /**
     * 系统配置表
     */
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    /**
     * 查询已经处于过期的
     * @return
     */
    public List<OrderAd> searchExpireOrderAd() {

        List<OrderAd> result= new ArrayList<>();
        SystemConfigExample example = new SystemConfigExample();
        SystemConfigExample.Criteria ca = example.or();
        ca.andTypeEqualTo(SystemConfigTypeEnum.ORDER_CLOSE_HOUR.getType());
        ca.andStateEqualTo(SystemConfigStateEnum.DEFAULT.getState());


        List<SystemConfig> list = systemConfigMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(list)){
            logger.info("系统未配置订单过期数据");
            return result;
        }
        SystemConfig systemConfig = list.get(0);
        int days  = Integer.valueOf(systemConfig.getValue());

        OrderAdExample orderAdexample = new OrderAdExample();
        OrderAdExample.Criteria orderCa = orderAdexample.or();
        List<Integer> status = new ArrayList<>();
        status.add(OrderAdStateEnum.ORDER_TRADEING.getState());

        orderCa.andStatusInForAll(status);
        orderCa.andCreateDateLessThanOrEqualToForAll(DateUtil.beforeDateDay(DateUtil.getNowTime(),days));
        return orderAdMapper.selectByExample(orderAdexample);
    }

    /**
     * 关闭订单
     * @param orderTrade
     */
    public void updateOrderAdStatus(OrderAd orderAd) {
        if(orderAd==null){
            return;
        }
        cacelOrderAd(orderAd.getId(),"定时任务下线过期订单");

    }


    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public  boolean cacelOrderAd(long orderId,String cancelDesc) {
        if(orderId<=0){
            return false;
        }
        OrderAd orderAd = orderAdMapper.selectByPrimaryKey(orderId);
        if(orderAd==null){
            return false;
        }
        if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()){
            return true;
        }
        if(OrderAdStateEnum.isOverState(orderAd.getStatus())){
            return false;
        }

        OrderAd updateOrder = new OrderAd();
        updateOrder.setStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        updateOrder.setUpdateDate(new Date());
        updateOrder.setCancelDesc(cancelDesc);
        int updateResult = orderAdMapper.updateByExampleSelective(updateOrder,getExampleFromEntity(orderAd.getStatus(),orderAd.getId()));
        if(updateResult<=0){
            return false;
        }
        StringBuffer remarks = new StringBuffer();
        if(orderAd.getType()== OrderAdTypeEnum.ORDER_SALE.getType()){
            int number  = orderAd.getProductNum() -orderAd.getTradedNum()  - orderAd.getTradingNum();
            boolean stockResult  = updateStock(orderAd.getUserId(),orderAd.getProductId(),-number);
            if(stockResult){
                remarks.append("取消成功,").append("退回库存成功,应退:").append(number).append(",执行快照为:").append(orderAd.toString());
            }else{
                remarks.append("取消失败,").append("退回库存失败,应退回:").append(number).append(",执行快照为:").append(orderAd.toString());
            }
        }else{
            remarks.append("取消成功,").append("执行快照为:").append(orderAd.toString());
        }
        OrderAdLog orderAdLog = new OrderAdLog();
        orderAdLog.setCreateDate(new Date());
        orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        orderAdLog.setOldStatus(orderAd.getStatus());
        orderAdLog.setOrderAdId(orderAd.getId());
        orderAdLog.setRemarks(remarks.toString());
        try{
            orderAdLogMapper.insert(orderAdLog);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("插入订单操作日志记录数据出错 {}",orderAdLog.toString());
        }
        return true ;
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


    /**
     * 获取有效的广告订单
     * @param productId
     * @return
     */
    public  OrderAdExample getEffectOrderExample(long productId) {
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();
        if(productId>0){
            ca.andProductIdEqualToForAll(productId);
        }
        List<Integer> states = new ArrayList<>();
        states.add(OrderAdStateEnum.ORDER_TRADEING.getState());
        states.add(OrderAdStateEnum.ORDER_DEFAULT.getState());
        ca.andStatusInForAll(states);
        return example;
    }


    /**
     * 获取简单查询实体
     * @param statue
     * @param id
     * @return
     */
    private OrderAdExample getExampleFromEntity(int statue,long id) {
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();
        ca.andIdEqualToForUpdate(id);
        ca.andStatusEqualToForUpdate(statue);
        return example;
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

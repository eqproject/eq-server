package org.eq.modules.order.biz;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.enums.OrderTradeBlockChainStateEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.enums.OrderTransferStateEnum;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.entity.OrderTransferExample;
import org.eq.modules.order.entity.OrderTransferLog;
import org.eq.modules.trade.dao.OrderTradeLogMapper;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.modules.trade.entity.OrderTradeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 出售交易订单回调
 * @author  kaka
 *
 * @version 2019/6/10
 */
@Component
public class OrderTradeSellCallback extends AbstractTaskCallBack {

    private static Logger logger = LoggerFactory.getLogger(OrderTradeSellCallback.class);


    @Autowired
    private static OrderTradeMapper orderTradeMapper;
    @Autowired
    private static OrderTradeLogMapper orderTradeLogMapper;

    public OrderTradeSellCallback() {
        super(BcTxRecordBizTypeEnum.SELL.getCode());
    }


    @Override
    public void success(String txId) {
        OrderTrade orderTrade =  getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = opTrade(orderTrade,true);
        if(!updateResult){
            logger.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }
    }

    @Override
    public void fail(String txId) {
        OrderTrade orderTrade =  getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = opTrade(orderTrade,false);
        if(!updateResult){
            logger.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }
    }

    /**
     * 通过TXid 获取订单信息
     * @param txId
     * @return
     */
    public static OrderTrade getOrderTradeByTxId(String txId){
        OrderTradeExample orderTradeExample = new OrderTradeExample();
        OrderTradeExample.Criteria ca = orderTradeExample.or();
        ca.andTxIdEqualTo(Long.valueOf(txId));
        List<OrderTrade> result = orderTradeMapper.selectByExample(orderTradeExample);
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    /**
     * 处理转让订单
     * @param orderTrade
     * @param isSuccess
     * @return
     */
    public static boolean opTrade(OrderTrade orderTrade,boolean isSuccess){
        if(orderTrade == null || orderTrade.getId()<=0){
            return false;
        }
        int oldTradeState = orderTrade.getStatus();
        int newTradeState =isSuccess? OrderTradeStateEnum.LOAN_ING.getState() : OrderTradeStateEnum.VOUCHER_ING.getState();

        OrderTradeExample orderTradeExample = new OrderTradeExample();
        OrderTradeExample.Criteria ca = orderTradeExample.or();
        ca.andIdEqualToForUpdate(orderTrade.getId());
        ca.andStatusEqualToForUpdate(oldTradeState);

        OrderTrade updateObj  = new OrderTrade();
        updateObj.setStatus(newTradeState);
        updateObj.setUpdateDate(new Date());
        updateObj.setBlockchainStatus(OrderTradeBlockChainStateEnum.FAIL.getState());
        if(isSuccess){
            updateObj.setBlockchainStatus(OrderTradeBlockChainStateEnum.SUCCESS.getState());
        }
        int updateResult = orderTradeMapper.updateByExampleSelective(updateObj,orderTradeExample);
        if(updateResult<=0){
            return false;
        }
        OrderTradeLog orderTradeLog = new OrderTradeLog();
        orderTradeLog.setOldStatus(oldTradeState);
        orderTradeLog.setNewStatus(newTradeState);
        orderTradeLog.setOrderTradeId(orderTrade.getId());
        orderTradeLog.setCreateDate(new Date());
        orderTradeLog.setRemarks("区块链接口回调结果。返回"+isSuccess +", 更新交易表状态");
        orderTradeLogMapper.insertSelective(orderTradeLog);
        return true;
    }
}

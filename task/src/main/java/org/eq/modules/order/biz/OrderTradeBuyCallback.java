package org.eq.modules.order.biz;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.enums.OrderTradeBlockChainStateEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
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
 * 购买交易订单回调
 * @author  kaka
 *
 * @version 2019/6/10
 */
@Component
public class OrderTradeBuyCallback extends AbstractTaskCallBack {

    private static Logger logger = LoggerFactory.getLogger(OrderTradeBuyCallback.class);

    public OrderTradeBuyCallback() {
        super(BcTxRecordBizTypeEnum.BUY.getCode());
    }


    @Override
    public void success(String txId) {
        OrderTrade orderTrade =  OrderTradeSellCallback.getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = OrderTradeSellCallback.opTrade(orderTrade,true);
        if(!updateResult){
            logger.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }
    }

    @Override
    public void fail(String txId) {
        OrderTrade orderTrade =  OrderTradeSellCallback.getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = OrderTradeSellCallback.opTrade(orderTrade,false);
        if(!updateResult){
            logger.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }
    }
}

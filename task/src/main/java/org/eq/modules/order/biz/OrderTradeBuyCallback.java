package org.eq.modules.order.biz;

import lombok.extern.slf4j.Slf4j;
import org.eq.basic.common.util.WebClientUtil;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.trade.entity.OrderTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 购买交易订单回调
 * @author  kaka
 *
 * @version 2019/6/10
 */
@Slf4j
@Component
public class OrderTradeBuyCallback extends AbstractTaskCallBack {
    public OrderTradeBuyCallback() {
        super(BcTxRecordBizTypeEnum.BUY.getCode());
    }
    @Autowired
    private OrderTradeBiz biz;

    @Override
    public void success(String txId) {
        OrderTrade orderTrade =  biz.getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = biz.opTrade(orderTrade,true);
        if(!updateResult){
            log.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }else{
            sendLoan(orderTrade);
        }
    }

    @Override
    public void fail(String txId) {
        OrderTrade orderTrade =  biz.getOrderTradeByTxId(txId);
        if(orderTrade ==null){
            return ;
        }
        boolean updateResult = biz.opTrade(orderTrade,false);
        if(!updateResult){
            log.error("更新售卖交易表数据失败,售卖交易表Id:{}",orderTrade.getId());
        }
    }

    public void sendLoan(OrderTrade orderTrade){
        String url= "http://120.27.71.34:8001/api/test/loan";
        Map<String,Object> params = new HashMap<>();
        params.put("tradeNo",orderTrade.getTradeNo());
        params.put("state",1);
        try{
            String response = WebClientUtil.syncPostByForm(url,params);
            System.out.println(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

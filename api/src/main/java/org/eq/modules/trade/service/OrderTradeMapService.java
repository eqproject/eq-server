package org.eq.modules.trade.service;

import org.eq.basic.common.util.DateUtil;
import org.eq.modules.auth.entity.User;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.vo.OrderTradeTradingVO;
import org.springframework.stereotype.Service;

/**
 * 处理外部与内部映射关系的服务
 * @author  kaka
 * @date  20190622
 */
public class OrderTradeMapService {



    /**
     * 格式化基本交易数据
     * @param orderTrade
     * @param user
     * @return
     */
    public static OrderTradeTradingVO initTradingVO(OrderTrade orderTrade,User user){
        if(orderTrade ==null || user ==null ){
            return null;
        }
        OrderTradeTradingVO result = new OrderTradeTradingVO();
        result.setTradeNo(orderTrade.getTradeNo());
        result.setAmount(orderTrade.getAmount());
        result.setOrderNum(orderTrade.getOrderNum());
        result.setRemindPay(orderTrade.getRemindPay());
        result.setSalePrice(orderTrade.getSalePrice());
        result.setStatus(orderTrade.getStatus());
        result.setServiceFee(orderTrade.getServiceFee()==null?0:orderTrade.getServiceFee());
        result.setUnitPrice(orderTrade.getUnitPrice());
        result.setCreateTime(DateUtil.foramtChinaFormat(orderTrade.getCreateDate()));
        result.setUpdateTime(DateUtil.foramtChinaFormat(orderTrade.getUpdateDate()));
        result.setProductImg(orderTrade.getProductImg());
        result.setProductName(orderTrade.getProductName());
        boolean isbuy = false;
        if(user.getId().equals(orderTrade.getBuyUserId())){
            isbuy = true;
        }
        result.setStateRemark(getTradingStateRemark(orderTrade.getStatus(),isbuy));
        return result;
    }

    /**
     * 获取状态描述
     * @param tradeState
     * @return
     */
    @SuppressWarnings("all")
    private static  String getTradingStateRemark(int tradeState,boolean isBuy){
        if(!OrderTradeStateEnum.isRunningState(tradeState)){
            return "已完成";
        }
        if(isBuy){
            if(tradeState == OrderTradeStateEnum.WAIT_PAY.getState()){
                return "待付款";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_ING.getState()){
                return "支付中";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_SUCCESS.getState()){
                return "已支付成功，等待发货";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_FAIL.getState()){
                return "支付失败，再次支付";
            }else if(tradeState ==  OrderTradeStateEnum.VOUCHER_ING.getState()){
                return "正在发券";
            }else if(tradeState ==  OrderTradeStateEnum.LOAN_ING.getState()){
                return "交易完成";
            }else if(tradeState ==  OrderTradeStateEnum.LOAN_FAIL.getState()){
                return "交易完成";
            }else if(tradeState ==  OrderTradeStateEnum.REFUND_ING.getState()){
                return "退款中";
            }
        }else{
            if(tradeState == OrderTradeStateEnum.WAIT_PAY.getState()){
                return "等待买家付款";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_ING.getState()){
                return "付款中";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_SUCCESS.getState()){
                return "买家已付款，等待放券";
            }else if(tradeState ==  OrderTradeStateEnum.PAY_FAIL.getState()){
                return "等待买家付款";
            }else if(tradeState ==  OrderTradeStateEnum.VOUCHER_ING.getState()){
                return "转券中";
            }else if(tradeState ==  OrderTradeStateEnum.LOAN_ING.getState()){
                return "正在回款";
            }else if(tradeState ==  OrderTradeStateEnum.LOAN_FAIL.getState()){
                return "回款失败，请联系客服";
            }else if(tradeState ==  OrderTradeStateEnum.REFUND_ING.getState()){
                return "退款中";
            }
        }
        return "";
    }


}

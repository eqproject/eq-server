package org.eq.modules.trade.vo;

import org.apache.commons.lang3.StringUtils;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.enums.OrderTradeTypeEnum;
import org.eq.modules.order.vo.SearchAcceptOrderVO;
import org.eq.modules.order.vo.SearchAdOrderVO;
import org.eq.modules.order.vo.SearchTransOrderVO;

/**
 * 验证交易订单
 * @author kaka
 * @date 2019-06-01
 */

public class VolidOrderTradeInfo {


    /**
     * 新建交易订单验证
     * @param orderTradeCreateReqVO
     * @return
     */
    public static String volidCreate(OrderTradeCreateReqVO orderTradeCreateReqVO){
        if(orderTradeCreateReqVO==null){
            return  "请求参数为空";
        }
        if(orderTradeCreateReqVO.getUserId()<=0){
            return "用户为空";
        }
        if(orderTradeCreateReqVO.getOrderNum()<=0){
            return "交易订单数量必须大于0";
        }
        if(StringUtils.isEmpty(orderTradeCreateReqVO.getAdNo())){
            return "订单号为空";
        }
        if(orderTradeCreateReqVO.getSalePrice()<=0){
            return "价格必须大于0";
        }
        return null;
    }



    /**
     * 新建交易订单验证
     * @param orderTradeSearchVO
     * @return
     */
    public static String volidSearch(OrderTradeSearchVO orderTradeSearchVO){
        if(orderTradeSearchVO==null){
            return  "请求参数为空";
        }
        if(StringUtils.isEmpty(orderTradeSearchVO.getTradeNo())){
            return "交易单号为空";
        }
        if(orderTradeSearchVO.getUserId()<=0){
            return "用户为空";
        }
        return null;
    }



    /**
     * 取消交易订单验证
     * @param orderTradeSearchVO
     * @return
     */
    public static String volidConcel(OrderTradeSearchVO orderTradeSearchVO){
        if(orderTradeSearchVO==null){
            return  "请求参数为空";
        }
        if(StringUtils.isEmpty(orderTradeSearchVO.getTradeNo())){
            return "交易单号为空";
        }
        if(orderTradeSearchVO.getUserId()<=0){
            return "用户为空";
        }
        return null;
    }

    /**
     * 获取交易中，代付款等数据个数
     * @param orderTradeSearchVO
     * @return
     */
    public static String volidPoolInfo(OrderTradeSearchVO orderTradeSearchVO){
        if(orderTradeSearchVO==null){
            return  "请求参数为空";
        }
        if(orderTradeSearchVO.getUserId()<=0){
            return "用户为空";
        }
        return null;
    }




    /**
     * 验证支付回调接口
     * @param orderTradePaymentReqVO
     * @return
     */
    public static String volidPayNotify(OrderTradePaymentReqVO orderTradePaymentReqVO){
        if(orderTradePaymentReqVO==null){
            return  "请求参数为空";
        }
        if(StringUtils.isEmpty(orderTradePaymentReqVO.getPayNo())){
            return  "支付流水号为空";
        }
        if(StringUtils.isEmpty(orderTradePaymentReqVO.getTradeNo())){
            return  "交易号为空";
        }
        if(orderTradePaymentReqVO.getPayAmout()<=0){
            return  "支付费用小于0";
        }
        if(orderTradePaymentReqVO.getPayType()==null){
            return "支付方式不存在";
        }
        if(orderTradePaymentReqVO.getPayType()!=1 && orderTradePaymentReqVO.getPayType()!=2){
            return "支付方式不存在";
        }
        if(orderTradePaymentReqVO.getPayStatus()==null){
            return "支付结果未知";
        }
        if(orderTradePaymentReqVO.getPayStatus()!=1 && orderTradePaymentReqVO.getPayStatus()!=2){
            return "支付结果未知";
        }
        return null;
    }



}

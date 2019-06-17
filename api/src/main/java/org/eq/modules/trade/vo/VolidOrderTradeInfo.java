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



}

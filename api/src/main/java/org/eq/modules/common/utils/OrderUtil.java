package org.eq.modules.common.utils;

import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.vo.ResOrderAdVO;

/**
 * 订单工具类
 * @author  kaka
 * @date  2019-05-27
 */
@SuppressWarnings("all")
public class OrderUtil{


    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static ResOrderAdVO transObj(OrderAd orderAd){
        if(orderAd==null){
            return null;
        }
        ResOrderAdVO resOrderAdVO = new ResOrderAdVO();
        resOrderAdVO.setOrderCode(orderAd.getOrderNo());
        resOrderAdVO.setProductId(String.valueOf(orderAd.getProductId()));
        resOrderAdVO.setTitle(orderAd.getTitle());
        return resOrderAdVO;
    }




}

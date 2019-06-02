package org.eq.modules.common.utils;

import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.vo.OrderAdSimpleVO;
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



    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static OrderAdSimpleVO transObjForSimple(OrderAd orderAd){
        if(orderAd==null){
            return null;
        }
        OrderAdSimpleVO orderAdSimpleVO = new OrderAdSimpleVO();
        orderAdSimpleVO.setOrderCode(orderAd.getOrderNo());
        orderAdSimpleVO.setProductId(String.valueOf(orderAd.getProductId()));
        orderAdSimpleVO.setProductName(orderAd.getProductName());
        orderAdSimpleVO.setImg(orderAd.getProductImg());
        orderAdSimpleVO.setPrice(orderAd.getPrice());
        orderAdSimpleVO.setOrderNumber(orderAd.getProductNum());
        orderAdSimpleVO.setSaleedNumber(orderAd.getTradedNum()+orderAd.getTradingNum());
        orderAdSimpleVO.setSaleNumber(orderAd.getProductNum()-orderAd.getTradedNum()-orderAd.getTradingNum());
        orderAdSimpleVO.setTitle(orderAd.getTitle());
        orderAdSimpleVO.setUserId(orderAd.getUserId());
        orderAdSimpleVO.setUserImg(orderAd.getPhotoHead());
        orderAdSimpleVO.setNickName(orderAd.getNickName());
        orderAdSimpleVO.setUserBoundState(orderAd.getAuthStatus());
        return orderAdSimpleVO;
    }








}

package org.eq.modules.utils;

import org.eq.basic.common.util.DateUtil;
import org.eq.modules.auth.entity.User;
import org.eq.modules.enums.*;
import org.eq.modules.order.entity.OrderAccept;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.vo.*;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshoot;

import java.math.BigDecimal;

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
        orderAdSimpleVO.setUnitPrice(orderAd.getUnitPrice());
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
        orderAdSimpleVO.setOrderType(orderAd.getType());
        orderAdSimpleVO.setCreateTime(DateUtil.foramtChinaFormat(orderAd.getCreateDate()));
        return orderAdSimpleVO;
    }


    /**
     * 转化对象实体
     * @param orderAd
     * @param tradeNum 总共订单数
     * @param treadedNum 已完成交易订单数
     * @return
     */
    public static OrderAdSimpleVO transObjForSimple(OrderAd orderAd,int tradeNum,int treadedNum){
        if(orderAd==null){
            return null;
        }
        OrderAdSimpleVO orderAdSimpleVO = transObjForSimple(orderAd);
        if(tradeNum>0){
            orderAdSimpleVO.setTradeNum(tradeNum);
            orderAdSimpleVO.setTradeRate(new BigDecimal(treadedNum).divide(new BigDecimal(tradeNum),2,BigDecimal.ROUND_HALF_DOWN).doubleValue());
        }
        return orderAdSimpleVO;
    }




    /**
     * 转化对象实体
     * @param product
     * @param user
     * @return
     */
    public static OrderFinishSnapshootSimpleVO transObjForSimple(OrderFinishSnapshoot orderFinishSnapshoot, User user){
        if(orderFinishSnapshoot==null){
            return null;
        }
        OrderFinishSnapshootSimpleVO result = new OrderFinishSnapshootSimpleVO();
        result.setProductId(orderFinishSnapshoot.getProductId());
        result.setProductImg(orderFinishSnapshoot.getProductImg());
        result.setProductName(orderFinishSnapshoot.getProductName());
        result.setUnitPrice(orderFinishSnapshoot.getUnitPrice());
        result.setTradeNo(orderFinishSnapshoot.getTradeNo());
        result.setOrderNo(orderFinishSnapshoot.getOrderNo());
        result.setAmount(orderFinishSnapshoot.getAmount());
        result.setOrderNum(orderFinishSnapshoot.getOrderNum());
        result.setSalePrice(orderFinishSnapshoot.getSaleprice());
        result.setServiceFee(orderFinishSnapshoot.getAmount() - orderFinishSnapshoot.getSaleprice());
        result.setStatus(OrderFinishStateEnum.getRemarkByState(orderFinishSnapshoot.getStatus()));
        result.setFinishTime(DateUtil.foramtChinaFormat(orderFinishSnapshoot.getCreateDate()));
        result.setType(orderFinishSnapshoot.getType());
        if(orderFinishSnapshoot.getSellUserId().equals(user.getId())){//当前用户是买家
            result.setUserHeadImg(orderFinishSnapshoot.getBuyPhotoHead());
            result.setUserNickname(orderFinishSnapshoot.getBuyNickName());
        }else{
            result.setUserHeadImg(orderFinishSnapshoot.getSellPhotoHead());
            result.setUserNickname(orderFinishSnapshoot.getSellNickName());
        }
        boolean isOrder = false;
        if (orderFinishSnapshoot.getType() == OrderFinishTypeEnum.ORDER_AD_SALE.getType() || orderFinishSnapshoot.getType() == OrderFinishTypeEnum.ORDER_AD_BUY.getType()) {
            result.setOrderAdNum(formateNum(orderFinishSnapshoot.getOrderNum()));
            result.setOrderAdTradeNum(formateNum(orderFinishSnapshoot.getTradeNum()));
            isOrder = true;
        }else{
            result.setOrderTradeNum(formateNum(orderFinishSnapshoot.getTradeNum()));
        }

        //String stateRemark = getFinishOrderStateRemark()
        return result;
    }

    private static int formateNum(Integer num) {
        return num == null ? 0 : num;
    }


    /**
     * 转化对象实体
     * @param orderTransfer
     * @return
     */
    public static OrderTransVO transObjForOrderTrans(OrderTransfer orderTransfer){
        if(orderTransfer==null){
            return null;
        }
        OrderTransVO orderTransVO = new OrderTransVO();
        orderTransVO.setUserId(orderTransfer.getUserId());
        orderTransVO.setProductId(orderTransfer.getProductId());
        orderTransVO.setTransNumber(orderTransfer.getProductNum());
        orderTransVO.setTransCode(orderTransfer.getTransferNo());
        orderTransVO.setStateRemark(OrderTransferStateEnum.getRemarkByState(orderTransfer.getStatus()));
        return orderTransVO;
    }


    /**
     * 转化对象实体
     * @param orderAccept
     * @return
     */
    public static OverdueVO transObjForOverdueVO(OrderAccept orderAccept){
        if(orderAccept==null){
            return null;
        }
        OverdueVO overdueVO = new OverdueVO();
        overdueVO.setUserId(orderAccept.getUserId());
        overdueVO.setProductId(orderAccept.getProductId());
        overdueVO.setNumber(orderAccept.getProductNum());
        overdueVO.setOverdueReason("已承兑");
        return overdueVO;
    }


    /**
     * 转化对象实体
     * @param orderAccept
     * @return
     */
    public static OrderAcceptVO transObjForOrderTrans(OrderAccept orderAccept){
        if(orderAccept==null){
            return null;
        }
        OrderAcceptVO orderAcceptVO = new OrderAcceptVO();
        orderAcceptVO.setUserId(orderAccept.getUserId());
        orderAcceptVO.setProductId(orderAccept.getProductId());
        orderAcceptVO.setNumber(orderAccept.getProductNum());
        orderAcceptVO.setAcceptCode(orderAccept.getAcceptNo());
        orderAcceptVO.setConsignee(orderAccept.getConsignee());
        orderAcceptVO.setConsigneeAddress(orderAccept.getConsigneeAddress());
        orderAcceptVO.setConsigneePhone(orderAccept.getConsigneeMobile());
        orderAcceptVO.setStateRemak(OrderAcceptStateEnum.getRemarkByState(orderAccept.getStatus()));
        orderAcceptVO.setAcceptCode(orderAccept.getAcceptNo());
        return orderAcceptVO;
    }



    /**
     * 获取状态描述
     * @param tradeState
     * @return
     */
    @SuppressWarnings("all")
    private static  String getFinishOrderStateRemark(int state,boolean isOrder,boolean isBuy){

        if(isBuy){

        }else{

        }
        return "";
    }








}

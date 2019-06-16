package org.eq.modules.utils;

import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.OrderAcceptStateEnum;
import org.eq.modules.enums.OrderTransferStateEnum;
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
            orderAdSimpleVO.setTradeRate(new BigDecimal(treadedNum).divide(new BigDecimal(tradeNum)).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue());
        }
        return orderAdSimpleVO;
    }




    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static OrderFinishSnapshootSimpleVO transObjForSimple(OrderFinishSnapshoot orderFinishSnapshoot){
        if(orderFinishSnapshoot==null){
            return null;
        }
        OrderFinishSnapshootSimpleVO orderFinishSnapshootSimpleVO = new OrderFinishSnapshootSimpleVO();
        orderFinishSnapshootSimpleVO.setProductId(orderFinishSnapshoot.getProductId());
        orderFinishSnapshootSimpleVO.setProductImg(orderFinishSnapshoot.getProductImg());
        orderFinishSnapshootSimpleVO.setProductName(orderFinishSnapshoot.getProductName());
        orderFinishSnapshootSimpleVO.setUnitPrice(orderFinishSnapshoot.getUnitPrice());
        orderFinishSnapshootSimpleVO.setTradeNo(orderFinishSnapshoot.getTradeNo());
        orderFinishSnapshootSimpleVO.setOrderNo(orderFinishSnapshoot.getOrderNo());
        orderFinishSnapshootSimpleVO.setAmount(orderFinishSnapshoot.getAmount());
        orderFinishSnapshootSimpleVO.setOrderNum(orderFinishSnapshoot.getOrderNum());
        orderFinishSnapshootSimpleVO.setSalePrice(orderFinishSnapshoot.getSaleprice());
        orderFinishSnapshootSimpleVO.setServiceFee(orderFinishSnapshoot.getAmount() - orderFinishSnapshoot.getSaleprice());
        orderFinishSnapshootSimpleVO.setStatus(orderFinishSnapshoot.getStatus());
        orderFinishSnapshootSimpleVO.setFinishTime(orderFinishSnapshoot.getCreateDate());
        orderFinishSnapshootSimpleVO.setType(orderFinishSnapshoot.getType());
        return orderFinishSnapshootSimpleVO;
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









}

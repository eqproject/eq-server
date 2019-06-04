package org.eq.modules.order.vo;

import org.apache.commons.lang3.StringUtils;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.enums.OrderAdTypeEnum;

/**
 * 验证订单
 * @author kaka
 * @date 2019-06-01
 */

public class VolidOrderInfo {


    /**
     * 验证广告订单
     * @param searchAdOrderVO
     * @return
     */
    public static String volidSearchOrderAd(SearchAdOrderVO searchAdOrderVO){
        if(searchAdOrderVO==null){
            return  "请求参数为空";
        }
        if(searchAdOrderVO.getUserId()<=0 ){
            return "用户为空";
        }
        if(searchAdOrderVO.getProductId()<=0){
            return "商品Id为空";
        }
        if(searchAdOrderVO.getNumber()<=0){
            return "求购或售卖数量必须大于0";
        }
        if(StringUtils.isEmpty(searchAdOrderVO.getAdTitle()) || searchAdOrderVO.getAdTitle().length()> StaticEntity.ORDER_AD_TITLE_LENGTH){
            return "标题为空或长度超过最大限制";
        }
        String result = OrderAdTypeEnum.getRemarkByType(searchAdOrderVO.getOrderType());
        if(StringUtils.isEmpty(result)){
            return "广告类型非法";
        }
        return null;
    }


    /**
     * 验证转让订单
     * @param searchTransOrderVO
     * @return
     */
    public static String volidSearchTransOrderAd(SearchTransOrderVO searchTransOrderVO){
        if(searchTransOrderVO==null){
            return  "请求参数为空";
        }
        if(searchTransOrderVO.getUserId()<=0 ){
            return "用户为空";
        }
        if(searchTransOrderVO.getProductId()<=0){
            return "商品不能为空";
        }
        if(searchTransOrderVO.getNumber()<=0){
            return "转让数量必须大于0";
        }
        if(StringUtils.isEmpty(searchTransOrderVO.getAddress())){
            return "转让地址必须填写";
        }
        return null;
    }


    /**
     * 验证承兑
     * @param searchAcceptOrderVO
     * @return
     */
    public static String volidSearchAcceptOrderAd(SearchAcceptOrderVO searchAcceptOrderVO){
        if(searchAcceptOrderVO==null){
            return  "请求参数为空";
        }
        if(searchAcceptOrderVO.getUserId()<=0 ){
            return "用户为空";
        }
        if(searchAcceptOrderVO.getProductId()<=0){
            return "商品不能为空";
        }
        if(searchAcceptOrderVO.getNumber()<=0){
            return "承兑数量必须大于0";
        }
        if(StringUtils.isEmpty(searchAcceptOrderVO.getConsignee())){
            return "承兑人必须填写";
        }
        if(StringUtils.isEmpty(searchAcceptOrderVO.getConsigneePhone())){
            return "承兑人电话号码必须填写";
        }
        if(StringUtils.isEmpty(searchAcceptOrderVO.getConsigneeAddress())){
            return "承兑人地址必须填写";
        }
        return null;
    }

}

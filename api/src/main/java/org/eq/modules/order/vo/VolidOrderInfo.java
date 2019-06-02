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
}

package org.eq.modules.business;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.entity.Product;

/**
 * 商品工具类
 */
public class ProductBusines {


    /**
     * 判断商品是否有效
     * @param product
     * @return
     */
    public static boolean isEffect(Product product){
        if(product==null){
            return false;
        }
        if(ProductStateEnum.ONLINE.getState()!=product.getStatus()){
            return false;
        }
        if(StringUtils.isEmpty(product.getExpirationEnd())){
            return true;
        }
        if(DateUtil.paseTimeStr(product.getExpirationEnd()).getTime()<DateUtil.getNowTime().getTime()){
            return false;
        }
        return true;
    }


}

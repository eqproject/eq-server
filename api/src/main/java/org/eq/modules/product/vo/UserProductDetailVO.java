package org.eq.modules.product.vo;

import lombok.Data;

@Data
public class UserProductDetailVO extends  ProductDetailVO {


    /**
     * 用户可用库存
     */
    private int number;


    /**
     * 锁定量
     */
    private int lockedNum;


}

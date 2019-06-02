package org.eq.modules.order.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 广告订单返回实体
 * @date 2019-0
 */
@Data
public class ResOrderAdVO  implements Serializable {


    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 标题
     */
    private String title;

}

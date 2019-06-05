package org.eq.modules.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 转出实体
 * @author  kaka
 * @date  2019-06-04
 */
@Data
public class OrderTransVO implements Serializable {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 转出订单号
     */
    private String  transCode;

    /**
     * 转出数量
     */
    private int transNumber;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 图片
     */
    private String img;

    /**
     * 装让状态说明
     */
    private String stateRemark;

    /**
     * 单价
     */
    private int unitPrice;



}

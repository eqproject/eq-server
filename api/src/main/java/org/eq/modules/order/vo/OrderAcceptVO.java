package org.eq.modules.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 转出实体
 * @author  kaka
 * @date  2019-06-04
 */
@Data
public class OrderAcceptVO implements Serializable {

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
    private String  acceptCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 承兑数量
     */
    private int number;

    /**
     * 承兑人名称
     */
    private String consignee;

    /**
     * 承兑人电话
     */
    private String consigneePhone;

    /**
     * 承兑人地址
     */
    private String consigneeAddress;

    /**
     * 单价
     */
    private int unitPrice;


    /**
     * 商家电话
     */
    private String acceptModile;


    /**
     * 状态说明
     */
    private String stateRemak;


}

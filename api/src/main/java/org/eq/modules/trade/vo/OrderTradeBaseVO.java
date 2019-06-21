package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 交易订单基本信息
 * @author  kaka
 * @date  20190622
 */
@Data
public class OrderTradeBaseVO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 交易号
     */
    private String tradeNo;

    /**
     * 面值
     */
    private int unitPrice;

    /**
     * 订单总价
     */
    private Integer amount;
    /**
     * 订单数量
     */
    private Integer orderNum;
    /**
     * 售卖单价
     */
    private Integer salePrice;
    /**
     * 服务费
     */
    private int serviceFee;

    /**
     * 是否已催
     */
    private Integer remindPay;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后一次交易时间
     */
    private String updateTime;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 商品名称
     */
    private String productName;
}

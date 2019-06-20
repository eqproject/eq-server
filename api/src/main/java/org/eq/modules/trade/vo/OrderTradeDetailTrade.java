package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin kaka
 * @Title: OrderTradeDetailTrade
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:23
 */
@Data
public class OrderTradeDetailTrade implements Serializable{

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
     * 交易最大时长
     */
    private int payTimeOut;

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


    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 状态描述
     */
    private String stateRemark;

}

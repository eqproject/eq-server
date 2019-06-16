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
    private String payNo;
    private Integer amount;
    private Integer orderNum;
    private Integer salePrice;
    /**
     * 服务费
     */
    private Integer serviceFee;
    private Integer remindPay;

    private Integer status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 交易最大时长
     */
    private int payTimeOut;

}

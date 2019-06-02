package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeDetailTrade
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:23
 */
@Data
public class OrderTradeDetailTrade implements Serializable{

    private static final long serialVersionUID = 1L;

    private String tradeNo;
    private String payNo;
    private Integer amount;
    private Integer orderNum;
    private Integer salePrice;
    private Integer serviceFee;
    private Integer remindPay;
    private Integer status;
    private String createTime;
    private String payTime;

}

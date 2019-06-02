package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeCreateResVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午7:21
 */
@Data
public class OrderTradeCreateResVO implements Serializable {


    private String tradeNo; // 交易订单号

    public OrderTradeCreateResVO(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}

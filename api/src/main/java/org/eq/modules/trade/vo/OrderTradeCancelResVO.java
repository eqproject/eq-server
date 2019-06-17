package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeCancelResVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午7:21
 */
@Data
public class OrderTradeCancelResVO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 交易订单号
    private String tradeNo;

    public OrderTradeCancelResVO(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}

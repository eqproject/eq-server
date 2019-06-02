package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeDetailResVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午7:21
 */
@Data
public class OrderTradeDetailResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private OrderTradeDetailProduct product;
    private OrderTradeDetailUser user;
    private OrderTradeDetailTrade trade;
}

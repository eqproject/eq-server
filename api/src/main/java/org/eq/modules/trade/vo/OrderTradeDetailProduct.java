package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeDetailProduct
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:23
 */
@Data
public class OrderTradeDetailProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productImg;
    private String name;
    private Integer unitPrice;


}

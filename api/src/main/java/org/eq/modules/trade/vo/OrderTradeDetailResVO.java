package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.product.vo.ProductDetailVO;

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

    /**
     * 商品详情
     */
    private ProductDetailVO product;

    /**
     * 用户详情
     */
    private OrderTradeUser user;


    /**
     * 订单详情
     */
    private OrderTradeDetailTrade trade;
}

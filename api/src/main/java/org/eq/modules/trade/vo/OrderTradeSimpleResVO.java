package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.product.vo.ProductDetailVO;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeSimpleResVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午9:31
 */
@Data
public class OrderTradeSimpleResVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private OrderTradeUser orderTradeUser;

    private OrderTradeDetailTrade trade;

}

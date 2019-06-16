package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.product.vo.ProductDetailVO;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeListResVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午9:31
 */
@Data
public class OrderTradeListResVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private ProductDetailVO product;
    private OrderTradeDetailUser user;
    private OrderTradeDetailTrade trade;

}

package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * @author admin
 * @Title: OrderTradeCreateReqVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午7:10
 */
@Data
public class OrderTradeCreateReqVO extends SearchBase{
    private static final long serialVersionUID = 1L;

    private Long buyUserId;
    private String adNo;
    private Long productId;
    private Integer orderNum;
    private Integer salePrice;
    private Integer serviceFee;
    private Integer type;
    private String remarks;

}

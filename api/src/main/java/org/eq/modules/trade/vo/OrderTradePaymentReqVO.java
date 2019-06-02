package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * @author admin
 * @Title: OrderTradePaymentReqVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午4:26
 */
@Data
public class OrderTradePaymentReqVO extends SearchBase {

    private static final long serialVersionUID = 1L;

    private String tradeNo;
    private String payNo;
    private Integer payAmout;
    private Integer payType;
    private Integer payStatus;

}

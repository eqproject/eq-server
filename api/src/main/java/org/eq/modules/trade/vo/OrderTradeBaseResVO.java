package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 交易订单简单返回实体
 * @author kaka
 *
 */
@Data
public class OrderTradeBaseResVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易订单号
     */
    private String tradeNo;


}

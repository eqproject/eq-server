package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 交易订单查询实体
 * @author kaka
 *
 */
@Data
public class OrderTradeSearchVO extends SearchBase{
    private static final long serialVersionUID = 1L;

    /**
     * 交易订单号
     */
    private String tradeNo;


    /**
     * 当前用户ID
     */
    private long userId;

}

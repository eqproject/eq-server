package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 交易订单放款实体
 * @author kaka
 *
 */
@Data
public class OrderTradeLoanReqVO extends SearchBase{
    private static final long serialVersionUID = 1L;

    /**
     * 交易订单号
     */
    private String tradeNo;

    /**
     * 放款状态
     */
    private Integer state;




}

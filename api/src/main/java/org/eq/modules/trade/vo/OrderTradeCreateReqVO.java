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

    /**
     * 购买人用户ID
     */
    private long userId;
    /**
     * 订单号
     */
    private String adNo;
    /**
     * 商品ID
     */
    private long productId;
    /**
     * 订单数量
     */
    private int orderNum;
    /**
     * 出价
     */
    private int salePrice;
    /**
     * 服务费
     */
    private double serviceFee;

    /**
     * 交易订单类型
     */
    private int type;
    /**
     * 备注
     */
    private String remarks;

}

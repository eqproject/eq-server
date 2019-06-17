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

    /**
     * 交易单号
     */
    private String tradeNo;
    /**
     * 流水号
     */
    private String payNo;
    /**
     * 支付金额
     */
    private int payAmout;
    /**
     * 支付方式 (1：支付宝；2：微信)
     */
    private Integer payType;
    /**
     *  支付状态 1：成功；2：失败
     */
    private Integer payStatus;

}

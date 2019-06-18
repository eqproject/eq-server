package org.eq.modules.common.enums;

import lombok.Getter;

/**
 * @author : gb 2019/6/18
 */
@Getter
public enum LogTypeEnum {

    AD(1, "广告订单日志"),
    TRADE(2, "交易订单日志"),
    TRADE_PAYMENT(3, "交易订单支付日志"),
    TRADE_REFUND(4, "交易订单退款日志"),
    ACCEPT(5, "承兑订单日志"),
    TRANSFER(6, "转出订单日志");

    /**
     * 类型
     */
    private int type;
    /**
     * 描述
     */
    private String desc;


    LogTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

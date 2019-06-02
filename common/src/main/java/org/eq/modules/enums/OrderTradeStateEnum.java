package org.eq.modules.enums;

/**
 * @author admin
 * @Title: OrderTradeStateEnum
 * @Copyright: Copyright (c) 2018
 * @Description: 订单交易状态
 * @Company: 123.com
 * @Created on 2019/6/2上午11:02
 */
public enum OrderTradeStateEnum {

    WAIT_PAY(1,"待支付"),
    CANCEL(2,"取消交易"),
    PAY_ING(3,"支付中"),
    PAY_SUCCESS(4,"支付成功"),
    PAY_FAIL(5,"支付失败"),
    CANCEL_PAY_TIMEOUT(6,"关闭交易(支付超时)"),
    VOUCHER_ING(7,"放券中"),
    LOAN_ING(8,"放款中"),
    LOAN_FAIL(9,"放款失败"),
    TRADE_SUCCESS(10,"交易成功"),
    REFUND_ING(11,"退款中"),
    REFUND_SUCCESS(12,"退款成功"),

    ;


    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;


    OrderTradeStateEnum(int state, String remark) {
        this.state = state;
        this.remark = remark;
    }

    public int getState() {

        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

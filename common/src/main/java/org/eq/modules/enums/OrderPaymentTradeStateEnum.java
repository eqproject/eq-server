package org.eq.modules.enums;

/**
 * 支付流水状态枚举
 * @author kaka
 */
public enum OrderPaymentTradeStateEnum {

    //(1:支付成功;2:支付失败;3:通知放款中;4:通知放款成功;5:通知放款失败6:取消)
    PAY_SUCCESS(1,"支付成功"),
    PAY_FAIL(2,"支付失败"),
    PAY_NOTICE_ING(3,"通知放款中"),
    PAY_NOTICE_SUCCESS(4,"通知放款成功"),
    PAY_NOTICE_FAIL(5,"通知放款失败"),
    PAY_CANCEL(6,"关闭交易");

    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;


    OrderPaymentTradeStateEnum(int state, String remark) {
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

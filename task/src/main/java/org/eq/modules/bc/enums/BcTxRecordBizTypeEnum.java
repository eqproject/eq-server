package org.eq.modules.bc.enums;

public enum BcTxRecordBizTypeEnum {
	BUY_GOODS(1,"购买物品"),
	DELIVERY_GOODS(2,"商品提货"),
	REFUND(3,"商品退款"),
	WITHDRAW(4,"提现"),
	EARNINGS(5,"返利收益"),
	TIMEOUT_DELIVERY_GOODS(6,"商品提货——超时情况"),
    ACTIVITY_REWARD(7,"投票奖励"),
    COUPON_PAY(8,"优惠券支付"),
    VOTE(9,"投票"),
	;
    
    private final Integer code;
    private final String message;

    private BcTxRecordBizTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
   
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

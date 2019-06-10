package org.eq.modules.bc.enums;

public enum BcTxRecordBizTypeEnum {
    ACTIVATE_ACCOUNT(1, "激活账户"),
    SELL(2, "售卖券"),
    BUY(3, "购买券"),
    TRANSFER(4, "转让"),
    ACCEPT(5, "承兑");

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

package org.eq.modules.enums;

/**
 * @author : gb 2019/6/19
 */
public enum BcAccountTypeEnum {
    ACTIVITY(1, "激活账户"),
    SELL(2, "售卖券"),
    BUY(3, "购买券"),
    TRANSFER(4, "转出"),
    ACCEPT(5, "承兑");
    private Integer code;
    private String name;

    private BcAccountTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}

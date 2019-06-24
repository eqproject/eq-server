package org.eq.modules.enums;

/**
 * * @author gb
 *
 * @version 2019/6/24
 */
public enum BcTxTypeEnum {
    BU(0, "BU转账"),
    ASSET_ATP10(1, "ATP10"),
    CONTRACT(2, "调用合约"),
    CREATE_ACCOUNT(3, "创建用户"),
    ;


    private final Integer code;
    private final String msg;

    private BcTxTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

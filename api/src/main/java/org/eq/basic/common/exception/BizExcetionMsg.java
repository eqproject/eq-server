package org.eq.basic.common.exception;

/**
 * * @author gb
 *
 * @version 2019/6/12
 */
public enum BizExcetionMsg {
    SIGN_NULL(100,"签名为空"),
    SIGN_INVALID(101,"签名无效"),
    USER_NULL(102,"用户不存在"),
    USER_WALLET_INACTIVE(103,"钱包未激活");

    private int code;
    private String msg;

    BizExcetionMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

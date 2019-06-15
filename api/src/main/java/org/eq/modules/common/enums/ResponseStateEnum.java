package org.eq.modules.common.enums;

/**
 * 返回枚举类
 * @author  kaka
 * @date 2019/05/30
 */
public enum ResponseStateEnum {

    SUCCESS("200","成功"),

    BUSINESS_ERROR("302", "失败"),

    ERROR_SIGN("300","认证失败"),

    ERROR_PARAMS("301","参数错误"),

    ERROR_SYSTEM("500","系统错误"), //遇到不明确的异常，统一为这个错误码

    ;

    /**
     * 状态
     */
    private String status;

    /**
     * 错误提示
     */
    private String errMsg;


    ResponseStateEnum(String status,String errMsg){
        this.status = status;
        this.errMsg = errMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

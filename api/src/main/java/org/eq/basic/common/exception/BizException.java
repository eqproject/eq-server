package org.eq.basic.common.exception;

import org.eq.modules.common.enums.ResponseStateEnum;

/**
 * * @author gb
 *
 * @version 2019/6/12
 */
public class BizException extends RuntimeException {
    private final static String ERROR = "500";
    private String code;

    public BizException() {
        this.code = ERROR;
    }

    public BizException(ResponseStateEnum state) {
        this(state.getStatus(), state.getErrMsg());
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = ERROR;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = ERROR;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = ERROR;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
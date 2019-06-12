package org.eq.basic.common.exception;

/**
 * * @author gb
 *
 * @version 2019/6/12
 */
public class BizException extends RuntimeException {
    private final static int ERROR = 1;
    private int code;

    public BizException() {
        this.code = ERROR;
    }

    public BizException(BizExcetionMsg bizExcetionMsg) {
        this(bizExcetionMsg.getCode(), bizExcetionMsg.getMsg());
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = 1;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = ERROR;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = ERROR;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
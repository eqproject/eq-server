package org.eq.modules.bc.exception;


import org.eq.modules.bc.enums.ExceptionEnum;

public class APIException extends RuntimeException{
	private static final long serialVersionUID = 429654902433634386L;
    private String errCode;
    private String errChineseMsg;
    
    public APIException(String message, Throwable cause) {
        super(message, cause);
        this.errCode = ExceptionEnum.SYS_ERR.getCode();
        this.errChineseMsg = message;
    }

    public APIException(Throwable cause) {
        super(cause);
        this.errCode = ExceptionEnum.SYS_ERR.getCode();
        this.errChineseMsg = ExceptionEnum.SYS_ERR.getChineseMessage();
    }

    public APIException(String errCode, String message) {
        this(errCode, message, message);
    }

    public APIException(ExceptionEnum errEnum) {
        this(errEnum.getCode(), errEnum.getChineseMessage());
    }

    public APIException(ExceptionEnum errEnum, String message) {
        this(errEnum.getCode(), message, message);
    }

    public APIException(String errCode, String message, String chineseMsg) {
        super(message);
        this.errCode = errCode;
        this.errChineseMsg = message;
        this.errChineseMsg = chineseMsg;
    }

    public String getErrCode() {
        return errCode;
    }
    
    public String getErrChineseMsg() {
        return errChineseMsg;
    }
}

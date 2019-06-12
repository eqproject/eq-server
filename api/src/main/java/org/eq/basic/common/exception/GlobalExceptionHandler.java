package org.eq.basic.common.exception;

import com.google.protobuf.ServiceException;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * * @author gb
 *
 * @version 2019/6/12
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }
    //ServiceException自定义业务异常
    @ExceptionHandler({BizException.class})
    public BaseResult baseExceptionHandler(HttpServletResponse response, BizException ex) {
        log.error(ex.getMessage(), ex);
        return new BaseResult(ex.getCode(),ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public BaseResult otherExceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        return new BaseResult(1, ex.getMessage());
    }

    @Getter
    class BaseResult{
        private int status;
        private String errMsg;

        public BaseResult(int code,String msg){
            this.status = code;
            this.errMsg = msg;
        }
    }
}

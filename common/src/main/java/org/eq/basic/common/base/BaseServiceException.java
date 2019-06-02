package org.eq.basic.common.base;

/**
 * @author admin
 * @Title: BaseServiceException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2上午11:32
 */
public class BaseServiceException extends RuntimeException{


    public BaseServiceException() {
    }

    public BaseServiceException(String message) {
        super(message);
    }

    public BaseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseServiceException(Throwable cause) {
        super(cause);
    }
}

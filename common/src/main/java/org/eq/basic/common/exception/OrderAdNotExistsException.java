package org.eq.basic.common.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: OrderAdNotExistsException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:16
 */
public class OrderAdNotExistsException extends BaseServiceException{

    public OrderAdNotExistsException() {
    }

    public OrderAdNotExistsException(String message) {
        super(message);
    }

    public OrderAdNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderAdNotExistsException(Throwable cause) {
        super(cause);
    }
}

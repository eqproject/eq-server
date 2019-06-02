package org.eq.basic.common.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: TradeOrderNotExistsException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:16
 */
public class TradeOrderNotExistsException extends BaseServiceException{

    public TradeOrderNotExistsException() {
    }

    public TradeOrderNotExistsException(String message) {
        super(message);
    }

    public TradeOrderNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeOrderNotExistsException(Throwable cause) {
        super(cause);
    }
}

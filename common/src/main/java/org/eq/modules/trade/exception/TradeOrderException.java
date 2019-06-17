package org.eq.modules.trade.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: TradeOrderException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:16
 */
public class TradeOrderException extends BaseServiceException{

    public TradeOrderException() {
    }

    public TradeOrderException(String message) {
        super(message);
    }

    public TradeOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeOrderException(Throwable cause) {
        super(cause);
    }
}

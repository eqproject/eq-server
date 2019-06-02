package org.eq.modules.trade.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: PaymentTradeOrderNotExistsException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:16
 */
public class PaymentTradeOrderNotExistsException extends BaseServiceException{

    public PaymentTradeOrderNotExistsException() {
    }

    public PaymentTradeOrderNotExistsException(String message) {
        super(message);
    }

    public PaymentTradeOrderNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentTradeOrderNotExistsException(Throwable cause) {
        super(cause);
    }
}

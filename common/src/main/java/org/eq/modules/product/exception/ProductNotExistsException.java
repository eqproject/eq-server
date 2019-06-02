package org.eq.modules.product.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: ProductNotExistsException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2上午11:34
 */
public class ProductNotExistsException extends BaseServiceException{

    public ProductNotExistsException() {
    }

    public ProductNotExistsException(String message) {
        super(message);
    }

    public ProductNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotExistsException(Throwable cause) {
        super(cause);
    }
}

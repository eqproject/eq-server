package org.eq.modules.auth.exception;

import org.eq.basic.common.base.BaseServiceException;

/**
 * @author admin
 * @Title: UserNotExistsException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午3:39
 */
public class UserNotExistsException extends BaseServiceException{

    public UserNotExistsException() {
    }

    public UserNotExistsException(String message) {
        super(message);
    }

    public UserNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistsException(Throwable cause) {
        super(cause);
    }
}

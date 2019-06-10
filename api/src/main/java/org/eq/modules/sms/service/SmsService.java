package org.eq.modules.sms.service;

import org.eq.modules.common.entitys.ResponseData;

public interface SmsService {
    ResponseData send(String mobile, int type);
}

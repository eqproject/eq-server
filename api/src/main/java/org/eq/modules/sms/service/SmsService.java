package org.eq.modules.sms.service;

import org.eq.modules.common.entitys.ResponseData;

public interface SmsService {
    /**
     *  按照电话以及模板类型进行发放
     * @param mobile
     * @param type
     * @return
     */
    ResponseData send(String mobile, int type);

}

package org.eq.modules.sms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsLogExample;

import java.util.List;

/**
 * 短信发送记录Mapper接口
 *
 * @author hobe
 * @version 2019-06-06
 */
@Mapper
public interface SmsLogMapper extends BaseMapper<SmsLog, SmsLogExample> {
    int insertSmsLogHistoryBatch(List<SmsLog> smsLogs);

    void deleteSmsLogBatch(List<SmsLog> list);
}
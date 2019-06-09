package org.eq.modules.sms.biz;

import lombok.RequiredArgsConstructor;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.bc.common.log.LoggerFactory;
import org.eq.modules.sms.dao.SmsLogMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsBiz {
    private static final int BATCH_NUM = 100;
    private final SmsLogMapper smsLogMapper;

    /**
     * 归档三十天前的日志
     */
    public void archiveHistoryLog() {
        SmsLogExample example = new SmsLogExample();
        SmsLogExample.Criteria ca = example.or();
        ca.andCreateDateLessThan(DateUtil.beforeDateDay(DateUtil.getNowTime(), 30));
        List<SmsLog> list = smsLogMapper.selectByExample(example);
        List<SmsLog> tempList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                tempList.add(list.get(i));
                if (tempList.size() == BATCH_NUM) {
                    moveSmsLog(tempList);
                    tempList.clear();
                }
            }
            moveSmsLog(tempList);
        }
    }

    /**
     * 批处理日志记录
     * @param list
     */
    private void moveSmsLog(List<SmsLog> list){
        try{
            // 写入历史表
            smsLogMapper.insertSmsLogHistoryBatch(list);
            // 删除历史记录
            smsLogMapper.deleteSmsLogBatch(list);
        }catch(Exception e){
            LoggerFactory.getLogger(SmsBiz.class).error(e.getMessage());
        }
    }
}

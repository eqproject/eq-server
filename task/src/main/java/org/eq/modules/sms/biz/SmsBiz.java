package org.eq.modules.sms.biz;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.bc.common.log.LoggerFactory;
import org.eq.modules.sms.dao.SmsLogMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsLogExample;
import org.eq.modules.sms.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     *
     * @param list
     */
    private void moveSmsLog(List<SmsLog> list) {
        try {
            // 写入历史表
            smsLogMapper.insertSmsLogHistoryBatch(list);
            // 删除历史记录
            smsLogMapper.deleteSmsLogBatch(list);
        } catch (Exception e) {
            LoggerFactory.getLogger(SmsBiz.class).error(e.getMessage());
        }
    }

    public void getSmsReport() {
        /**
         * {
         *     "status":"0",
         *     "balance":302842,
         *     "list":[
         *         {
         *             "flag":1,
         *             "mid":"6B05C6C386738A80",
         *             "spid":"900592",
         *             "accessCode":"106905",
         *             "mobile":"18927401913",
         *             "stat":"DELIVRD",
         *             "time":"2019-06-11 01:28:35"
         *         }
         *     ]
         * }
         */
        String report = SmsUtils.report();
        Map<String, Object> map = (Map<String, Object>) JSON.parse(report);

        if (!map.get("status").equals("0")) {
            LoggerFactory.getLogger(SmsBiz.class).error("获取短信发送状态报告失败");
            return;
        }
        List<Map<String, String>> list = (List<Map<String, String>>) map.get("list");

        if(list == null && list.size() == 0) {
            LoggerFactory.getLogger(SmsBiz.class).error("获取短信发送状态报告失败");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> item = list.get(i);
            String mobile = item.get("mobile");
            int status = 1;
            if (item.get("stat").equals("DELIVRD")) {
                //更新smsLog表状态
                status = 2;
            } else {
                status = 3;
            }
            updateSmsLogStatus(mobile, status);
        }
    }

    /**
     * 更新短信发送状态
     * @param mobile
     * @param status
     */
    private void updateSmsLogStatus(String mobile, int status) {
        SmsLog smsLog = new SmsLog();
        smsLog.setStatus(status);
        SmsLogExample smsLogExample = new SmsLogExample();
        smsLogExample.or().andStatusEqualTo(1).andMobileEqualTo(mobile);
        smsLogMapper.updateByExampleSelective(smsLog,smsLogExample);

    }

}

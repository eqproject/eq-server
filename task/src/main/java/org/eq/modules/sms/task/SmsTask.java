package org.eq.modules.sms.task;

import lombok.RequiredArgsConstructor;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.sms.biz.SmsBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsTask extends BaseLog {
    private final SmsBiz smsBiz;

    /**
     * 每天0:30执行
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void archiveSmsHistoryLog() {
        logger.info("30天前的短信记录迁移到历史日志表-开始");
        try {
            smsBiz.archiveHistoryLog();
        } catch (Exception e) {
            logger.error("30天的短信记录迁移到历史日志表异常", e);
        }
        logger.info("30天的短信记录迁移到历史日志表-结束");
    }
}

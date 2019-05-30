package org.eq.modules.order;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * * @author gb
 *
 * @version 2019/5/31
 */

@Component
@Log
public class DemoTask {

    @Scheduled(cron = "* 0/1 * * * ?")
    public void process1() {
        log.info("cron每分钟执行");
    }

    @Scheduled(fixedDelay = 60*1000)
    public void process2() {
        log.info("fixedDelay每分钟执行");
    }
}

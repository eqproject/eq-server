package org.eq.modules.order;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 将（广告订单+交易订单）状态终结的记录，生成快照存放至order_finish_snapshoot表中
 *  * @author gb
 *  * @version 2019.05.31
 */

@Component
@Log
public class OrderFinishSnapshotTask {


    @Scheduled(cron = "* 0/1 * * * ?")
    public void process() {
        log.info("每分钟执行");
    }
}

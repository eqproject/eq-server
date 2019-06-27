package org.eq.modules.order.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.eq.modules.order.biz.OrderFinishSnapshotBiz;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 将（广告订单+交易订单）状态终结的记录，生成快照存放至order_finish_snapshoot表中
 *  * @author gb
 *  * @version 2019.05.31
 */

@Component
@Log
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderFinishSnapshotTask {

    private final OrderFinishSnapshotBiz biz;


    @Scheduled(cron = "0 0/5 * * * ?")
    public void process() {
        log.info("每分钟执行");
        biz.searchFinishOrderAd().forEach(o->{
            OrderFinishSnapshoot finish = biz.toOrderFinishSnapshoot(o);
            biz.insert(finish);
        });

        biz.searchFinishOrderTrade().forEach(o->{
            OrderFinishSnapshoot finish = biz.toOrderFinishSnapshoot(o);
            biz.insert(finish);
        });
    }
}

package org.eq.modules.order.task;

import lombok.RequiredArgsConstructor;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.order.biz.OrderAdExpireBiz;
import org.eq.modules.order.biz.OrderTradePayExpireBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时关闭过期广告订单
 * @author kaka
 * @date  20190613
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderAdExpireTask extends BaseLog {

    private final OrderAdExpireBiz orderAdExpireBiz;

    @Scheduled(cron = "0 0 0 * * ?")
    public void process() {
        logger.info("OrderAdExpireTask 每天开始执行-开始");
        try {
            orderAdExpireBiz.searchExpireOrderAd().forEach(b->{
                orderAdExpireBiz.updateOrderAdStatus(b);
            });
        } catch (Exception e) {
            logger.error("OrderAdExpireTask 执行异常:",e);
        }
        logger.info("OrderAdExpireTask 每天执行-结束");
    }
}

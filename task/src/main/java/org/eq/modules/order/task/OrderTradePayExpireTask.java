package org.eq.modules.order.task;

import lombok.RequiredArgsConstructor;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.order.biz.OrderTradePayExpireBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  交易订单过期  交易订单支付过期
 * @author kaka
 * @date  20190616
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderTradePayExpireTask extends BaseLog {

    private final OrderTradePayExpireBiz orderTradePayExpireBiz;

    @Scheduled(cron = "* 0/1 * * * ?")
    public void process() {
        logger.info("OrderTradePayExpireTask  过期交易订单 & 过期交易中订单过期");
        try {
            orderTradePayExpireBiz.searchPayExpireOrderTrade().forEach(b->{
                orderTradePayExpireBiz.updateOrderTradeStatus(b);
            });
        } catch (Exception e) {
            logger.error("OrderTradePayExpireTask 执行异常:",e);
        }
        logger.info("OrderTradePayExpireTask 每小时执行一次");
    }
}

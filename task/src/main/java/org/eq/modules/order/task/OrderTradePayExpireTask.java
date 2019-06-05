package org.eq.modules.order.task;

import lombok.RequiredArgsConstructor;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.order.biz.OrderTradePayExpireBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @Title: OrderTradePayExpireTask
 * @Copyright: Copyright (c) 2018
 * @Description: 关闭交易(支付超时)
 * @Company: 123.com
 * @Created on 2019/6/3下午11:44
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderTradePayExpireTask extends BaseLog {

    private final OrderTradePayExpireBiz orderTradePayExpireBiz;

    @Scheduled(cron = "* 0/1 * * * ?")
    public void process() {
        logger.info("OrderTradePayExpireTask 每分钟执行-开始");
        try {
            orderTradePayExpireBiz.searchPayExpireOrderTrade().forEach(b->{
                orderTradePayExpireBiz.updateOrderTradeStatus(b);
            });
        } catch (Exception e) {
            logger.error("OrderTradePayExpireTask 执行异常:",e);
        }
        logger.info("OrderTradePayExpireTask 每分钟执行-结束");
    }
}

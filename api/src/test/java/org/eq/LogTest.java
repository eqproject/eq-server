package org.eq;

import org.apache.commons.lang3.RandomStringUtils;
import org.eq.modules.common.enums.LogTypeEnum;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.log.OrderLogService;
import org.eq.modules.order.entity.OrderAdLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * * @author gb
 *
 * @version 2019/6/18
 */
public class LogTest extends BaseTest{
    @Autowired
    private OrderLogService logService;

    @Test
    public void test(){
        OrderAdLog orderAdLog = new OrderAdLog();
        orderAdLog.setCreateDate(new Date());
        orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        orderAdLog.setOldStatus(OrderAdStateEnum.ORDER_DEFAULT.getState());
        orderAdLog.setOrderAdId(Long.parseLong(RandomStringUtils.random(4, false, true)));
        orderAdLog.setRemarks("test case");
        logService.save(LogTypeEnum.AD,orderAdLog);
    }
}

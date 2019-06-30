package org.eq;

import org.eq.modules.order.biz.OrderTradeSellCallback;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * * @author gb
 *
 * @version 2019/6/30
 */
public class FinishTask extends BaseTest {

    @Autowired
    private OrderTradeSellCallback sellCallback;
    @Test
    public void test(){
        sellCallback.success("0");
    }
}

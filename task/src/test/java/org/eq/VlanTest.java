package org.eq;

import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.init.KeyStoreManager;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.bc.service.BcTxService;
import org.eq.modules.bc.task.HandleBcTxTransferJob;
import org.eq.modules.bc.task.HandleQueryTransferResultJob;
import org.eq.modules.order.biz.OrderTradeBiz;
import org.eq.modules.order.biz.OrderTradeBuyCallback;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author  kaka
 */
public class VlanTest extends BaseTest{


    @Autowired
    private OrderTradeBuyCallback orderTradeBuyCallback;

    @Test
    public void test(){
        orderTradeBuyCallback.success(String.valueOf(22398));
    }


}

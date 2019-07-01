package org.eq;

import org.eq.modules.order.task.OrderFinishSnapshotTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * * @author gb
 *
 * @version 2019/6/30
 */
public class FinishTask extends BaseTest {

    @Autowired
    private OrderFinishSnapshotTask task;
    @Test
    public void test(){
        task.process();
    }
}

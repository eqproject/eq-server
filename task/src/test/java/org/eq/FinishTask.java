package org.eq;

import com.alibaba.fastjson.JSON;
import org.eq.modules.order.biz.OrderFinishSnapshotBiz;
import org.eq.modules.order.task.OrderFinishSnapshotTask;
import org.eq.modules.trade.entity.OrderTrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * * @author gb
 *
 * @version 2019/6/30
 */
public class FinishTask extends BaseTest {

    @Autowired
    private OrderFinishSnapshotTask task;
    @Autowired
    private OrderFinishSnapshotBiz biz;
    @Test
    public void test(){
        task.process();
    }

    @Test
    public void test2(){
        List<OrderTrade> list =  biz.searchFinishOrderTrade();
        System.out.println(JSON.toJSONString(list));
    }
}

package org.eq;

import org.eq.modules.common.cache.ProductCache;
import org.eq.modules.enums.OrderTradeTypeEnum;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.trade.service.impl.OrderTradeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : gb 2019/6/25
 */
public class BcTest extends BaseTest{
    @Autowired
    private ProductCache productCache;
    @Autowired
    private OrderTradeServiceImpl service;

    @Test
    public void test(){
        ProductAll productAll =  productCache.getProduct("5");
        service.insertBx(productAll,"buQpQzbno6269ijXpw2e7WvrEjDda1c3GCa4","buQoVrxc4yuCbAGRQB4qcBTMbccgGFzikTRc",1,OrderTradeTypeEnum.ORDER_BUY.getType());
    }
}

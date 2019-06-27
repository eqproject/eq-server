package org.eq.modules.product.task;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.product.biz.ProductBiz;
import org.eq.modules.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品过期下架任务
 * @author kaka
 * @date  2019-06-09
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOverdueTask extends BaseLog {

    @Autowired
    private final ProductBiz productBiz;

    @Scheduled(cron = "0 0 0 * * ?")
    public void process() {
        logger.info("ProductOverdueTask 每天执行-开始");
        try {
            List<Product> overList =  productBiz.listOverdueProduct();
            if(CollectionUtils.isEmpty(overList)){
                logger.info("ProductOverdueTask 本次查询未发现过期商品信息");
            }
            for(Product product : overList){
                boolean isover = productBiz.overdueProduct(product.getId());
                if(isover){
                    isover = productBiz.overdueOrderByProductId(product.getId());
                }
                if(!isover){
                    logger.error("OrderTradePayExpireTask 下线商品有误 ，商品ID:{}",product.getId());
                }
            }
        } catch (Exception e) {
            logger.error("OrderTradePayExpireTask 执行异常:",e);
        }
        logger.info("ProductOverdueTask 每天执行-结束");
    }
}

package org.eq.modules.product.task;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.base.BaseLog;
import org.eq.modules.bc.common.util.StringUtil;
import org.eq.modules.product.biz.ProductBiz;
import org.eq.modules.product.biz.ProductLoadBiz;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductBlockchain;
import org.eq.modules.product.entitys.TicketProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品同步定时任务
 * @author kaka
 * @date  2019-06-09
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductLoadTask extends BaseLog {

    @Autowired
    private final ProductLoadBiz  productLoadBiz;

    @Scheduled(cron = "0 57 20 * * ?")
    public void process() {
        logger.info("ProductLoadTask 每天执行-开始");
        try {
            List<TicketProduct> tickList =  productLoadBiz.listProduct();
            if(CollectionUtils.isEmpty(tickList)){
                logger.info("ProductLoadTask 本次未加载到商品信息");
            }

            for(TicketProduct ticketProduct : tickList){
                if(StringUtil.isEmpty(ticketProduct.getVoucherId()) || StringUtil.isEmpty(ticketProduct.getTrancheId())){
                   continue;
                }
                ProductBlockchain productBlockchain =  productLoadBiz.getProductBlockchain(ticketProduct.getVoucherId(),ticketProduct.getTrancheId());
                if(productBlockchain!=null){
                    continue;
                }
                long productId = productLoadBiz.insertProduct(ticketProduct);
                if(productId<=0){
                    logger.error("ProductLoadTask 商品未插入成功");
                }else{
                    logger.info("ProductLoadTask 商品插入成功,商品ID为 {} ",productId);
                }



            }
        } catch (Exception e) {
            logger.error("ProductLoadTask 执行异常:",e);
        }
        logger.info("ProductLoadTask 每天执行-结束");
    }
}

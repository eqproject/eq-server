package org.eq.modules.product.biz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.bc.common.log.Logger;
import org.eq.modules.bc.common.log.LoggerFactory;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.order.biz.OrderAdExpireBiz;
import org.eq.modules.order.dao.OrderAdLogMapper;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.dao.UserProductStockMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品过期任务类
 * @author  kaka
 * @date  20190606
 */
@SuppressWarnings("all")
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductBiz {

    private Logger logger = LoggerFactory.getLogger(ProductBiz.class);

    /**
     * 商品服务
     */
    @Autowired
    private final ProductMapper productMapper;

    @Autowired
    private OrderAdExpireBiz orderAdExpireBiz;

    /**
     * 广告
     */
    @Autowired
    private OrderAdMapper orderAdMapper;




    /**
     * 查询过期商品信息
     * @return
     */
    public List<Product> listOverdueProduct() {
        ProductExample example = new ProductExample();
        ProductExample.Criteria ca = example.or();
        ca.andExpirationEndLessThanOrEqualTo(DateUtil.getNowTimeStr());
        ca.andStatusIn(ProductStateEnum.getRunStatus());
        return productMapper.selectByExample(example);
    }



    /**
     * 过期商品信息
     * @return
     */
    public boolean  overdueProduct(long productId) {
       Product product = productMapper.selectByPrimaryKey(productId);
       if(product==null){
           return false;
       }
       if(product.getStatus() == ProductStateEnum.OFFLINE.getState()){
           return false;
       }
       ProductExample example = new ProductExample();
       ProductExample.Criteria ca = example.or();
       ca.andStatusEqualTo(product.getStatus());
       ca.andIdEqualTo(productId);

       Product updateProduct = new Product();
       updateProduct.setStatus(ProductStateEnum.OFFLINE.getState());
       updateProduct.setUpdateDate(new Date());

       boolean result = false;
       try {
           result = productMapper.updateByExampleSelective(updateProduct,example)>0?true : false;
       }catch (Exception e){
           e.printStackTrace();
           logger.error("更新商品状态异常",e);
       }
       return result;
    }

    /**
     * 按照商品下线订单
     * @param productId
     * @return
     */
    public boolean  overdueOrderByProductId(long productId){
        if(productId<=0){
            return false;
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return false;
        }
        OrderAdExample orderAdExample = orderAdExpireBiz.getEffectOrderExample(productId);
        List<OrderAd> list = orderAdMapper.selectByExample(orderAdExample);
        if(CollectionUtils.isEmpty(list)){
            return true;
        }
        int result = 0;
        for(OrderAd orderAd : list){
            //待审 和 交易中 才可取消
            if(orderAd.getStatus() != OrderAdStateEnum.ORDER_DEFAULT.getState() &&  orderAd.getStatus()!=OrderAdStateEnum.ORDER_TRADEING.getState()){
                continue;
            }
            boolean upresult = orderAdExpireBiz.cacelOrderAd(orderAd.getId(),"定时任务下架商品，订单取消");
            if(!upresult){
                logger.error("取消订单失败,订单编号为:{}",orderAd.getOrderNo());
            }
            result= result+1;
        }
        return true;

    }






}

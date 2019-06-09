package org.eq.modules.product.biz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.bc.common.log.Logger;
import org.eq.modules.bc.common.log.LoggerFactory;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.enums.ProductStateEnum;
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

    /**
     * 广告
     */
    @Autowired
    private OrderAdMapper orderAdMapper;

    /**
     * 广告日志
     */
    @Autowired
    private OrderAdLogMapper orderAdLogMapper;


    /**
     * 用户库存
     */
    @Autowired
    private UserProductStockMapper userProductStockMapper;


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
        OrderAdExample orderAdExample = getEffectOrderExample(productId);
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
            boolean upresult = cacelOrderAd(orderAd.getId());
            if(!upresult){
                logger.error("取消订单失败,订单编号为:{}",orderAd.getOrderNo());
            }
            result= result+1;
        }
        return true;

    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    private  boolean cacelOrderAd(long orderId) {
        if(orderId<=0){
            return false;
        }
        OrderAd orderAd = orderAdMapper.selectByPrimaryKey(orderId);
        if(orderAd==null){
            return false;
        }
        if(orderAd.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()){
            return true;
        }
        if(OrderAdStateEnum.isOverState(orderAd.getStatus())){
            return false;
        }

        OrderAd updateOrder = new OrderAd();
        updateOrder.setStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        updateOrder.setUpdateDate(new Date());
        updateOrder.setCancelDesc("定时任务下架商品，订单取消");
        int updateResult = orderAdMapper.updateByExampleSelective(updateOrder,getExampleFromEntity(orderAd.getStatus(),orderAd.getId()));
        if(updateResult<=0){
            return false;
        }
        StringBuffer remarks = new StringBuffer();
        if(orderAd.getType()== OrderAdTypeEnum.ORDER_SALE.getType()){
            int number  = orderAd.getProductNum() -orderAd.getTradedNum()  - orderAd.getTradingNum();
            boolean stockResult  = updateStock(orderAd.getUserId(),orderAd.getProductId(),-number);
            if(stockResult){
                remarks.append("取消成功,").append("退回库存成功,应退:").append(number).append(",执行快照为:").append(orderAd.toString());
            }else{
                remarks.append("取消失败,").append("退回库存失败,应退回:").append(number).append(",执行快照为:").append(orderAd.toString());
            }
        }else{
            remarks.append("取消成功,").append("执行快照为:").append(orderAd.toString());
        }
        OrderAdLog orderAdLog = new OrderAdLog();
        orderAdLog.setCreateDate(new Date());
        orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        orderAdLog.setOldStatus(orderAd.getStatus());
        orderAdLog.setOrderAdId(orderAd.getId());
        orderAdLog.setRemarks(remarks.toString());
        try{
            orderAdLogMapper.insert(orderAdLog);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("插入订单操作日志记录数据出错 {}",orderAdLog.toString());
        }
        return true ;
    }

    /**
     * 更改用户库存信息
     * @param userId
     * @param productId
     * @param number
     * @return
     */
    private boolean updateStock(long userId,long productId,int number){

        List<UserProductStock> userProductStockList = userProductStockMapper.selectByExample(getUserProductStockExapple(userId,productId));
        if(CollectionUtils.isEmpty(userProductStockList)){
            return false;
        }
        UserProductStock userProductStock = userProductStockList.get(0);
        int lockNum = userProductStock.getLockedNum();

        UserProductStock update = new UserProductStock();
        update.setLockedNum(userProductStock.getLockedNum()+number);
        update.setUpdateDate(new Date());

        UserProductStockExample example = new UserProductStockExample();
        UserProductStockExample.Criteria ca = example.or();
        ca.andIdEqualToForSimple(userProductStock.getId());
        ca.andLockNumForUpdate(lockNum);
        int updateResult = userProductStockMapper.updateByExampleSelective(update,example);
        if(updateResult>0){
            return true;
        }
        return false;
    }


    /**
     * 获取有效的广告订单
     * @param productId
     * @return
     */
    private  OrderAdExample getEffectOrderExample(long productId) {
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();
        if(productId>0){
            ca.andProductIdEqualToForAll(productId);
        }
        List<Integer> states = new ArrayList<>();
        states.add(OrderAdStateEnum.ORDER_TRADEING.getState());
        states.add(OrderAdStateEnum.ORDER_DEFAULT.getState());
        ca.andStatusInForAll(states);
        return example;
    }


    /**
     * 获取简单查询实体
     * @param statue
     * @param id
     * @return
     */
    private OrderAdExample getExampleFromEntity(int statue,long id) {
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();
        ca.andIdEqualToForUpdate(id);
        ca.andStatusEqualToForUpdate(statue);
        return example;
    }



    private UserProductStockExample getUserProductStockExapple(Long userId,Long productId) {
        UserProductStockExample example = new UserProductStockExample();
        UserProductStockExample.Criteria ca = example.or();
        if(userId!=null){
            ca.andUserIdEqualTo(userId);
        }
        if(productId!=null){
           ca.andProductIdEqualTo(productId);
        }
        return example;
    }




}

package org.eq.modules.trade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.modules.trade.service.OrderTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 订单交易ServiceImpl
 *
 * @author yufei.sun
 * @version 1.0
 */
@Service
@Transactional
@AutowiredService
public class OrderTradeServiceImpl extends ServiceImplExtend<OrderTradeMapper, OrderTrade, OrderTradeExample> implements OrderTradeService {


    @Autowired
    private ProductService productService;

    @Override
    public OrderTradeExample getExampleFromEntity(OrderTrade orderTrade, Map<String, Object> params) {
        OrderTradeExample example = new OrderTradeExample();
        OrderTradeExample.Criteria ca = example.or();
        if (orderTrade == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        Date beginCreateDate = null;
        Date endCreateDate = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
            beginCreateDate = (Date) params.get("beginCreateDate");
            endCreateDate = (Date) params.get("endCreateDate");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (orderTrade.getSellUserId() != null) {
            ca.andSellUserIdEqualTo(orderTrade.getSellUserId());
        }
        if (orderTrade.getBuyUserId() != null) {
            ca.andBuyUserIdEqualTo(orderTrade.getBuyUserId());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getAdNo())) {
            ca.andAdNoEqualTo(orderTrade.getAdNo());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getTradeNo())) {
            ca.andTradeNoEqualTo(orderTrade.getTradeNo());
        }
        if (orderTrade.getProductId() != null) {
            ca.andProductIdEqualTo(orderTrade.getProductId());
        }
        if (orderTrade.getOrderNum() != null) {
            ca.andOrderNumEqualTo(orderTrade.getOrderNum());
        }
        if (orderTrade.getType() != null) {
            ca.andTypeEqualTo(orderTrade.getType());
        }
        if (orderTrade.getBlockchainStatus() != null) {
            ca.andBlockchainStatusEqualTo(orderTrade.getBlockchainStatus());
        }
        if (orderTrade.getSalePrice() != null) {
            ca.andSalePriceEqualTo(orderTrade.getSalePrice());
        }
        if (orderTrade.getUnitPrice() != null) {
            ca.andUnitPriceEqualTo(orderTrade.getUnitPrice());
        }
        if (orderTrade.getAmount() != null) {
            ca.andAmountEqualTo(orderTrade.getAmount());
        }
        if (orderTrade.getRemindPay() != null) {
            ca.andRemindPayEqualTo(orderTrade.getRemindPay());
        }
        if (orderTrade.getFinishTime() != null) {
            ca.andFinishTimeEqualTo(orderTrade.getFinishTime());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getRemarks())) {
            ca.andRemarksEqualTo(orderTrade.getRemarks());
        }
        if (StringLowUtils.isNotBlank(orderTrade.getCancelDesc())) {
            ca.andCancelDescEqualTo(orderTrade.getCancelDesc());
        }
        if (orderTrade.getCreateDate() != null) {
            ca.andCreateDateEqualTo(orderTrade.getCreateDate());
        }
        if (orderTrade.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(orderTrade.getUpdateDate());
        }
        if (orderTrade.getTxId() != null) {
            ca.andTxIdEqualTo(orderTrade.getTxId());
        }
        if (beginCreateDate != null) {
            ca.andCreateDateGreaterThanOrEqualTo(beginCreateDate);
        }
        if (endCreateDate != null) {
            ca.andCreateDateLessThan(endCreateDate);
        }
        if (orderTrade.getProductName() != null) {
            ca.andProductNameLikeForAll(orderTrade.getProductName());
        }

        // 设置商品的查询条件
        //setProductQueryContion(ca, orderTrade);

        return example;
    }

    /**
     * 设置按商品名称或商品编码的查询条件
     *
     * @param ca
     * @param order
     */
    private void setProductQueryContion(OrderTradeExample.Criteria ca, OrderTrade order) {
        List<Long> productIdList = new LinkedList<>();
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteriaProduct = productExample.or();
        if (StringUtils.isNotBlank(order.getProductName())) {
            productIdList.add(Long.valueOf(-1));
            if (StringUtils.isNotBlank(order.getProductName())) {
                criteriaProduct.andProductImgNotLike(order.getProductName());
            }
            List<Product> productList = productService.findListByExample(productExample);
            if (!CollectionUtils.isEmpty(productList)) {
                for (Product product : productList) {
                    productIdList.add(product.getId());
                }
            }
            if (productIdList.size() > 1) {
                ca.andProductIdIn(productIdList);
            } else {
                ca.andProductIdEqualTo(productIdList.get(0));
            }
        }
    }

}
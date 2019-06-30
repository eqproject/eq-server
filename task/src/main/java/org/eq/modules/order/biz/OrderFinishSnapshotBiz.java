package org.eq.modules.order.biz;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.enums.*;
import org.eq.modules.order.dao.OrderAdMapper;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
import org.eq.modules.orderfinish.dao.OrderFinishSnapshootMapper;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshoot;
import org.eq.modules.orderfinish.entity.OrderFinishSnapshootExample;
import org.eq.modules.product.dao.ProductMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.trade.dao.OrderTradeMapper;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * @author gb
 *
 * @version 2019/6/2
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderFinishSnapshotBiz {

    private final OrderAdMapper orderAdMapper;
    private final OrderTradeMapper orderTradeMapper;
    private final OrderFinishSnapshootMapper orderFinishSnapshootMapper;
    private final ProductMapper productMapper;


    public List<OrderAd> searchFinishOrderAd() {
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();

        List<Integer> status = new ArrayList<>();
        status.add(OrderAdStateEnum.ORDER_CANCEL.getState());
        status.add(OrderAdStateEnum.ORDER_FINISH.getState());
        status.add(OrderAdStateEnum.ORDER_REJECT.getState());

        TaskTime time = new TaskTime().build();

        ca.andStatusInForAll(status);
        ca.andUpdateDateBetweenForAll(time.getStart(), time.getEnd());
        return orderAdMapper.selectByExample(example);
    }


    public List<OrderTrade> searchFinishOrderTrade() {
        OrderTradeExample example = new OrderTradeExample();
        OrderTradeExample.Criteria ca = example.or();

        List<Integer> status = new ArrayList<>();
        status.add(OrderTradeStateEnum.CANCEL.getState());
        status.add(OrderTradeStateEnum.CANCEL_PAY_TIMEOUT.getState());
        status.add(OrderTradeStateEnum.TRADE_SUCCESS.getState());
        status.add(OrderTradeStateEnum.REFUND_SUCCESS.getState());
        ca.andStatusInForAll(status);
        TaskTime time = new TaskTime().build();

        ca.andStatusInForAll(status);
        ca.andUpdateDateBetweenForAll(time.getStart(), time.getEnd());
        return orderTradeMapper.selectByExample(example);
    }

    public void insert(OrderFinishSnapshoot o) {
        OrderFinishSnapshoot entity = getEntity(o);
        if (entity == null) {
            orderFinishSnapshootMapper.insert(o);
        } else {
            BeanUtils.copyProperties(o, entity, "id");
            orderFinishSnapshootMapper.updateByPrimaryKeySelective(entity);
        }
    }

    private OrderFinishSnapshoot getEntity(OrderFinishSnapshoot o) {
        OrderFinishSnapshootExample example = new OrderFinishSnapshootExample();
        example.or()
                .andOrderNoEqualToForAll(o.getOrderNo())
                .andTradeNoEqualToForAll(o.getTradeNo());
        List<OrderFinishSnapshoot> list = orderFinishSnapshootMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public OrderFinishSnapshoot toOrderFinishSnapshoot(OrderAd order) {
        Product product = productMapper.selectByPrimaryKey(order.getProductId());

        OrderFinishSnapshoot o = new OrderFinishSnapshoot();
        if (order.getType() == OrderAdTypeEnum.ORDER_SALE.getType()) {
            o.setSellUserId(order.getUserId());
        } else {
            o.setBuyUserId(order.getUserId());
        }

        o.setOrderNo(order.getOrderNo());
        o.setTradeNo("");
        o.setProductId(order.getProductId());
        o.setProductName(product.getName());
        o.setUnitPrice(product.getUnitPrice());
        o.setSaleprice(order.getPrice());
        o.setOrderNum(order.getProductNum());

        int type = order.getType() == OrderAdTypeEnum.ORDER_SALE.getType()
                ? OrderFinishTypeEnum.ORDER_AD_SALE.getType()
                : OrderFinishTypeEnum.ORDER_AD_BUY.getType();
        o.setType(type);
        o.setAmount(order.getAmount());

        if (order.getStatus() == OrderAdStateEnum.ORDER_CANCEL.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_CANCEL.getState());

        } else if (order.getStatus() == OrderAdStateEnum.ORDER_FINISH.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_FINISH.getState());

        } else if (order.getStatus() == OrderAdStateEnum.ORDER_REJECT.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_CLOSE.getState());
        }
        o.setCreateDate(order.getUpdateDate());
        return o;
    }

    public OrderFinishSnapshoot toOrderFinishSnapshoot(OrderTrade order) {
        Product product = productMapper.selectByPrimaryKey(order.getProductId());

        OrderFinishSnapshoot o = new OrderFinishSnapshoot();
        o.setSellUserId(order.getSellUserId());
        o.setBuyUserId(order.getBuyUserId());
        o.setOrderNo(order.getAdNo());
        o.setTradeNo(order.getTradeNo());
        o.setProductId(order.getProductId());
        o.setProductName(product.getName());
        o.setUnitPrice(product.getUnitPrice());
        o.setSaleprice(order.getSalePrice());
        o.setOrderNum(order.getOrderNum());

        int type = order.getType() == OrderTradeTypeEnum.ORDER_SALE.getType()
                ? OrderFinishTypeEnum.ORDER_TRADE_SALE.getType()
                : OrderFinishTypeEnum.ORDER_TRADE_BUY.getType();
        o.setType(type);
        o.setAmount(order.getAmount());

        if (order.getStatus() == OrderTradeStateEnum.CANCEL.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_CANCEL.getState());

        } else if (order.getStatus() == OrderTradeStateEnum.PAY_FAIL.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_CLOSE.getState());

        } else if (order.getStatus() == OrderTradeStateEnum.CANCEL_PAY_TIMEOUT.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_CLOSE.getState());

        } else if (order.getStatus() == OrderTradeStateEnum.TRADE_SUCCESS.getState()) {
            o.setStatus(OrderFinishStateEnum.ORDER_FINISH.getState());
        }
        o.setCreateDate(order.getUpdateDate());
        return o;
    }

    @Data
    static class TaskTime {
        private Date start;
        private Date end;

        TaskTime build() {
            LocalDate now = LocalDate.now();
            String start = now.plusDays(-1).toString();
            String end = now.toString();

            String startTime = start + " 00:00:00";
            String endTime = end + " 23:59:59";
            this.setStart(startTime);
            this.setEnd(endTime);
            return this;
        }

        private void setStart(String time) {
            this.start = DateUtil.passDate(time, DateUtil.DATE_FORMAT_FULL_01);
        }

        private void setEnd(String time) {
            this.end = DateUtil.passDate(time, DateUtil.DATE_FORMAT_FULL_01);
        }
    }
}

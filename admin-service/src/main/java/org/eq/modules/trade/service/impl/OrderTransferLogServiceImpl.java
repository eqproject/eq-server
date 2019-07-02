package org.eq.modules.trade.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.order.dao.OrderTransferLogMapper;
import org.eq.modules.order.entity.OrderTransferLog;
import org.eq.modules.order.entity.OrderTransferLogExample;
import org.eq.modules.trade.service.OrderTransferLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 转让日志ServiceImpl
 * @author hobe
 * @version 2019-07-03
 */
@Service
@Transactional
@AutowiredService
public class OrderTransferLogServiceImpl extends ServiceImplExtend<OrderTransferLogMapper, OrderTransferLog, OrderTransferLogExample> implements OrderTransferLogService {

    @Override
    public OrderTransferLogExample getExampleFromEntity(OrderTransferLog orderTransferLog, Map<String, Object> params) {
        OrderTransferLogExample example = new OrderTransferLogExample();
        OrderTransferLogExample.Criteria ca = example.or();
        if (orderTransferLog == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (orderTransferLog.getId() != null) {
            ca.andIdEqualTo(orderTransferLog.getId());
        }
        if (orderTransferLog.getOrderTransferId() != null) {
            ca.andOrderTransferIdEqualTo(orderTransferLog.getOrderTransferId());
        }
        if (orderTransferLog.getOldStatus() != null) {
            ca.andOldStatusEqualTo(orderTransferLog.getOldStatus());
        }
        if (orderTransferLog.getNewStatus() != null) {
            ca.andNewStatusEqualTo(orderTransferLog.getNewStatus());
        }
        if (orderTransferLog.getCreateDate() != null) {
            ca.andCreateDateEqualTo(orderTransferLog.getCreateDate());
        }
        if (StringLowUtils.isNotBlank(orderTransferLog.getRemarks())) {
            ca.andRemarksEqualTo(orderTransferLog.getRemarks());
        }
        return example;
    }

}
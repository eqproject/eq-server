/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.orderFinish.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户完成订单Entity
 *
 * @author gb
 * @version 2019-06-02
 */
@Data
public class OrderFinishSnapshoot extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long id;        // 唯一标识
    private String orderNo;        // 商品订单号
    private String tradeNo;        // 交易订单号
    private Long productId;        // 商品Id
    private String productName;        // 商品名称
    private Integer unitPrice;        // 商品单价
    private Integer orderNum;        // 订单数量
    private Integer tradeNum;        // 交易数量
    private Integer type;        // 订单类型:(1:广告出售订单;2:广告求购订单;3:交易出售订单;4:交易求购订单)
    private Integer status;        // 状态:(1:已完成;2:已关闭;3:已取消;)
    private Integer saleprice;        //
    private Integer amount;        // 金额（分）
}
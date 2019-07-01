/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.orderfinish.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户完成订单Entity
 *
 * @author gb
 * @version 2019-06-02
 */
@Data
public class OrderFinishSnapshoot {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 售卖用户id
     */
    private Long sellUserId;
    /**
     * 购买用户id
     */
    private Long buyUserId;
    /**
     * 商品订单号
     */
    private String orderNo;
    /**
     * 交易订单号
     */
    private String tradeNo;
    /**
     * 商品Id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单价
     */
    private Integer unitPrice;
    /**
     * 订单数量
     */
    private Integer orderNum;
    /**
     * 交易数量
     */
    private Integer tradeNum;
    /**
     * 订单类型
     *
     * @see org.eq.modules.enums.OrderFinishTypeEnum
     */
    private Integer type;
    /**
     * 状态
     *
     * @see org.eq.modules.enums.OrderFinishStateEnum
     */
    private Integer status;
    /**
     * 售卖价格
     */
    private Integer saleprice;

    /**
     * 订单价格
     */
    private Integer amount;


    /**
     * 创建时间
     */
    private Date createDate;


    /**
     * 昵称
     */
    private String sellNickName;


    /**
     * 头像地址
     */
    private String sellPhotoHead;


    /**
     * 昵称
     */
    private String buyNickName;


    /**
     * 头像地址
     */
    private String buyPhotoHead;

    /**
     * 商品图片地址
     */
    private String productImg;
}
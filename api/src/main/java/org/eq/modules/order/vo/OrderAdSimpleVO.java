package org.eq.modules.order.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告订单返回实体
 * @date 2019-0
 */
@Data
public class OrderAdSimpleVO implements Serializable {


    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 销售单价
     */
    private int price;


    /**
     * 订单总量
     */
    private int orderNumber;

    /**
     * 已完成交易量
     */
    private int saleedNumber;


    /**
     * 可交易量
     */
    private int saleNumber;

    /**
     * 标题
     */
    private String title;


    /**
     * 用户ID
     */
    private  long userId;

    /**
     * 用户头像
     */
    private String userImg;


    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 认证情况
     */
    private int userBoundState;

    /**
     * 订单类型
     */
    private int orderType;


    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 交易数
     */
    private int tradeNum;

    /**
     * 完成率
     */
    private double tradeRate;


}

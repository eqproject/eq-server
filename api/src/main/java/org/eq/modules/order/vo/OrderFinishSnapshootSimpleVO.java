package org.eq.modules.order.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 完成订单返回实体
 * @date 2019-0
 */
@Data
public class OrderFinishSnapshootSimpleVO implements Serializable {


    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 单价
     */
    private long unitPrice;

    /**
     * 交易号
     */
    private String  tradeNo;


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单总金额
     */
    private int amount;


    /**
     * 订单总量
     */
    private int orderNum;

    /**
     * 销售单价
     */
    private int salePrice;


    /**
     * 服务费
     */
    private  int serviceFee;

    /**
     * 状态
     */
    private String status;


    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 订单类型
     */
    private int type;

    /**
     * 用户头像
     */
    private String userHeadImg;

    /**
     * 用户呢称
     */
    private String userNickname;

    /**
     * 广告应交易数量描述
     */
    private String orderWantNumberRemark;
    /**
     * 广告已交易数量描述
     */
    private String orderFinishNumberRemark;

}

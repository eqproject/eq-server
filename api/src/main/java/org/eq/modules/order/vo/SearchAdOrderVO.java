package org.eq.modules.order.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * API层 调用广告订单实体
 * @author  kaka
 * @date  2019-06-01
 */
@Data
public class SearchAdOrderVO extends SearchBase {

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 广告标题
     */
    private String adTitle;

    /**
     * 单价
     */
    private int price;


    /**
     * 售卖求购数量
     */
    private int  number;


    /**
     * 用户ID
     */
    private long userId;

    /**
     * 广告类型
     * @see org.eq.modules.enums.OrderAdTypeEnum
     */
    private int orderType;


    /**
     * 订单号
     */
    private String orderCode;




}

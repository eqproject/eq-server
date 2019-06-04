package org.eq.modules.order.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 承兑查询实体
 * @author  kaka
 * @date  2019-06-01
 */
@Data
public class SearchAcceptOrderVO extends SearchBase {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 转让数量
     */
    private int  number;

    /**
     * 联系人
     */
    private String consignee;


    /**
     * 联系人电话
     */
    private String consigneePhone;


    /**
     * 联系人地址
     */
    private String consigneeAddress;


}

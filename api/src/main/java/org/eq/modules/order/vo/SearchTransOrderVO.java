package org.eq.modules.order.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 转让查询实体
 * @author  kaka
 * @date  2019-06-01
 */
@Data
public class SearchTransOrderVO extends SearchBase {

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
     * 转让地址
     */
    private String address;


}

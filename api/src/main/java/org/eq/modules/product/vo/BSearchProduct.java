package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 内部查询实体
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class BSearchProduct {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品状态
     * @see  org.eq.modules.enums.ProductStateEnum
     */
    private Integer state;

    /**
     * 是否过期
     *  默认有效
     */
    private boolean isOver;
}

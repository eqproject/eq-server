package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 查询商品信息实体
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class SearchProductVO extends SearchBase {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long id;


}

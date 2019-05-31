package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 查询商品信息实体
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class SearchPageProductVO extends SearchBase {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 每页最大条数
     */
    private int  pageSize;

    /**
     * 页号
     */
    private int pageNum;

}

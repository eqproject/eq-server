package org.eq.modules.order.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * 承兑查询实体
 * @author  kaka
 * @date  2019-06-01
 */
@Data
public class SearchPageAcceptVO extends SearchBase {

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

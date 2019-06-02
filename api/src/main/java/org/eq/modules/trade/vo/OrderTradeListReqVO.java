package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * @author admin
 * @Title: OrderTradeListReqVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午9:29
 */
@Data
public class OrderTradeListReqVO extends SearchBase{

    private static final long serialVersionUID = 1L;

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

package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.common.entitys.SearchBase;

/**
 * @author admin
 * @Title: OrderTradeCancelReqVO
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午7:10
 */
@Data
public class OrderTradeCancelReqVO extends SearchBase{
    private static final long serialVersionUID = 1L;

    private String tradeNo;

}

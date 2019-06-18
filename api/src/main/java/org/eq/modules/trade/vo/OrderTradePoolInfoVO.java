package org.eq.modules.trade.vo;

import lombok.Data;
import org.eq.modules.product.vo.ProductDetailVO;

import java.io.Serializable;


/**
 *  汇总数据
 * @author kaka
 * @date  20190619
 */
@Data
public class OrderTradePoolInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 等待付款
     */
    private int  waitPay;


    /**
     * 进行中订单数
     */
    private int progress;
}

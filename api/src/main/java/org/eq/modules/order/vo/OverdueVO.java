package org.eq.modules.order.vo;

import lombok.Data;
import org.eq.modules.product.vo.ProductBaseVO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 失效商品
 * @author  kaka
 * @date  2019-06-04
 */
@Data
public class OverdueVO implements Serializable , Comparator<OverdueVO> {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 商品ID
     */
    private long productId;


    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 失效数量
     */
    private int number;

    /**
     * 面值
     */
    private int unitPrice;


    /**
     * 失效原因
     */
    private String overdueReason;

    /**
     * 排序
     */
    private int sort;



    @Override
    public int compare(OverdueVO o1, OverdueVO o2) {
        return o1.getSort()-o2.getSort();
    }
}

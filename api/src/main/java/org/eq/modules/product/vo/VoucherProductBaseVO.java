package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

import java.util.Comparator;

/**
 * 券包信息表
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class VoucherProductBaseVO extends ProductBaseVO {

    /**
     * 用户持有数量
     */
    private int number;


    /**
     * 用户有效量
     */
    private int effectNumber;


    /**
     * 锁定量
     */
    private int lockNumber;


    /**
     * 商品ID
     */
    private long productId;
}

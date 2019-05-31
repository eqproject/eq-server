package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

import java.io.Serializable;

/**
 * 商品扩展信息
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class ProductExtend implements Serializable {

    /**
     * 提货说明
     */
    private  String receive;



}

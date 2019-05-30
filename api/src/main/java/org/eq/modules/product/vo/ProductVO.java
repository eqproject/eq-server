package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

/**
 * 商品信息表
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class ProductVO  extends EntityBase {

    /**
     * 商品名称
     */
    private  String productName;

    /**
     * 单价
     */
    private Integer unitPrice;

    /**
     * 商品图片地址
     */
    private String img;

    /**
     * 商品描述
     */
    private String desc;

    /**
     * 提货说明
     */
    private String receive;

    /**
     * 有效期开始时间
     */
    private String expirationStart;

    /**
     * 有效期结束时间
     */
    private String expirationEnd;





}

package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

import java.util.Comparator;

/**
 * 商品信息表
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class ProductDetailVO extends EntityBase{

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


    /**
     * 排序
     */
    private int sort;

    /**
     * 承兑商名称
     */
    private String acceptName;

    /**
     * 承兑商图片
     */
    private String acceptImg;


    /**
     * 承兑商地址
     */
    private String acceptAddress;


    /**
     * 承兑商简介
     */
    private String acceptIntro;

    /**
     * 发行商名称
     */
    private String issuerName;

    /**
     * 发行商图片
     */
    private String issuerImg;


    /**
     * 发行商地址
     */
    private String issuerAddress;


    /**
     * 发行商简介
     */
    private String issuerIntro;
}

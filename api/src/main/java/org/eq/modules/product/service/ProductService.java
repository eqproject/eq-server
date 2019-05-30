/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;

/**
 * 商品信息Service
 * @author kaka
 * @version 2019.05.08
 */
public interface ProductService extends ServiceExtend<Product,ProductExample> {

    /**
     * 通过排序值获取符合条件的商品信息
     * @param score 目标分数
     * @param isup true: 大于目标分数   false: 小于目标分数
     * @return
     */
    public Product getProductBetweenScore(int score, boolean isup);

}
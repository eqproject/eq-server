/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchProductVO;

/**
 * 商品信息Service
 * @author kaka
 * @version 2019.05.08
 */
public interface ProductService extends ServiceExtend<Product,ProductExample> {

    /**
     *  分页获取简单的商品信息
     * @param searchProductVO
     * @return
     */
    PageResultData<ProductVO> pageSimpeProduct(SearchProductVO searchProductVO);

}
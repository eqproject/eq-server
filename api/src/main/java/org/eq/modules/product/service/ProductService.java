/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.ProductDetailVO;
import org.eq.modules.product.vo.SearchPageProductVO;
import org.eq.modules.product.vo.BSearchProduct;

import java.util.List;

/**
 * 商品信息Service
 * @author kaka
 * @version 2019.05.08
 */
public interface ProductService extends ServiceExtend<Product,ProductExample> {

    /**
     *  分页获取简单的商品信息
     * @param searchPageProductVO
     * @return
     */
    PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO);


    /**
     * 全量获取
     * @param bsearchProduct
     * @return
     */
    List<ProductAll> listProductAll(BSearchProduct bsearchProduct);




    /**
     * 全量获取
     * @param bsearchProduct
     * @return
     */
    ProductDetailVO getProductAll(BSearchProduct bsearchProduct);
}
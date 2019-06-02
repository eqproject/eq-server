/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.vo.*;

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
     * 获取平台单个商品
     * @param bsearchProduct 查询实体
     * @return
     */
    ProductDetailVO getProductAll(BSearchProduct bsearchProduct);


    /**
     * 获取用户单个商品
     * @param bsearchProduct 查询实体
     * @param user
     * @return
     */
    UserProductDetailVO getUserProductAll(BSearchProduct bsearchProduct, User user);
}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.product.entity.ProductBlockchain;
import org.eq.modules.product.entity.ProductBlockchainExample;

/**
 * productBlockService
 * @author kaka
 * @version 1.0.1
 */
public interface ProductBlockchainService extends ServiceExtend<ProductBlockchain, ProductBlockchainExample> {


    /**
     * 通过商品ID 获取对象
     * @param productId
     * @return
     */
    ProductBlockchain getBuyProductId(long productId);
}
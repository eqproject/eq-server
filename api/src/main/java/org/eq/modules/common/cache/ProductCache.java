package org.eq.modules.common.cache;

import org.eq.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * 商品缓存
 */
@ComponentScan
public class ProductCache implements  BaseCache{


    @Autowired
    private ProductService productService;

    @Override
    public void init() {
        //TODO
    }
}

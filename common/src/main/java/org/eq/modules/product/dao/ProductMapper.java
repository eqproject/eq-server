/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.dao;


import org.apache.ibatis.annotations.Mapper;
import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.springframework.data.domain.Example;

import java.util.List;

/**
 * 商品基本信息管理Mapper接口
 * @author kaka
 * @version 1.0.1
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductExample> {


    /**
     * 输出商品全部信息
     * @param productExample
     * @return
     */
    List<ProductAll> selectProductAllByExample(ProductExample productExample);
	
}
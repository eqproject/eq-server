/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchPageProductVO;

import java.util.List;

/**
 * 用户商品管理Service
 * @author kaka
 * @version 1.0.1
 */
public interface UserProductStockService extends ServiceExtend<UserProductStock,UserProductStockExample> {



    /**
     *  分页获取简单的商品信息
     * @param searchPageProductVO
     * @param user
     * @return
     */
    PageResultData<ProductVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO,User user);


}
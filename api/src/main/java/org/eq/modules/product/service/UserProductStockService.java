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
import org.eq.modules.product.vo.ProductBaseVO;
import org.eq.modules.product.vo.SearchPageProductVO;

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
    PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO, User user);


    /**
     * 获取用户库存信息数据
     * @param productId 商品ID
     * @param user  用户信息
     * @return
     */
    UserProductStock getUserProductStock(long productId,User user);


}
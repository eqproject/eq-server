/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.product.entity.UserProductStock;
import org.eq.modules.product.entity.UserProductStockExample;

/**
 * 用户商品管理Service
 * @author kaka
 * @version 1.0.1
 */
public interface UserProductStockService extends ServiceExtend<UserProductStock,UserProductStockExample> {



    /**
     * 更改锁定量
     * @param productId
     * @param userId
     * @param number 为正则增加锁定量  为负数减少锁定量
     * @return
     */
    boolean updateStock(long productId, long userId, int number);


}
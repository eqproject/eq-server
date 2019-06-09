/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
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


    /**
     * 获取平台单个商品
     * @param productId 查询实体
     * @return
     */
    ProductAll getProductAll(long productId);


    /**
     * 更改商品状态
     * 1、判定商品是否存在
     * 2、商品已处于  过期  下线状态，不允许修改商品状态
     * 3、如果商品新状态为下线、过期， 旧状态为上线，则关闭订单
     * @param newState
     * @param oldState
     * @param productId
     * @return  影响商品数据
     */
    int updateProductState(int newState,int oldState,long productId);


    /**
     * 同步商品信息
     * @return
     */
    int loadProduct();


}
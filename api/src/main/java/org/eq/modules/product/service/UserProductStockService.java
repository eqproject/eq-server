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
import org.eq.modules.product.vo.VoucherProductBaseVO;

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
    PageResultData<ProductBaseVO> pageSimpeProduct(SearchPageProductVO searchPageProductVO, User user);


    /**
     * 获取用户库存信息数据
     *   1、验证平台商品是否存在
     *   2、验证区块链用户商品是否存在
     *   3、查询用户平台库存 如果没有则新建库存信息
     *   4、对比库存情况
     *   5、验证通过后 返回用户库存信息数据
     * @param productId 商品ID
     * @param user  用户信息
     * @return
     */
    UserProductStock getUserProductStock(long productId,User user);

    /**
     * 更改锁定量
     * @param productId
     * @param userId
     * @param number 为正则增加锁定量  为负数减少锁定量
     * @return
     */
    boolean updateStock(long productId,long userId,int number);


    /**
     * 获取用户有效的商品信息
     *  1、获取区块链商品信息
     *  2、验证平台商品是否存在
     *  3、查询用户平台库存 如果没有则新建库存信息
     *  4、对比库存情况
     *  5、验证通过后 返回用户库存信息数据
     * @param user
     * @return
     */
    List<Long> listUserProdutId(User user);


    /**
     *  分页获取用户钱包数据
     * @param searchPageProductVO
     * @param user
     * @return
     */
    PageResultData<VoucherProductBaseVO> pageVoucherProduct(SearchPageProductVO searchPageProductVO, User user);



}
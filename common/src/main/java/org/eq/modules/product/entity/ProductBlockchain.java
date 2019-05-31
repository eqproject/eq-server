/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * productBlockEntity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class ProductBlockchain {

	private static final long serialVersionUID = 1L;
    /**
     * 商品库ID
     */
	private Long productId;
    /**
     * 资产code
     */
	private String assetCode;
    /**
     *  资产发行人
     */
	private String assetIssuer;
    /**
     * 合约地址
     */
	private String contractAddress;
    /**
     * 区块链券ID
     */
	private String ticketid;
    /**
     * 区块链券有效期分组ID
     */
	private String trancheid;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;
}
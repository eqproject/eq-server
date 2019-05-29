/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.entity;

import lombok.Data;

import java.util.Date;

/**
 * 管理用户个人订单Entity
 * @author kaka
 * @version 1.0.0
 */
@Data
public class UserOrder {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单唯一标识
	 */
	private Long id;
	/**
	 * 商品订单号
	 */
	private String orderNo;
	/**
	 * 商品id
	 */
	private Long productId;
	/**
	 * 商品数量
	 */
	private Integer productNum;
	/**
	 * 商品交易数量
	 */
	private Integer tradeNum;
	/**
	 * 订单类型:(1:出售;2:求购)
	 * @see cn.bubi.c2c.enums.UserOrderTypeEnum
	 */
	private Integer type;
	/**
	 * 状态
	 *  @see cn.bubi.c2c.enums.UserOrderStateEnum
	 */
	private Integer status;
	/**
	 *  销售单价
	 */
	private Integer price;
	/**
	 * 金额
	 */
	private Long amount;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 * 中止取消原因
	 */
	private String cancelDesc;

	/**
	 * 创建人
	 */
	private Long  createBy;


	/**
	 * 创建时间
	 */
	private Date  createDate;

	/**
	 * 更新人
	 */
	private Long  updateBy;


	/**
	 * 更新时间
	 */
	private Date  updateDate;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 *
	 * 商品名称(关联商品库)
	 */
	private String productName;

    /**
     *
     * 商品编号 (关联商品库)
     */
	private String productCode;

	/**
	 * 创建人姓名(实时查询)
	 */
	private String createByName;



}
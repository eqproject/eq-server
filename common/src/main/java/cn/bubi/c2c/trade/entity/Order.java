/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.trade.entity;

import lombok.Data;

import java.util.Date;

/**
 * 交易订单管理Entity
 * @author yufei.sun
 * @version 1.0.0
 */
@Data
public class Order {

	private static final long serialVersionUID = 1L;
	private Integer amount;		// 金额
	private String code;		// 交易订单编号
	private String description;		// 商品描述
	private Long id;		// 唯一标识
	private Integer payType;		// 支付方式：(1:微信;2:支付宝)
	private Long productId;		// 商品id
	private Integer productNum;		// 商品数量
	private Integer status;		// 状态:(1:创建;2:待支付;3:取消;4:下线取消;5:支付成功;6:支付失败)
	private Integer type;		// 交易类型:(1:出售;2:求购)
	private String cancelDesc;  // 中止取消原因
	protected String remarks; // 备注
	protected Long createBy; // 创建者
	protected Date createDate;// 创建日期
	protected Long updateBy; // 更新者
	protected Date updateDate;// 更新日期

	// 定义查询条件参数
	private String productCode;//商品编码
	private String productName;//商品名称
	private String mobile; //手机号




}
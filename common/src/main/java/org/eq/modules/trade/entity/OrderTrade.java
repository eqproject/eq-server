/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单交易Entity
 * @author yufei.sun
 * @version 1.0
 */
@Data
public class OrderTrade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;		// 唯一标识
	private Long sellUserId;		// 售卖用户id
	private Long buyUserId;		// 购买用户id
	private String adNo;		// 广告订单号
	private String tradeNo;		// 交易订单号
	private Long productId;		// 商品id
	private Integer orderNum;		// 订单数量
	private Integer type;		// 订单类型:(1:出售;2:求购)
	private Integer status;		// 状态:(1:待支付;2:取消交易;3:支付中;4:支付成功;5:支付失败6:关闭交易(支付超时);7:放券中;8:放款中;9:放款失败;10:交易成功;11:退款中;12:退款成功)
	private Integer blockchainStatus;		// 状态:(1:区块链处理中;2:区块链处理成功;3:区块链处理失败;)
	private Integer salePrice;		// 售卖价格
	private Integer unitPrice;		// 商品面值
	private Integer amount;		// 金额（分）
	private Integer remindPay;		// 是否已催款:(0:否;1:是)
	private Date finishTime;		// 订单终结时间(全部终结状态）
	private String cancelDesc;		// 中止取消原因
	private Long txId;		// 区块链交易ID
	private String remarks; // 备注
	private Date createDate;// 创建日期
	private Date updateDate;// 更新日期

	// 查询条件
	private String productName;// 商品名称
}
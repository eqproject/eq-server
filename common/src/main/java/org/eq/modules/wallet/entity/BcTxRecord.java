/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 激活钱包任务Entity
 * @author hobe
 * @version 2019-06-01
 */
@Data
public class BcTxRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;		// 交易id
	private String fromAddress;		// 转出方地址
	private String toAddress;		// 转入方地址
	private String transferAmount;		// 
	private String assetCode;		// 资产code
	private String assetIssuer;		// 资产发行人
	private String contractAddress;		// 合约地址
	private Integer assetType;		// 1(ATP60协议) 2(BU转帐）
	private Integer txStatus;		// 0初始化 1处理中 2成功 3失败
	private String txHash;		// 交易hash
	private Integer bizType;		// 业务类型 (1,创建账户)(2,售卖券)(3,购买券)(4,转出)(5,承兑)
	private Date createTime;		// 
	private Date updateTime;		// 
	private String optMetadata;		// 交易描述
}
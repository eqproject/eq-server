/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.bc.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 区块链任务Entity
 * @author gb
 * @version 2019-06-03
 */
@Data
public class BlockchainTx {

	private static final long serialVersionUID = 1L;
	private Long id;		// 交易主键
	private String bcErrorCode;		// 交易错误码
	private String bcErrorMsg;		// 交易错误码描述
	private Integer bizType;		// 业务类型
	private String txBlob;		// 交易blob
	private String txFee;		// 交易预估费用
	private String txHash;		// 交易hash
	private String txInitiatorAddress;		// 交易发起人
	private Integer txStatus;		// 交易状态 0处理中 1成功 2失败
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
}
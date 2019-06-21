/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

/**
 * 用户钱包Entity
 * @author hobe
 * @version 2019-05-30
 */
@Data
public class UserWallet extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户id
	private String address;		// 区块链钱包地址
	private String publicKey;		// 公匙
	private String keyStore;		// key_store
	private Integer status;		// 激活状态（0：未激活；1已激活）
	private Long txId;		// 区块链交易ID
}
/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.entity;

import org.eq.basic.common.base.BaseEntity;
import java.util.Date;

/**
 * 用户钱包Entity
 * @author hobe
 * @version 2019-05-30
 */
public class UserWallet extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long userId;		// 用户id
	private String address;		// 区块链钱包地址
	private String pubilcKey;		// 公匙
	private String keyStore;		// key_store
	private Integer status;		// 激活状态（0：未激活；1已激活）
	private Long txId;		// 区块链交易ID

	public UserWallet() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPubilcKey() {
		return pubilcKey;
	}

	public void setPubilcKey(String pubilcKey) {
		this.pubilcKey = pubilcKey;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTxId() {
		return txId;
	}

	public void setTxId(Long txId) {
		this.txId = txId;
	}

}
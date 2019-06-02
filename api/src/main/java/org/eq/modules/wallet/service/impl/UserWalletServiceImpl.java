/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.wallet.dao.UserWalletMapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
import org.eq.modules.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 用户钱包ServiceImpl
 * @author hobe
 * @version 2019-05-30
 */
@Service
@Transactional
@AutowiredService
public class UserWalletServiceImpl extends ServiceImplExtend<UserWalletMapper, UserWallet, UserWalletExample> implements UserWalletService {

	@Autowired
	public UserWalletServiceImpl(UserWalletMapper mapper){
		super.setMapper(mapper);
	}

	@Override
	public UserWalletExample getExampleFromEntity(UserWallet userWallet, Map<String, Object> params) {
		UserWalletExample example = new UserWalletExample();
		UserWalletExample.Criteria ca = example.or();
		if(userWallet==null){
			return example;
		}
		String orderName = null;
		String orderDir = null;
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
		}
		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		if(userWallet.getUserId()!=null){
			ca.andUserIdEqualTo(userWallet.getUserId());
		}
		if(StringLowUtils.isNotBlank(userWallet.getAddress())){
			ca.andAddressEqualTo(userWallet.getAddress());
		}
		if(userWallet.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userWallet.getCreateDate());
		}
		if(userWallet.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(userWallet.getUpdateDate());
		}
		if(StringLowUtils.isNotBlank(userWallet.getPubilcKey())){
			ca.andPubilcKeyEqualTo(userWallet.getPubilcKey());
		}
		if(StringLowUtils.isNotBlank(userWallet.getKeyStore())){
			ca.andKeyStoreEqualTo(userWallet.getKeyStore());
		}
		if(userWallet.getStatus()!=null){
			ca.andStatusEqualTo(userWallet.getStatus());
		}
		if(userWallet.getTxId()!=null){
			ca.andTxIdEqualTo(userWallet.getTxId());
		}
		return example;
	}

}
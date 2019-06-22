/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.auth.service.UserWalletService;
import org.eq.modules.wallet.dao.UserWalletMapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
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

	@Override
	public UserWalletExample getExampleFromEntity(UserWallet userWallet, Map<String, Object> params) {
		UserWalletExample example = new UserWalletExample();
		if(userWallet==null){
			return example;
		}
		return example;
	}
}
/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.dao;

import org.eq.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;

/**
 * 用户钱包Mapper接口
 * @author hobe
 * @version 2019-05-30
 */
@Mapper
public interface UserWalletMapper extends BaseMapper<UserWallet,UserWalletExample> {
	
}
/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.service;

import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
import org.eq.basic.common.base.ServiceExtend;

/**
 * 用户钱包Service
 * @author hobe
 * @version 2019-05-30
 */
public interface UserWalletService extends ServiceExtend<UserWallet,UserWalletExample> {
    Integer activateWallet(Long userId);
}
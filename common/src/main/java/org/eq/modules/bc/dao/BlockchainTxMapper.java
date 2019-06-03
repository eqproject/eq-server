/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.bc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.bc.entity.BlockchainTx;
import org.eq.modules.bc.entity.BlockchainTxExample;

/**
 * 区块链任务Mapper接口
 * @author gb
 * @version 2019-06-03
 */
@Mapper
public interface BlockchainTxMapper extends BaseMapper<BlockchainTx,BlockchainTxExample> {
	
}
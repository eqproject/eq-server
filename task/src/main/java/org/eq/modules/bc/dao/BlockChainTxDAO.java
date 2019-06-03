package org.eq.modules.bc.dao;

import java.util.List;

import io.bumo.mall.talent.domain.BlockChainTX;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog= "dataSource" )
public interface BlockChainTxDAO {
	
	public final String COLUMN = " id,tx_hash,tx_initiator_address,tx_blob,tx_fee,tx_status,biz_type,bc_error_code,bc_error_msg,create_time,update_time ";
	
	@SQL("INSERT INTO blockchain_tx($COLUMN)"
			+ " VALUES (null,:1.txHash,:1.txInitiatorAddress,:1.txBlob,:1.txFee,:1.txStatus,:1.bizType,:1.bcErrorCode,:1.bcErrorMsg,NOW(),NOW())")
	public Boolean addBcTx(BlockChainTX blockChainTX);
	
	@SQL("select $COLUMN from blockchain_tx where tx_status=:1 order by create_time limit 0,100")
	public List<BlockChainTX> queryBcTX4Status(Integer txStatus);
	
	@SQL("update blockchain_tx set tx_status=:1,tx_fee=:2,bc_error_code=:4 where id=:3")
	public boolean updateBcTXStatus(Integer txStatus, String txFee, long id, Integer bcErrorCode);
	
}

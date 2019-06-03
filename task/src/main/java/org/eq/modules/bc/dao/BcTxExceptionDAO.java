package org.eq.modules.bc.dao;

import io.bumo.mall.talent.domain.BcTxException;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog= "dataSource" )
public interface BcTxExceptionDAO {
	
	public final String COLUMN = " id,tx_hash,action_status,create_time,update_time ";
	
	
	@SQL("INSERT INTO bc_tx_exception($COLUMN)"
			+ " VALUES (null,:1.txHash,:1.actionStatus,NOW(),NOW())")
	public long addBcTxException(BcTxException bcTxException);
	
}

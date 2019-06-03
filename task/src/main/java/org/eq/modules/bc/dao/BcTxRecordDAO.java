package org.eq.modules.bc.dao;

import java.util.List;

import io.bumo.mall.talent.domain.BcTxRecord;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog= "dataSource" )
public interface BcTxRecordDAO {
	
	public final String COLUMN = "id,from_address,to_address,transfer_amount,asset_code,asset_issuer,asset_decimal,tx_status,tx_hash,biz_type,opt_metadata,create_time,update_time ";
	
	@ReturnGeneratedKeys
	@SQL("INSERT INTO bc_tx_record($COLUMN)"
			+ " VALUES (null,:1.fromAddress,:1.toAddress,:1.transferAmount,:1.assetCode,:1.assetIssuer,:1.assetDecimal,:1.txStatus,:1.txHash,:1.bizType,:1.optMetadata,NOW(),NOW())")
	public Long addBcTxRecord(BcTxRecord bcTxRecord);
	
	@SQL("select $COLUMN from bc_tx_record where tx_status=:1 order by create_time limit 0,80")
	public List<BcTxRecord> queryBcTxRecord4Init(Integer txStatus);
	
	@SQL("update bc_tx_record set tx_hash=:1,tx_status=:2 where 1=1 ##(:sqlParam)")
	public int updateBcHashAndTxStatus(String hash, Integer status, @SQLParam("sqlParam") String sqlParam);
	
	@SQL("update bc_tx_record set tx_status=:2,update_time=now() where tx_hash=:1 ")
	public boolean updateTxStatusByHash(String hash, Integer status);
	
	@SQL("select * from bc_tx_record where tx_hash=:1 ")
	public List<BcTxRecord> queryBcTxRecordByHash(String hash);
	
	@SQL("select * from bc_tx_record where id=:1 ")
	public BcTxRecord queryBcTxRecordById(String id);
}

package org.eq.modules.bc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.bumo.mall.talent.dao.BcTxExceptionDAO;
import io.bumo.mall.talent.dao.BcTxRecordDAO;
import io.bumo.mall.talent.dao.BlockChainTxDAO;
import io.bumo.mall.talent.domain.BcTxException;
import io.bumo.mall.talent.domain.BlockChainTX;
import io.bumo.mall.talent.enums.BcStatusEnum;
import io.bumo.mall.talent.enums.BcTxExceptionStatusEnum;

public class BlockChainTxService {
	@Autowired
	private BlockChainTxDAO blockChainTxDAO;
	@Autowired
	private BcTxRecordDAO bcTxRecordDAO;
	@Autowired
	private BcTxExceptionDAO bcTxExceptionDAO;
	
	public void addBcTx(BlockChainTX blockChainTX){
		blockChainTxDAO.addBcTx(blockChainTX);
	}
	
	public List<BlockChainTX> queryBcTX4Status(Integer status){
		return blockChainTxDAO.queryBcTX4Status(status);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateSuccessBcTX(String hash, String txFee, long id, Integer errorCode) {
		blockChainTxDAO.updateBcTXStatus(BcStatusEnum.SUCCESS.getCode(), txFee, id, errorCode);
		bcTxRecordDAO.updateTxStatusByHash(hash, BcStatusEnum.SUCCESS.getCode());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateFailBcTX(String hash, String txFee, long id, Integer errorCode) {
		blockChainTxDAO.updateBcTXStatus(BcStatusEnum.FAIL.getCode(), txFee, id, errorCode);
		bcTxRecordDAO.updateTxStatusByHash(hash, BcStatusEnum.FAIL.getCode());
	}
	
	public void addBcTxException(String txHash){
		BcTxException bcTxException = new BcTxException();
		bcTxException.setActionStatus(BcTxExceptionStatusEnum.INIT.getCode());
		bcTxException.setTxHash(txHash);
		bcTxExceptionDAO.addBcTxException(bcTxException);
	}
	
}

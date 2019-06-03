package org.eq.modules.bc.service;

import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.eq.modules.bc.dao.BcTxExceptionMapper;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.dao.BlockchainTxMapper;
import org.eq.modules.bc.entity.*;
import org.eq.modules.bc.enums.BcStatusEnum;
import org.eq.modules.bc.enums.BcTxExceptionStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BlockChainTxService {
	private final BlockchainTxMapper blockchainTxMapper;
	private final BcTxRecordMapper bcTxRecordMapper;
	private final BcTxExceptionMapper bcTxExceptionMapper;
	
	public void addBcTx(BlockchainTx blockchainTx){
		Date now = new Date();
		blockchainTx.setCreateTime(now);
		blockchainTx.setUpdateTime(now);
		blockchainTxMapper.insert(blockchainTx);
	}
	
	public List<BlockchainTx> queryBcTX4Status(Integer status){
		BlockchainTxExample example = new BlockchainTxExample();
		BlockchainTxExample.Criteria c = example.or();
		c.andTxStatusEqualTo(status);
		example.setOrderByClause("create_time");
		PageHelper.startPage(0, 100);
		return blockchainTxMapper.selectByExample(example);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateSuccessBcTX(String hash, String txFee, long id, Integer errorCode) {
		updateBcTx(hash,txFee,id,errorCode,BcStatusEnum.SUCCESS);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateFailBcTX(String hash, String txFee, long id, Integer errorCode) {
		updateBcTx(hash,txFee,id,errorCode,BcStatusEnum.FAIL);
	}

	public void addBcTxException(String txHash){
		BcTxException bcTxException = new BcTxException();
		bcTxException.setActionStatus(BcTxExceptionStatusEnum.INIT.getCode());
		bcTxException.setTxHash(txHash);
		Date now = new Date();
		bcTxException.setCreateTime(now);
		bcTxException.setUpdateTime(now);
		bcTxExceptionMapper.insert(bcTxException);
	}

	private void updateBcTx(String hash, String txFee, long id, Integer errorCode,BcStatusEnum status){
		BlockchainTx tx = new BlockchainTx();
		tx.setTxStatus(status.getCode());
		tx.setTxFee(txFee);
		tx.setBcErrorCode(errorCode.toString());

		BlockchainTxExample example0 = new BlockchainTxExample();
		example0.or().andIdEqualTo(id);
		blockchainTxMapper.updateByExampleSelective(tx,example0);

		BcTxRecord record = new BcTxRecord();
		record.setTxStatus(status.getCode());
		record.setUpdateTime(new Date());

		BcTxRecordExample example1 = new BcTxRecordExample();
		example1.or().andTxHashEqualTo(hash);
		bcTxRecordMapper.updateByExampleSelective(record,example1);
	}
	
}

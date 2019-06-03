package org.eq.modules.bc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.bumo.mall.talent.dao.BcTxRecordDAO;
import io.bumo.mall.talent.domain.BcTxRecord;
import io.bumo.mall.talent.enums.BcStatusEnum;

public class BcTxService {
	
	@Autowired
	private BcTxRecordDAO bcTxRecordDAO;
	
	public long insertBcTxRecord(BcTxRecord bcTxRecord){
		return bcTxRecordDAO.addBcTxRecord(bcTxRecord);
	}
	
	public List<BcTxRecord> queryBcTxRecord4Init(){
		return bcTxRecordDAO.queryBcTxRecord4Init(BcStatusEnum.INIT.getCode());
	}
	
	public int updateBcTxRecord4BcProcess(List<BcTxRecord> recordList, String hash) {
		StringBuffer sb = new StringBuffer();
		sb.append(" and id in (");
		int length = recordList.size();
		for(int i=0;i<length;i++){
			BcTxRecord bcTxRecord = recordList.get(i);
			sb.append(bcTxRecord.getId());
			if(i!=length-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return bcTxRecordDAO.updateBcHashAndTxStatus(hash, BcStatusEnum.PROCESS.getCode(), sb.toString());
	}
	
	public List<BcTxRecord> queryBcTxRecord4Hash(String txHash){
		return bcTxRecordDAO.queryBcTxRecordByHash(txHash);
	}
	
	public BcTxRecord queryBcTxRecordById(String id){
		return bcTxRecordDAO.queryBcTxRecordById(id);
	}
	
}

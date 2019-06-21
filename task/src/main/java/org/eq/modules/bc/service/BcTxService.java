package org.eq.modules.bc.service;

import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BcTxRecordExample;
import org.eq.modules.bc.enums.BcStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BcTxService {
	private final BcTxRecordMapper bcTxRecordMapper;
	
	public long insertBcTxRecord(BcTxRecord bcTxRecord){
		Date now = new Date();
		bcTxRecord.setCreateTime(now);
		bcTxRecord.setUpdateTime(now);
		bcTxRecordMapper.insertReturnId(bcTxRecord);
		return bcTxRecord.getId();
	}
	
	public List<BcTxRecord> queryBcTxRecord4Init(){
		BcTxRecordExample example = new BcTxRecordExample();
		BcTxRecordExample.Criteria c = example.or();
		c.andTxStatusEqualTo(BcStatusEnum.INIT.getCode());
		example.setOrderByClause("create_time");
		PageHelper.startPage(0, 80);
		return bcTxRecordMapper.selectByExample(example);
	}
	
	public int updateBcTxRecord4BcProcess(List<BcTxRecord> recordList, String hash) {
		if(recordList==null || recordList.isEmpty()){
			return 0;
		}
		List<Long> ids = recordList.stream().map(o->o.getId()).collect(Collectors.toList());

		BcTxRecord record = new BcTxRecord();
		record.setTxHash(hash);
		record.setTxStatus(BcStatusEnum.PROCESS.getCode());

		BcTxRecordExample example = new BcTxRecordExample();
		BcTxRecordExample.Criteria c = example.or();
		c.andIdIn(ids);
		return bcTxRecordMapper.updateByExampleSelective(record,example);
	}
	
	public List<BcTxRecord> queryBcTxRecord4Hash(String txHash){
		BcTxRecordExample example = new BcTxRecordExample();
		BcTxRecordExample.Criteria c = example.or();
		c.andTxHashEqualTo(txHash);
		return bcTxRecordMapper.selectByExample(example);
	}
	
	public BcTxRecord queryBcTxRecordById(String id){
		return bcTxRecordMapper.selectByPrimaryKey(Long.parseLong(id));
	}
	
}

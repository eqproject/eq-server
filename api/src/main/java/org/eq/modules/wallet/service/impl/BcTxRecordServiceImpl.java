/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.wallet.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.bc.dao.BcTxRecordMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BcTxRecordExample;
import org.eq.modules.wallet.service.BcTxRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 激活钱包任务ServiceImpl
 * @author hobe
 * @version 2019-06-01
 */
@Service
@Transactional
@AutowiredService
public class BcTxRecordServiceImpl extends ServiceImplExtend<BcTxRecordMapper, BcTxRecord, BcTxRecordExample> implements BcTxRecordService {

	@Override
	public BcTxRecordExample getExampleFromEntity(BcTxRecord bcTxRecord, Map<String, Object> params) {
		BcTxRecordExample example = new BcTxRecordExample();
		BcTxRecordExample.Criteria ca = example.or();
		if(bcTxRecord==null){
			return example;
		}
		String orderName = null;
		String orderDir = null;
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
		}
		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		if(bcTxRecord.getId()!=null){
			ca.andIdEqualTo(bcTxRecord.getId());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getFromAddress())){
			ca.andFromAddressEqualTo(bcTxRecord.getFromAddress());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getToAddress())){
			ca.andToAddressEqualTo(bcTxRecord.getToAddress());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getTransferAmount())){
			ca.andTransferAmountEqualTo(bcTxRecord.getTransferAmount());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getAssetIssuer())){
			ca.andAssetIssuerEqualTo(bcTxRecord.getAssetIssuer());
		}
		if(bcTxRecord.getAssetType()!=null){
			ca.andAssetTypeEqualTo(bcTxRecord.getAssetType());
		}
		if(bcTxRecord.getTxStatus()!=null){
			ca.andTxStatusEqualTo(bcTxRecord.getTxStatus());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getTxHash())){
			ca.andTxHashEqualTo(bcTxRecord.getTxHash());
		}
		if(bcTxRecord.getBizType()!=null){
			ca.andBizTypeEqualTo(bcTxRecord.getBizType());
		}
		if(bcTxRecord.getCreateTime()!=null){
			ca.andCreateTimeEqualTo(bcTxRecord.getCreateTime());
		}
		if(bcTxRecord.getUpdateTime()!=null){
			ca.andUpdateTimeEqualTo(bcTxRecord.getUpdateTime());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getOptMetadata())){
			ca.andOptMetadataEqualTo(bcTxRecord.getOptMetadata());
		}
		if(StringLowUtils.isNotBlank(bcTxRecord.getInput())){
			ca.andInputEqualTo(bcTxRecord.getInput());
		}
		if(bcTxRecord.getTxType()!=null){
			ca.andTxTypeEqualTo(bcTxRecord.getTxType());
		}
		return example;
	}

}
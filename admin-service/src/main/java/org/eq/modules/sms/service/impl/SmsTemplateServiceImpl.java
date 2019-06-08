/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.sms.service.impl;

import org.eq.modules.sms.entity.SmsTemplate;
import org.eq.modules.sms.dao.SmsTemplateMapper;
import org.eq.modules.sms.entity.SmsTemplateExample;
import org.eq.modules.sms.service.SmsTemplateService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 短信ServiceImpl
 * @author hobe
 * @version 2019-06-08
 */
@Service
@Transactional
@AutowiredService
public class SmsTemplateServiceImpl extends ServiceImplExtend<SmsTemplateMapper, SmsTemplate, SmsTemplateExample> implements SmsTemplateService {

	@Override
	public SmsTemplateExample getExampleFromEntity(SmsTemplate smsTemplate, Map<String, Object> params) {
		SmsTemplateExample example = new SmsTemplateExample();
		SmsTemplateExample.Criteria ca = example.or();
		if(smsTemplate==null){
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
		if(smsTemplate.getId()!=null){
			ca.andIdEqualTo(smsTemplate.getId());
		}
		if(StringLowUtils.isNotBlank(smsTemplate.getCode())){
			ca.andCodeEqualTo(smsTemplate.getCode());
		}
		if(StringLowUtils.isNotBlank(smsTemplate.getName())){
			ca.andNameEqualTo(smsTemplate.getName());
		}
		if(smsTemplate.getLimitDay()!=null){
			ca.andLimitDayEqualTo(smsTemplate.getLimitDay());
		}
		if(smsTemplate.getType()!=null){
			ca.andTypeEqualTo(smsTemplate.getType());
		}
		if(StringLowUtils.isNotBlank(smsTemplate.getContent())){
			ca.andContentEqualTo(smsTemplate.getContent());
		}
		if(smsTemplate.getCreateBy()!=null){
			ca.andCreateByEqualTo(smsTemplate.getCreateBy());
		}
		if(smsTemplate.getCreateDate()!=null){
			ca.andCreateDateEqualTo(smsTemplate.getCreateDate());
		}
		if(smsTemplate.getUpdateBy()!=null){
			ca.andUpdateByEqualTo(smsTemplate.getUpdateBy());
		}
		if(smsTemplate.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(smsTemplate.getUpdateDate());
		}
		return example;
	}

}
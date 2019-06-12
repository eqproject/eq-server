/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.modules.enums.SystemConfigStateEnum;
import org.eq.modules.enums.SystemConfigTypeEnum;
import org.eq.modules.support.dao.SystemConfigMapper;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.entity.SystemConfigExample;
import org.eq.modules.support.service.SystemConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统配置ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@SuppressWarnings("all")
@Service
@Transactional
@AutowiredService
public class SystemConfigServiceImpl extends ServiceImplExtend<SystemConfigMapper, SystemConfig, SystemConfigExample> implements SystemConfigService {

	@Override
	public SystemConfigExample getExampleFromEntity(SystemConfig systemConfig, Map<String, Object> params) {
		SystemConfigExample example = new SystemConfigExample();
		SystemConfigExample.Criteria ca = example.or();
		if(systemConfig==null){
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
		if(systemConfig.getId()!=null){
			ca.andIdEqualTo(systemConfig.getId());
		}
		if(systemConfig.getType()!=null){
			ca.andTypeEqualTo(systemConfig.getType());
		}
		if(systemConfig.getState()!=null){
			ca.andStateEqualTo(systemConfig.getState());
		}
		return example;
	}

	@Override
	public SystemConfig getSystemConfigByType(int type) {
		String configRemark = SystemConfigTypeEnum.getRemarkByState(type);
		if(StringUtils.isEmpty(configRemark)){
			return  null;
		}
		SystemConfigExample example = new SystemConfigExample();
		SystemConfigExample.Criteria ca = example.or();
		ca.andTypeEqualTo(type);
		ca.andStateEqualTo(SystemConfigStateEnum.DEFAULT.getState());
		List<SystemConfig> result = findListByExample(example);
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		return result.get(0);
	}
}
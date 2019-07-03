/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;

import org.eq.modules.auth.entity.UserClientWhitelist;
import org.eq.modules.auth.dao.UserClientWhitelistMapper;
import org.eq.modules.auth.entity.UserClientWhitelistExample;
import org.eq.modules.auth.service.UserClientWhitelistService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 用户白名单ServiceImpl
 * @author hobe
 * @version 2019-07-03
 */
@Service
@Transactional
@AutowiredService
public class UserClientWhitelistServiceImpl extends ServiceImplExtend<UserClientWhitelistMapper, UserClientWhitelist, UserClientWhitelistExample> implements UserClientWhitelistService {

	@Override
	public UserClientWhitelistExample getExampleFromEntity(UserClientWhitelist userClientWhitelist, Map<String, Object> params) {
		UserClientWhitelistExample example = new UserClientWhitelistExample();
		UserClientWhitelistExample.Criteria ca = example.or();
		if(userClientWhitelist==null){
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
		if(userClientWhitelist.getId()!=null){
			ca.andIdEqualTo(userClientWhitelist.getId());
		}
		if(StringLowUtils.isNotBlank(userClientWhitelist.getMobile())){
			ca.andMobileEqualTo(userClientWhitelist.getMobile());
		}
		if(userClientWhitelist.getStatus()!=null){
			ca.andStatusEqualTo(userClientWhitelist.getStatus());
		}
		if(userClientWhitelist.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userClientWhitelist.getCreateDate());
		}
		return example;
	}

}
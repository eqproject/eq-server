/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;

import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.auth.dao.UserIdentityAuthMapper;
import org.eq.modules.auth.entity.UserIdentityAuthExample;
import org.eq.modules.auth.service.UserIdentityAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 实名认证ServiceImpl
 * @author hobe
 * @version 2019-05-31
 */
@Service
@Transactional
@AutowiredService
public class UserIdentityAuthServiceImpl extends ServiceImplExtend<UserIdentityAuthMapper, UserIdentityAuth, UserIdentityAuthExample> implements UserIdentityAuthService {

	public UserIdentityAuthServiceImpl(UserIdentityAuthMapper mapper){
		super.setMapper(mapper);
	}

	@Override
	public UserIdentityAuthExample getExampleFromEntity(UserIdentityAuth userIdentityAuth, Map<String, Object> params) {
		UserIdentityAuthExample example = new UserIdentityAuthExample();
		UserIdentityAuthExample.Criteria ca = example.or();
		if(userIdentityAuth==null){
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
		if(userIdentityAuth.getId()!=null){
			ca.andIdEqualTo(userIdentityAuth.getId());
		}
		if(StringLowUtils.isNotBlank(userIdentityAuth.getIdentityCard())){
			ca.andIdentityCardEqualTo(userIdentityAuth.getIdentityCard());
		}
		if(StringLowUtils.isNotBlank(userIdentityAuth.getIdentityName())){
			ca.andIdentityNameEqualTo(userIdentityAuth.getIdentityName());
		}
		if(userIdentityAuth.getUserId()!=null){
			ca.andUserIdEqualTo(userIdentityAuth.getUserId());
		}
		if(userIdentityAuth.getResultStatus()!=null){
			ca.andResultStatusEqualTo(userIdentityAuth.getResultStatus());
		}
		if(StringLowUtils.isNotBlank(userIdentityAuth.getResultMsg())){
			ca.andResultMsgEqualTo(userIdentityAuth.getResultMsg());
		}
		if(userIdentityAuth.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userIdentityAuth.getCreateDate());
		}
		if(userIdentityAuth.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(userIdentityAuth.getUpdateDate());
		}
		return example;
	}

}
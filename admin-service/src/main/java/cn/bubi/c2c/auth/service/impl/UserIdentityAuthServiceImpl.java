/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.auth.service.impl;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.StringLowUtils;
import cn.bubi.c2c.auth.dao.UserIdentityAuthMapper;
import cn.bubi.c2c.auth.entity.UserIdentityAuth;
import cn.bubi.c2c.auth.entity.UserIdentityAuthExample;
import cn.bubi.c2c.auth.service.UserIdentityAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 实名认证记录ServiceImpl
 * @author bo.gao
 * @version 1.0.0
 */
@Service
@Transactional
@AutowiredService
public class UserIdentityAuthServiceImpl extends ServiceImplExtend<UserIdentityAuthMapper, UserIdentityAuth, UserIdentityAuthExample> implements UserIdentityAuthService {

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
		if(StringLowUtils.isNotBlank(userIdentityAuth.getAuthResult())){
			ca.andAuthResultEqualTo(userIdentityAuth.getAuthResult());
		}
		if(userIdentityAuth.getCreateBy()!=null){
			ca.andCreateByEqualTo(userIdentityAuth.getCreateBy());
		}
		if(userIdentityAuth.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userIdentityAuth.getCreateDate());
		}
		return example;
	}

}
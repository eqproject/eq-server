/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;

import org.eq.modules.auth.entity.UserAccountBind;
import org.eq.modules.auth.dao.UserAccountBindMapper;
import org.eq.modules.auth.entity.UserAccountBindExample;
import org.eq.modules.auth.service.UserAccountBindService;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;

import java.util.Map;

/**
 * 支付账号绑定ServiceImpl
 * @author hobe
 * @version 2019-06-13
 */
@Service
@Transactional
@AutowiredService
public class UserAccountBindServiceImpl extends ServiceImplExtend<UserAccountBindMapper, UserAccountBind, UserAccountBindExample> implements UserAccountBindService {

	@Override
	public UserAccountBindExample getExampleFromEntity(UserAccountBind userAccountBind, Map<String, Object> params) {
		UserAccountBindExample example = new UserAccountBindExample();
		UserAccountBindExample.Criteria ca = example.or();
		if(userAccountBind==null){
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
		if(userAccountBind.getId()!=null){
			ca.andIdEqualTo(userAccountBind.getId());
		}
		if(userAccountBind.getType()!=null){
			ca.andTypeEqualTo(userAccountBind.getType());
		}
		if(userAccountBind.getUserId()!=null){
			ca.andUserIdEqualTo(userAccountBind.getUserId());
		}
		if(StringLowUtils.isNotBlank(userAccountBind.getPayLoginId())){
			ca.andPayLoginIdEqualTo(userAccountBind.getPayLoginId());
		}
		if(StringLowUtils.isNotBlank(userAccountBind.getIdentityNo())){
			ca.andIdentityNoEqualTo(userAccountBind.getIdentityNo());
		}
		if(userAccountBind.getCreateDate()!=null){
			ca.andCreateDateEqualTo(userAccountBind.getCreateDate());
		}
		if(userAccountBind.getUpdateDate()!=null){
			ca.andUpdateDateEqualTo(userAccountBind.getUpdateDate());
		}
		if(userAccountBind.getStatus()!=null){
			ca.andStatusEqualTo(userAccountBind.getStatus());
		}
		if(userAccountBind.getDefaultReceip()!=null){
			ca.andDefaultReceipEqualTo(userAccountBind.getDefaultReceip());
		}
		return example;
	}

}
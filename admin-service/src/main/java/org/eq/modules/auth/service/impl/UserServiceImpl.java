/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;


import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.dao.UserMapper;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;
import org.eq.modules.auth.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;

import java.util.Map;

/**
 * 用户管理ServiceImpl
 * @author kaka
 * @version 1.0.
 */
@Service
@Transactional
@AutowiredService
public class UserServiceImpl extends ServiceImplExtend<UserMapper, User, UserExample> implements UserService {

	@Override
	public UserExample getExampleFromEntity(User user, Map<String, Object> params) {
		UserExample example = new UserExample();
		UserExample.Criteria ca = example.or();
		if(user==null){
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
		if(StringLowUtils.isNotBlank(user.getNickname())){
			ca.andNicknameLike(user.getNickname());
		}

		if(StringLowUtils.isNotBlank(user.getMobile())){
			ca.andMobileEqualTo(user.getMobile());
		}

		return example;
	}


	@Override
	public int updateUserDelFlagById(User user, Integer oldDelFlag) {
		UserExample example = new UserExample();
		UserExample.Criteria ca = example.or();
		if(user==null){
			return 0;
		}

		ca.andDelFlagEqualTo(oldDelFlag);
		return this.updateByExampleSelective(user,example);
	}
}
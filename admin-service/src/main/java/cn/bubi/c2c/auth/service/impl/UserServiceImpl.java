/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.auth.service.impl;


import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.StringLowUtils;
import cn.bubi.c2c.auth.dao.UserMapper;
import cn.bubi.c2c.auth.entity.User;
import cn.bubi.c2c.auth.entity.UserExample;
import cn.bubi.c2c.auth.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.bubi.basic.common.annotation.AutowiredService;

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
		if(user.getId()!=null){
			ca.andIdEqualTo(user.getId());
		}
		if(StringLowUtils.isNotBlank(user.getNickname())){
			ca.andNicknameLike(user.getNickname());
		}

		if(StringLowUtils.isNotBlank(user.getMobile())){
			ca.andMobileEqualTo(user.getMobile());
		}

		if(user.getStatus()!=null){
			ca.andStatusEqualTo(user.getStatus());
		}
		if(user.getDelFlag()!=null){
			ca.andDelFlagEqualTo(user.getDelFlag());
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

		ca.andIdEqualTo(user.getId());
		ca.andDelFlagEqualTo(oldDelFlag);
		return this.updateByExampleSelective(user,example);
	}
}
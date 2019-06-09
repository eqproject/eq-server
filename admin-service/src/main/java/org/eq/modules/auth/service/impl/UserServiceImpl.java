/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.dao.UserMapper;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;
import org.eq.modules.auth.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eq.basic.common.annotation.AutowiredService;

import java.util.Date;
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
		if(user.getDelFlag()!=null){
			ca.andDelFlagEqualTo(user.getDelFlag());
		}
		if(user.getAuthStatus()!=null){
			ca.andAuthStatusEqualTo(user.getAuthStatus());
		}

		return example;
	}


	@Override
	public int updateUserDelFlagById(long userId,Integer newDelFalg,Integer oldDelFlag) {
		if(userId<=0 || newDelFalg ==null  || oldDelFlag ==null ){
			return 0;
		}
		UserExample example = new UserExample();
		UserExample.Criteria ca = example.or();
		ca.andIdEqualTo(userId);
		ca.andDelFlagEqualTo(oldDelFlag);

		User updateUser = new User();
		updateUser.setUpdateDate(new Date());
		updateUser.setDelFlag(newDelFalg);
		StringBuffer remarkBuffer = new StringBuffer(DateUtil.getNowTimeStr()+"变更用户状态为:");
		if(BaseEntity.DEL_FLAG_NORMAL.equals(String.valueOf(newDelFalg))){
			remarkBuffer.append("启用状态");
		}else{
			remarkBuffer.append("禁用状态");
		}
		updateUser.setRemarks(remarkBuffer.toString());
		return this.updateByExampleSelective(updateUser,example);
	}
}
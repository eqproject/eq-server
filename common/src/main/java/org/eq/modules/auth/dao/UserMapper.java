/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.dao;

import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理Mapper接口
 * @author kaka
 * @version 1.0.
 */
@Mapper
public interface UserMapper extends BaseMapper<User,UserExample> {
	
}
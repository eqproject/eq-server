/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.auth.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.auth.entity.User;
import cn.bubi.c2c.auth.entity.UserExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理Mapper接口
 * @author kaka
 * @version 1.0.
 */
@Mapper
public interface UserMapper extends BaseMapper<User,UserExample> {
	
}
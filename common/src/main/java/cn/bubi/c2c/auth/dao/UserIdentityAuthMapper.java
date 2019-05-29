/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.auth.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.auth.entity.UserIdentityAuth;
import cn.bubi.c2c.auth.entity.UserIdentityAuthExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实名认证记录Mapper接口
 * @author bo.gao
 * @version 1.0.0
 */
@Mapper
public interface UserIdentityAuthMapper extends BaseMapper<UserIdentityAuth,UserIdentityAuthExample> {
	
}
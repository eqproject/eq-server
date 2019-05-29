/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.dao;

import java.util.List;

import cn.bubi.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.entity.SysUserExample;

/**
 * 用户表Mapper接口
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser, SysUserExample> {

    /**
     * 根据条件查询登录和用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> findUserList(@Param("sysUser") SysUser sysUser);
}
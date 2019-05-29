/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.entity.SysUserExample;
import cn.bubi.basic.modules.sys.security.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysUserService extends ServiceExtend<SysUser, SysUserExample> {

    /**
     * 根据名字 精确查找用户
     *
     * @param username
     * @return
     */
    SysUser findUserByNmae(String username);

    /**
     * 根据名字 查找用户所有信息
     *
     * @param username
     * @return
     */
    UserInfo findUserInfo(String username);

    /**
     * 插入用户信息
     *
     * @param sysUser
     * @return
     */
    boolean insertUser(SysUser sysUser);

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    boolean updateUser(SysUser sysUser);

    /**
     * 删除用户信息
     *
     * @param ids
     * @param virtual
     * @return
     */
    int deleteUser(String ids, String virtual);

    /**
     * 权限控制下 用户信息的查询
     *
     * @param sysUser
     * @param params
     * @return
     */
    BaseTableData findDataTableByRecordForPagePermission(SysUser sysUser, Map<String, Object> params);

    /**
     * 查询用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectUser(SysUser sysUser);
}
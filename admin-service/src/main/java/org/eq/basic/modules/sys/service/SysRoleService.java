/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysRole;
import org.eq.basic.modules.sys.entity.SysRoleExample;

/**
 * 角色表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysRoleService extends ServiceExtend<SysRole, SysRoleExample> {

    /**
     * 插入角色 角色菜单
     *
     * @param sysRole
     * @return
     */
    boolean insertRole(SysRole sysRole);

    /**
     * 修改角色 角色菜单
     *
     * @param sysRole
     * @return
     */
    boolean updateRole(SysRole sysRole);

    /**
     * 删除角色 角色菜单
     *
     * @param ids
     * @param virtual
     * @return
     */
    int deleteRole(String ids, String virtual);

    /**
     * 权限控制下的 角色类型
     *
     * @return
     */
    List<SysDict> getRoleTypePermission(List<Integer> typeLongs);

    /**
     * 权限控制下的 角色分页查询
     *
     * @param sysRole
     * @param params
     * @return
     */
    BaseTableData findDataTableByRecordForPagePermission(SysRole sysRole, Map<String, Object> params);

    /**
     * 权限控制下的角色查询
     *
     * @param type
     *            权限控制下的方式 默认0 登录者角色类型之下（含）
     * @return
     */
    List<SysRole> findRolePermission(Integer type);
}
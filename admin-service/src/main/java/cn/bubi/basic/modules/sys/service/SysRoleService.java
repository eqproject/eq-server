/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import java.util.List;
import java.util.Map;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.entity.SysRole;
import cn.bubi.basic.modules.sys.entity.SysRoleExample;

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
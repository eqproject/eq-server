/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import java.util.List;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysMenu;
import org.eq.basic.modules.sys.entity.SysMenuExample;
import org.eq.basic.modules.sys.security.UserInfo;

/**
 * 菜单表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysMenuService extends ServiceExtend<SysMenu, SysMenuExample> {

    /**
     * 根据登录人信息 按权限查询
     *
     * @param userInfo
     * @param sysMenu
     * @return
     */
    List<SysMenu> findListPermission(UserInfo userInfo, SysMenu sysMenu);
}
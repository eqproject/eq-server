/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysAreaExample;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.security.UserInfo;

import java.util.List;

/**
 * 地区表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysAreaService extends ServiceExtend<SysArea, SysAreaExample> {

    /**
     * 权限控制下 地区查询
     *
     * @param userInfo
     * @param sysArea
     * @return
     */
    List<SysArea> findListPermission(UserInfo userInfo, SysArea sysArea);

    /**
     * 权限控制下的 地区类型
     *
     * @param typeLongs
     * @return
     */
    List<SysDict> getAreaTypePermission(List<Integer> typeLongs);
}
/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.sys.entity.SysArea;
import cn.bubi.basic.modules.sys.entity.SysAreaExample;
import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.security.UserInfo;

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
/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import java.util.List;

import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.sys.entity.SysOffice;
import cn.bubi.basic.modules.sys.entity.SysOfficeExample;
import cn.bubi.basic.modules.sys.security.UserInfo;

/**
 * 机构表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysOfficeService extends ServiceExtend<SysOffice, SysOfficeExample> {

    /**
     * 权限控制下 机构查询
     *
     * @param userInfo
     * @param sysOffice
     * @return
     */
    List<SysOffice> findListPermission(UserInfo userInfo, SysOffice sysOffice);
}
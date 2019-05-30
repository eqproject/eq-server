/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import java.util.List;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysOfficeExample;
import org.eq.basic.modules.sys.security.UserInfo;

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